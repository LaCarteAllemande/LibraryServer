package mediatheque;

import ex.ExNonReservable;
import ex.ExNonRetournable;

public interface Etat {
	Etat reserver() throws ExNonReservable;
	Etat empurunter() throws ExNonEmpruntable;
	
	default Etat retourner() throws ExNonRetournable {
		throw new ExNonRetournable();
	}
	
	default boolean disponible() {
		return false;
	}
}
