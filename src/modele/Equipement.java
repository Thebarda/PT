package modele;
/**
 * Classe relatif a  un equipement
 */
public class Equipement {
	private int id;
	private String nom;
	private String description;
	private int idCentrale;
	private String ECSH;
	private boolean estSupprime;
	
	/**
	 * Constructeur pour un equipement en specifiant son id, son nom, sa descriptione et l'id de la Centrale relatif a  cet equipement
	 * @param id
	 * 		id de l'equipement
	 * @param nom
	 * 		nom de l'equipement
	 * @param description
	 * 		description de l'equipement
	 * @param idCentrale
	 * 		id de la Centrale relatif a l'equipement
	 * @param ECSH
	 * 		repere ECSH de l'equipement
	 * @param
	 * 		definit si l'equipement est supprime ou non
	 */
	public Equipement(int id, String nom, String description, int idCentrale,String ECSH, boolean estSupprime){
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.idCentrale=idCentrale;
		this.ECSH=ECSH;
		this.estSupprime = estSupprime;
	}
	
	/**
	 * Getter pour l'id d'un equipement 
	 * @return id
	 * 		l'identifiant de l'equipement
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter pour l'id d'un equipement
	 * @param id
	 * 		Nouvel id de de l'equipement
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter pour l'id de la centrale d'un equipement d'un equipement 
	 * @return id
	 * 		l'identifiant de la centrale
	 */
	public int getIdCentrale() {
		return idCentrale;
	}
	/**
	 * Setter pour l'id de la centrale d'un equipement
	 * @param id
	 * 		Nouvel id de la centrale
	 */
	public void setIdCentrale(int id) {
		this.idCentrale = id;
	}
	/**
	 * Getter pour le nom d'un equipement
	 * @return nom
	 * 		nom de l'equipement
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Setter pour le nom d'un equipement
	 * @param nom
	 * 		Nouveaux nom d'un equipement
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Getter pour la description d'un equipement
	 * @return description
	 * 		Description de l'equipement
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Setter pour la description d'un equipement
	 * @param description
	 * 		Nouvelle description de l'equipement
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Geter pour le repere ECSH de l'equipement
	 * @return
	 * 		le repere de l'equipement
	 */
	public String getECSH(){
		return ECSH;
	}
	
	/**
	 * Setter pour le repere ECSH de l'equipement 
	 * @param ECSH
	 * 		nouveau repere
	 */
	public void setECSH(String ECSH){
		this.ECSH=ECSH;
	}
	
	/**
	 * Getter pour savoir si l'equipement est supprime
	 * @return estSupprime
	 * 		estSupprime : vrai si l'equipement est supprime, faux sinon
	 */
	public Boolean getEstSupprime() {
		return estSupprime;
	}
	/**
	 * Setter pour definir si l'equipement est supprime
	 * @param estSupprime
	 * 		estSupprime : vrai si l'equipement est supprime, faux sinon
	 */
	public void setEstSupprime(boolean estSupprime) {
		this.estSupprime = estSupprime;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "["+id+"]"+nom;
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
		boolean sontEgaux=false;
		if(obj instanceof Equipement){
			sontEgaux=(this.getId()==((Equipement)obj).getId());
		}
		return sontEgaux;
	}

}
