package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

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
	
	JsonObject[] stations;
	
	String nomJson="/home/clement/Dropbox/Projet tut EDF/Json/testJson2.json";
	
	@FXML
	private void initialize(){
		JsonReader reader;
		try {
			reader = Json.createReader(new FileInputStream(nomJson));
			JsonObject tournee = reader.readObject();
			int nbStations= tournee.getInt("nbStations");
			stations=JsonController.loadStations(nomJson);
			for (int i=0;i<nbStations;i++){
				Label releve=new Label(stations[i].getString("nomStation"));
				releve.setPrefHeight(138.0);
				releve.setPrefWidth(200.0);
				releve.setStyle("-fx-border-style: solid;");
				releve.setWrapText(true);
				if(i>0){
					releve.setLayoutX((i*200)-1);
				}
				releve.setAlignment(Pos.CENTER);
				Stations.getChildren().add(releve);
			}
			charge(0);
			

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
	
	
}
