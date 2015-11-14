
package modele;
/**
 * 
 * @author ThebardaPNK
 *@version 1
 */
public class Fichier {
	private int  id;
	private String  descriptif;
	private String chemin;
	/**
	 * Le constructeur
	 * @param id
	 * @param descriptif
	 * @param chemin
	 */
	public Fichier(int id, String descriptif, String chemin) 
	{
		this.id = id;
		this.descriptif = descriptif;
		this.chemin = chemin;
	}
	/**
	 * retourne l'id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * modifier l'id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * retourne le descriptif
	 * @return descriptif
	 */
	public String getDescriptif() {
		return descriptif;
	}
	/**
	 * modifie le descriptif
	 * @param descriptif
	 */
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	/**
	 * retourne le chemin du fichier
	 * @return chemin
	 */
	public String getChemin() {
		return chemin;
	}
	/**
	 * modifie le chemin du fichier
	 * @param chemin
	 */
	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chemin == null) ? 0 : chemin.hashCode());
		result = prime * result + ((descriptif == null) ? 0 : descriptif.hashCode());
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
		Fichier other = (Fichier) obj;
		if (chemin == null) {
			if (other.chemin != null)
				return false;
		} else if (!chemin.equals(other.chemin))
			return false;
		if (descriptif == null) {
			if (other.descriptif != null)
				return false;
		} else if (!descriptif.equals(other.descriptif))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
