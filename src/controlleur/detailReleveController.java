package controlleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class detailReleveController {

	@FXML
	TextArea instruLongue;
	
	@FXML
	Label paramFon;
	
	@FXML
	private void initialize(){
		instruLongue.setText(saisirReleveController.instruLongue);
		paramFon.setText(saisirReleveController.paramFonc);
	}
	
	public void annuler(){
		instruLongue.getParent().getScene().getWindow().hide();
	}
	
}
