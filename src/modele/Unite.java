package modele;
/**
 * Classe qui instancie, modifie et affiche une unité de mesure
 * @author 
 * @version 1
 */
public class Unite {
	private int id;
	private String nom;
	/**
	 * Construit l'unité par l'id et le nom
	 * @param id
	 * @param nom
	 */
	public Unite(int id, String nom){
		this.id = id;
		this.nom = nom;
	}
	/**
	 * Retourne l'id de l'unité
	 * @return id
	 */
	public int getId(){
		return this.id;
	}
	/**
	 * Retourne le nom de l'unité
	 * @return nom
	 */
	public String getNom(){
		return this.nom;
	}
	/**
	 * Modifie l'id de l'unité
	 * @param id
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * Modifie le nom de l'unité
	 * @param nom
	 */
	public void setNom(String nom){
		this.nom = nom;
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
		Unite other = (Unite) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString(){
		return "["+id+"]"+nom;
	}
}
