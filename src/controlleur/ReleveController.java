package controlleur;

import java.text.DecimalFormat;

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
	public static double controller(int num, double releve, String commentaire) {
		
		JsonObject station = tabStations[num];
		double seuilHaut = station.getInt("seuilHaut");
		double seuilBas = station.getInt("seuilBas");
		double result=0.0;
		
		
		if(releve < seuilBas){
			result= seuilBas-releve;
		}
		else if(releve > seuilHaut){
			result= releve-seuilHaut;
		}
		enregistrer(station.getInt("idStation"),releve,commentaire);
		DecimalFormat format=new DecimalFormat();
		format.setMaximumFractionDigits(2);
		return Double.parseDouble(format.format(result).replace(",", "."));
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
