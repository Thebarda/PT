package controlleur;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class ImportationController {
		
	@FXML
	private TextField route;
	
	@FXML 
	private Label erreurRoute;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	protected File file;

	
	
	 
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
     }
 
	/**
	 * Fonction qui permet de valider une centrale, et donc de l'ajouter à la base
	 * On ajoute la centrale que si les champs nom et localisation sont non vide
	 */
	public void ValiderImportation(){
		boolean estValide=true;
		resetErreur();
		if(estVide(route)){
			erreurRoute.setText("Erreur : veuillez ssp�cifier votre fichier ");
			estValide=false;
		}		
		if(estValide == true)
		{
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler l'exportation d'un fichier, et cache la fenetre correspondante
	 */
	public void annulerImportation()
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

