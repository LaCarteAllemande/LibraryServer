package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import documents.DVD;
import mediatheque.Abonne;
import mediatheque.Document;

public class Mediatheque {
	
	private HashMap<Integer, Document> supports ;
	private HashMap<Integer, Abonne> clients ;
	private List<Document> documents;
	
	private static final int RETARD_MAX = 14; 
	private static final int DUREE_BANNISSEMMENT = 30; 
	
	private MediathequeServer server; 
	
	
	public Mediatheque() {
		this.clients  = new HashMap<>();
		this.supports = new HashMap<>(); 
		this.documents = new ArrayList<>();
		
		try {
			this.server = new MediathequeServer(this);
		} 
		catch (IOException e) {
			
		}
	}
	
	
	public static void main(String[] args) {
		DVD dvd = new DVD("got", 1, true);
		
	}
	
	private void emprunter(int numeroSupport, int numeroClient) {
		supports.get(numeroSupport).empruntPar(clients.get(numeroClient));
	}
	
	public void reserver (int abonne, int document ) {
		
	}
	
	public boolean estDisponible(int numeroDocument) {
		Document d = supports.get(Integer.valueOf(numeroDocument));
		return d.emprunteur() == null && d.reserveur() == null;
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
		
		return clients.containsKey(Integer.valueOf(abonne)) && supports.containsKey(Integer.valueOf(document));
	}
}
