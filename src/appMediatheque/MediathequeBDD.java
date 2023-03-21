package appMediatheque;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import documents.DVD;
import mediatheque.Abonne;
import mediatheque.Document;

public class MediathequeBDD {
	private Connection connection;

	public MediathequeBDD() throws SQLException {
		this.connection = connect();
	}

	public Map<Integer, Abonne> abonnes() throws SQLException {
		Map<Integer, Abonne> abonnes = new HashMap<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM abonnes");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int numero = rs.getInt("numero");
			String nom = rs.getString("nom");
			Date date = rs.getDate("date");

			Abonne abonne = new Abonne(numero, nom, date);
			abonnes.put(Integer.valueOf(numero), abonne);
		}

		return abonnes;
	}

	//pas finie
	public Map<Integer, Document> documents(Map<Integer, Abonne> abonnes, List<Class<? extends Document>> TYPES)
			throws SQLException {
		Map<Integer, Document> documents = new HashMap<>();

		for (Class<? extends Document> type : TYPES) {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM" + type);
			ResultSet rs = stmt.executeQuery();
			List<Integer> entiers = new ArrayList<>();
			String titre="";
			boolean adulte=false;

			while (rs.next()) {

				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = rs.getObject(fieldName);

					if (value instanceof Integer) {
						entiers.add((Integer) value);

					} else if (value instanceof String) {
						titre = (String) value;

					} else if (value instanceof Boolean) {
						adulte = (Boolean) value;
					}
				}
			}
			
			Class<?> clazz;
			try {
				clazz = Class.forName(type.toString());
				Object instance = clazz.getDeclaredConstructor().newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			} 
			
		

			Document document = new DVD(entiers.get(0), titre, abonnes.get(entiers.get(1)), abonnes.get(entiers.get(2)),adulte);
			documents.put(entiers.get(0), document);
		}
		
		return documents;

	}
	
	
	public Map<Document, Date> reservations(Map<Integer, Document> documents) throws SQLException {
		Map<Document, Date>  reservations = new HashMap<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservations");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int numeroDocument = rs.getInt("numero");
			Date date = rs.getDate("date");

			reservations.put(documents.get(Integer.valueOf(numeroDocument)), date);
		}

		return reservations;
	}

private Connection connect() throws SQLException {
	// ces variables pourraient être passées en paramètre à la creation pour rendre
	// la classe générique
	String url = "jdbc:mysql://localhost:3306/library";
	String user = "brette";
	String password = "20sur20";
	return DriverManager.getConnection(url, user, password);
}

}
