package modele;

import java.util.HashMap;

/**
 * Classe qui cr�er une tourn�e
 * @author 
 * @version 1
 */
public class Tournee 
{
	private int id;
	private final String nom;
	private final int idModele;
	private boolean estExportee;
	private HashMap<Integer,Station> stations;
	
	
	
	public Tournee(int id, String nom, int idModele, HashMap<Integer, Station> stations) {
		super();
		this.id = id;
		this.nom = nom;
		this.idModele = idModele;
		// une tourn�e est par default non export�e
		this.estExportee = false;
		this.stations = stations;
	}
	/**
	 * retourne l'id 
	 * @return l'id
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
	 * retourne le nom
	 * @return
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * retourne l'id du mod�le auquel appartient la tourn�e
	 * @return
	 */
	public int getIdModele() {
		return idModele;
	}
	/**
	 * indique si la tourn�e a �t� au moins une fois export�e
	 * @return si la tourn�e est exportee
	 */
	public boolean isEstExportee() {
		return estExportee;
	}
	/**
	 * modifie la valeur de l'importation d'une tourn�e
	 * @param estExportee indique si la tourn�e est export�e
	 */
	public void setEstExportee(boolean estExportee) {
		this.estExportee = estExportee;
	}
	/**
	 * retourne les station de la tourn�e
	 * @return les station de la tourn�e
	 */
	public HashMap<Integer, Station> getStations() {
		return stations;
	}
	
	/**
	 * le but de cette fonction est de fournir un nom explicite a la tourn�e en fonction de certain param�tre
	 * exemples de nom = TourneeBarrageJuillet
	 * @param mois mois courant
	 * @return un nom explicite pour la tourn�e
	 */
	public static String getNomTournee(int mois)
	{
		return "Tournee";
	}
	
}
