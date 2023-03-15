package documents;

import ex.RestrictionException;
import mediatheque.Abonne;
import mediatheque.Document;

public class DocumentAbstrait implements Document {
	

	private int numero;
	private String titre;
	private Abonne emprunteur;
	private Abonne reserveur;
	private static int nbDocuments=0;
	
	public DocumentAbstrait(String titre) {
		nbDocuments++;
		this.numero= Integer.valueOf(nbDocuments);
		this.titre = titre;
	}
	
	public DocumentAbstrait(String titre, int numero) {
		this.titre = titre;
		this.numero = numero;
		
		if (numero > nbDocuments)
			nbDocuments = numero;
	}
	
	public String titre() {
		return titre;
	}
	
	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public Abonne emprunteur() {
		return this.emprunteur;
	}

	@Override
	public Abonne reserveur() {
		return this.reserveur;
	}

	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		this.reserveur = ab;
		
	}

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException{
		this.emprunteur = ab;
		
	}

	@Override
	public void retour() {
		this.reserveur =  null;
		this.emprunteur =  null;
		
	}

}
