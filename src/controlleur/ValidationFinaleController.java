package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vue.Main;
/**
 * Classe qui gere la validation finale de la tournee
 * @author ThebardaPNK
 *
 */
public class ValidationFinaleController {
	@FXML
	AnchorPane Stations;
	@FXML
	Button oui;
	
	@FXML
	Button non;
	
	@FXML
	ImageView imageDroite;
	
	@FXML
	ImageView imageGauche;
	
	@FXML
	ScrollPane scroll;
	
	
	public static String nomJson;
	private int nbStations;
	private int compteur;
	private JsonObject[] stations;
	private List<Label> labels = new ArrayList<>();
	int currentPos;
	final static Stage dialog = new Stage();
	/**
	 * Initialisation
	 */
	@FXML
	public void initialize(){
		JsonReader reader;
		double releveDejaSaisi;
		boolean releveDejaSaisiAnormaleBas = false;
		boolean releveDejaSaisiAnormaleHaut = false;
		boolean releveDejaSaisiNormale = false;
		boolean correctBas = false;
		boolean correctHaut = false;
		try {
			nomJson = ImportationController.chemin;
			reader = Json.createReader(new FileInputStream(nomJson));
			JsonObject tournee = reader.readObject();
			nbStations = tournee.getInt("nbStations");
			stations = JsonController.loadStations(nomJson);
			ReleveController.initialize(stations, nomJson);
			compteur = 0;
			System.out.println(System.getProperty("user.dir"));
			imageDroite.setImage(new Image("File:droite.png"));
			imageGauche.setImage(new Image("File:gauche.png"));
			if(nbStations > 4){
				scroll.setOnMouseReleased(releaseScroll);
				imageGauche.setVisible(false);
			}
			else{
				imageGauche.setVisible(false);
				imageDroite.setVisible(false);
			}
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
				releve.setTextAlignment(TextAlignment.CENTER);
				Stations.getChildren().add(releve);
				labels.add(releve);
				if (JsonController.estReleveSaisi(nomJson, stations[compteur].getInt("idStation"))) {
					releveDejaSaisi = JsonController.getReleve(nomJson, stations[compteur].getInt("idStation"));
					if (ReleveController.existeSeuilBas(compteur)
							&& releveDejaSaisi >= stations[compteur].getInt("seuilBas")) {
						correctBas = true;
					}
					if (ReleveController.existeSeuilHaut(compteur)
							&& releveDejaSaisi <= stations[compteur].getInt("seuilHaut")) {
						correctHaut = true;
					}
					if (ReleveController.existeSeuilBas(compteur) && ReleveController.existeSeuilHaut(compteur)) {
						if (correctBas && correctHaut) {
							releveDejaSaisiNormale = true;
						} else {
							if(correctHaut || (!correctBas && !ReleveController.existeSeuilHaut(compteur))){
								releveDejaSaisiAnormaleBas = true;
							}
							else if(correctBas || (!correctHaut && !ReleveController.existeSeuilBas(compteur))){
								releveDejaSaisiAnormaleHaut = true;
							}
						}
					} else if ((ReleveController.existeSeuilBas(compteur) && correctBas)
							|| (ReleveController.existeSeuilHaut(compteur) && correctHaut)
							|| (!ReleveController.existeSeuilHaut(compteur)
									&& !ReleveController.existeSeuilBas(compteur))) {
							releveDejaSaisiNormale = true;
					} else {
						if(correctHaut || (!correctBas && !ReleveController.existeSeuilHaut(compteur))){
							releveDejaSaisiAnormaleBas = true;
						}
						else if(correctBas || (!correctHaut && !ReleveController.existeSeuilBas(compteur))){
							releveDejaSaisiAnormaleHaut = true;
						}
					}
					if (releveDejaSaisiNormale == true) {
						labels.get(compteur).setText(
								stations[compteur].getString("nomStation") + "\n" + releveDejaSaisi + " " + stations[compteur].getString("unite") + "\nValide");
						labels.get(compteur).setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
						releveDejaSaisiNormale = false;
						correctHaut = false;
						correctBas = false;
					}
					if(releveDejaSaisiAnormaleBas == true){
						labels.get(compteur).setText(
								stations[compteur].getString("nomStation") + "\n" + releveDejaSaisi + " " + stations[compteur].getString("unite") + "\nAnormale");
						labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
						releveDejaSaisiAnormaleBas = false;
						correctHaut = false;
						correctBas = false;
					}
					if(releveDejaSaisiAnormaleHaut == true){
						labels.get(compteur).setText(
								stations[compteur].getString("nomStation") + "\n" + releveDejaSaisi + " " + stations[compteur].getString("unite") + "\nAnormale");
						labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
						releveDejaSaisiAnormaleHaut = false;
						correctHaut = false;
						correctBas = false;
					}
					
				}
				compteur++;
			}
			currentPos = 0;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	EventHandler<MouseEvent> releaseScroll = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			ScrollPane scroll = (ScrollPane) e.getSource(); 
			if(scroll.getHvalue()==0){
				imageGauche.setVisible(false);
				imageDroite.setVisible(true);
			}
			if(scroll.getHvalue()==1){
				imageDroite.setVisible(false);
				imageGauche.setVisible(true);
			}
			if(scroll.getHvalue()!=1 && scroll.getHvalue()!=0){
				imageDroite.setVisible(true);
				imageGauche.setVisible(true);
			}
		}
	};
	/**
	 * Passage vers la fenetre de fin de l'application
	 */
	@FXML
	public void toExportation(){
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ChoixExportation.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Exportation");
	        dialog.show();
	        dialog.getIcons().add(new Image("file:logo.png"));
	        dialog.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent arg0) {
					dialog.close();
				}
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Retourne vers la fenetre de saisie des releves pour modification
	 */
	public void modif(){
		non.getParent().getScene().getWindow().hide();
	}
}
