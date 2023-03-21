package mediatheque;

import java.util.Calendar;
import java.util.Date;

public class Abonne {
	private Integer numero;
	private String nom;
	private Date dateDeNaissance;
	private int nbJoursBannis;
	private static int AGE_MINIMAL_ADULTE=17;
	
	public Abonne(Integer numero, String nom, Date dateDeNaissance) {
		this.numero= numero;
		this.nom = nom;
		this.dateDeNaissance = dateDeNaissance;
		this.nbJoursBannis =0;
	}
	
	public void bannir(int nbJours) {
		this.nbJoursBannis =+ nbJours;
		if (nbJoursBannis < 0)
			nbJoursBannis=0;
	}
	
	public int getNbJoursBannis() {
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
