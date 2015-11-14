package modele;
/**
 * Classe relatif à un equipement
 */
public class Equipement {
	private int id;
	private String nom;
	private String description;
	private int idCentrale;
	
	/**
	 * Constructeur pour un équipement en spécifiant son id, son nom, sa descriptione et l'id de la Centrale relatif à cet équipement
	 * @param id
	 * 		id de l'équipement
	 * @param nom
	 * 		nom de l'équipement
	 * @param description
	 * 		description de l'équipement
	 * @param idCentrale
	 * 		id de la Centrale relatif à l'équipement
	 */
	public Equipement(int id, String nom, String description, int idCentrale){
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.idCentrale=idCentrale;
	}
	
	/**
	 * Getter pour l'id d'un équipement 
	 * @return id
	 * 		l'identifiant de l'équipement
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter pour l'id d'un équipement
	 * @param id
	 * 		Nouveuax équipement de l'équipement
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter pour le nom d'un équipement
	 * @return nom
	 * 		nom de l'équipement
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Setter pour le nom d'un équipement
	 * @param nom
	 * 		Nouveaux nom d'un équipement
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Getter pour la description d'un équipement
	 * @return description
	 * 		Description de l'équipement
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Setter pour la description d'un équipement
	 * @param description
	 * 		Nouvelle description de l'équipement
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Equipement [centrale="+ idCentrale +"id=" + id + ", nom=" + nom + ", description=" + description + "]";
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
		boolean sontEgaux=false;
		if(obj instanceof Equipement){
			sontEgaux=(this.getId()==((Equipement)obj).getId());
		}
		return sontEgaux;
	}

}
