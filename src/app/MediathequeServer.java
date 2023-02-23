package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MediathequeServer {
	private ServerSocket reservationSocket;
	private ServerSocket empruntSocket;
	private ServerSocket retourSocket;
	
	private final int PORT_DEMANDES = 3000;
	private final int PORT_EMPRUNTS = 4000;
	private final int PORT_RETOURS = 5000;

	public MediathequeServer() throws IOException {
		reservationSocket = new ServerSocket(PORT_DEMANDES);
		empruntSocket = new ServerSocket(PORT_EMPRUNTS);
		retourSocket = new ServerSocket(PORT_RETOURS);
	}

	public void run() {
		while (true) {
			try {
				Socket reservationClient = reservationSocket.accept();

				ReservationHandler reservationHandler = new ReservationHandler(reservationClient);
				Thread reservationThread = new Thread(reservationHandler);
				reservationThread.start();

				// Attendre une connexion sur le port d'emprunt
				Socket empruntClient = empruntSocket.accept();
				// Traiter la demande d'emprunt
				EmpruntHandler empruntHandler = new EmpruntHandler(empruntClient);
				Thread empruntThread = new Thread(empruntHandler);
				empruntThread.start();

				// Attendre une connexion sur le port de retour
				Socket retourClient = retourSocket.accept();
				// Traiter la demande de retour
				RetourHandler retourHandler = new RetourHandler(retourClient);
				Thread retourThread = new Thread(retourHandler);
				retourThread.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
