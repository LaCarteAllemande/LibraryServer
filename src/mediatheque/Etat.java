package mediatheque;

public interface Etat {
	Etat reserver();
	Etat empurunter();
	Etat retourner();
	Etat disponible();
}
