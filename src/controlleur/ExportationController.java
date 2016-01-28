package controlleur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ExportationController {
	@FXML
	private Label chemin;
	@FXML
	private Button quit;
	
	private String route;
	/**
	 * Initialisation
	 */
	@FXML
	public void initialize(){
		this.route = ChoixExportationController.route;
		chemin.setText(route);
		JsonController.exporterJson(JsonController.def_fichier_tmp, route, ChoixExportationController.complete);
	}
	/**
	 * Ferme l'application donc toutes les fenetres d ouvertes
	 */
	public void fin(){
		Platform.exit();
	}
}
