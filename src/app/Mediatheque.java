package app;

import java.io.IOException;
import java.util.HashMap;

import documents.DVD;
import mediatheque.Abonne;
import mediatheque.Document;

public class Mediatheque {
	
	private HashMap<Integer, Document> supports = new HashMap<>();
	private HashMap<Integer, Abonne> clients = new HashMap<>();
	
	private final int PORT_RESERVATIONS = 3000, PORT_EMPRUNTS = 4000, PORT_RETOURS = 5000;
	private final int RETARD_MAX = 14, DUREE_BANNISSEMMENT = 30; 
	private MediathequeServer server;
	
	public Mediatheque() {
		try {
			this.server = new MediathequeServer(this, PORT_RESERVATIONS, PORT_EMPRUNTS, PORT_RETOURS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void main(String[] args) {
		
		DVD dvd = new DVD("got", 1, true);
		this.server.run();

	}
	
	public boolean disponible(int numeroDocument) {
		if (supports.get(numeroDocument).)
	}
	
	public void emprunter(int numeroSupport, int numeroClient) {
		supports.get(numeroSupport).empruntPar(clients.get(numeroClient));
	}

}
