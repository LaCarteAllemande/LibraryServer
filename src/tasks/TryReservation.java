package tasks;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.TimerTask;

import mediatheque.ExAbonneBannis;
import mediatheque.ExDocumentEmprunte;
import mediatheque.ExDocumentReseve;
import mediatheque.Mediatheque;
import mediatheque.RestrictionException;

public class TryReservation extends TimerTask {
	private int numeroAbonne;
	private int numeroDocument;
	Mediatheque mediatheque;
	private PrintWriter out;
	public TryReservation(int abonne, int document, Mediatheque m, PrintWriter p) {
		numeroAbonne = abonne;
		numeroDocument = document;
		mediatheque=m;
		out = p;
	}
	
	@Override
	public void run(){
		String reponse="";
		try {
			mediatheque.reserver(numeroAbonne, numeroDocument);
			reponse = mediatheque.getDocument(numeroDocument) + " est réservé jusqu’à " + new SimpleDateFormat("hh'h'mm").format(mediatheque.dateReservationMax(numeroDocument));
		}
		catch (ExDocumentReseve e1) {
			reponse = "Une intervention divine a permis à quelqu'un d'être plus rapide que vous.";
		} catch (ExDocumentEmprunte e) {
			reponse = "Votre offrande n'a pas suffit au grand chaman.";
			e.printStackTrace();
		} catch (RestrictionException e) {
			reponse = "vous n’avez pas l’âge pour reserver " + mediatheque.getDocument(numeroDocument);
		} catch (ExAbonneBannis e) {
			reponse = "Vous êtes bannis " + e.duree() + " jours";
		}
		
		out.println(reponse);

	}

}
