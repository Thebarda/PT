package controlleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ExportationController {
	@FXML
	private Label chemin;
	@FXML
	private Button quit;
	
	private String route;
	public void initialize(String route){
		this.route = route;
	}
}
