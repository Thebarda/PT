package controleur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import vue.Main;

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
	
	final String Santa_Claus_Is_Coming_To_Town =
            "You better watch out\n"
            + "You better not cry\n"
            + "Better not pout\n"
            + "I'm telling you why\n"
            + "Santa Claus is coming to town\n"
            + "\n"
            + "He's making a list\n"
            + "And checking it twice;\n"
            + "Gonna find out Who's naughty and nice\n"
            + "Santa Claus is coming to town\n"
            + "\n"
            + "He sees you when you're sleeping\n"
            + "He knows when you're awake\n"
            + "He knows if you've been bad or good\n"
            + "So be good for goodness sake!\n"
            + "\n"
            + "O! You better watch out!\n"
            + "You better not cry\n"
            + "Better not pout\n"
            + "I'm telling you why\n"
            + "Santa Claus is coming to town\n"
            + "Santa Claus is coming to town\n";
     
	
	 
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
         
         //Show save file dialog
         file = fileChooser.showSaveDialog(null);
         
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
			erreurRoute.setText("Erreur : veuillez sp�cifier votre r�pertoire ");
			estValide=false;
		}	
		//if(estValide == true)
		{
			System.out.println(file);
			 if(file != null){
             SaveFile(Santa_Claus_Is_Coming_To_Town, file);
         }
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
	private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             System.out.println(file);
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
	
}

