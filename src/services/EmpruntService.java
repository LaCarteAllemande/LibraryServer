package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import app.Mediatheque;
import ex.RestrictionException;
import mediatheque.Document;

public class EmpruntService extends ServerService {

	public EmpruntService(Socket socket, Mediatheque m) {
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
				String reponse = "Le document a bien été emprunté";
				out.println("Votre numéro d'abonné:");

				try {
					int numeroAbonne = Integer.parseInt(in.readLine());
					out.println("Le numéro du document que vous souhaitez emprunter:");
					int numeroDocument = Integer.parseInt(in.readLine());
					if (mediatheque.checkData(numeroAbonne, numeroDocument)) {
						Document document = mediatheque.getDocument(numeroDocument);
						if ((document.emprunteur() == null && document.reserveur() == null)
								|| document.reserveur() == mediatheque.getAbonne(numeroAbonne)) {
							try {
								mediatheque.emprunter(numeroAbonne, numeroDocument);
							}

							catch (RestrictionException e) {
								reponse = "vous n’avez pas l’âge pour emprunter ce DVD";
							}
						}

						else
							reponse = "Document indisponible";

					}

					else
						reponse = "Numéro de document ou d'abonné invalide";
				}

				catch (NumberFormatException e) {
					reponse = "Données invalides";
				}

				out.println(reponse);
			} catch (IOException e) {

				System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
			}

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
