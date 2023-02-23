package supports;

import mediatheque.Client;
import mediatheque.Etat;
import mediatheque.Support;

public class AbstractSupport implements Support {
	
	private Etat etat;
	private int numero;
	private String titre;
	private Client clientActuel;
	
	@Override
	public void emprunter(Client c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reserver(Client c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retourner() {
		// TODO Auto-generated method stub

	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public void setClient(Client c) {
		this.clientActuel = c;
	}

}
