package controlleur;

import javax.json.JsonObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import vue.Main;

public class ValiderReleveController {

	@FXML
	Label seuilDepasse;
	
	static JsonObject[] tabStations;
	static int currentPos;
	
	public static void initialise(JsonObject[] tab,int pos) {
		tabStations = tab;
		currentPos=pos;
	}
	
	@FXML
	private void initialize(){
		seuilDepasse.setText("Ce releve devait être compris entre "+saisirReleveController.seuilBas+" et "+saisirReleveController.seuilHaut+" . Or vous avez saisie "+saisirReleveController.releveEff+"(ecart de "+saisirReleveController.ecartSeuil+")");
	}
	
	public void annuler(){
		seuilDepasse.getParent().getScene().getWindow().hide();
	}
	
	public void valider(){
		ReleveController.enregistrer(tabStations[currentPos].getInt("idStation"), saisirReleveController.releveEff, saisirReleveController.commentaireEff);
		saisirReleveController.doitChargerSuivant=true;
		seuilDepasse.getParent().getScene().getWindow().hide();
		
	}
}
