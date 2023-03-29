package mediatheque;

import java.util.TimerTask;

public class VerifyReservation extends TimerTask {
	private Document document;
	public VerifyReservation(Document d) {
		document =d;
	}
	
	@Override
	public void run() {
		synchronized (document) {
			if (document.emprunteur() == null)
				document.retour();			
		}
	}
}
