package mediatheque;

public interface Document {
	int numero();
	

	Abonne emprunteur() ;
	Abonne reserveur() ;
	//@pre ni réservé ni emprunté
	void reservationPour(Abonne ab) throws RestrictionException;
	//@pre libre ou réservé par l’abonné qui vient emprunter
	void empruntPar(Abonne ab) throws RestrictionException;
	//@brief retour d’un document ou annulation d‘une réservation
	void retour();
}
