package mediatheque;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Mediatheque {

	private Map<Integer, Document> documents;
	private Map<Integer, Abonne> abonnes;
	private Map<Document, Date> reservations;
	private Map<Document, Date> emprunts;
	private static final int RETARD_MAX = 14; //retard maximum d'un retour en jour
	private static final int DUREE_EMPRUNT = 21; //durée d'un emprunt en jours
	private static final int DUREE_BANNISSEMMENT = 30; //bannisment en jour pour un membre de la tribu
	private static final int DELAI_RESERVATION = 7200000; // temps en ms avant qu'une reservation n'expire

	public Mediatheque(Map<Integer, Document> d, Map<Integer, Abonne> a, Map<Document, Date> r, Map<Document, Date> e) {
		this.documents = d;
		this.abonnes = a;
		this.reservations = r;
		this.emprunts = e;
		Timer timer = new Timer();
		TimerTask task = new MajBannissement(abonnes);
		timer.scheduleAtFixedRate(task, new Date(), 24 * 60 * 60 * 1000);
	}

	public void retour(int numeroDocument) throws ExNonRetournable {
		Document document;
		synchronized (documents) {
			document = documents.get(Integer.valueOf(numeroDocument));
		}

		if (document != null) {
			Date dateEmprunt;
			synchronized (emprunts) {
				dateEmprunt = emprunts.get(document);
			}
			synchronized (document) {
				if (document.emprunteur() != null || document.reserveur() != null) {
					long difference = (dateEmprunt.getTime() - new Date().getTime());
					
					//on vérifie que l'abonné ne rend pas le document trop en retard
					if (TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) > (DUREE_EMPRUNT + RETARD_MAX))
						document.emprunteur().bannir(DUREE_BANNISSEMMENT);
					document.retour();
				}

				else
					throw new ExNonRetournable();
			}

			synchronized (reservations) {
				this.reservations.remove(document);
			}

			synchronized (emprunts) {
				this.emprunts.remove(document);
			}
		}

		else
			throw new IllegalArgumentException();

	}

	public void emprunter(int numeroClient, int numeroDocument)
			throws RestrictionException, ExDocumentEmprunte, ExDocumentReseve, ExAbonneBannis {

		Document document;
		Abonne abonne;
		Date dateReservation;
		int nbJoursBannis = 0;
		synchronized (documents) {
			document = documents.get(numeroDocument);
		}

		synchronized (abonnes) {
			abonne = abonnes.get(numeroClient);
		}

		synchronized (reservations) {
			dateReservation = this.reservations.get(document);
		}

		if (document != null && abonne != null) {
			synchronized (abonne) {
				nbJoursBannis = abonne.getNbJoursBannis();
			}

			synchronized (document) {
				if (document.emprunteur() != null)
					throw new ExDocumentEmprunte();

				else if (document.reserveur() != null && document.reserveur() != abonne)
					throw new ExDocumentReseve(dateReservation);

				else if (document.emprunteur() == null
						&& (document.reserveur() == null || document.reserveur() == abonne)) {
					// on vérifie que l'abonné n'est pas bannis
					if (nbJoursBannis > 0)
						throw new ExAbonneBannis(nbJoursBannis);
					else {
						document.empruntPar(abonne);

					}
				}
			}

			synchronized (emprunts) {
				this.emprunts.put(document, new Date());
			}

			synchronized (reservations) {
				this.reservations.remove(document);
			}

		}

		else
			throw new IllegalArgumentException();
	}

	public void reserver(int numeroAbonne, int numeroDocument)
			throws RestrictionException, ExDocumentEmprunte, ExDocumentReseve, ExAbonneBannis {
		boolean reserve = false;
		Document document;
		Abonne abonne;
		Date dateReservation;
		int nbJoursBannis = 0;
		synchronized (documents) {
			document = documents.get(Integer.valueOf(numeroDocument));
		}

		synchronized (abonnes) {
			abonne = abonnes.get(Integer.valueOf(numeroAbonne));
		}
		
		synchronized (reservations) {
			dateReservation = this.reservations.get(document);
		}

		if (document != null && abonne != null) {
			synchronized (abonne) {
				nbJoursBannis = abonne.getNbJoursBannis();
			}
			
			synchronized (document) {
				if (document.emprunteur() != null)
					throw new ExDocumentEmprunte();

				else if (document.reserveur() != null && document.reserveur() != abonne)
					throw new ExDocumentReseve(dateReservation);

				else if (document.emprunteur() == null
						&& (document.reserveur() == null || document.reserveur() == abonne)) {
					if (nbJoursBannis > 0)
						throw new ExAbonneBannis(nbJoursBannis);

					else {
						document.reservationPour(abonne);
						reserve = true;
					}

				}				
			}

			synchronized (reservations) {
				this.reservations.put(document, new Date(System.currentTimeMillis() + DELAI_RESERVATION));
			}
			
		} else
			throw new IllegalArgumentException();

		if (reserve) { // on met cette condition pour pouvoir sortir la création du Timer du
			// synchronized
			Timer timer = new Timer();
			TimerTask emprunt = new VerifyReservation(document);
			timer.schedule(emprunt, DELAI_RESERVATION);
		}
	}

	public Date dateReservationMax(int numeroDocument) {
		synchronized (reservations) {
			return reservations.get(getDocument(numeroDocument));
		}

	}

	public Document getDocument(int numero) {
		synchronized (documents) {
			return documents.get(Integer.valueOf(numero));
		}

	}

	public Abonne getAbonne(int numero) {
		synchronized (abonnes) {
			return abonnes.get(Integer.valueOf(numero));
		}
	}

	public List<String> getDocumentsDisponibles() {
		synchronized (documents) {
			List<String> list = new ArrayList<>();

			for (Document d : documents.values()) {
				if (d.emprunteur() == null && d.reserveur() == null)
					list.add(d.toString());
			}
			return list;
		}
	}

}
