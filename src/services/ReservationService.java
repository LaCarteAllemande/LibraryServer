package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import app.Mediatheque;
import ex.RestrictionException;

public class ReservationService extends ServerService {
	private final static String END_STRING="END";
	
	public ReservationService(Socket socket, Mediatheque m) {
		super(socket, m);
	}

	@Override
	public void run() {

		List<String> list = mediatheque.getDocumentsDisponibles();

		PrintWriter out = null;
		BufferedReader in = null;

		try {	    
			out = new PrintWriter(socket().getOutputStream ( ), true);

			for (String s : list)
				out.println(s);
			out.println(END_STRING);
			in = new BufferedReader (new InputStreamReader(socket().getInputStream()));

			try {

				String reponse ="Le document a bien été réservé";
				out.println("Votre numéro d'abonné:");

				try {
					int numeroAbonne = Integer.parseInt(in.readLine());
					out.println("Le numéro du document que vous souhaitez reserver:");
					int numeroDocument = Integer.parseInt(in.readLine());	
					if (mediatheque.checkData(numeroAbonne, numeroDocument)) {

						if (mediatheque.estDisponible(numeroDocument)) {
							try {
								mediatheque.reserver(numeroAbonne, numeroDocument);
							}		

							catch(RestrictionException e) {
								reponse = "vous n’avez pas l’âge pour emprunter ce DVD";
							}	    			
						}

						else
							reponse = "Document indisponible";

					}

					else
						reponse = "Numéro de document ou d'abonné invalide";}


				catch (NumberFormatException e) {
					reponse = "Données invalides";
				}


				out.println(reponse);
		} catch (IOException e) {

			System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
		}

	}catch(IOException e)
	{

		System.out.println("Erreur lors de la lecture ou de l'écriture sur la socket");
	}

	finally
	{
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
