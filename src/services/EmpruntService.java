package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

import appMediatheque.MediathequeService;
import mediatheque.ExAbonneBannis;
import mediatheque.ExDocumentEmprunte;
import mediatheque.ExDocumentReseve;
import mediatheque.Mediatheque;
import mediatheque.RestrictionException;

public class EmpruntService extends MediathequeService {
	private Mediatheque mediatheque;

	public EmpruntService(Socket socket) {
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

			String reponse = "Le document a bien été emprunté";
			out.println("Votre numéro d'abonné:");

			try {
				int numeroAbonne = Integer.parseInt(in.readLine());
				out.println("Le numéro du document que vous souhaitez emprunter:");
				int numeroDocument = Integer.parseInt(in.readLine());

				try {
					mediatheque.emprunter(numeroAbonne, numeroDocument);
				} catch (IllegalArgumentException e3) {
					reponse = "Veuillez-entrer le numéro correct de l'abonné et du document";
				}

				catch (RestrictionException e) {
					reponse = "vous n’avez pas l’âge pour emprunter " + mediatheque.getDocument(numeroDocument);
				}

				catch (ExDocumentEmprunte e1) {
					reponse = mediatheque.getDocument(numeroDocument) + " est déjà emprunté";
				}

				catch (ExDocumentReseve e2) {
					reponse = mediatheque.getDocument(numeroDocument) + "est réservé jusqu’à"
							+ new SimpleDateFormat("hh'h'mm").format(e2.reservation());
				}
				catch (ExAbonneBannis e) {
					reponse = "Vous êtes bannis " + e.duree() + " jours";
				}

			} catch (NumberFormatException e) {
				reponse = "Veuillez-entrer des numéros valides";
			}
			out.println(reponse);
		}

		catch (IOException e) {

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
