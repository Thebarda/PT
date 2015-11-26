package modele;

import java.util.HashMap;

/**
 * Classe qui créer une tournée
 * @author 
 * @version 1
 */
public class Tournee 
{
	private int id;
	private final String nom;
	private final int idModele;
	private boolean estExportee;
	private boolean estTerminee;
	private HashMap<Integer,Station> stations;
	
	
	
	public Tournee(int id, String nom, int idModele, HashMap<Integer, Station> stations, boolean estExportee, boolean estTerminee) {
		super();
		this.id = id;
		this.nom = nom;
		this.idModele = idModele;
		// une tournée est par default non exportée
		this.estExportee = estExportee;
		this.estTerminee = estTerminee;
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
	 * retourne l'id du modèle auquel appartient la tournée
	 * @return
	 */
	public int getIdModele() {
		return idModele;
	}
	/**
	 * indique si la tournée a été au moins une fois exportée
	 * @return si la tournée est exportee
	 */
	public boolean isEstExportee() {
		return estExportee;
	}
	/**
	 * indique si la tournée a été terminée
	 * @return si la tournée est terminée
	 */
	public boolean isTerminee() {
		return estTerminee;
	}
	/**
	 * modifie la valeur de l'importation d'une tournée
	 * @param estExportee indique si la tournée est exportée
	 */
	public void setEstExportee(boolean estExportee) {
		this.estExportee = estExportee;
	}
	/**
	 * Definit une tournée comme terminee ou non
	 * @param estTerminee indique si la tournée est terminée
	 */
	public void setEstTerminee(boolean estTerminee) {
		this.estTerminee = estTerminee;
	}
	/**
	 * retourne les station de la tournée
	 * @return les station de la tournée
	 */
	public HashMap<Integer, Station> getStations() {
		return stations;
	}
	
	/**
	 * le but de cette fonction est de fournir un nom explicite a la tournée en fonction de certain paramètre
	 * exemples de nom = TourneeBarrageJuillet
	 * @param mois mois courant
	 * @return un nom explicite pour la tournée
	 */
	public static String getNomTournee(int mois)
	{
		return "Tournee";
	}
	
}
