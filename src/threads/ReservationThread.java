package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import app.Mediatheque;



public class ReservationThread extends Thread implements Runnable{
	private ServerSocket serverSocket;
	private Mediatheque mediatheque;
	public ReservationThread(ServerSocket s, Mediatheque m) {
		this.serverSocket=s;
		this.mediatheque = m;
	}
	
	@Override
	public void run() {
        while(true) {
            try {
                Socket s = serverSocket.accept();
                InputStream inputStream = s.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String[] emprunt = reader.readLine().split(" ", 2);
                
                if (this.mediatheque.disponible(emprunt[1]));
                this.mediatheque.emprunter(Integer.parseInt(emprunt[0]), Integer.parseInt(emprunt[1]));
                

            } catch (IOException e) {
                System.out.println("Erreur durant la connection");
            }
        }
		
	}

}
