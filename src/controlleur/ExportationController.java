package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.json.Json;
import javax.json.JsonReader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import vue.Main;

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
		this.route = saisirReleveController.nomJson;
		chemin.setText(route);
		JsonController.changerEstComplete(route, 1);
	}
	/**
	 * Ferme l'application donc toutes les fenetres d ouvertes
	 */
	public void fin(){
		Platform.exit();
	}
}
