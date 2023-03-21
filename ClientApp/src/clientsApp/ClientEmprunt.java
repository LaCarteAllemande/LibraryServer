package clientsApp;

public class ClientEmprunt extends ClientApp{
	

	public ClientEmprunt(String ip, int port) {
		super(port, ip);
	}
	
	public static void main(String[] args) {
		//recuperer par ligne de commande
		final int PORT = Integer.parseInt(args[0]);
		String ip =args[1];
		ClientEmprunt app = new ClientEmprunt(ip, PORT);

	}
}


