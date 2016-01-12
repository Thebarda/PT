package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
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
	
	@FXML
	Label erreur;

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
	static String instruLongue;
	static String paramFonc;

	int nbStations;

	int compteur;
	
	double x;
	
	double y;

	@FXML
	private void initialize() {
		JsonReader reader;
		double releveDejaSaisi;
		boolean releveDejaSaisiAnormale=false;
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
				labels.add(releve);
				if(JsonController.estReleveSaisi(nomJson, stations[compteur].getInt("idStation"))){
					releveDejaSaisi=JsonController.getReleve(nomJson, stations[compteur].getInt("idStation"));
					if(releveDejaSaisi>=stations[compteur].getInt("seuilBas")){
						if(releveDejaSaisi<=stations[compteur].getInt("seuilHaut")){
							labels.get(compteur).setText(stations[compteur].getString("nomStation")+"\n"+releveDejaSaisi+" "+unite.getText()+"\nValide");
							labels.get(compteur).setStyle("-fx-background-color: green; -fx-border-style: solid;");
						}
						else{
							releveDejaSaisiAnormale=true;
						}
					}
					else{
						releveDejaSaisiAnormale=true;
					}
					
					if(releveDejaSaisiAnormale==true){
						labels.get(compteur).setText(stations[compteur].getString("nomStation")+"\n"+releveDejaSaisi+" "+unite.getText()+"\nAnormale");
						labels.get(compteur).setStyle("-fx-background-color: orange; -fx-border-style: solid;");
						releveDejaSaisiAnormale=false;
					}
				}
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
			if(((e.getSceneX() - x) < 10.0) && ((e.getSceneY() - y) < 10.0)){
				if(((e.getSceneX() - x) > -10.0) && ((e.getSceneY() - y) > -10.0)){
				Label label = (Label)e.getSource();
				currentPos=Integer.parseInt(label.getId());
				charge(Integer.parseInt(label.getId()));
			
				}
			}
		}
	};

	/**
	 * Charge la station suivante
	 * 
	 * @param numStation
	 */
	public void charge(int numStation) {
		erreur.setVisible(false);
		seuilBas=stations[numStation].getInt("seuilBas");
		seuilHaut=stations[numStation].getInt("seuilHaut");
		paramFonc=stations[numStation].getString("paramFonc");
		instruLongue=stations[numStation].getString("instructionsLongues");
		nom.setText(stations[numStation].getString("nomStation"));
		instrCourte.setText(stations[numStation].getString("instructionsCourtes"));
		unite.setText(stations[numStation].getString("unite"));
		seuil.setText("Seuil Bas: " + seuilBas + "		Seuil Haut: "
				+ seuilHaut + "		Valeur Normale: "
				+ stations[numStation].getInt("valeurNormale"));
		releve.setText("");
		commentaire.setText("");
		if(JsonController.estReleveSaisi(nomJson, stations[currentPos].getInt("idStation"))){
			releve.setText(Double.toString(JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation"))));
			commentaire.setText(JsonController.getCommentaire(nomJson, stations[currentPos].getInt("idStation")));
		}
	}

	/**
	 * Permet la validation d'une station et d�cale le scroll des stations
	 */
	public void valider(){
		erreur.setVisible(false);
		String releveStr=releve.getText();
		if(releveStr.contains(",")){
			releveStr=releveStr.replace(",", ".");
		}
		if (!estUnDouble(releveStr)){
			erreur.setVisible(true);
		}
		else{
			releveEff=Double.parseDouble(releveStr);
			ecartSeuil=ReleveController.controller(currentPos, releveEff, commentaire.getText());
			if (ecartSeuil!=0){
				verifierReleve();
			}
			else{
				releveNormale(currentPos);
				passerSuivant();
			}
		}
	}
	
	public void verifierReleve(){
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
			releveAnormale(currentPos);
			passerSuivant();
			doitChargerSuivant=false;
		}
	}
	
	public void afficherDetails(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("detailsReleves.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Detail d'un releve");
	        dialog.show();
	        dialog.setOnHiding(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean estUnDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	public void releveNormale(int pos){
		labels.get(pos).setText(stations[pos].getString("nomStation")+"\n"+releve.getText()+" "+unite.getText()+"\nValide");
		labels.get(pos).setStyle("-fx-background-color: green; -fx-border-style: solid;");
	}
	
	public void releveAnormale(int pos){
		labels.get(pos).setText(stations[pos].getString("nomStation")+"\n"+releve.getText()+" "+unite.getText()+"\nAnormale");
		labels.get(pos).setStyle("-fx-background-color: orange; -fx-border-style: solid;");
	}
}
