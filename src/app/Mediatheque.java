package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import documents.DVD;
import mediatheque.Abonne;
import mediatheque.Document;
import tasks.VerifyReservation;

public class Mediatheque {
	
	private HashMap<Integer, Document> supports ;
	private HashMap<Integer, Abonne> clients ;
	private List<Document> documents;
	
	private static final int RETARD_MAX = 14; 
	private static final int DUREE_BANNISSEMMENT = 30; 
	private static final int DELAI_RESERVATION = 7200000;
	
	private MediathequeServer server; 
	
	
	public Mediatheque() {
		this.clients  = new HashMap<>();
		this.supports = new HashMap<>(); 
		this.documents = new ArrayList<>();
		
		try {
			this.server = new MediathequeServer(this);
			new Thread(server).start();
		} 
		catch (IOException e) {
			System.out.println("Impossible de créer le serveur");
		}
	}
	
	public void retour (int numeroDocument) {
		synchronized(this) {
			if (supports.containsKey(Integer.valueOf(numeroDocument))) {
				Document d =  supports.get(Integer.valueOf(numeroDocument));
				d.retour();
		}
		}
	}
	
	
	public void emprunter(int numeroClient, int numeroDocument) {
		synchronized(this) {
			if (checkData(numeroClient, numeroDocument)) {
				Document d = supports.get(numeroDocument);
				Abonne a = clients.get(numeroClient);
				if (d.reserveur() == null || d.reserveur() == a)
					d.empruntPar(a);	
			}
		}

	}
	
	
	public void reserver (int abonne, int document ) {
		boolean reserve = false;
		Document d =null;
		synchronized(this) {
			if (checkData(abonne, document) && estDisponible(document)) {
				d = supports.get(Integer.valueOf(document));
				d.reservationPour(clients.get(Integer.valueOf(abonne)));
				reserve =true;
			}		
		}	
		
		if (reserve) { //on met cette condition pour pouvoir sortir la création du Timer du synchronized
			Timer timer = new Timer();
			TimerTask emprunt = new VerifyReservation(d);
			timer.schedule(emprunt, DELAI_RESERVATION);
		}
	}
	
	public boolean estDisponible(int numeroDocument) {
		synchronized(this) {
			Document d = supports.get(Integer.valueOf(numeroDocument));
			return d.emprunteur() == null && d.reserveur() == null;			
		}

	}
	
	
	public List<String> getDocumentsDisponibles(){
		synchronized(this) {
			List<String> list = new ArrayList<>();
			
			for (Document d : documents) {
				if (d.emprunteur() == null && d.reserveur() == null)
					list.add(d.toString());
			}
			return list;
		}
		
		
	}
	
	public boolean checkData(int abonne, int document) {
		
		synchronized(this) {
			return clients.containsKey(Integer.valueOf(abonne)) && supports.containsKey(Integer.valueOf(document));
		}
		
	}
}
