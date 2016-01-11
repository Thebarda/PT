package controlleur;

import javax.json.JsonObject;

public class ReleveController {

	static private JsonObject[] tabStations;
	static private String nomFichier;

	public static void initialize(JsonObject[] tab, String nom) {
		tabStations = tab;
		nomFichier=nom;
	}

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

	public static void enregistrer(int num, double releve, String commentaire) {
		JsonController.ecrireReleve(nomFichier, num, commentaire, releve);
	}

}
