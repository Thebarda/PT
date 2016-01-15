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
	 * 
	 * @param tab
	 *            tableau avec les stations a relever
	 * @param nom
	 *            chemin du fichier json utilise
	 */
	public static void initialize(JsonObject[] tab, String nom) {
		tabStations = tab;
		nomFichier = nom;
	}

	/**
	 * Controle la valeur d'un releve (en fonction des seuils). La valeur est
	 * ajoute dans le json si elle est correcte
	 * 
	 * @param num
	 *            numero de la station relatif au releve
	 * @param releve
	 *            releve effectue
	 * @param commentaire
	 *            commentaire effectue
	 * @return 0 si la valeur du releve est correcte, l'ecart au seuil sinon
	 */
	public static double controller(int num, double releve, String commentaire) {

		JsonObject station = tabStations[num];
		double seuilHaut;
		double seuilBas;
		double result = 0.0;
		DecimalFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(2);
		if (station.getString("marqueur").equals("001") || station.getString("marqueur").equals("000")) {
			seuilHaut = station.getInt("seuilHaut");
			seuilBas = station.getInt("seuilBas");
			if (releve < seuilBas) {
				result = -(seuilBas - releve);
			} else if (releve > seuilHaut) {
				result = releve - seuilHaut;
			} else {
				enregistrer(station.getInt("idStation"), releve, commentaire);
			}
		} else if (!existeSeuilHaut(num) && existeSeuilBas(num)) {
			seuilBas = station.getInt("seuilBas");
			if (releve < seuilBas) {
				result = -(seuilBas - releve);
			} else {
				enregistrer(station.getInt("idStation"), releve, commentaire);
			}
		} else if (existeSeuilHaut(num) && !existeSeuilBas(num)) {
			seuilHaut = station.getInt("seuilHaut");
			if (releve > seuilHaut) {
				result = releve - seuilHaut;
			} else {
				enregistrer(station.getInt("idStation"), releve, commentaire);
			}
		} else { // Cas ou il n'y a ni seuil haut ni seuil bas : toutes les
					// valeurs sont donc valides
			enregistrer(station.getInt("idStation"), releve, commentaire);

		}

		return Double.parseDouble(format.format(result).replace(",", "."));
	}

	/**
	 * Enregistre un releve dans un fichier json
	 * 
	 * @param num
	 *            numero de la station où le releve est a enregistrer
	 * @param releve
	 *            releve a enregistrer
	 * @param commentaire
	 *            commentaire a enregistrer
	 */
	public static void enregistrer(int num, double releve, String commentaire) {
		if (!JsonController.ecrireReleve(nomFichier, num, commentaire, releve)) {
			saisirReleveController.nbReleveEff++;
		}
	}

	/**
	 * Retourne les seuils sous la forme
	 * "Seuil Bas:  Seuil Haut: 	Valeur Normale:"
	 * 
	 * @param num
	 *            numero de la station pour lequel on souhaite afficher les
	 *            informations sur les seuils
	 * @return String Informations sur les seuils
	 */
	public static String seuil(int num) {
		JsonObject station = tabStations[num];
		String seuil = "";

		if (existeSeuilBas(num)) {
			seuil += "Seuil Bas: " + station.getInt("seuilBas") + "		";
		}

		if (existeSeuilHaut(num)) {
			seuil += "Seuil Haut: " + station.getInt("seuilHaut") + "		";

		}

		if (existeValeurOptimale(num)) {
			seuil += "Valeur Optimale: " + station.getInt("valeurNormale");

		}
		if (seuil.equals("")) {
			seuil += "Toutes valeurs acceptees";
		}
		return seuil;
	}

	public static double getValeurOptimale(int num){
		JsonObject station = tabStations[num];
		return station.getInt("valeurNormale");
	}

	/**
	 * Retourne les seuils sous la forme "Compris entre seuilBas et seuilHaut"
	 * ou "inferieure a seuilHaut" ou "superieure a seuilBas"
	 * 
	 * @param num
	 *            numero de la station pour lequel on souhaite afficher les
	 *            informations sur les seuils
	 * @return String Informations sur les seuils
	 */
	public static String seuil2(int num) {
		JsonObject station = tabStations[num];
		String seuil = "";

		if (existeSeuilBas(num) && existeSeuilHaut(num)) {
			seuil = "compris entre " + station.getInt("seuilBas") + " et " + station.getInt("seuilHaut");
		} else if (existeSeuilHaut(num)) {
			seuil = "inferieure a " + station.getInt("seuilHaut");
		} else if (existeSeuilBas(num)) {
			seuil = "superieure a " + station.getInt("seuilBas");
		}
		return seuil;
	}

	/**
	 * fonction qui permet de savoir si une station a un seuil bas
	 * @param num le numero de station 
	 * @return true si la station a un seuil bas
	 */
	public static boolean existeSeuilBas(int num) {
		JsonObject station = tabStations[num];
		return station.getString("marqueur").substring(1, 2).equals("0");
	}
	/**
	 * fonction qui permet de savoir si une station a un seuil haut
	 * @param num le numero de station 
	 * @return true si la station a un seuil haut
	 */
	public static boolean existeSeuilHaut(int num) {
		JsonObject station = tabStations[num];
		return station.getString("marqueur").substring(0, 1).equals("0");
	}
	/**
	 * fonction qui permet de savoir si une station a une valeur optimale
	 * @param num le numero de station 
	 * @return true si la station a un une valeur optimale
	 */
	public static boolean existeValeurOptimale(int num) {
		JsonObject station = tabStations[num];
		return station.getString("marqueur").substring(2, 3).equals("0");
	}
	
	public static boolean isSuperieur(int num){
		return false;
	}
}
