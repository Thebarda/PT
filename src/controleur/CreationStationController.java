package controleur;



import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Centrale;
import modele.Equipement;
import modele.Unite;
import vue.Main;

public class CreationStationController {
		
	@FXML
	private TextField nom;
	
	@FXML
	private TextArea instructionCourtes ;
	
	@FXML
	private TextArea instructionLongues ;
	
	@FXML
	private ComboBox<Unite> ListeUnite;
	
	@FXML
	private TextField seuilHaut;
	
	@FXML
	private TextField seuilBas;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private Label erreurinstrCourtes;
	
	@FXML
	private Label erreurinstrLongues;
	
	@FXML
	private Label erreurUnite;
	
	@FXML
	private Label erreurSeuilHaut;
	
	@FXML
	private Label erreurSeuilBas;
	
	@FXML
	private Label erreurFrequence;
		
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private int idEquipement;
	
	@FXML
	private ComboBox<Integer> listeFrequence;
	
	@FXML
	private Button ajouter;
	
	ObservableList<Unite> unite=UniteController.loadUnites();
	
	@FXML
	TableColumn<Unite, Integer> ID;
	
	@FXML
	TableColumn<Unite, String> Nom;
	
	ObservableList<Integer> frequence=FXCollections.observableArrayList(1,3,6,12);
	
	@FXML
	private void initialize() {
		ListeUnite.setItems(unite);
		listeFrequence.setItems(frequence);
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
	public Boolean estVideComboBox(ComboBox<Unite> combo)
	{
		if(combo.getValue()==null)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Fonction qui permet de valider une station, et donc de l'ajouter à la base
	 * On ajoute la station que si les champs nom,instructions courtes,unité, seuil haut, seuil bas instruction longues sont non vide
	 */
	public void ValiderStation(){
		boolean estValide=true;
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(instructionCourtes.getText().isEmpty())
		{
			erreurinstrCourtes.setText("Erreur : l'instruction courte est vide");
			estValide=false;
		}
		if(instructionLongues.getText().isEmpty())
		{
			erreurinstrLongues.setText("Erreur : l'instruction longues est vide");
			estValide=false;
		}
		if(estVideComboBox(ListeUnite))
		{
			erreurUnite.setText("Erreur : l'unite est vide");
			estValide=false;
		}
		if(estVide(seuilHaut))
		{
			erreurSeuilHaut.setText("Erreur : le seuil haut est vide");
			estValide=false;
		}
		if(estVide(seuilBas))
		{
			erreurSeuilBas.setText("Erreur : le seuil bas est vide");
			estValide=false;
		}
		if(listeFrequence.getValue()==null){
			erreurFrequence.setText("Erreur : la fréquence est vide");
			estValide=false;
		}
		
		
		if(estValide==true)
		{
			StationController.addStation( nom.getText(), instructionCourtes.getText(),instructionLongues.getText(),ListeUnite.getValue().getId(),listeFrequence.getValue(),Integer.parseInt(seuilBas.getText()),Integer.parseInt(seuilHaut.getText()),idEquipement);
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une centrale, et ferme donc la fenêtre correspondante
	 */
	public void annulerStation()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	public void init(Equipement equipement)
	{
		idEquipement=equipement.getId();
	}
	
	public void ajouterUnit(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Unit.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        CreationUnitController controller = loader.getController();
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerUnit();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public void ListerUnit(){
		ObservableList<Unite> unit=UniteController.loadUnites();
		ListeUnite.setItems(unit);
	}
	
	
}
