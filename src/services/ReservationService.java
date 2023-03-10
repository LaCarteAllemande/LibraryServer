package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import app.Mediatheque;


public class ReservationService implements Runnable{
	private Socket socket;
	Mediatheque mediatheque;
	
	public ReservationService(Socket socket, Mediatheque m) {
		this.socket =socket;
		this.mediatheque = m;
	}
	
	@Override
	public void run() {
		
		List<String> list = mediatheque.getDocumentsDisponibles();
		
		try {	    
		    PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);
		    
		    for (String s : list)
		    	System.out.println(s);
		    
		    BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
		    
		    try {
		    	int numeroAbonne = Integer.parseInt(in.readLine());
		    	int numeroDocument = Integer.parseInt(in.readLine());
		    	
		    	
		    	System.out.println("Données invalides");

		    }
		    
		    catch (NumberFormatException e) {
		    	System.out.println("Données invalides");
		    }
		    
		} catch (IOException e) {
		    // Handle the exception
		}
	}
	
}
