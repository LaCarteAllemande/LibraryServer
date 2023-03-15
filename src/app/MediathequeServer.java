package app;

import java.io.IOException;
import java.util.ArrayList;

import services.LibraryService;
import services.ReservationService;

import threads.LibraryRunnable;

public class MediathequeServer {
	private Mediatheque mediatheque;
	private final int PORT_RESERVATONS = 3000, PORT_EMPRUNTS = 4000, PORT_RETOURS = 5000;
	private ArrayList<Runnable> runnables; 
	private final Class<? extends LibraryService> rClass = ReservationService.class; 

	public MediathequeServer(Mediatheque m) throws IOException {
		//ici ou deleguer dans les threads ?
		//donner les .class aux serveurs
		this.mediatheque = m;
		
		this.runnables.add(new LibraryRunnable(PORT_RESERVATONS, mediatheque, rClass));

	

	}

	   public void run() {
		   
		   for (Runnable t : runnables)
			   new Thread(t).start();
	    }
}
