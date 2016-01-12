package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vue.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

	List<Label> labels=new ArrayList<Label>();
	
	static String nomJson;
	
	static double ecartSeuil;
	static double releveEff;
	static String commentaireEff;
	static double seuilBas;
	static double seuilHaut;
	static boolean doitChargerSuivant=false;

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
				labels.add(releve);
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
			if(((e.getSceneX() - x) < 10.0) && ((e.getSceneY() - y) < 10.0)){
				Label label = (Label)e.getSource();
				currentPos=Integer.parseInt(label.getId());
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
		seuilBas=stations[numStation].getInt("seuilBas");
		seuilHaut=stations[numStation].getInt("seuilHaut");
		nom.setText(stations[numStation].getString("nomStation"));
		instrCourte.setText(stations[numStation].getString("instructionsCourtes"));
		unite.setText(stations[numStation].getString("unite"));
		seuil.setText("Seuil Bas: " + seuilBas + "		Seuil Haut: "
				+ seuilHaut + "		Valeur Normale: "
				+ stations[numStation].getInt("valeurNormale"));
		/*if(JsonController.estReleveSaisi(nomJson, stations[currentPos].getInt("idStation"))){
			System.out.println(Integer.toString(JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation"))));
		}*/
	}

	/**
	 * Permet la validation d'une station et d�cale le scroll des stations
	 */
	public void valider(){
		ecartSeuil=ReleveController.controller(currentPos, Double.parseDouble(releve.getText()), commentaire.getText());
		if (ecartSeuil!=0){
			verifierReleve();
		}
		else{
			labels.get(currentPos).setText(labels.get(currentPos).getText()+"\n"+releve.getText()+" "+unite.getText()+"\nValidé");
			labels.get(currentPos).setStyle("-fx-background-color: green;");
			passerSuivant();
		}
	}
	
	public void verifierReleve(){
		releveEff=Double.parseDouble(releve.getText());
		commentaireEff=commentaire.getText();
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("validerReleve.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Valider un releve");
	        dialog.show();
	        ValiderReleveController.initialise(stations, currentPos);
	        dialog.setOnHiding(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	dialog.close();
	            	chargerSuivant();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void passerSuivant(){
		currentPos++;
		charge(currentPos);
		releve.setText("");
		commentaire.setText("");
		scroll.setHvalue((double)currentPos/((double)nbStations-4));
	}
	
	public void chargerSuivant(){
		if (doitChargerSuivant){
			labels.get(currentPos).setText(labels.get(currentPos).getText()+"\n"+releve.getText()+" "+unite.getText()+"\nAnormale");
			labels.get(currentPos).setStyle("-fx-background-color: orange;");
			passerSuivant();
			doitChargerSuivant=false;
		}
	}

}
