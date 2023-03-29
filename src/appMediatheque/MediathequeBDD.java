package appMediatheque;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import documents.DVD;
import documents.Livre;
import mediatheque.Abonne;
import mediatheque.Document;

public class MediathequeBDD {
	private Connection connection;
	private final int TYPE_DVD=1, TYPE_LIVRE=2;

	public MediathequeBDD() throws SQLException {
		this.connection = connect();
	}

	public Map<Integer, Abonne> abonnes() throws SQLException {
		Map<Integer, Abonne> abonnes = new HashMap<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM abonnes");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int numero = rs.getInt("Numero");
			String nom = rs.getString("Nom");
			Date date = rs.getDate("DateDN");
			int nbJours = rs.getInt("NbJoursBannis");

			Abonne abonne = new Abonne(numero, nom, date);
			abonne.bannir(nbJours);
			abonnes.put(Integer.valueOf(numero), abonne);
		}

		return abonnes;
	}

	public Map<Integer, Document> documents(Map<Integer, Abonne> abonnes) throws SQLException {
		Map<Integer, Document> documents = new HashMap<>();

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM document");
		ResultSet rs = stmt.executeQuery();
		Document d;

		while (rs.next()) {
			int numero = rs.getInt("numero");
			String titre = rs.getString("titre");
			Abonne reserveur = abonnes.get(rs.getInt("reserveur"));
			Abonne emprunteur = abonnes.get(rs.getInt("emprunteur"));
			int type = rs.getInt("type");
			
			switch(type) {
			case TYPE_DVD:
				boolean adulte = rs.getBoolean("adulte");
				d = new DVD(numero, titre, reserveur, emprunteur, adulte);
				documents.put(Integer.valueOf(d.numero()), d);
			
			case TYPE_LIVRE:
				String maisonEdition = rs.getString("MaisonEdition");
				d = new Livre(numero, titre, reserveur, emprunteur, maisonEdition);
				documents.put(Integer.valueOf(d.numero()), d);
				
			}
		}

		return documents;
	}

	public Map<Document, Date> reservations(Map<Integer, Document> documents) throws SQLException {
		Map<Document, Date> reservations = new HashMap<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reservations");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int numeroDocument = rs.getInt("NumeroReservation");
			Date date = rs.getDate("DateReservation");
			reservations.put(documents.get(Integer.valueOf(numeroDocument)), date);
		}

		return reservations;
	}

	public Map<Document, Date> emprunts(Map<Integer, Document> documents) throws SQLException {
		Map<Document, Date> emprunts = new HashMap<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM emprunts");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int numeroDocument = rs.getInt("NumeroEmprunt");
			Date date = rs.getDate("DateEmprunt");
			emprunts.put(documents.get(Integer.valueOf(numeroDocument)), date);
		}

		return emprunts;
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
