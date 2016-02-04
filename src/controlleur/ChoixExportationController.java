package controlleur;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vue.Main;

public class ChoixExportationController {
	@FXML
	private TextField chemin;
	@FXML
	private Button quit;
	@FXML 
	private Label erreurRoute;
	@FXML
	private Button valider;
	@FXML 
	private Button annuler;
	
	public static String route;
	
	private static Stage stage;
	
	public static boolean complete;
	/**
	 * Initialisation
	 */
	@FXML
	public void initialize(){
		route = JsonController.fichier;
		if(route != null)
			chemin.setText(route);
	}
	
	public static void init (Stage stage1, boolean complete1)
	{
		stage = stage1;
		complete = complete1;
	}
	
	/**
	 * Ferme l'application donc toutes les fenetres d ouvertes
	 */
	public void fin(){
		Platform.exit();
	}
	
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) aï¿½ analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVide(TextField texte)
	{
		return texte.getText().isEmpty();
	}
	
	/**
	 * Fonction qui permet de valider l'import
	 */
	public void ValiderImportation(){
		boolean estValide=true;
		resetErreur();
	
		if(estVide(chemin)){
			erreurRoute.setText("Erreur : veuillez specifier votre fichier ");
			estValide=false;
		}
		if(estValide==true){
			
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
	 * Fonction qui charge et lance la fenetre de fin
	 */
	public void toFin(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("Exportation.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Fichier bien exporté !");
	        dialog.show();
	        dialog.getIcons().add(new Image("file:logo.png"));
	        dialog.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent arg0) {
					Platform.exit();
				}
	        });
	        Main.primaryStage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	public void Exportation() {
   	 FileChooser fileChooser = new FileChooser();
		 
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        
        
        File file = fileChooser.showSaveDialog(stage);
        chemin.setText(file.getPath());
    }

	/**
	 * Fonction qui permet de valider une centrale, et donc de l'ajouter Ã  la base
	 * On ajoute la centrale que si les champs nom et localisation sont non vide
	 */
	public void ValiderExportation(){
		boolean estValide=true;
		resetErreur();
		if(estVide(chemin)){
			erreurRoute.setText("Erreur : veuillez specifier votre fichier ");
			estValide=false;
		}		
		if(estValide == true)
		{
			if(!chemin.getText().endsWith(".json")){
				chemin.setText(chemin.getText()+".json");
			}
			route = chemin.getText();
			toFin();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler l'exportation d'un fichier, et cache la fenetre correspondante
	 */
	public void annulerExportation()
	{
		annuler.getParent().getScene().getWindow().hide();
	}
}
