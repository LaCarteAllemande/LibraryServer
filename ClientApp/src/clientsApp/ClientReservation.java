package clientsApp;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientReservation extends ClientApp{
	private List<String> documents; 
	
	
	public ClientReservation (String ip, int port) {
		super(port, ip);
		try {
			initDocuments();
			connect();
		} catch (IOException e) {
			System.out.println("Erreur durant la connexion");
		}
		
	}
	
	@Override
	protected void connect() throws IOException {
		for (String s : documents) {
			System.out.println(s);
		}
		
		super.connect();
	}
		   
	private void initDocuments() throws IOException{
		String s = "";
		documents = new ArrayList<>();
		s = reader().readLine();
		int nbDocuments= Integer.parseInt(s);
		while (documents.size()!= nbDocuments) {
			s = reader().readLine();
			documents.add(s);
		}
		
	}
	
	
	public static void main(String[] args) {
		final int PORT = 3000;
		String ip="localhost";
		ClientReservation app = new ClientReservation(ip, PORT);
		
	}
}

	
