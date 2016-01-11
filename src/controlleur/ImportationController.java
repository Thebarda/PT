package controlleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import modele.Station;

/**
 * Controleur relatif Ã  l'interface de crÃ©ation de centrale
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
	
	protected File file;
	JsonReader reader;
	String chemin;
	JsonObject tournee;
	JsonArray releves;
	JsonArray stations;
	JsonObject[] objects;
	
	public void initialize(){
		station.setVisible(false);
		nomTournee.setVisible(false);
		description.setVisible(false);
		LnomTournee.setVisible(false);
		Ldescription.setVisible(false);
	}
	 
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) Ã  analyser
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
         chemin = file.getAbsolutePath();
         route.setText(file.getPath());
     }
 
	/**
	 * Fonction qui permet de valider une centrale, et donc de l'ajouter Ã  la base
	 * On ajoute la centrale que si les champs nom et localisation sont non vide
	 */
	public void ValiderImportation(){
		boolean estValide=true;
		resetErreur();
		try {
			reader = Json.createReader(new FileInputStream(route.getText()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		tournee = reader.readObject();
		if(estVide(route)){
			erreurRoute.setText("Erreur : veuillez sspécifier votre fichier ");
			estValide=false;
		}
		/*if(tournee.getString("nomApp")!="relevEDF"){
			estValide=false;
			erreurRoute.setText("Erreur : fichier n'appartenant pas à l'application");
		}
		if(tournee.getBoolean("estComplete")==true){
			estValide=false;
			erreurRoute.setText("Erreur : fichier déjà complété");
		}*/
		if(estValide==true){
			station.setVisible(true);
			nomTournee.setVisible(true);
			description.setVisible(true);
			LnomTournee.setVisible(true);
			Ldescription.setVisible(true);
		}
		releves = tournee.getJsonArray("Releves");
		stations = tournee.getJsonArray("stations");
		nomTournee.setText(tournee.getString("nomApp"));
		description.setText(tournee.getString("dateExport"));
	}	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurRoute.setText("");
	}
	
}

