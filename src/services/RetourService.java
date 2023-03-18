package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import app.Mediatheque;

public class RetourService extends ServerService {

	public RetourService(Socket socket, Mediatheque m) {
		super(socket, m);
	}

	@Override
	public void run() {

		PrintWriter out = null;
		BufferedReader in = null;

		try {
			out = new PrintWriter(socket().getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket().getInputStream()));
			String reponse = "Le document a bien été rendu";

	
				out.println("Le numéro du document:");
				
				try {
					int numero = Integer.parseInt(in.readLine());
					
					if (mediatheque.documentExists(numero)) {

						if (!mediatheque.estDisponible(numero))
							mediatheque.retour(numero);

						else
							reponse = "Le document n'est pas emprunté";

					}

					else
						reponse = "Numéro de document invalide";
				}
				
				catch (NumberFormatException e) {
					reponse = "Entrée invalide";
				}
				out.println(reponse);

		} catch (IOException e) {

			System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
		}

		finally {
			try {
				out.close();
				in.close();
				socket().close();
			} catch (IOException e) {
				System.out.println("Erreur lors de la fermeture des ressources");
			}
		}

	}

}
