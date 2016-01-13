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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
import javafx.scene.layout.VBox;
/**
 * Classe qui g�re la saisie des releves
 * @author ThebardaPNK
 *
 */
public class saisirReleveController {

	@FXML
	AnchorPane Stations;
	
	@FXML
	VBox Historique;

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
	static String seuilAff;
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
	/**
	 * Initialisation
	 */
	@FXML
	private void initialize() {
		JsonReader reader;
		double releveDejaSaisi;
		boolean releveDejaSaisiAnormale=false;
		boolean releveDejaSaisiNormale=false;
		boolean correctBas=false;
		boolean correctHaut=false;
		try {
			nomJson = ImportationController.chemin;
			reader = Json.createReader(new FileInputStream(nomJson));
			JsonObject tournee = reader.readObject();
			nbStations = tournee.getInt("nbStations");
			stations = JsonController.loadStations(nomJson);
			ReleveController.initialize(stations, nomJson);
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
				releve.setTextAlignment(TextAlignment.CENTER);
				releve.setOnMousePressed(pressed);
				releve.setOnMouseReleased(release);
				Stations.getChildren().add(releve);
				labels.add(releve);
				if(JsonController.estReleveSaisi(nomJson, stations[compteur].getInt("idStation"))){
					releveDejaSaisi=JsonController.getReleve(nomJson, stations[compteur].getInt("idStation"));
					if(ReleveController.existeSeuilBas(compteur) && releveDejaSaisi>=stations[compteur].getInt("seuilBas")){
						correctBas=true;
					}
					if(ReleveController.existeSeuilHaut(compteur) && releveDejaSaisi<=stations[compteur].getInt("seuilHaut")){
						correctHaut=true;
					}
					if(ReleveController.existeSeuilBas(compteur)&& ReleveController.existeSeuilHaut(compteur)){
						if(correctBas && correctHaut){
							releveDejaSaisiNormale=true;
						}
						else{
							releveDejaSaisiAnormale=true;
						}
					}
					else if((ReleveController.existeSeuilBas(compteur) && correctBas) || (ReleveController.existeSeuilHaut(compteur) && correctHaut) || (!ReleveController.existeSeuilHaut(compteur) && !ReleveController.existeSeuilBas(compteur)) ){
						releveDejaSaisiNormale=true;
					}
					else{
						releveDejaSaisiAnormale=true;
					}
					
					if(releveDejaSaisiNormale==true){
						labels.get(compteur).setText(stations[compteur].getString("nomStation")+"\n"+releveDejaSaisi+" "+stations[compteur].getString("unite")+"\nValide");
						labels.get(compteur).setStyle("-fx-background-color: green; -fx-border-style: solid;");
						releveDejaSaisiNormale=false;
						correctHaut=false;
						correctBas=false;
					}
					
					if(releveDejaSaisiAnormale==true){
						labels.get(compteur).setText(stations[compteur].getString("nomStation")+"\n"+releveDejaSaisi+" "+stations[compteur].getString("unite")+"\nAnormale");
						labels.get(compteur).setStyle("-fx-background-color: orange; -fx-border-style: solid;");
						releveDejaSaisiAnormale=false;
						correctHaut=false;
						correctBas=false;
					}
				}
				compteur++;
			}
			currentPos = 0;
			charge(0);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Gere l'evenement quand le bouton de la souris est presse
	 */
	EventHandler<MouseEvent> pressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			x = e.getSceneX();
			y = e.getSceneY();
		}
	};
	/**
	 * Gere l'evenement quand le outon de la souris est relache
	 */
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
	 * @param numStation
	 */
	public void charge(int numStation) {
		unite.setFont(new Font(24.0));
		erreur.setVisible(false);
		seuilBas=stations[numStation].getInt("seuilBas");
		seuilHaut=stations[numStation].getInt("seuilHaut");
		paramFonc=stations[numStation].getString("paramFonc");
		instruLongue=stations[numStation].getString("instructionsLongues");
		nom.setText(stations[numStation].getString("nomStation"));
		instrCourte.setText(stations[numStation].getString("instructionsCourtes"));
		unite.setText(stations[numStation].getString("unite"));
		if(unite.getText().length()>=12){
			unite.setFont(new Font(15));
		};
		seuil.setText(ReleveController.seuil(numStation));
		releve.setText("");
		commentaire.setText("");
		if(JsonController.estReleveSaisi(nomJson, stations[currentPos].getInt("idStation"))){
			releve.setText(Double.toString(JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation"))));
			commentaire.setText(JsonController.getCommentaire(nomJson, stations[currentPos].getInt("idStation")));
		}
		afficherHistorique(stations[numStation].getInt("idStation"));
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
			seuilAff=ReleveController.seuil2(currentPos);
			if (ecartSeuil!=0){
				verifierReleve();
			}
			else{
				releveNormale(currentPos);
				passerSuivant();
			}
		}
	}
	/**
	 * Ouvre la fenetre qui permet de valider ou non une valeur anormale
	 */
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
	/**
	 * Permet de passer � la station suivante au niveau du scroll pane
	 */
	public void passerSuivant(){
		if(currentPos+1!=nbStations){
			currentPos++;
			charge(currentPos);
			releve.setText("");
			commentaire.setText("");
			scroll.setHvalue((double)currentPos/((double)nbStations-4));
		}
	}
	/**
	 * Charge les valeurs de la station suivante
	 */
	public void chargerSuivant(){
		if (doitChargerSuivant){
			releveAnormale(currentPos);
			passerSuivant();
			doitChargerSuivant=false;
		}
	}
	/**
	 * Affiche les d�tails
	 */
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
	/**
	 * V�rifie si la valeur passe en parametre est un double ou pas
	 * @param chaine
	 * @return booleen
	 */
	public boolean estUnDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	/**
	 * Code couleur vert et affichage du resultat si la valeur est normale
	 * @param pos
	 */
	public void releveNormale(int pos){
		labels.get(pos).setText(stations[pos].getString("nomStation")+"\n"+releve.getText()+" "+unite.getText()+"\nValide");
		labels.get(pos).setStyle("-fx-background-color: green; -fx-border-style: solid;");
	}
	/**
	 * Code coumeur orange et affichage du resultat si la valeur est anormale
	 * @param pos
	 */
	public void releveAnormale(int pos){
		labels.get(pos).setText(stations[pos].getString("nomStation")+"\n"+releve.getText()+" "+unite.getText()+"\nAnormale");
		labels.get(pos).setStyle("-fx-background-color: orange; -fx-border-style: solid;");
	}
	/**
	 * Fin des releves et passage vers la fenetre de validation finale
	 */
	public void fin(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ValidationFinale.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Validation");
	        dialog.show();
	        dialog.setOnHiding(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	dialog.close();
	            	currentPos = 0;
	            	charge(0);
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * Affiche l'historique
	 * @param idStation
	 */
	public void afficherHistorique(int idStation){
		Historique.getChildren().clear();
		JsonObject[] historique=JsonController.loadHistoriques(nomJson,idStation);
		for(JsonObject obj : historique){
			Label valhist = new Label(obj.getString("date")+"\n"+obj.getJsonNumber("valeur").doubleValue());
			valhist.setPrefHeight(80);
			valhist.setPrefWidth(140);
			valhist.setStyle("-fx-border-style: solid;");
			valhist.setWrapText(true);
			valhist.setId(String.valueOf(compteur));
			valhist.setAlignment(Pos.CENTER);
			valhist.setTextAlignment(TextAlignment.CENTER);
			Historique.getChildren().add(valhist);
		}
	}
}
