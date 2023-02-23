package app;

import java.util.HashMap;

import mediatheque.Client;
import mediatheque.Support;
import supports.DVD;

public class Mediatheque {
	
	private HashMap<Integer, Support> supports = new HashMap<>();
	private HashMap<Integer, Client> clients = new HashMap<>();
	
	private static final int RETARD_MAX = 14; 
	private static final int DUREE_BANNISSEMMENT = 30; 
	
	public static void main(String[] args) {
		DVD dvd = new DVD();

	}
	
	private void emprunter(int numeroSupport, int numeroClient) {
		supports.get(numeroSupport).emprunter(clients.get(numeroClient));
	}

}
