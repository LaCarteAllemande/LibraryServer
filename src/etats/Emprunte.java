package etats;

import ex.ExNonReservable;
import ex.ExNonRetournable;
import mediatheque.Etat;
import mediatheque.ExNonEmpruntable;

public class Emprunte implements Etat{

	@Override
	public Etat reserver() throws ExNonReservable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Etat empurunter() throws ExNonEmpruntable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Etat retourner() throws ExNonRetournable {
		// TODO Auto-generated method stub
		return null;
	}

}
