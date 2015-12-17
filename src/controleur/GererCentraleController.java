package controleur;


import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Centrale;
import vue.Main;
import javafx.scene.Scene;

/**
 * Controleur relatif à l'interface de gestion des centrale
 */
public class GererCentraleController {
	
	@FXML
	TableView<Centrale> tableCentrale;
	
	@FXML
	TableColumn<Centrale, Integer> ID;
	
	@FXML
	TableColumn<Centrale, String> Nom;
	
	@FXML
	TableColumn<Centrale, String> codeIdentiteNationale;
	
	ObservableList<Centrale> data=CentraleControler.loadCentrales();
	
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser le tableaux de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		ID.setCellValueFactory(new PropertyValueFactory<Centrale, Integer>("id"));
		Nom.setCellValueFactory(new PropertyValueFactory<Centrale, String>("nom"));
		codeIdentiteNationale.setCellValueFactory(new PropertyValueFactory<Centrale, String>("identiteNationale"));
		data.removeAll(data);
		data=CentraleControler.loadCentrales();
		tableCentrale.setItems(data);
	}
	/**
	 * Fonction qui permet d'ouvrir la popup d'ajout de Centrale.
	 * Quand l'utisateur annule l'ajout ou ajoute une centrale, on actualise le tableaux de centrale et ferme la popup
	 */
	public void ajouterCentrale(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Centrale.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Creation d'une centrale");
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	initialize();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
