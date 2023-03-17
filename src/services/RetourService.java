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

			try {

				String reponse = "Le document a bien été rendu";
				out.println("Le numéro du document:");
				int numero = Integer.parseInt(in.readLine());

				if (mediatheque.documentExists(numero)) {

					if (!mediatheque.estDisponible(numero))
						mediatheque.retour(numero);

					else
						reponse = "Le document n'est pas emprunté";

				}

				else
					reponse = "Numéro de document invalide";

				out.println(reponse);
				socket().close();

			}

			catch (NumberFormatException e) {
				System.out.println("Entrée invalide");
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
