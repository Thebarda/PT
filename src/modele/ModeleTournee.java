package modele;

import java.util.HashMap;

import controleur.TourneeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
/**
 * Classe qui creer un modèle de tournée à partir de station
 * @author 
 *@version 1
 */
public class ModeleTournee 
{
	private int  id;
	private String nom;
	private String description;
	private HashMap<Integer,Station> stations;
	private boolean estSupprime;
	
	/**
	 * Constructeur d'un modele de tournee
	 * @param id : l'id du modèle de tournee
	 * @param nom : le nom du modèle de tournee
	 * @param description : la description du modele de tournee
	 */
	public ModeleTournee(int id, String nom, String description, boolean estSupprime) 
	{
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.stations = new HashMap<Integer,Station>();
		this.estSupprime = estSupprime;
	}

	/**
	 * Ajouter une station a un modele de tournee
	 * @param station : la station à ajouter
	 */
	public void ajouterStation(Station station, int ordre){
		this.stations.put(ordre, station);
	}
	
	/**
	 * Recuperer les stations du modele de tournee
	 * @return les stations du modele de tournee dans une arraylist
	 */
	public ObservableMap<Integer, Station> getStationN(int ordre){
		ObservableMap<Integer, Station> ObsStations = FXCollections.observableHashMap();
		ObsStations.putAll(stations);
		return ObsStations;
	}
	
	/**
	 * retourne l'id du modele de tournee
	 * @return id : l'id du modele de tournee
	 */
	public int getId() {
		return id;
	}
	/**
	 * modifie l'id du modele de tournee
	 * @param id : le nouvel id du modele de tournee
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * retourne le nom du modele de tournee
	 * @return nom : le nom du modele de tournee
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * modifie le nom du modele de tournee
	 * @param nom : le nouveau nom du modele de tournee
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Methode permettant de recuperer la description du modele de tournee
	 * @return description : la description du modele de tournee
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Methode permettant de modifier la description du modele de tournee
	 * @param description : la nouvelle description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter pour savoir si le modele est supprime
	 * @return estSupprime
	 * 		estSupprime : vrai si le modele est supprime, faux sinon
	 */
	public Boolean getEstSupprime() {
		return estSupprime;
	}
	/**
	 * Setter pour definir si le modele est supprime
	 * @param estSupprime
	 * 		estSupprime : vrai si le modele est supprime, faux sinon
	 */
	public void setEstSupprime(boolean estSupprime) {
		this.estSupprime = estSupprime;
	}

	/**
	 * Methode permettant de recuperer les stations du modele de tournee
	 * @return stations : les stations du modele de tournee
	 */
	public HashMap<Integer, Station> getStations() {
		return stations;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModeleTournee [id=" + id + ", nom=" + nom + ", description=" + description
				+ ", stations=" + stations + "]";
	}
	
	/**
	 * cette fonction permet de generer la tournee d'un modele
	 * @param modele
	 * @return 
	 */
	public void genererTournee()
	{
		Tournee tournee;
		String date = "";
		tournee = new Tournee(this.getNom(),this.getId(),this.stations,date);
		TourneeController.addTournee(tournee);
	}
}
