package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import threads.EmpruntThread;
import threads.ReservationThread;
import threads.RetourThread;

public class MediathequeServer {
	private Mediatheque mediatheque;
	private ServerSocket reservationSocket, empruntSocket, retourSocket;
	private Thread reservationThread, empruntThread, retourThread;

	
	//passé les ports en paramètre ?
	public MediathequeServer(Mediatheque m, int portReservations, int portEmprunts, int portRetours) throws IOException {
		this.mediatheque = m;
		reservationSocket = new ServerSocket(portReservations);
		empruntSocket = new ServerSocket(portEmprunts);
		retourSocket = new ServerSocket(portRetours);
		
		creerThreads();
	}
	
	private void creerThreads() {
		this.reservationThread = new ReservationThread(reservationSocket,mediatheque);
		//this.empruntThread = new EmpruntThread();
		//this.retourThread = new RetourThread();
		

	}
	
	
	
	public void run() {
        reservationThread.start();
        empruntThread.start();
        retourThread.start();
    }
}
