package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import threads.ReservationThread;

public class MediathequeServer {
	private Mediatheque mediatheque;
	private ServerSocket reservationSocket, empruntSocket, retourSocket;
	private Runnable reservationThread;
	private final int PORT_DEMANDES = 3000, PORT_EMPRUNTS = 4000, PORT_RETOURS = 5000;

	public MediathequeServer(Mediatheque m) throws IOException {
		//ici ou deleguer dans les threads ?
		//donner les .class aux serveurs
		this.mediatheque = m;
		reservationThread =  new ReservationThread(PORT_DEMANDES, mediatheque);
		
		empruntSocket = new ServerSocket(PORT_EMPRUNTS);
		retourSocket = new ServerSocket(PORT_RETOURS);			

	}

	   public void run() {
		   new Thread(reservationThread).start();
	    }
}
