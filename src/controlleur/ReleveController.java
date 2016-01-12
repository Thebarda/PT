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
		double seuilHaut;
		double seuilBas;
		double result=0.0;
		DecimalFormat format=new DecimalFormat();
		format.setMaximumFractionDigits(2);
		
		if(station.getString("marqueur").equals("110") || station.getString("marqueur").equals("111")){
			seuilHaut = station.getInt("seuilHaut");
			seuilBas = station.getInt("seuilBas");
			if(releve < seuilBas){
				result= seuilBas-releve;
			}
			else if(releve > seuilHaut){
				result= releve-seuilHaut;
			}
			else{
				enregistrer(station.getInt("idStation"),releve,commentaire);
			}
		}
		else if(station.getString("marqueur").substring(0, 0).equals("0") && station.getString("marqueur").substring(1, 1).equals("1")){
			seuilBas = station.getInt("seuilBas");
			if(releve < seuilBas){
				result= seuilBas-releve;
			}
			else{
				enregistrer(station.getInt("idStation"),releve,commentaire);
			}
		}
		else if(station.getString("marqueur").substring(0, 0).equals("1") && station.getString("marqueur").substring(1, 1).equals("0")){
			seuilHaut = station.getInt("seuilHaut");
			if(releve > seuilHaut){
				result= releve-seuilHaut;
			}
			else{
				enregistrer(station.getInt("idStation"),releve,commentaire);
			}
		}
		
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
	
	public static String seuil(int num){
		JsonObject station = tabStations[num];
		String seuil="";
		if (station.getString("marqueur").substring(1, 1).equals("1")){
			seuil+="Seuil Bas: "+station.getInt("seuilBas")+ "		";
		}
		
		if(station.getString("marqueur").substring(0, 0).equals("1")){
			seuil+="Seuil Haut: "+station.getInt("seuilHaut")+ "		";
		}
		
		if(station.getString("marqueur").substring(2, 2).equals("1")){
			seuil+="Valeur Normale: "+station.getInt("valeurNormale");;
		}
		return seuil;
	}

}
