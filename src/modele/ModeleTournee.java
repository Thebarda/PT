package modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
/**
 * Classe qui cr�er un mod�le de tourn�e � partir de station
 * @author 
 *@version 1
 */
public class ModeleTournee 
{
	/**
	 *  ce champ permet de fixer le mois "t0" d'un mod�le de tourn�e
	 *  c'est a dire que si t0 = 0, le mois de d�but est janvier
	 *  les tourn�e des 3 mois sont  t0 + 3, les 6 mois t0 + 6....
	 *  si aucun param�tre de t0 n'est indiqu�, le mois courant deviens alors le mois de base pour ce mod�le de tourn�e
	 *  permet de determiner quelles stations il faut inclure pour la prochaine tourn�e a exporter
	 */
	private final int t0;
	/**
	 * sert a savoir quand on peut importer la prochaine tourn�e
	 */
	private int moisDernierImport;
	private int  id;
	private String nom;
	private String description;
	private HashMap<Integer,Station> stations;
	
	/**
	 * Constructeur d'un modele de tourn�e avec un T0 par default
	 * @param id : l'id du mod�le de tourn�e
	 * @param nom : le nom du mod�le de tourn�e
	 * @param description : la description du mod�le de tourn�e
	 */
	public ModeleTournee(int id, String nom, String description, int t0) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.stations = new HashMap<Integer,Station>();
		
		// date courante
		//GregorianCalendar date = new GregorianCalendar();			A faire dans le controller de creation de modele de tournee
		//this.t0 = date.get(Calendar.MONTH);						--
		this.t0 = t0;
		//initialiser a -1 car on a pas r�aliser d'import
		this.moisDernierImport = -1;
	}
	/**
	 * Constructeur d'un modele de tourn�e avec un T0 indiqu�, attention janvier=0.... decembre = 11
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
		//initialiser a -1 car on a pas r�aliser d'import
		this.moisDernierImport = -1;
	}

	/**
	 * Ajouter une station a un modele de tourn�e
	 * @param station : la station � ajouter
	 */
	public void ajouterStation(Station station, int ordre){
		this.stations.put(ordre, station);
	}
	
	/**
	 * Recuperer les stations du modele de tournee
	 * @return les stations du mod�le de tourn�e dans une arraylist
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
	 * retourne le nom du mod�le de tourn�e
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
