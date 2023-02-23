package app;

import java.util.HashMap;

import documents.DVD;
import mediatheque.Abonne;
import mediatheque.Document;

public class Mediatheque {
	
	private HashMap<Integer, Document> supports = new HashMap<>();
	private HashMap<Integer, Abonne> clients = new HashMap<>();
	
	private static final int RETARD_MAX = 14; 
	private static final int DUREE_BANNISSEMMENT = 30; 
	
	public static void main(String[] args) {
		DVD dvd = new DVD("got", 1, true);

	}
	
	private void emprunter(int numeroSupport, int numeroClient) {
		supports.get(numeroSupport).empruntPar(clients.get(numeroClient));
	}

}
