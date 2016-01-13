package controlleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modele.Station;
import vue.Main;

/**
 * Controleur relatif a� l'interface de l'importation du fichier json
 */
public class ImportationController {
		
	@FXML
	private TextField route;
	
	@FXML 
	private Label erreurRoute;
	
	@FXML
	private Button valider;
	
	@FXML
	private TableView<Station> station;
	
	
	@FXML
	private TableColumn<Station, String> nom;
	
	@FXML
	private TableColumn<Station, String> instru;
	
	@FXML
	private Label nomTournee;
	
	@FXML
	private Label description;
	
	@FXML
	private Label LnomTournee;
	
	@FXML
	private Label Ldescription;
	
	@FXML
	private Label Ldescription1;
	
	@FXML
	private Label id;
	
	@FXML
	private Label IDD;
	
	@FXML
	private Label Complete;
	
	@FXML
	ObservableList<Station> listStations;
	
	protected File file;
	JsonReader reader;
	public static String chemin;
	JsonObject tournee;
	JsonArray releves;
	JsonArray stations;
	JsonObject[] objects;
	/**
	 * Initialisation
	 */
	public void initialize(){
		valider.setVisible(false);
		station.setVisible(false);
		nomTournee.setVisible(false);
		description.setVisible(false);
		LnomTournee.setVisible(false);
		Ldescription.setVisible(false);
		id.setVisible(false);
		IDD.setVisible(false);
		Ldescription1.setVisible(false);
		Complete.setVisible(false);
	}
	 
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVide(TextField texte)
	{
		if(texte.getText().isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Fonction qui importe un fichier
	 */
     public void Importation() {
    	 FileChooser fileChooser = new FileChooser();
    	 
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
         fileChooser.getExtensionFilters().add(extFilter);
        
         //Show open file dialog
         file = fileChooser.showOpenDialog(null);
         route.setText(file.getPath());
         chemin = route.getText();
     	try {
			reader = Json.createReader(new FileInputStream(route.getText()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		tournee = reader.readObject();
		valider.setVisible(true);
 			station.setVisible(true);
 			nomTournee.setVisible(true);
 			description.setVisible(true);
 			LnomTournee.setVisible(true);
 			Ldescription.setVisible(true);
 			id.setVisible(true);
 			IDD.setVisible(true);
 			Ldescription1.setVisible(true);
 			Complete.setVisible(true);
 			
        releves = tournee.getJsonArray("Releves");
 		nomTournee.setText(tournee.getString("nomModele"));
 		description.setText(tournee.getString("descModele"));
 		id.setText(""+tournee.getInt("idTournee"));
 		listStations = JsonController.loadObservableStations(route.getText());
 		nom.setCellValueFactory(new PropertyValueFactory<Station, String>("nom"));
 		instru.setCellValueFactory(new PropertyValueFactory<Station, String>("instructionCourte"));
 		station.setItems(listStations);
 		if(releves.toArray(new JsonObject[0]).length==0)
 		{
 			Complete.setText("non");
 		}
 		else
 		{
 			Complete.setText("oui");
 		}
 		resetErreur();
     }
 
	/**
	 * Fonction qui permet de valider l'import
	 */
	public void ValiderImportation(){
		boolean estValide=true;
		resetErreur();
	
		if(estVide(route)){
			erreurRoute.setText("Erreur : veuillez specifier votre fichier ");
			estValide=false;
		}
		if(tournee.getString("nomApp")=="relevEDF"){
			estValide=false;
			erreurRoute.setText("Erreur : fichier n'appartenant pas a l'application");
		}
		if(tournee.getInt("estComplete")==1){
			estValide=false;
			erreurRoute.setText("Erreur : fichier deja complete");
		}
		if(estValide==true){
			toSaisirReleve();	
		}
		
		
	}	

	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurRoute.setText("");
	}
	/**
	 * Fonction qui charge et lance la fenetre de la saisie des releves
	 */
	public void toSaisirReleve(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("saisirReleve.fxml"));
		try {
			VBox page = (VBox) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Saisir un releve");
	        dialog.show();
	        dialog.getIcons().add(new Image("file:logo.png"));
	        Main.primaryStage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
}

