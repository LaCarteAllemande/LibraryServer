package app;

import java.io.IOException;
import java.util.ArrayList;

import services.ServerService;
import services.ReservationService;
import services.RetourService;
import threads.LibraryRunnable;

public class MediathequeServer implements Runnable{
	private Mediatheque mediatheque;
	private final int PORT_RESERVATONS = 3000, PORT_EMPRUNTS = 4000, PORT_RETOURS = 5000;
	private ArrayList<Runnable> runnables; 
	private final Class<? extends ServerService> rClass = ReservationService.class; 
	private final Class<? extends ServerService> eClass = RetourService.class; 

	
	public MediathequeServer(Mediatheque m)  throws IOException {
		//ici ou deleguer dans les threads ?
		//donner les .class aux serveurs
		this.mediatheque = m;
		
		this.runnables.add(new LibraryRunnable(PORT_RESERVATONS, mediatheque, rClass));
		this.runnables.add(new LibraryRunnable(PORT_RETOURS, mediatheque, eClass));

	}
	@Override
	   public void run() {
		   
		   for (Runnable t : runnables)
			   new Thread(t).start();
	    }
}
