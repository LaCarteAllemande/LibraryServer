package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RetourApp {
	private Socket socket;
	private final String IP = "localhost";
	Scanner scanner;


	//on conait  l'IP à l'avance ? (ip connue)
	public RetourApp(int port) {
		scanner = new Scanner(System.in);

		try {
			socket = new Socket(IP, port);
			try {
				retour();
			}

			catch (IOException e) {
				System.out.println("Erreur lors du retour");
				socket.close();
			}

		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de la connexion");

			try {
				socket.close();
			} catch (IOException e1) {
			}

		}
	}
	
	private void retour() throws IOException {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		System.out.println(buffer.readLine());
		int numero = lireNumero();
		out.println(numero);		
	}


	private int lireNumero() {

		int num =0;
		boolean b = true;

		while (b) {
			System.out.print("Numéro du document à rendre: ");
			if (scanner.hasNextInt()) {
				num = scanner.nextInt();
				b= false;

			} else {
				System.out.println("Numéro invalide");
			}

		}

		return num;
	}


	public void finalize() {
		scanner.close();
	}

	public static void main(String[] args) {
		final int PORT = 5000;
		RetourApp app = new RetourApp(PORT);

	}
}


