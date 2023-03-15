package etats;

import ex.ExNonReservable;
import ex.ExNonRetournable;
import mediatheque.Etat;


public class Emprunte implements Etat{

	@Override
	public Etat reserver() throws ExNonReservable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Etat empurunter() throws ex.ExNonEmpruntable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Etat retourner() throws ExNonRetournable {
		// TODO Auto-generated method stub
		return null;
	}

}
