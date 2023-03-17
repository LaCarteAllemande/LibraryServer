package app;

import java.io.IOException;
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
import java.util.Timer;
import java.util.TimerTask;

import documents.DVD;
import ex.RestrictionException;
import mediatheque.Abonne;
import mediatheque.Document;
import tasks.VerifyReservation;

public class Mediatheque {

	private Map<Integer, Document> supports;
	private Map<Integer, Abonne> abonnes;
	private List<Document> documents;

	private static final int RETARD_MAX = 14;
	private static final int DUREE_BANNISSEMMENT = 30;
	private static final int DELAI_RESERVATION = 7200000;

	private MediathequeServer server;

	public Mediatheque() {
		this.abonnes = new HashMap<>();
		this.supports = new HashMap<>();
		this.documents = new ArrayList<>();

		try {
			initAbonnes();
			initDocuments();
			try {
				this.server = new MediathequeServer(this);
				new Thread(server).start();
			} catch (IOException e) {
				System.out.println("Impossible de créer le serveur");
			}
		} catch (SQLException e1) {
			System.out.println("Impossible d'accéder à la base de données");
		}

	}

	private void initAbonnes() throws SQLException {
		Connection conn = connectDB();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM abonnes");
		ResultSet rs = stmt.executeQuery(); 

		while (rs.next()) {
			int numero = rs.getInt("numero");
			String nom = rs.getString("nom");
			Date date = rs.getDate("date");

			Abonne abonne = new Abonne(numero, nom, date);
			abonnes.put(Integer.valueOf(numero), abonne);
		}	
	}

	public void retour(int numeroDocument) {
		synchronized (this) {
			if (supports.containsKey(Integer.valueOf(numeroDocument))) {
				Document d = supports.get(Integer.valueOf(numeroDocument));
				d.retour();
			}
		}
	}

	private Connection connectDB() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/library";
		String user = "username";
		String password = "password";
		return DriverManager.getConnection(url, user, password);
	}
	

	private void initDocuments() throws SQLException {

		Connection conn = connectDB();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM dvd");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int numero = rs.getInt("numero");
			String titre = rs.getString("titre");
			int emprunteur = rs.getInt("emprunteur");
			int reserveur = rs.getInt("reserveur");
			boolean adulte = rs.getBoolean("adult");

			Document document = new DVD(numero, titre, abonnes.get(Integer.valueOf(emprunteur)), abonnes.get(Integer.valueOf(reserveur)), adulte);
			documents.add(document);
		}
	}

	public void emprunter(int numeroClient, int numeroDocument) throws RestrictionException {
		synchronized (this) {
			if (checkData(numeroClient, numeroDocument)) {
				Document d = supports.get(numeroDocument);
				Abonne a = abonnes.get(numeroClient);
				if (d.reserveur() == null || d.reserveur() == a)
					d.empruntPar(a);
			}
		}

	}

	public void reserver(int abonne, int document) throws RestrictionException {
		boolean reserve = false;
		Document d = null;
		synchronized (this) {
			if (checkData(abonne, document) && estDisponible(document)) {
				d = supports.get(Integer.valueOf(document));
				d.reservationPour(abonnes.get(Integer.valueOf(abonne)));
				reserve = true;
			}
		}

		if (reserve) { // on met cette condition pour pouvoir sortir la création du Timer du
			// synchronized
			Timer timer = new Timer();
			TimerTask emprunt = new VerifyReservation(d);
			timer.schedule(emprunt, DELAI_RESERVATION);
		}
	}

	public boolean estDisponible(int numeroDocument) {
		synchronized (this) {
			Document d = supports.get(Integer.valueOf(numeroDocument));
			return d.emprunteur() == null && d.reserveur() == null;
		}
	}

	public List<String> getDocumentsDisponibles() {
		synchronized (this) {
			List<String> list = new ArrayList<>();

			for (Document d : documents) {
				if (d.emprunteur() == null && d.reserveur() == null)
					list.add(d.toString());
			}
			return list;
		}

	}
	
	public boolean documentExists(int numero) {
		return supports.containsKey(Integer.valueOf(numero));
	}

	public boolean checkData(int abonne, int document) {

		synchronized (this) {
			return abonnes.containsKey(Integer.valueOf(abonne)) && supports.containsKey(Integer.valueOf(document));
		}

	}
}
