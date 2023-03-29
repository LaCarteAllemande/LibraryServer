package mediatheque;

import java.util.Date;

//cette exception connait la date d'expiration de la reservation qui l'a levée
public class ExDocumentReseve extends Exception {
	private Date dateFinReservation;
	public ExDocumentReseve(Date r) {
		this.dateFinReservation=r;
	}
	public Date getDateReservation() {
		return dateFinReservation;
	}
}
