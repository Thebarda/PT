package modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
/**
 * Classe qui créer un modèle de tournée à partir de station
 * @author 
 *@version 1
 */
public class ModeleTournee 
{
	/**
	 *  ce champ permet de fixer le mois "t0" d'un modèle de tournée
	 *  c'est a dire que si t0 = 0, le mois de début est janvier
	 *  les tournée des 3 mois sont  t0 + 3, les 6 mois t0 + 6....
	 *  si aucun paramètre de t0 n'est indiqué, le mois courant deviens alors le mois de base pour ce modèle de tournée
	 *  permet de determiner quelles stations il faut inclure pour la prochaine tournée a exporter
	 */
	private final int t0;
	/**
	 * sert a savoir quand on peut importer la prochaine tournée
	 */
	private int moisDernierImport;
	private int  id;
	private String nom;
	private String description;
	private HashMap<Integer,Station> stations;
	
	/**
	 * Constructeur d'un modele de tournée avec un T0 par default
	 * @param id : l'id du modèle de tournée
	 * @param nom : le nom du modèle de tournée
	 * @param description : la description du modèle de tournée
	 */
	public ModeleTournee(int id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.stations = new HashMap<Integer,Station>();
		
		// date courante
		GregorianCalendar date = new GregorianCalendar();
		this.t0 = date.get(Calendar.MONTH);
		//initialiser a -1 car on a pas réaliser d'import
		this.moisDernierImport = -1;
	}
	/**
	 * Constructeur d'un modele de tournée avec un T0 indiqué, attention janvier=0.... decembre = 11
	 * @param t0
	 * @param id
	 * @param nom
	 * @param description
	 * @param stations
	 */
	public ModeleTournee(int t0, int id, String nom, String description, HashMap<Integer, Station> stations) 
	{
		this.t0 = t0;
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.stations = stations;
		//initialiser a -1 car on a pas réaliser d'import
		this.moisDernierImport = -1;
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
	
	
	
	public int getMoisDernierImport() {
		return moisDernierImport;
	}
	public void setMoisDernierImport(int moisDernierImport) {
		this.moisDernierImport = moisDernierImport;
	}
	public int getT0() {
		return t0;
	}
	
	public HashMap<Integer, Station> getStations() {
		return stations;
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
		return "ModeleTournee [t0=" + t0 + ", moisDernierImport=" + moisDernierImport + ", id=" + id + ", nom=" + nom
				+ ", description=" + description + ", stations=" + stations + "]";
	}
}
