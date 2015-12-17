package modele;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
	private boolean estTerminee;
	private String moisAnnee;
	private HashMap<Integer,Station> stations;
	
	
	
	public Tournee(int id, String nom, int idModele, HashMap<Integer, Station> stations, boolean estExportee, boolean estTerminee,String moisAnnee) 
	{
		this.id = id;
		this.nom = nom;
		this.idModele = idModele;
		// une tourn�e est par default non export�e
		this.estExportee = estExportee;
		this.estTerminee = estTerminee;
		this.stations = stations;
		this.moisAnnee = moisAnnee;
	}
	public Tournee(String nom, int idModele, HashMap<Integer, Station> stations,String moisAnnee) 
	{
		this(-1,nom,idModele,stations,false,false,moisAnnee);
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
	 * indique si la tourn�e a �t� termin�e
	 * @return si la tourn�e est termin�e
	 */
	public boolean isTerminee() {
		return estTerminee;
	}
	/**
	 * modifie la valeur de l'importation d'une tourn�e
	 * @param estExportee indique si la tourn�e est export�e
	 */
	public void setEstExportee(boolean estExportee) {
		this.estExportee = estExportee;
	}
	/**
	 * Definit une tourn�e comme terminee ou non
	 * @param estTerminee indique si la tourn�e est termin�e
	 */
	public void setEstTerminee(boolean estTerminee) {
		this.estTerminee = estTerminee;
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
	public static String getNomTournee(String nomModele)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		String temp=nomModele;
		
		int numMois = calendar.get(Calendar.MONTH);
		switch(numMois)
		{
		case 0 :
			temp+="_Janvier";
			break;
		case 1 :
			temp+="_Fevrier";
			break;
		case 2 :
			temp+="_Mars";
			break;
		case 3 :
			temp+="_Avril";
			break;
		case 4 :
			temp+="_Mai";
			break;
		case 5 :
			temp+="_Juin";
			break;
		case 6 :
			temp+="_Juillet";
			break;
		case 7 :
			temp+="_Aout";
			break;
		case 8 :
			temp+="_Septembre";
			break;
		case 9 :
			temp+="_Octobre";
			break;
		case 10 :
			temp+="_Novembre";
			break;
		case 11 :
			temp+="_Decembre";
			break;
		}
		if(calendar.get(calendar.MONTH) > numMois)
		{
			temp+= (calendar.getWeekYear()+1);
		}
		else
		{
			temp+= calendar.getWeekYear();
		}
		
		
		return temp;
	}
	@Override
	public String toString() {
		return "Tournee [id=" + id + ", nom=" + nom + ", idModele=" + idModele + ", estExportee=" + estExportee
				+ ", estTerminee=" + estTerminee + ", moisAnnee=" + moisAnnee + ", stations=" + stations + "]";
	}
	public String getDate() {
		return this.moisAnnee;
	}
	
	
}
