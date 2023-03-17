package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import app.Mediatheque;
import ex.RestrictionException;


public class ReservationService extends ServerService {

	
	public ReservationService(Socket socket, Mediatheque m) {
		super(socket, m);
	}
	
	@Override
	public void run() {
		
		List<String> list = mediatheque.getDocumentsDisponibles();
		
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {	    
		    out = new PrintWriter (socket().getOutputStream ( ), true);
		    
		    for (String s : list)
		    	out.println(s);
		    
		   in = new BufferedReader (new InputStreamReader(socket().getInputStream()));
		    
		    try {
		    	
		    	String reponse ="Le document a bien été réservé";
		    	out.println("Votre numéro d'abonné:");
		    	int numeroAbonne = Integer.parseInt(in.readLine());
		    	out.println("Lenuméro du document que vous souhaitez reserver:");
		    	int numeroDocument = Integer.parseInt(in.readLine());
		    	
		    	
		    	if (mediatheque.checkData(numeroAbonne, numeroDocument)) {
		    		
		    		if (mediatheque.estDisponible(numeroDocument)) {
		    			try {
		    				mediatheque.reserver(numeroAbonne, numeroDocument);
		    			}		
		    			
			    		catch(RestrictionException e) {
			    			reponse = "Trop jeune désolé";
			    		}	    			
		    		}
		    		
		    		else
		    			reponse = "Document indisponible";

		    	}
		    			
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
	    	
	        System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
	    } 
		
		finally {
	        try {
	            if (out != null) {
	                out.close();
	            }
	            if (in != null) {
	                in.close();
	            }
	            socket().close();
	        } catch (IOException e) {
	            System.out.println("Erreur lors de la fermeture des ressources");
	        }
	    }
		
		
	}
	
}
