package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import app.Mediatheque;


public class ReservationService extends LibraryService {

	
	public ReservationService(Socket socket, Mediatheque m) {
		super(socket, m);
	}
	
	@Override
	public void run() {
		
		List<String> list = mediatheque.getDocumentsDisponibles();
		
		try {	    
		    PrintWriter out = new PrintWriter (socket().getOutputStream ( ), true);
		    
		    for (String s : list)
		    	out.println(s);
		    
		    BufferedReader in = new BufferedReader (new InputStreamReader(socket().getInputStream()));
		    
		    try {
		    	String reponse ="Le document a bien été réservé";
		    	int numeroAbonne = Integer.parseInt(in.readLine());
		    	int numeroDocument = Integer.parseInt(in.readLine());
		    	
		    	
		    	if (mediatheque.checkData(numeroAbonne, numeroDocument))
		    		if (mediatheque.estDisponible(numeroDocument))
		    				mediatheque.reserver(numeroAbonne, numeroDocument);
		    		else
		    			reponse = "Document indisponible";
		    	else
		    		reponse = "Numéro de document ou d'abonné invalide";
		    	
		    
		    	out.println(reponse);
		    	socket().close();
		 
		    }
		    
		    catch (NumberFormatException e) {
		    	System.out.println("Données invalides");
		    	socket().close();
		    }
		    
		} catch (IOException e) {
			
			try {
				socket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    // Handle the exception
		}
		
		
	}
	
}
