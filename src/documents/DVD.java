package documents;

import mediatheque.Abonne;
import mediatheque.RestrictionException;

public class DVD extends AbstractDocument {
	private boolean adulte;
	public DVD(int numero, String titre, Abonne reserveur, Abonne emprunteur, boolean adulte) {
		super(numero, titre, reserveur, emprunteur);
		this.adulte = adulte;
	}
	
	@Override
	public String toString() {
		String s = "Tout public";
		if (adulte)
			s= "Adult only";
		return super.toString() + " " + s + " " + super.numero();
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
