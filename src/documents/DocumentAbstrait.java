package documents;

import etats.Disponible;
import mediatheque.Abonne;
import mediatheque.Document;
import mediatheque.Etat;

public class DocumentAbstrait implements Document {
	
	private Etat etat;
	private int numero;
	private String titre;
	private Abonne abonneActuel;
	private static int nbDocuments=0;
	
	public DocumentAbstrait(String titre) {
		nbDocuments++;
		this.numero= Integer.valueOf(nbDocuments);
		this.titre = titre;
	}
	
	public DocumentAbstrait(String titre, int numero) {
		this.titre = titre;
		this.numero = numero;
		
		this.etat = new Disponible();
		if (numero > nbDocuments)
			nbDocuments = numero;
	}

	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public Abonne emprunteur() {
		return this.abonneActuel;
	}

	@Override
	public Abonne reserveur() {
		return this.abonneActuel;
	}

	@Override
	public void reservationPour(Abonne ab) {
		this.abonneActuel = ab;
		
	}

	@Override
	public void empruntPar(Abonne ab) {
		this.abonneActuel = ab;
		
	}

	@Override
	public void retour() {
		this.abonneActuel =  null;
		
	}

}
