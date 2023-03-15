package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientReservationApp {
	private Socket socket;
	private List<String> documents; 
	private int numeroAbonne;
	private final String END = "END";
	private final String IP = "localhost";
	
	
	//on conait  l'IP à l'avance ? (ip connue)
	public ClientReservationApp(int port) {
		documents = new ArrayList<>();
		
		
		try {
		    socket = new Socket(IP, port);
		    try {
		    	this.initDocuments();
				initAbonne();
				reserver();
		    }
		    
		    catch (IOException e) {
		    	System.out.println("Erreur lors de la reception du catalogue");
		    	socket.close();
		    }
		    
		    
		
		    
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de la connexion");
			
		}
		

	}
	
	public void reserver() {
		for (String s : documents) {
			System.out.println(s);
		}
		int numero = lireNumero();
		
		
	}
	
	private int lireNumero() {
        Scanner scanner = new Scanner(System.in);
        int num =0;
        boolean b = true;
        
        while (b) {
	        System.out.print("Numéro du document à reserver: ");
	        if (scanner.hasNextInt()) {
	            num = scanner.nextInt();
	            b= false;

	        } else {
	            System.out.println("Numéro invalide");
	        }
        
        }
        scanner.close();
        
        return num;
	}
	
	private void initAbonne() {
        Scanner scanner = new Scanner(System.in);
        
        boolean b = true;
        while (b) {
	        System.out.print("Numéro d'abonnée: ");
	        if (scanner.hasNextInt()) {
	            this.numeroAbonne = scanner.nextInt();
	            b= false;
	        } else {
	            System.out.println("Numéro invalide");
	        }
        
        }
        scanner.close();
	}
	
	
	private void initDocuments() throws IOException{
		String s = "";

		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		s = buffer.readLine();
			
		while (!s.equals(END)) {
			documents.add(s);
			s = buffer.readLine();
		}
		
	}
	
	public static void main(String[] args) {
		final int PORT = 3000;
		ClientReservationApp app = new ClientReservationApp(PORT);
		
	}
}

	
