package clientsApp;
 
import java.io.IOException;

public class ClientRetour extends ClientApp{
	public ClientRetour(int port, String ip) {
		super(port, ip);
	}

	public static void main(String[] args) {
		final int PORT = 5000;
		String ip="localhost";
		ClientRetour app = new ClientRetour(PORT, ip);
	}

	@Override
	protected void connect() throws IOException {
		System.out.println(reader().readLine()); 
		writer().println(scanner().nextLine());
		System.out.println(reader().readLine());
		
	}

}
