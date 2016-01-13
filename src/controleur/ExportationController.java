package controleur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vue.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import modele.Tournee;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class ExportationController {
		
	@FXML
	private TextField route;
	
	@FXML 
	private Label erreurRoute;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	 File file;

	 static Tournee tournee ;
	 
	 static Stage stage;
	
	
	 public static void init (Tournee tourne ,Stage stage1 )
	 {
		 tournee=tourne;
		 stage = stage1;
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
	

     public void Exportation() {
    	 FileChooser fileChooser = new FileChooser();
    	 
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
         fileChooser.getExtensionFilters().add(extFilter);
         
         
         File file = fileChooser.showSaveDialog(stage);
         route.setText(file.getPath());
     }
 
	/**
	 * Fonction qui permet de valider une centrale, et donc de l'ajouter à la base
	 * On ajoute la centrale que si les champs nom et localisation sont non vide
	 */
	public void ValiderExportation(){
		boolean estValide=true;
		resetErreur();
		if(estVide(route)){
			erreurRoute.setText("Erreur : veuillez specifier votre fichier ");
			estValide=false;
		}		
		if(estValide == true)
		{
			if(!route.getText().endsWith(".json")){
				route.setText(route.getText()+".json");
			}
			JsonController.exporterTournee(route.getText(),tournee);
               
            
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler l'exportation d'un fichier, et cache la fenetre correspondante
	 */
	public void annulerExportation()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurRoute.setText("");
	}

	
	
}

