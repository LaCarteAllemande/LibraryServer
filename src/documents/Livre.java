package documents;

import mediatheque.Abonne;

public class Livre extends AbstractDocument {
	private String maisonEdition;
	public Livre(int numero, String titre, Abonne reserveur, Abonne emprunteur, String maisonEdition) {
		super(numero, titre, reserveur, emprunteur);
		this.maisonEdition=maisonEdition;
	}

}
