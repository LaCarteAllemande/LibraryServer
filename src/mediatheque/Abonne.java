package mediatheque;

import java.util.Date;

public abstract class Abonne {
	private Integer numero;
	private String nom;
	private Date dateDeNaissance;
	private static int nbClients =0;
	
	private int nbJoursBannis;
	
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
	

	
	
	
	
}
