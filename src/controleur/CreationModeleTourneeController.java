package controleur;



import java.awt.TextArea;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modele.Centrale;
import modele.Equipement;
import modele.ModeleTournee;
import modele.Station;
import modele.Unite;
import vue.Main;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class CreationModeleTourneeController {
	
	ModeleTournee modele;
	private Map<Integer,Station> map;
	private int rangActuel=0;
		
	@FXML
	private TextField nom;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private TextField description  ;
	
	@FXML 
	private Label erreurDescription;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	TableView<ObservableMap.Entry<Integer,Station>> tableStation;
	
	@FXML
	TableColumn<ObservableMap.Entry<Integer,Station>, Integer> ID;
	
	@FXML
	TableColumn<ObservableMap.Entry<Integer,Station>, Integer> Nom;
	
	@FXML
	TableColumn<ObservableMap.Entry<Integer,Station>, Integer> Ordre;
	
	private int idCentrale ;
	
	ObservableList<ModeleTournee> data=ModeleTourneeController.loadAllModeleTournee(idCentrale);

	Map<Integer, Station> stations=new HashMap<Integer, Station>();
	ObservableMap<Integer,Station> Ostations=FXCollections.observableHashMap();
	ObservableList<ObservableMap.Entry<Integer,Station>> rowMaps;
	public void init2()
	{
		
		 rowMaps = FXCollections.observableArrayList(Ostations.entrySet());
	
	}
	
	
	public void init(Centrale centrale)
	{
		//Ostations= ModeleTourneeController.loadStationIntoModeleTournee(modele);
		idCentrale=centrale.getId();
	}
	
	public void initFils(ObservableMap<Integer,Station> nouvelleMap,int nouveauRang)
	{
		Ostations=nouvelleMap;
		rangActuel=nouveauRang;
		
	}
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVideField(TextField texte)
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
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextArea) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVide(TextArea textea)
	{
		if(textea.getText().isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Fonction qui permet de valider un modele de tournee , et donc de l'ajouter à la base
	 * On ajoute le modele de tournee que si le champs nom est non vide
	 */
	public void ValiderModeleTournee(){
		boolean estValide=true;
		resetErreur();
		if(estVideField(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(estVideField(description)){
			erreurNom.setText("Erreur : la description est vide");
			estValide=false;
		}
		if(ID.getText() == null){
			erreurNom.setText("Erreur : il faut des station pour une tournee ");
			estValide=false;
		}
		if(estValide == true)
		{
			ModeleTournee.addModeleTournee(nom.getText(),description.getText(),Ostations);
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une unité, et cache la fenêtre correspondante
	 * En réalité. La page est fermé par le controller gérant la page de gestion de Station
	 */
	public void annulerModeleTournee()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurNom.setText("");
	}
	public void ajouterStationTournee()
	{
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ajout_Station.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        AjoutStationController controller = loader.getController();
	        controller.init(idCentrale, map,rangActuel,this,Ostations);
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	//ListerStation();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void ListerStation(){
		
		ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getKey()).asObject();
            }
        });
		
		Nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Station>, ObservableValue<Station>>() {

            @Override
            public ObservableValue<Station> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Station> p) {
                return new SimpleObjectProperty<Station>(p.getValue().getValue());
            }
        });
		Ordre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Station>, ObservableValue<Station>>() {

            @Override
            public ObservableValue<Station> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Station> p) {
                return new SimpleObjectProperty<Station>(p.getValue().getValue());
            }
        });
		data.removeAll(data);
		data=ModeleTourneeController.loadAllModeleTournee(idCentrale);
		tableStation.setItems(rowMaps);
	}
}
