package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;

public class ValidationFinaleController {
	@FXML
	AnchorPane Stations;
	@FXML
	Button oui;
	
	@FXML
	Button non;
	private String nomJson;
	private int nbStations;
	private int compteur;
	private JsonObject[] stations;
	private ArrayList<Labeled> labels;
	int currentPos;
	@FXML
	public void initialize(){
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
				Stations.getChildren().add(releve);
				labels.add(releve);
				if(JsonController.estReleveSaisi(nomJson, stations[compteur].getInt("idStation"))){
					releveDejaSaisi=JsonController.getReleve(nomJson, stations[compteur].getInt("idStation"));
					if(releveDejaSaisi>=stations[compteur].getInt("seuilBas")){
						if(releveDejaSaisi<=stations[compteur].getInt("seuilHaut")){
							labels.get(compteur).setText(stations[compteur].getString("nomStation")+"\n"+releveDejaSaisi+" "+stations[compteur].getString("unite")+"\nValide");
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
						labels.get(compteur).setText(stations[compteur].getString("nomStation")+"\n"+releveDejaSaisi+" "+stations[compteur].getString("unite")+"\nAnormale");
						labels.get(compteur).setStyle("-fx-background-color: orange; -fx-border-style: solid;");
						releveDejaSaisiAnormale=false;
					}
				}
				compteur++;
			}
			currentPos = 0;
			ReleveController.initialize(stations, nomJson);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
