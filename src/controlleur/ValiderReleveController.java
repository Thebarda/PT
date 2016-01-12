package controlleur;

import javax.json.JsonObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import vue.Main;
/**
 * Classe qui gere la validation d'un releve si la valeur de celui ci est anormale
 * @author ThebardaPNK
 *
 */
public class ValiderReleveController {

	@FXML
	Label seuilDepasse;
	
	static JsonObject[] tabStations;
	static int currentPos;
	/**
	 * Init
	 * @param tab
	 * @param pos
	 */
	public static void initialise(JsonObject[] tab,int pos) {
		tabStations = tab;
		currentPos=pos;
	}
	/**
	 * Initialisation
	 */
	@FXML
	private void initialize(){
		seuilDepasse.setText("Ce releve devrait etre "+ saisirReleveController.seuilAff+". Or vous avez saisie "+saisirReleveController.releveEff+"(ecart de "+saisirReleveController.ecartSeuil+")");
	}
	/**
	 * Annule la validation 
	 */
	public void annuler(){
		seuilDepasse.getParent().getScene().getWindow().hide();
	}
	/**
	 * Valide la valeur anormale
	 */
	public void valider(){
		ReleveController.enregistrer(tabStations[currentPos].getInt("idStation"), saisirReleveController.releveEff, saisirReleveController.commentaireEff);
		saisirReleveController.doitChargerSuivant=true;
		seuilDepasse.getParent().getScene().getWindow().hide();
		
	}
}
