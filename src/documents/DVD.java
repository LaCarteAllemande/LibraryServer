package documents;


public class DVD extends DocumentAbstrait {
	private boolean adulte;
	public DVD(String titre, int numero, boolean adulte) {
		super(titre, numero);
		this.adulte = adulte;
	}

	
	
}
