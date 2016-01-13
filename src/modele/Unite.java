package modele;
/**
 * Classe qui instancie, modifie et affiche une unite de mesure
 * @author 
 * @version 1
 */
public class Unite {
	private int id;
	private String nom;
	/**
	 * Construit l'unite par l'id et le nom
	 * @param id	: l'id de l'unite
	 * @param nom	: le nom de l'unite
	 */
	public Unite(int id, String nom){
		this.id = id;
		this.nom = nom;
	}
	/**
	 * Retourne l'id de l'unite
	 * @return id : l'id de l'unite
	 */
	public int getId(){
		return this.id;
	}
	/**
	 * Retourne le nom de l'unite
	 * @return nom : le nom de l'unite
	 */
	public String getNom(){
		return this.nom;
	}
	/**
	 * Modifie l'id de l'unite
	 * @param id : nouvel id de l'unite
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * Modifie le nom de l'unite
	 * @param nom : le nouveau nom de l'unite
	 */
	public void setNom(String nom){
		this.nom = nom;
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
		Unite other = (Unite) obj;
		if (id != other.id)
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "["+id+"]"+nom;
	}
}
