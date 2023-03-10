package threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import app.Mediatheque;
import mediatheque.Document;
import services.ReservationService;


public class ReservationThread implements Runnable {
	private ServerSocket serverSocket;
	private List<String> documents;
	Mediatheque mediatheque;
	
	public ReservationThread(int port, Mediatheque m) throws IOException{
		serverSocket = new ServerSocket(port);
		this.mediatheque = m;
	}
	
	
	@Override
	public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                ReservationService reservationService = new ReservationService(socket, mediatheque);
                new Thread(reservationService).start();
            } catch (IOException e) {
                System.out.println("Error accepting reservation client connection: " + e.getMessage());
            }
        }

	}

}
