package modele;

import controleur.UniteController;

/**
 * classe permettant la gestion d'une station
 * @author 
 * @version 1
 */
public class Station {
	private int id;
	private String nom;
	private String instructionCourte;
	private String instructionLongue;
	private int idUnite;
	private String marqueur;
	private double seuilHaut;
	private double seuilBas;
	private double valeurNormale;
	private String paramFonc;
	private boolean MISH;
	private boolean estSupprime;
	
	/**
	 * constructeur d'une station en indiquant tous les parametres qui la constitue
	 * @param id : id de la station
	 * @param nom : nom de la station
	 * @param instructionCourte : instruction courte de la station
	 * @param instructionLongue : instruction de la station
	 * @param idUnite : id de l'unite de mesure de la station
	 * @param frequence : frequence de controle de la station
	 * @param marqueur : marqueur permettant de savoir quels seuils ont etes saisis
	 * @param seuilHaut : seuil maximum pour les mesures
	 * @param seuilBas : seuil minimum pour les mesures
	 * @param valeurNormale : valeur normale pour les mesures
	 * @param paramFonc : parametre de fonctionnement de la station
	 * @param MISH : MISH de la station
	 * @param estSupprime : definit si la station est supprime ou non
	 */
	public Station(int id, String nom, String instructionCourte, String instructionLongue, int idUnite, String marqueur,
			double seuilHaut, double seuilBas,double valeurNormale,String paramFonc,boolean MISH, boolean estSupprime) {
		super();
		this.id = id;
		this.nom = nom;
		this.instructionCourte = instructionCourte;
		this.instructionLongue = instructionLongue;
		this.idUnite = idUnite;
		this.marqueur = marqueur;
		this.seuilHaut = seuilHaut;
		this.seuilBas = seuilBas;
		this.valeurNormale=valeurNormale;
		this.paramFonc=paramFonc;
		this.MISH=MISH;
		this.estSupprime = estSupprime;
	}
	/**
	 * getter de l'id de la station
	 * @return id : l'id de la station
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * setter de l'id de la station
	 * @param id : id de la station
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * getter du marqueur de la station
	 * @return marqueur : le marqueur de la station
	 */
	public String getMarqueur() {
		return marqueur;
	}
	
	/**
	 * setter du marqueur de la station
	 * @param marqueur : nouveau marqueur de la station
	 */
	public void setMarqueur(String marqueur) {
		this.marqueur = marqueur;
	}
	
	/**
	 * getter du nom de la station
	 * @return nom : le nom de la station
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * setter du nom de la station
	 * @param nom : nouveau nom de la station
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * getter de l'instruction courte de la station
	 * @return instructionCourte : l'instruction courte de la station
	 */
	public String getInstructionCourte() {
		return instructionCourte;
	}
	/**
	 * setter de l'instruction courte de la station
	 * @param instructionCourte : instruction courte de la station
	 */
	public void setInstructionCourte(String instructionCourte) {
		this.instructionCourte = instructionCourte;
	}
	/**
	 * getter de l'instruction longue de la station
	 * @return instructionLongue : l'instruction longue de la station
	 */
	public String getInstructionLongue() {
		return instructionLongue;
	}
	/**
	 * setter de l'instruction longue d'une station
	 * @param instructionLongue : la nouvelle instruction longue de la station
	 */
	public void setInstructionLongue(String instructionLongue) {
		this.instructionLongue = instructionLongue;
	}
	/**
	 * getter de l'id de l'unite de controle de la station
	 * @return idUnite : l'id de l'unite associe a la station
	 */
	public int getIdUnite() {
		return idUnite;
	}
	/**
	 * setter de l'id de l'unite de la station
	 * @param idUunite : id de l'unite de la station
	 */
	public void setIdUnite(int unite) {
		this.idUnite = unite;
	}
	/**
	 * getter du seuil haut de controle de la station
	 * @return seuilHaut : le seuil haut de controle de la station
	 */
	public double getSeuilHaut() {
			return seuilHaut;
	}
	/**
	 * setter du seuil haut de la station
	 * @param seuilHaut : seuil de controle haut de la station
	 */
	public void setSeuilHaut(double seuilHaut) {
		this.seuilHaut = seuilHaut;
	}
	/**
	 * getter du seuil bas de controle de la station
	 * @return seuilBas : le seuil bas de controle de la station
	 */
	public double getSeuilBas() {
		return seuilBas;
	}
	/**
	 * setter du seuil bas de controle de la station
	 * @param seuilBas : seuil bas de controle de la station
	 */
	public void setSeuilBas(double seuilBas) {
		this.seuilBas = seuilBas;
	}
	
	/**
	 * getter du parametre de fonctionnement
	 * @return paramFonc : le parametre de fonctionnement de la station
	 */
	public String getParamFonc() {
		return paramFonc;
	}
	/**
	 * getter de la valeur normale
	 * @return valeurNormale : la valeur normale de la station
	 */
	public double getValeurNormale() {
		return valeurNormale;
	}
	/**
	 * getter du MISH
	 * @return le MISH de la station en booleen
	 */
	public int getMishEntier() {
		if(MISH)
			return 1;
		else
			return 0;
	}
	
	/**
	 * Getter pour savoir si la station est supprime
	 * @return estSupprime
	 * 		estSupprime : vrai si la station est supprime, faux sinon
	 */
	public Boolean getEstSupprime() {
		return estSupprime;
	}
	/**
	 * Setter pour definir si la station est supprime
	 * @param estSupprime
	 * 		estSupprime : vrai si la station est supprime, faux sinon
	 */
	public void setEstSupprime(boolean estSupprime) {
		this.estSupprime = estSupprime;
	}
	
	/**
	 * getter du nom de l'unite de la station
	 * @return le nom de l'unite de la station
	 */
	public String getNomUnite() {
		return UniteController.idVersNom(idUnite);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (id != other.id)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(this.getEstSupprime()==true)
		{
			return "["+"SUPPR"+"]"+nom;
		}
		else
		{
			return "["+id+"]"+nom;
		}
		
	}
	
}
