package controlleur;

import javax.json.JsonObject;

public class ReleveController {

	static private JsonObject[] tabStations;
	static private String nomFichier;
	/**
	 * Initialisation
	 * @param tab
	 * @param nom
	 */
	public static void initialize(JsonObject[] tab, String nom) {
		tabStations = tab;
		nomFichier=nom;
	}
	/**
	 * Controle la valeur d'un releve
	 * @param num
	 * @param releve
	 * @param commentaire
	 * @return
	 */
	public static boolean controller(int num, double releve, String commentaire) {
		
		JsonObject station = tabStations[num];
		double seuilHaut = station.getInt("seuilHaut");
		double seuilBas = station.getInt("seuilBas");
		
		if((releve < seuilBas) || ( releve > seuilHaut))
		{
			return false;
		}
		enregistrer(station.getInt("idStation"),releve,commentaire);
		return true;
	}
	/**
	 * Enregistre un releve dans un fichier json
	 * @param num
	 * @param releve
	 * @param commentaire
	 */
	public static void enregistrer(int num, double releve, String commentaire) {
		JsonController.ecrireReleve(nomFichier, num, commentaire, releve);
	}

}
