package modele;

import java.util.HashMap;

/**
 * Classe qui creer une tournee
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
	private String dateExport;
	private String dateReleve;
	private HashMap<Integer,Station> stations;
	
	
	
	/**
	 * Contructeur permettant de creer une tournee
	 * @param id			: id de la tournee
	 * @param nom			: nom de la tournee
	 * @param idModele		: id du modele de tournee de la tournee
	 * @param stations		: stations de la tournee
	 * @param estExportee	: si la tournee est exportee ou non
	 * @param estTerminee	: si la tournee est terminee ou non
	 * @param dateExport		: date d'export de la tournee
	 */
	public Tournee(int id, String nom, int idModele, HashMap<Integer, Station> stations, boolean estExportee, boolean estTerminee,String dateExport,String dateReleve) 
	{
		this.id = id;
		this.nom = nom;
		this.idModele = idModele;
		// une tournée est par default non exportée
		this.estExportee = estExportee;
		this.estTerminee = estTerminee;
		this.stations = stations;
		this.dateExport = dateExport;
	}
	/**
	 * Contructeur permettant de creer une tournee
	 * @param nom			: nom de la tournee
	 * @param idModele		: id du modele de la tournee
	 * @param stations		: stations de la tournee
	 * @param moisAnnee		: date de la tournee
	 */
	public Tournee(String nom, int idModele, HashMap<Integer, Station> stations,String dateExport) 
	{
		this(-1,nom,idModele,stations,false,false,dateExport,"");
	}
	/**
	 * retourne l'id de la tournee
	 * @return id : l'id de la tournee
	 */
	public int getId() {
		return id;
	}
	/**
	 * modifie l'id de la tournee
	 * @param id : nouvel id de la tournee
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * retourne le nom de la tournee
	 * @return nom : le nom de la tournee
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * retourne l'id du modele auquel appartient la tournee
	 * @return idModele : l'id du modele de la tournee
	 */
	public int getIdModele() {
		return idModele;
	}
	/**
	 * indique si la tournee a ete au moins une fois exportee
	 * @return estExportee : vrai si la tournee est exportee, faux sinon
	 */
	public boolean isEstExportee() {
		return estExportee;
	}
	/**
	 * indique si la tournée a ete terminee
	 * @return estTerminee : vrai si la tournee est terminee, faux sinon
	 */
	public boolean isTerminee() {
		return estTerminee;
	}
	/**
	 * modifie la valeur de l'exportation d'une tournee
	 * @param estExportee : indique si la tournée est exportee
	 */
	public void setEstExportee(boolean estExportee) {
		this.estExportee = estExportee;
	}
	/**
	 * Definit une tournee comme terminee ou non
	 * @param estTerminee : indique si la tournee est terminee
	 */
	public void setEstTerminee(boolean estTerminee) {
		this.estTerminee = estTerminee;
	}
	/**
	 * retourne les station de la tournee
	 * @return stations : les station de la tournee
	 */
	public HashMap<Integer, Station> getStations() {
		return stations;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tournee [id=" + id + ", nom=" + nom + ", idModele=" + idModele + ", estExportee=" + estExportee
				+ ", estTerminee=" + estTerminee + ", dateExport=" + dateExport + ", stations=" + stations + "]";
	}
	/**
	 * Retourne la date de la tournee
	 * @return dateExport : a date de la tournee
	 */
	public String getDate() {
		return this.dateExport;
	}
	/**
	 * Retourne la date du releve de la tournee
	 * @return dateExport : la date du releve de la tournee
	 */
	public String getDateReleve() {
		return this.dateReleve;
	}
	
}
