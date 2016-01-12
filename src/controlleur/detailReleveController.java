package controlleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
/**
 * Classe qui affiche les détails d'un releve
 * @author ThebardaPNK
 *
 */
public class detailReleveController {

	@FXML
	TextArea instruLongue;
	
	@FXML
	Label paramFon;
	/**
	 * Initialisation
	 */
	@FXML
	private void initialize(){
		instruLongue.setText(saisirReleveController.instruLongue);
		paramFon.setText(saisirReleveController.paramFonc);
	}
	/**
	 * Retourne vers l'écran de saisie du releve
	 */
	public void annuler(){
		instruLongue.getParent().getScene().getWindow().hide();
	}
	
}
