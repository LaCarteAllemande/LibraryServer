package mediatheque;

import java.util.Date;

public class ExDocumentReseve extends Exception {
	private Date dateFinReservation;
	public ExDocumentReseve(Date r) {
		this.dateFinReservation=r;
	}
	public Date reservation() {
		return dateFinReservation;
	}
}
