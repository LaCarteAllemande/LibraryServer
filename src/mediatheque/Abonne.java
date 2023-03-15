package mediatheque;

import java.util.Calendar;
import java.util.Date;

public abstract class Abonne {
	private Integer numero;
	private String nom;
	private Date dateDeNaissance;
	private static int nbClients =0;
	
	private int nbJoursBannis;
	private static int AGE_MINIMAL_ADULTE=17;
	
	public Abonne(String nom, Date dateDeNaissance) {
		nbClients++;
		this.numero= Integer.valueOf(nbClients);
		this.nom = nom;
		this.dateDeNaissance = dateDeNaissance;
		this.nbJoursBannis =0;
	}
	
	private void bannir(int nbJours) {
		this.nbJoursBannis =+ nbJours;
	}
	
	private int getNbJoursBannis() {
		return this.nbJoursBannis;
	}
	
	public boolean adulte() {
        Date date = new Date(); 

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateDeNaissance);
        int yearsDiff = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);

        return yearsDiff >= AGE_MINIMAL_ADULTE;
	}
	

	
	
	
	
}
