package controleur;

import java.io.IOException;

import javax.json.JsonObject;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Tournee;
import vue.Main;
/**
 * Classe qui gere l'exportation d'un export de tournee si cette tournee a dejà etait exportee
 */
public class ValiderExportController {

	@FXML
	Label date;
	
	static Tournee tournee ;
	 
	static Stage stage;
	
	public static void init (Tournee tourne ,Stage stage1 )
	 {
		 tournee=tourne;
		 stage = stage1;
	 }
	/**
	 * Initialisation
	 */
	@FXML
	private void initialize(){
		date.setText(PlanningController.dateExport);
	}
	
	public void annuler(){
		date.getParent().getScene().getWindow().hide();
	}
	
	public void valider(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("Exportation.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.show();
	        ExportationController.init(tournee,stage);
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	dialog.close();
	            }
	        });
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		date.getParent().getScene().getWindow().hide();
		
	}
}
