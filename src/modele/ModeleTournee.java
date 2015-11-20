package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
/**
 * Classe qui créer un modèle de tournée à partir de station
 * @author 
 *@version 1
 */
public class ModeleTournee {
	private int  id;
	private String nom;
	private String description;
	private HashMap<Integer,Station> stations;
	
	/**
	 * Constructeur d'un modele de tournée
	 * @param id : l'id du modèle de tournée
	 * @param nom : le nom du modèle de tournée
	 * @param description : la description du modèle de tournée
	 */
	public ModeleTournee(int id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.stations = new HashMap<Integer,Station>();
	}
	
	/**
	 * Ajouter une station a un modele de tournée
	 * @param station : la station à ajouter
	 */
	public void ajouterStation(Station station, int ordre){
		this.stations.put(ordre, station);
	}
	
	/**
	 * Recuperer les stations du modele de tournee
	 * @return les stations du modèle de tournée dans une arraylist
	 */
	public ObservableMap<Integer, Station> getStationN(int ordre){
		ObservableMap<Integer, Station> ObsStations = FXCollections.observableHashMap();
		ObsStations.putAll(stations);
		return ObsStations;
	}
	
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
