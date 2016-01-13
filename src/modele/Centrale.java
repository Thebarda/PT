package modele;
/**
 * Classe permettant la gestion de centrale
 */
public class Centrale 
{
	private int id;
	private String nom;
	private String identiteNationale;
	/**
	 * Constructeur pour la centrale en specifiant son id, son nom et sa localisation
	 * @param id
	 * 		id de la centrale
	 * @param nom
	 * 		nom de la centrale
	 * @param lieu
	 * 		localisation de la centrale
	 */
	public Centrale(int id, String nom, String identiteNationale) {
		
		this.id = id;
		this.nom = nom;
		this.identiteNationale = identiteNationale;
		
	}

	/**
	 * Getter pour l'id de la Centrale 
	 * @return id
	 * 		entier correspondant a  l'id de la centrale
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter pour l'id de la centrale
	 * @param id
	 * 		Nouvelle identifiant de la centrale
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter pour le nom de la centrale
	 * @return nom
	 * 		nom de la centrale
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * 	Setter pour le nom de la centrale
	 * @param nom
	 * 		nouveau nom de la centrale
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Getter pour la localisation de la centrale
	 * @return lieu
	 * 		Localisation de la centrale
	 */
	public String getIdentiteNationale() {
		return identiteNationale;
	}
	/**
	 * Setter pour la localisation de la centrale
	 * @param lieu
	 * 		Nouvelle localisation de la centrale
	 */
	public void setIdentiteNationale(String identiteNationale) {
		this.identiteNationale = identiteNationale;
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
		if(obj instanceof Centrale){
			sontEgaux=(this.getId()==((Centrale)obj).getId());
		}
		return sontEgaux;
	}
}
