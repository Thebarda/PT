package controlleur;

import javax.json.JsonObject;

public class ReleveController {

	private JsonObject[] tabStations;
	private String nomFichier;

	public void initialize(JsonObject[] tab, String nomFichier) {
		tabStations = tab;
	}

	public boolean controller(int num, double releve, String commentaire) {
		
		JsonObject station = tabStations[num];
		double seuilHaut = station.getInt("seuilHaut");
		double seuilBas = station.getInt("seuilBas");
		
		if((releve < seuilBas) || ( releve > seuilHaut))
		{
			return false;
		}
		enregistrer(num,releve,commentaire);
		return true;
	}

	public void enregistrer(int num, double releve, String commentaire) {
		JsonController.ecrireReleve(nomFichier, num, commentaire, releve);
	}

}
