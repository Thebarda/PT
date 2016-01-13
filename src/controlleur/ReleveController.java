package controlleur;

import java.text.DecimalFormat;

import javax.json.JsonObject;
/**
 * Classe qui gere un releve
 */
public class ReleveController {

	static private JsonObject[] tabStations;
	static private String nomFichier;
	/**
	 * Initialisation du tableau de stations et du chemin du json
	 * @param tab
	 * 		tableau avec les stations a relever
	 * @param nom
	 * 			chemin du fichier json utilise
	 */
	public static void initialize(JsonObject[] tab, String nom) {
		tabStations = tab;
		nomFichier=nom;
	}
	/**
	 * Controle la valeur d'un releve (en fonction des seuils). La valeur est ajoute dans le json si elle est correcte
	 * @param num
	 * 		numero de la station relatif au releve
	 * @param releve
	 * 		releve effectue
	 * @param commentaire
	 * 		commentaire effectue
	 * @return
	 * 		0 si la valeur du releve est correcte, l'ecart au seuil sinon
	 */
	public static double controller(int num, double releve, String commentaire) {
		
		JsonObject station = tabStations[num];
		double seuilHaut;
		double seuilBas;
		double result=0.0;
		DecimalFormat format=new DecimalFormat();
		format.setMaximumFractionDigits(2);
		
		if(station.getString("marqueur").equals("001") || station.getString("marqueur").equals("000")){
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
		else if(station.getString("marqueur").substring(0, 1).equals("1") && station.getString("marqueur").substring(1, 2).equals("0")){
			seuilBas = station.getInt("seuilBas");
			if(releve < seuilBas){
				result= seuilBas-releve;
			}
			else{
				enregistrer(station.getInt("idStation"),releve,commentaire);
			}
		}
		else if(station.getString("marqueur").substring(0, 1).equals("0") && station.getString("marqueur").substring(1, 2).equals("1")){
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
	 * 		numero de la station où le releve est a enregistrer
	 * @param releve
	 * 		releve a enregistrer
	 * @param commentaire
	 * 		commentaire a enregistrer
	 */
	public static void enregistrer(int num, double releve, String commentaire) {
		JsonController.ecrireReleve(nomFichier, num, commentaire, releve);
	}
	/**
	 * Retourne les seuils sous la forme "Seuil Bas:  Seuil Haut: 	Valeur Normale:"
	 * @param num
	 * 		numero de la station pour lequel on souhaite afficher les informations sur les seuils
	 * @return String
	 * 			Informations sur les seuils
	 */
	public static String seuil(int num){
		JsonObject station = tabStations[num];
		String seuil="";
		
		if (station.getString("marqueur").substring(1, 2).equals("0")){
			seuil+="Seuil Bas: "+station.getInt("seuilBas")+ "		";
		}
		
		if(station.getString("marqueur").substring(0, 1).equals("0")){
			seuil+="Seuil Haut: "+station.getInt("seuilHaut")+ "		";

		}
		
		if(station.getString("marqueur").substring(2, 3).equals("0")){
			seuil+="Valeur Normale: "+station.getInt("valeurNormale");

		}
		return seuil;
	}
	/**
	 * Retourne les seuils sous la forme "Compris entre seuilBas et seuilHaut" ou "inferieure a seuilHaut" ou "superieure a seuilBas"
	 * @param num
	 * 		numero de la station pour lequel on souhaite afficher les informations sur les seuils
	 * @return String
	 * 			Informations sur les seuils
	 */
	public static String seuil2(int num){
		JsonObject station = tabStations[num];
		String seuil="";
		
		if (station.getString("marqueur").substring(1, 2).equals("0") && station.getString("marqueur").substring(0, 1).equals("0")){
			seuil="compris entre "+station.getInt("seuilBas")+" et "+station.getInt("seuilHaut");
		}
		else if(station.getString("marqueur").substring(0, 1).equals("0")){
			seuil="infeieure a "+station.getInt("seuilHaut");
		}
		else if(station.getString("marqueur").substring(1, 2).equals("0")){
			seuil="superieure a "+station.getInt("seuilBas");
		}
		return seuil;
	}

}
