package controlleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Station;
import vue.Main;

/**
 * Controleur relatif a  l'interface de l'importation du fichier json
 */
public class ReprendreController {
		
	@FXML
	private Button valider;
	
	@FXML
	private Button autreTournee;
	
	@FXML
	private TableView<Station> station;
	
	
	@FXML
	private TableColumn<Station, String> nom;
	
	@FXML
	private TableColumn<Station, String> valeur;
	
	@FXML
	private Label nomTournee;
	
	@FXML
	private TextArea description;
	
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
	private Label Ldate;
	
	@FXML
	private Label date;
	
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
		file = new File(JsonController.def_fichier_tmp);
       
    	chemin = file.getPath();
    	try {
			reader = Json.createReader(new FileInputStream(chemin));
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
		Ldate.setVisible(true);
		date.setVisible(true);
			
       releves = tournee.getJsonArray("Releves");
		nomTournee.setText(tournee.getString("nomModele"));
		description.setText(tournee.getString("descModele"));
		id.setText(""+tournee.getInt("idTournee"));
		date.setText(tournee.getString("dateExport"));
		listStations = JsonController.loadObservableStations(chemin);
		nom.setCellValueFactory(new PropertyValueFactory<Station, String>("nom"));
		valeur.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Station, String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Station, String> p) {
            	    
            	double value=JsonController.getReleve(chemin, p.getValue().getId());
            	
            	return new SimpleObjectProperty<String>(Double.toString(value));
            }
        });
		station.setItems(listStations);
	}
	 
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) a� analyser
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
	 * Fonction qui charge et lance la fenetre de la saisie des releves
	 */
	public void toSaisirReleve(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("saisirReleve.fxml"));
		try {
			ImportationController.chemin = JsonController.def_fichier_tmp;
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
		
		/**
		 * Fonction qui lance la fenetre d'import de fichier (cas ou l'utilisateur ne veut pas reprendre la saisie)
		 */
		public void toImport(){
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Importation.fxml"));
			try {
				AnchorPane page = (AnchorPane) loader.load();
				Scene dialogScene = new Scene(page);
		        dialog.setScene(dialogScene);
		        dialog.setResizable(false);
				dialog.setTitle("Importer une tournee");
		        dialog.show();
		        dialog.getIcons().add(new Image("file:logo.png"));
		        Main.primaryStage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}

