package modele;

/**
 * Classe qui permet de créer un relevé
 * @author 
 * @version 1
 *
 */
public class Releve {
	private int  id;
	private String  commentaire;
	private double  valeur;
	private String date;
	
	public Releve(int id, String commentaire, double valeur, String date){
		this.id = id;
		this.commentaire = commentaire;
		this.valeur = valeur;
		this.date = date;
	}
	
	/**
	 * Retourne l'id du relevé
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Modifie l'id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Retourne le commentaire
	 * @return commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}
	/**
	 * Modifie le commentaire
	 * @param commentaire
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	/**
	 * Retourne la valeur
	 * @return valeur
	 */
	public double getValeur() {
		return valeur;
	}
	/**
	 * Retourne la date du relevé
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Modifie la valeur
	 * @param valeur
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + id;
		result = (int) (prime * result + valeur);
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
		Releve other = (Releve) obj;
		if (commentaire == null) {
			if (other.commentaire != null)
				return false;
		} else if (!commentaire.equals(other.commentaire))
			return false;
		if (id != other.id)
			return false;
		if (valeur != other.valeur)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Releve [id=" + id + ", commentaire=" + commentaire + ", valeur=" + valeur + "]";
	}
}
