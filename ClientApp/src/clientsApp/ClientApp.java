package clientsApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class ClientApp {
	private Socket socket;
	private Scanner scanner;
	private BufferedReader in;
	private PrintWriter out;

	public ClientApp(int port, String ip) {
		scanner = new Scanner(System.in);

		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de la connexion");
		}
	}

	protected void connect() throws IOException {
		System.out.println(reader().readLine());
		writer().println(scanner().nextLine());
		System.out.println(reader().readLine());
		writer().println(scanner().nextLine());
		System.out.println(reader().readLine());
	}

	protected Scanner scanner() {
		return scanner;
	}

	protected Socket socket() {
		return socket;
	}

	protected BufferedReader reader() {
		return in;
	}

	protected PrintWriter writer() {
		return out;
	}

	protected void finalize() {
		try {
			in.close();
			out.close();
			scanner.close();
		} catch (IOException e) {

		}
	}

}
