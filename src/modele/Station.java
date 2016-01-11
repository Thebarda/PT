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
	private int idUnite;
	private int seuilHaut;
	private int seuilBas;
	private int valeurNormale;
	private String paramFonc;
	private boolean MISH;
	
	/**
	 * constructeur d'une station en indiquant tous les param�tre qui la constitue
	 * @param id id de la station
	 * @param nom nom de la station
	 * @param instructionCourte instruction courte de la station
	 * @param instructionLongue instruction de la station
	 * @param idUnite id de l'unit� de mesure de la station
	 * @param frequence frequence de controle de la station
	 * @param seuilHaut seuil maximum pour les mesures
	 * @param seuilBas seuil minimum pour les mesures
	 * @param valeurNotmale valeur normale pour les mesures
	 * @param paramFonc parametre de fonctionnement de la station
	 * @param MISH MISH de la station
	 */
	public Station(int id, String nom, String instructionCourte, String instructionLongue, int idUnite,
			int seuilHaut, int seuilBas,int valeurNormale,String paramFonc,boolean MISH) {
		super();
		this.id = id;
		this.nom = nom;
		this.instructionCourte = instructionCourte;
		this.instructionLongue = instructionLongue;
		this.idUnite = idUnite;
		this.seuilHaut = seuilHaut;
		this.seuilBas = seuilBas;
		this.valeurNormale=valeurNormale;
		this.paramFonc=paramFonc;
		this.MISH=MISH;
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
	public int getIdUnite() {
		return idUnite;
	}
	/**
	 * setter de l'id de l'unit� de la station
	 * @param unite id de l'unit� de la station
	 */
	public void setIdUnite(int unite) {
		this.idUnite = unite;
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
	 * getter du nom de l'unite de la station
	 * @return le nom de l'unite de la station
	 */
	public String getNomUnite() {
		return UniteController.idVersNom(idUnite);
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
