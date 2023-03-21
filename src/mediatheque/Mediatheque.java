package mediatheque;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import tasks.MajBannissement;
import tasks.VerifyReservation;

public class Mediatheque {

	private Map<Integer, Document> documents;
	private Map<Integer, Abonne> abonnes;
	private Map<Document, Date> reservations;
	private Map<Document, Date> emprunts;
	private static final int RETARD_MAX = 14;
	private static final int DUREE_EMPRUNT = 21;
	private static final int DUREE_BANNISSEMMENT = 30;
	private static final int DELAI_RESERVATION = 7200000;

	public Mediatheque() {
		Timer timer = new Timer();
		TimerTask task = new MajBannissement(abonnes);
		timer.scheduleAtFixedRate(task, new Date(), 24 * 60 * 60 * 1000);
	}
	
	
	public void init(Map<Integer, Document> documents, Map<Integer, Abonne> abonnes, Map<Document, Date> reservations) {
		this.documents=documents;
		this.abonnes = abonnes;
		this.reservations = reservations;
	}
	public void retour(int numeroDocument) throws ExNonRetournable {
		synchronized (this) {
			if (documents.containsKey(Integer.valueOf(numeroDocument))) {
				Document d = documents.get(Integer.valueOf(numeroDocument));
				
				if (d.emprunteur()!=null || d.reserveur()!=null) {
					Date dateEmprunt = emprunts.get(d);
					long difference = (dateEmprunt.getTime() - new Date().getTime());
					if (TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) > (DUREE_EMPRUNT + RETARD_MAX))
						d.emprunteur().bannir(DUREE_BANNISSEMMENT);
					d.retour();
					this.reservations.remove(d);	
					this.emprunts.remove(d);
				}
				
				else
					throw new ExNonRetournable();

			}
			
			else
				throw new IllegalArgumentException();
		}
	}



	public void emprunter(int numeroClient, int numeroDocument) throws RestrictionException, ExDocumentEmprunte, ExDocumentReseve, ExAbonneBannis {
		synchronized (this) {
			Document d = documents.get(numeroDocument);
			Abonne a = abonnes.get(numeroClient);
			if (d!= null && a!= null)
				if (d.emprunteur() != null)
					throw new ExDocumentEmprunte();
			
				else if(d.reserveur()!= null && d.reserveur()!=a)
					throw new ExDocumentReseve(this.reservations.get(d));
			
				else if (d.emprunteur()== null && (d.reserveur()==null || d.reserveur()==a)) {
					if (a.getNbJoursBannis() > 0)
						throw new ExAbonneBannis(a.getNbJoursBannis());
					else {
						d.empruntPar(a);
						this.emprunts.put(d, new Date());
						this.reservations.remove(d);						
					}

				}
			else
				throw new IllegalArgumentException();
		}

	}

	public void reserver(int abonne, int document) throws RestrictionException, ExDocumentEmprunte, ExDocumentReseve, ExAbonneBannis {
		boolean reserve = false;
		Document d = null;
		Abonne a = null;
		synchronized (this) {
			d = documents.get(Integer.valueOf(document));
			a = abonnes.get(Integer.valueOf(abonne));
			if (d != null && a != null) {
				if (d.emprunteur() != null)
					throw new ExDocumentEmprunte();
				
				else if(d.reserveur()!= null && d.reserveur()!=a)
					throw new ExDocumentReseve(this.reservations.get(d));
				
				else if (d.emprunteur()== null && (d.reserveur()==null || d.reserveur()==a)) {
					if (a.getNbJoursBannis() > 0)
						throw new ExAbonneBannis(a.getNbJoursBannis());
					
					else
					{
						d.reservationPour(a);
						this.reservations.put(d, new Date(System.currentTimeMillis() + DELAI_RESERVATION));
						reserve = true;						
					}
					
				}
			}
			else
				throw new IllegalArgumentException();
		}

		if (reserve) { // on met cette condition pour pouvoir sortir la création du Timer du
			// synchronized
			Timer timer = new Timer();
			TimerTask emprunt = new VerifyReservation(d);
			timer.schedule(emprunt, DELAI_RESERVATION);
		}
	}
	
	public Date dateReservationMax(int numeroDocument) {
		return reservations.get(getDocument(numeroDocument));
	}
	
	public Document getDocument(int numero) {
		synchronized(documents) {
			return documents.get(Integer.valueOf(numero));
		}
		
	}
	
	public Abonne getAbonne(int numero) {
		synchronized(abonnes) {
			return abonnes.get(Integer.valueOf(numero));
		}
	}

	public List<String> getDocumentsDisponibles() {
		synchronized (this) {
			List<String> list = new ArrayList<>();

			for (Document d : documents.values()) {
				if (d.emprunteur() == null && d.reserveur() == null)
					list.add(d.toString());
			}
			return list;
		}
	}

}
