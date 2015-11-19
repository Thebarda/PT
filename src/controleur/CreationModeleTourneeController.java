package controleur;



import java.awt.TextArea;
import java.io.IOException;
import java.util.Map;

import javafx.collections.ObservableList;
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
	
	private Map<Integer,Station> map;
	private int rangActuel=0;
		
	@FXML
	private TextField nom;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private TextArea description ;
	
	@FXML 
	private Label erreurDescription;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	TableView<ModeleTournee> tableStation;
	
	@FXML
	TableColumn<ModeleTournee, Integer> ID;
	
	@FXML
	TableColumn<ModeleTournee, String> Nom;
	
	@FXML
	TableColumn<ModeleTournee, Integer> Ordre;
	
	private int idCentrale ;
	
	ObservableList<ModeleTournee> data=ModeleTourneeController.loadAllModeleTournee(idCentrale);

	
	@FXML
	private int idUnit;
	
	
	public void init(Centrale centrale)
	{
		idCentrale=centrale.getId();
	}
	
	public void initFils(Map<Integer,Station> nouvelleMap,int nouveauRang)
	{
		map=nouvelleMap;
		rang=nouveauRang;
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
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextArea) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVide(TextArea texte)
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
	 * Fonction qui permet de valider un modele de tournee , et donc de l'ajouter à la base
	 * On ajoute le modele de tournee que si le champs nom est non vide
	 */
	public void ValiderModeleTournee(){
		boolean estValide=true;
		resetErreur();
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(estVide(description)){
			erreurNom.setText("Erreur : la description est vide");
			estValide=false;
		}
		if(ID.getText() == null){
			erreurNom.setText("Erreur : il faut des station pour une tournee ");
			estValide=false;
		}
		if(estValide == true)
		{
			ModeleTournee.addModeleTournee(nom.getText(),description.getText(),);
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
	        controller.init(idCentrale, map,rangActuel,this);
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerStation();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void ListerStation(){
	ID.setCellValueFactory(new PropertyValueFactory<Station, Integer>("id"));
	Nom.setCellValueFactory(new PropertyValueFactory<Station, String>("nom"));
	Ordre.setCellValueFactory(new PropertyValueFactory<Station, String>("ordre "));
	data.removeAll(data);
	data=ModeleTourneeController.loadAllModeleTournee(idCentrale);
	tableStation.setItems(data);
}
}
