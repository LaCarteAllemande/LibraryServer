package documents;

import ex.RestrictionException;
import mediatheque.Abonne;

public class DVD extends DocumentAbstrait {
	private boolean adulte;
	public DVD(String titre, int numero, boolean adulte) {
		super(titre, numero);
		this.adulte = adulte;
	}
	
	@Override
	public String toString() {
		String s = "Tout public";
		if (adulte)
			s= "Adult only";
		return super.titre() + " " + s + " " + super.numero();
	}
	
	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		if (this.adulte && !ab.adulte())
				throw new RestrictionException();
		else
			super.reservationPour(ab);
	}

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		if (this.adulte && !ab.adulte())
				throw new RestrictionException();
		else
			super.empruntPar(ab);
	}	
	
}
