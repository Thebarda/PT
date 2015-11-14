package modele;

import java.util.ArrayList;
/**
 * 
 * @author ThebardaPNK
 *@version 1
 */
public class ModeleTournee {
	private int  id;
	private String nom;
	private String description;
	private ArrayList<Station> stations;
	/**
	 * retourne l'id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * modifie l'id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * retourne le nom du modèle de tournée
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * modifie le nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		ModeleTournee other = (ModeleTournee) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModeleTournee [id=" + id + ", nom=" + nom + ", description=" + description + "]";
	}

	
	
}
