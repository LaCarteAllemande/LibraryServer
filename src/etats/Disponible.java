package etats;

import mediatheque.Etat;

public class Disponible implements Etat {
	
	@Override
	public Etat reserver() {
		return new Reserve();
	}

	@Override
	public Etat empurunter() {
		return new Emprunte();
	}


	@Override
	public boolean disponible() {
		return true;
	}

}
