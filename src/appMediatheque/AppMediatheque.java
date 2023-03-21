package appMediatheque;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import documents.DVD;
import mediatheque.Abonne;
import mediatheque.Document;
import mediatheque.Mediatheque;
import server.ServerRunnable;
import server.ServerService;
import services.EmpruntService;
import services.ReservationService;
import services.RetourService;

public class AppMediatheque {
	private final int PORTS[] = { 3000, 4000, 5000 };
	private List<Runnable> runnables;
	private List<Class<? extends ServerService>> serviceClasses = Arrays.asList(ReservationService.class,
			EmpruntService.class, RetourService.class);
	private MediathequeBDD database;
	private Mediatheque mediatheque;
	private final List<Class<? extends Document>> CLASSES_OF_DOCUMENTS = Arrays.asList(DVD.class);

	public AppMediatheque() throws IOException {
		mediatheque = new Mediatheque();
		MediathequeService.setMediatheque(mediatheque); 
		try {
			database =  new MediathequeBDD();
			Map<Integer, Abonne> abonnes = database.abonnes();
			Map<Integer, Document> documents= database.documents(Collections.unmodifiableMap(abonnes), CLASSES_OF_DOCUMENTS);
			Map<Document, Date> reservations = database.reservations(documents);
			mediatheque.init(documents, abonnes, reservations);

		} catch (SQLException e1) {
			System.out.println("Les grands esprits chamans ne concèdent pas l'accès à la base de données");
		}
		
		this.runnables = new ArrayList<Runnable>();

		for (Class<? extends ServerService> s : serviceClasses) {
			this.runnables.add(new ServerRunnable(PORTS[serviceClasses.indexOf(s)], s));
		}
	}

	public void lancer() {

		for (Runnable t : runnables)
			new Thread(t).start();
	}

	public static void main(String[] args) {
		try {
			AppMediatheque app = new AppMediatheque();
			app.lancer();
		} catch (IOException e) {
			System.out.println("Impossible de lancer le serveur, l'offrande sacrificielle n'est pas assez importante");
		}
	}
}
