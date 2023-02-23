package supports;

import mediatheque.Client;

public class DVD extends AbstractSupport {
	private boolean adulte;
	

	@Override
	public void emprunter(Client c) {
		super.setClient(c);
		
	}

	@Override
	public void reserver(Client c) {
		super.setClient(c);
		
	}

	@Override
	public void retourner() {
		// TODO Auto-generated method stub
		
	}
}
