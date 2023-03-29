package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import mediatheque.Document;
import mediatheque.ExNonRetournable;
import mediatheque.Mediatheque;
import utilitaire.Utilitaire;

public class RetourService extends MediathequeService {
	private Mediatheque mediatheque; 	
	public RetourService(Socket socket, Mediatheque m) {
		super(socket);
		mediatheque = super.mediatheque();
	}

	@Override
	public void run() {

		PrintWriter out = null;
		BufferedReader in = null;

		try {
			out = new PrintWriter(socket().getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket().getInputStream()));
			String reponse = "Le document a bien été rendu";

			out.println(Utilitaire.encrypt("Merci d'entrer le numéro du document à rendre au grand chaman:"));

			try {
				int numero = Integer.parseInt(in.readLine());
				Document d = mediatheque.getDocument(numero);
				if (d != null) {
					try {
						mediatheque.retour(numero);
					} catch (ExNonRetournable e) {
						reponse = d +" ne peut pas être rendu";		
					}
				}

				else
					reponse = "Numéro de document inconnu";
			}

			catch (NumberFormatException e) {
				reponse = "Entrée invalide";

			out.println(Utilitaire.encrypt(reponse));
			}
			
		} catch (IOException e) {

			System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
		}

		finally {
			try {
				socket().close();
				out.close();
				in.close();
			} catch (IOException e) {
				System.out.println("Erreur lors de la fermeture des ressources");
			}
		}
	}
}
