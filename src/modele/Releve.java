package modele;

/**
 * Classe qui permet de cr�er un releve
 * @author 
 * @version 1
 *
 */
public class Releve {
	private int  id;
	private String  commentaire;
	private double  valeur;
	private String date;
	private int idStation;
	private String nomStation;
	private int idTournee;
	
	
	/**
	 * Constructeur permettant de creer un releve
	 * @param id			: l'id du releve
	 * @param commentaire	: commentaire du releve
	 * @param valeur		: valeur du releve
	 * @param date			: date du releve
	 * @param idStation		: id de la station
	 * @param nomStation	: nom de la station
	 * @param idTournee		: id de la tournee
	 */
	public Releve(int id, String commentaire, double valeur, String date, int idStation, String nomStation, int idTournee){
		this.id = id;
		this.commentaire = commentaire;
		this.valeur = valeur;
		this.date = date;
		this.idStation = idStation;
		this.nomStation = nomStation;
		this.idTournee = idTournee;
	}
	
	/**
	 * Retourne l'id du releve
	 * @return id : l'id du releve
	 */
	public int getId() {
		return id;
	}
	/**
	 * Modifie l'id du releve
	 * @param id : le nouvel id du releve
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Retourne l'id de la station du releve
	 * @return idStation : l'id de la station du releve
	 */
	public int getIdStation() {
		return idStation;
	}
	/**
	 * Modifie l'id de la Station du releve
	 * @param idStation : le nouvel id de la station du releve
	 */
	public void setIdStation(int idStation) {
		this.idStation = idStation;
	}
	
	/**
	 * Retourne l'id de la tournee du releve
	 * @return idTournee : l'id de la tournee du releve
	 */
	public int getIdTournee() {
		return idTournee;
	}
	/**
	 * Modifie l'id de la tournee du releve
	 * @param idTournee : le nouvel id de la tournee du releve
	 */
	public void setIdTournee(int idTournee) {
		this.idTournee = idTournee;
	}
	
	/**
	 * Retourne le nom de la station du releve
	 * @return nomStation : le nom de la station du releve
	 */
	public String getNomStation() {
		return nomStation;
	}
	/**
	 * Modifie le nom de la station du releve
	 * @param nomStation : le nouveau nom de la station du releve
	 */
	public void setNomStation(String nomStation) {
		this.nomStation = nomStation;
	}
	
	/**
	 * Retourne le commentaire du releve
	 * @return commentaire : le commentaire du releve
	 */
	public String getCommentaire() {
		return commentaire;
	}
	/**
	 * Modifie le commentaire du releve
	 * @param commentaire : le nouveau commentaire du releve
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	/**
	 * Retourne la valeur du releve
	 * @return valeur : la valeur du releve
	 */
	public double getValeur() {
		return valeur;
	}
	/**
	 * Retourne la date du releve
	 * @return date : la date du releve
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Modifie la valeur du releve
	 * @param valeur : la nouvelle valeur du releve
	 */
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + id;
		result = (int) (prime * result + valeur);
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Releve [id=" + id + ", commentaire=" + commentaire + ", valeur=" + valeur + "]";
	}
}
