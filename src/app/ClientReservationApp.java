package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientReservationApp implements Runnable{
	private Socket socket;
	private List<String> documents; 
	
	public ClientReservationApp(String ip) {
		documents = new ArrayList<>();
		try {
		    Socket socket = new Socket(ip, 8080);
		    
		    try {
		    	this.initDocuments();
		    }
		    
		    catch() {
		    	socket.close();
		    }
		    
		} catch (IOException e) {
		    // Handle the exception
		}
	}
	
	public void reserver() {
		
	}
	
	private void initDocuments() {
		String s = "";
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
			
			s = buffer.readLine();
			
			while (s != "END") {
				documents.add(s);
				s = buffer.readLine();
			}
			
		    socket.close();
			
	    
		} catch (IOException e) {
		    // Handle the exception
		}
	}

	@Override
	public void run() {
		this.
		
	}
}
