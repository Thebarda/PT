package modele;

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
	private String unite;
	private int seuilHaut;
	private int seuilBas;
	private int valeurNormale;
	private String paramFonc;
	private int MISH;
	
	/**
	 * constructeur d'une station en indiquant tous les param�tre qui la constitue
	 * @param id id de la station
	 * @param nom nom de la station
	 * @param instructionCourte instruction courte de la station
	 * @param instructionLongue instruction de la station
	 * @param unite id de l'unit� de mesure de la station
	 * @param frequence frequence de controle de la station
	 * @param seuilHaut seuil maximum pour les mesures
	 * @param seuilBas seuil minimum pour les mesures
	 * @param valeurNotmale valeur normale pour les mesures
	 * @param paramFonc parametre de fonctionnement de la station
	 * @param i MISH de la station
	 */
	public Station(int id, String nom, String instructionCourte, String instructionLongue, String unite,
			int seuilHaut, int seuilBas,int valeurNormale,String paramFonc,int i) {
		super();
		this.id = id;
		this.nom = nom;
		this.instructionCourte = instructionCourte;
		this.instructionLongue = instructionLongue;
		this.unite = unite;
		this.seuilHaut = seuilHaut;
		this.seuilBas = seuilBas;
		this.valeurNormale=valeurNormale;
		this.paramFonc=paramFonc;
		this.MISH=i;
	}
	/**
	 * getter de l'id de la station
	 * @return l'id de la station
	 */
	public int getId() {
		return id;
	}
	/**
	 * setter de l'id de la station
	 * @param id id de la station
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * getter du nom de la station
	 * @return le nom de la station
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * setter du nom de la station
	 * @param nom nom de la station
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * getter de l'instruction courte de la station
	 * @return l'instruction courte de la station
	 */
	public String getInstructionCourte() {
		return instructionCourte;
	}
	/**
	 * setter de l'instruction courte de la station
	 * @param instructionCourte instruction courte de la station
	 */
	public void setInstructionCourte(String instructionCourte) {
		this.instructionCourte = instructionCourte;
	}
	/**
	 * getter de l'instruction longue de la station
	 * @return l'instruction longue de la station
	 */
	public String getInstructionLongue() {
		return instructionLongue;
	}
	/**
	 * setter de l'instruction longue d'une station
	 * @param instructionLongue
	 */
	public void setInstructionLongue(String instructionLongue) {
		this.instructionLongue = instructionLongue;
	}
	/**
	 * getter de l'id de l'unit� de controle de la station
	 * @return l'id de l'unit� associ� a la station
	 */
	public String getUnite() {
		return unite;
	}
	/**
	 * setter de l'id de l'unit� de la station
	 * @param unite id de l'unit� de la station
	 */
	public void setUnite(String unite) {
		this.unite = unite;
	}
	/**
	 * getter du seuil haut de controle de la station
	 * @return le seuil haut de controle de la station
	 */
	public int getSeuilHaut() {
		return seuilHaut;
	}
	/**
	 * setter du seuil haut de la station
	 * @param seuilHaut seuil de controle haut de la station
	 */
	public void setSeuilHaut(int seuilHaut) {
		this.seuilHaut = seuilHaut;
	}
	/**
	 * getter du seuil bas de controle de la station
	 * @return la seuil bas de controle de la station
	 */
	public int getSeuilBas() {
		return seuilBas;
	}
	/**
	 * setter du seuil bas de controle de la station
	 * @param seuilBas seuil bas de controle de la station
	 */
	public void setSeuilBas(int seuilBas) {
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
	public int getMish() {
		return MISH;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
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
	@Override
	public String toString() {
		return "["+id+"]"+nom;
	}
	
}
