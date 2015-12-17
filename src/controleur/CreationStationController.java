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
	private TextField valeurNormale;
	
	@FXML
	private TextField MISH;
	
	@FXML
	private Label erreurMISH;
	
	@FXML
	private TextField paramFonc;
	
	@FXML
	private Label erreurParamFonc;
	
	@FXML 
	private Label erreurValeurNormale;
	
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
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private int idEquipement;
	
	
	@FXML
	private Button ajouter;
	
	ObservableList<Unite> unite=UniteController.loadUnites();
	
	@FXML
	TableColumn<Unite, Integer> ID;
	
	@FXML
	TableColumn<Unite, String> Nom;
		
	@FXML
	private void initialize() {
		ListeUnite.setItems(unite);
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
	 * Fonction qui permet de determiner si une combobox d'unite est vide
	 * @param combo
	 * 		combobox a tester
	 * @return
	 * 		vrai si elle est vide, faux sinon
	 */
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
	 * Fonction qui permet de determiner si une chaine est un entier
	 * @param chaine
	 * 		chaine a tester
	 * @return
	 * 		vrai si c'est un entier, faux sinon
	 */
	public boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	/**
	 * Fonction qui permet de valider une station, et donc de l'ajouter à la base
	 * On ajoute la station que si les champs nom,instructions courtes,unité, seuil haut, seuil bas instruction longues sont non vide
	 */
	public void ValiderStation(){
		boolean estValide=true;
		Integer haut=null;
		Integer bas=null;
		Integer normale=null;
		resetErreur();
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(instructionCourtes.getText().isEmpty())
		{
			erreurinstrCourtes.setText("Erreur : l'instruction courte est vide");
			estValide=false;
		}
		if(estVideComboBox(ListeUnite))
		{
			erreurUnite.setText("Erreur : l'unite est vide");
			estValide=false;
		}
		if(estVide(MISH)){
			erreurMISH.setText("Erreur : MISH est vide");
			estValide=false;
		}
		if(estVide(paramFonc)){
			erreurParamFonc.setText("Erreur : le parametre de fonctionnement est vide");
			estValide=false;
		}
		if(!estVide(MISH) && (MISH.getText()!="1" || MISH.getText()!="0")){
			erreurMISH.setText("Erreur : MISH doit être un booleen");
			estValide=false;
		}
		if(!seuilHaut.getText().isEmpty() && !estUnEntier(seuilHaut.getText())){
			erreurSeuilHaut.setText("Erreur : un nombre doit être saisi");
			estValide=false;
		}
		
		if(!seuilBas.getText().isEmpty() &&!estUnEntier(seuilBas.getText())){
			erreurSeuilBas.setText("Erreur : un nombre doit être saisi");
			estValide=false;
		}
		if(!valeurNormale.getText().isEmpty() &&!estUnEntier(valeurNormale.getText())){
			erreurValeurNormale.setText("Erreur : un nombre doit être saisi");
			estValide=false;
		}
		if(!seuilBas.getText().isEmpty() && estUnEntier(seuilBas.getText()) && !seuilHaut.getText().isEmpty() && estUnEntier(seuilHaut.getText()) && Integer.parseInt(seuilHaut.getText())<Integer.parseInt(seuilBas.getText())){
			erreurSeuilBas.setText("Erreur : le seuil bas doit être inférieur au seuil haut !");
			estValide=false;
		}
		if(!valeurNormale.getText().isEmpty() && estUnEntier(valeurNormale.getText()) && !seuilHaut.getText().isEmpty() && estUnEntier(seuilHaut.getText()) && Integer.parseInt(seuilHaut.getText())<Integer.parseInt(valeurNormale.getText())){
			erreurValeurNormale.setText("Erreur : la valeur doit être inférieur au seuil haut !");
			estValide=false;
		}
		if(!valeurNormale.getText().isEmpty() && estUnEntier(valeurNormale.getText()) && !seuilBas.getText().isEmpty() && estUnEntier(seuilBas.getText()) && Integer.parseInt(seuilBas.getText())>Integer.parseInt(valeurNormale.getText())){
			erreurValeurNormale.setText("Erreur : la valeur doit être supérieur au seuil bas !");
			estValide=false;
		}
		
		if(estValide==true)
		{
			if(!seuilHaut.getText().isEmpty()){
				haut=Integer.parseInt(seuilHaut.getText());
			}
			if(!seuilBas.getText().isEmpty()){
				bas=Integer.parseInt(seuilBas.getText());
			}
			if(!valeurNormale.getText().isEmpty()){
				normale=Integer.parseInt(valeurNormale.getText());
			}
			StationController.addStation( nom.getText(), instructionCourtes.getText(),instructionLongues.getText(),ListeUnite.getValue().getId(),haut,bas,idEquipement,paramFonc.getText(),normale,MISH.getText());
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une station, et ferme donc la fenêtre correspondante
	 */
	public void annulerStation()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	/**
	 * Fonction qui permet l'initialiser l'id de l'équipement choisi precedemment pour la création d'une station
	 * @param equipement
	 * 		Equipement choisi
	 */
	public void init(Equipement equipement)
	{
		idEquipement=equipement.getId();
	}
	
	/**
	 * Fonction qui permet d'ouvrir la fenêtre pour créer une unité
	 * On gère aussi la fermeture de cette fenêtre, avec la mise à jour de la liste des Unité
	 */
	public void ajouterUnit(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Unit.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
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
	/**
	 * Fonction qui permet de rafraichir la liste des unités
	 */
	public void ListerUnit(){
		ObservableList<Unite> unit=UniteController.loadUnites();
		ListeUnite.setItems(unit);
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurNom.setText("");
		erreurinstrCourtes.setText("");
		erreurUnite.setText("");
		erreurSeuilBas.setText("");
		erreurSeuilHaut.setText("");
		erreurValeurNormale.setText("");
		erreurParamFonc.setText("");
		erreurMISH.setText("");
	}
	
}
