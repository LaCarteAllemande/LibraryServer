package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mediatheque.ExAbonneBannis;
import mediatheque.ExDocumentEmprunte;
import mediatheque.ExDocumentReseve;
import mediatheque.Mediatheque;
import mediatheque.RestrictionException;
import utilitaire.Utilitaire;

public class ReservationService extends MediathequeService {
	private final static int TEMPS_MAX_ATTENTE = 30;
	private Mediatheque mediatheque;
	private String timerTaskResponse;
	private TryReservation task;

	public ReservationService(Socket socket) {
		super(socket);
		mediatheque = super.mediatheque();
	}

	@Override
	public void run() {

		List<String> list = mediatheque.getDocumentsDisponibles();
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			out = new PrintWriter(socket().getOutputStream(), true);
			StringBuilder message = new StringBuilder();
			for (String s : list)
				message.append(s).append(System.lineSeparator());

			in = new BufferedReader(new InputStreamReader(socket().getInputStream()));

			try {

				String reponse = "";
				message.append("Votre numéro d'abonné:");
				out.println(Utilitaire.encrypt(message.toString()));

				try {
					int numeroAbonne = Integer.parseInt(Utilitaire.decrypt(in.readLine()));
					out.println(Utilitaire.encrypt("Le numéro du document que vous souhaitez reserver:"));
					int numeroDocument = Integer.parseInt(Utilitaire.decrypt(in.readLine()));

					try {
						mediatheque.reserver(numeroAbonne, numeroDocument);
						reponse = mediatheque.getDocument(numeroDocument) + " est réservé jusqu’à " + new SimpleDateFormat("hh'h'mm").format(mediatheque.dateReservationMax(numeroDocument));
					}

					catch (ExDocumentReseve e2) {
						Date date = e2.reservation();
						long difference = (date.getTime() - new Date().getTime()) / 1000;

						if (difference > TEMPS_MAX_ATTENTE)
							reponse = "Désolé, " + mediatheque.getDocument(numeroDocument) + "est réservé jusqu’à" + new SimpleDateFormat("hh'h'mm").format(e2.reservation());
						else {
							
							Timer t = new Timer();
							task = new TryReservation(numeroAbonne, numeroDocument, mediatheque);
							t.schedule(task, date);	
							reponse = "Une musique célèste se propage dans votre salon...veuillez attendre " + difference + " secondes";
						}
					}

					catch (IllegalArgumentException e3) {
						reponse = "Veuillez-entrer le numéro correct de l'abonné et du document";
					}

					catch (RestrictionException e) {
						reponse = "vous n’avez pas l’âge pour reserver " + mediatheque.getDocument(numeroDocument);
					}

					catch (ExDocumentEmprunte e1) {
						reponse = mediatheque.getDocument(numeroDocument) + " est déjà emprunté";
						
					} catch (ExAbonneBannis e) {
						reponse = "Vous êtes bannis " + e.duree() + " jours";
					}
					
					

				} catch (NumberFormatException e) {
					reponse = "Veuillez-entrer des numéros valides";
				}
				
				//si la Timertask a été crée
				if (task != null) {
					StringBuilder s = new StringBuilder();
					s.append(reponse).append(System.lineSeparator());
					String taskReponse="";
					while (taskReponse.isEmpty()) {
						taskReponse = task.getReponse();
					}
					s.append(taskReponse);
					reponse = s.toString();
				}
				out.println(Utilitaire.encrypt(reponse));

			} catch (IOException e) {

				System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
			}

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
	
	public void setReponse(String reponse) {
		this.timerTaskResponse = reponse;
	}

}
