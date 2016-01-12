package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class saisirReleveController {

	@FXML
	AnchorPane Stations;

	@FXML
	Label nom;

	@FXML
	TextArea instrCourte;

	@FXML
	Label unite;

	@FXML
	Label seuil;

	@FXML
	TextField releve;

	@FXML
	ScrollPane scroll;

	@FXML
	TextArea commentaire;

	JsonObject[] stations;

	int currentPos;

	static String nomJson;

	int nbStations;

	int compteur;
	
	double x;
	
	double y;

	@FXML
	private void initialize() {
		JsonReader reader;
		try {
			nomJson = ImportationController.chemin;
			reader = Json.createReader(new FileInputStream(nomJson));
			JsonObject tournee = reader.readObject();
			nbStations = tournee.getInt("nbStations");
			stations = JsonController.loadStations(nomJson);
			compteur = 0;
			while (compteur < nbStations) {
				Label releve = new Label(stations[compteur].getString("nomStation"));
				releve.setPrefHeight(138.0);
				releve.setPrefWidth(175.0);
				releve.setStyle("-fx-border-style: solid;");
				releve.setWrapText(true);
				releve.setId(String.valueOf(compteur));
				if (compteur > 0) {
					releve.setLayoutX((compteur * 175) - 1);
				}
				releve.setAlignment(Pos.CENTER);
				releve.setOnMousePressed(pressed);
				releve.setOnMouseReleased(release);
				Stations.getChildren().add(releve);
				compteur++;
			}
			currentPos = 0;
			charge(0);
			ReleveController.initialize(stations, nomJson);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	EventHandler<MouseEvent> pressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			x = e.getSceneX();
			y = e.getSceneY();
		}
	};
	
	EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			if(((e.getSceneX() - x) < 2.0) && ((e.getSceneY() - y) < 2.0)){
				Label label = (Label)e.getSource();
				charge(Integer.parseInt(label.getId()));
			}
		}
	};

	/**
	 * Charge la station suivante
	 * 
	 * @param numStation
	 */
	public void charge(int numStation) {
		nom.setText(stations[numStation].getString("nomStation"));
		instrCourte.setText(stations[numStation].getString("instructionsCourtes"));
		unite.setText(stations[numStation].getString("unite"));
		seuil.setText("Seuil Bas: " + stations[numStation].getInt("seuilBas") + "		Seuil Haut: "
				+ stations[numStation].getInt("seuilHaut") + "		Valeur Normale: "
				+ stations[numStation].getInt("valeurNormale"));
	}

	/**
	 * Permet la validation d'une station et décale le scroll des stations
	 */
	public void valider() {
		boolean estValide = ReleveController.controller(currentPos, Double.parseDouble(releve.getText()),
				commentaire.getText());
		if (!estValide) {
			System.out.println("PAS COMPRIS ENTRE LES SEUILS");
		} else {
			currentPos++;
			charge(currentPos);
			releve.setText("");
			commentaire.setText("");
			scroll.setHvalue((double) currentPos / ((double) nbStations - 4));
			System.out.println(currentPos / nbStations);
			System.out.println(scroll.getHvalue());
		}
	}

}
