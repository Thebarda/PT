package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

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
	
	
	@FXML
	private void initialize(){
		JsonReader reader;
		try {
			nomJson = ImportationController.chemin;
			reader = Json.createReader(new FileInputStream(nomJson));
			JsonObject tournee = reader.readObject();
			nbStations= tournee.getInt("nbStations");
			stations=JsonController.loadStations(nomJson);
			for (int i=0;i<nbStations;i++){
				Label releve=new Label(stations[i].getString("nomStation"));
				releve.setPrefHeight(138.0);
				releve.setPrefWidth(75.0);
				releve.setStyle("-fx-border-style: solid;");
				releve.setWrapText(true);
				if(i>0){
					releve.setLayoutX((i*200)-1);
				}
				releve.setAlignment(Pos.CENTER);
				Stations.getChildren().add(releve);
			}
			currentPos=0;
			charge(0);
			ReleveController.initialize(stations, nomJson);
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void charge(int numStation){
		nom.setText(stations[numStation].getString("nomStation"));
		instrCourte.setText(stations[numStation].getString("instructionsCourtes"));
		unite.setText(stations[numStation].getString("unite"));
		seuil.setText("Seuil Bas: "+stations[numStation].getInt("seuilBas")+"		Seuil Haut: "+stations[numStation].getInt("seuilHaut")+"		Valeur Normale: "+stations[numStation].getInt("valeurNormale"));
	}
	
	public void valider(){
		boolean estValide=ReleveController.controller(currentPos, Double.parseDouble(releve.getText()), commentaire.getText());
		if (!estValide){
			System.out.println("PAS COMPRIS ENTRE LES SEUIL");
		}
		else{
			currentPos++;
			charge(currentPos);
			releve.setText("");
			commentaire.setText("");
			scroll.setHvalue(1/scroll.getWidth()-(double)(nbStations * 200));
			System.out.println(currentPos/nbStations);
			System.out.println(scroll.getHvalue());
		}
	}
	
	
}
