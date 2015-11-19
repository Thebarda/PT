package controleur;


import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
import modele.Equipement;
import modele.ModeleTournee;
import vue.Main;
import javafx.scene.Scene;

/**
 * Controleur relatif à l'interface de gestion des modèle de tournées
 */
public class GererModeleTourneeController {
	
	@FXML
	ComboBox<Centrale> listeCentrale;
	
	@FXML
	TableView<ModeleTournee> tableModeleTournee;
	
	@FXML
	TableColumn<ModeleTournee, Integer> ID;
	
	@FXML
	TableColumn<ModeleTournee, String> Nom;
	
	@FXML
	TableColumn<ModeleTournee, String> Description;
	
	@FXML
	Button Ajouter;
	
	ObservableList<Centrale> centrale=CentraleControler.loadCentrales();
	
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		Ajouter.setVisible(false);
		listeCentrale.setItems(centrale);
	}
	/**
	 * Fonction qui permet d'ouvrir la popup d'ajout de modèle de tournées.
	 * Une fois que l'utilisateur à annuler l'ajout ou ajouter un modèle de tournées , on recharge le tableau et ferme la popup
	 */
	public void ajouterModeleTournees(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Equipement.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        CreationEquipementController controller = loader.getController();
	        controller.init(listeCentrale.getValue());
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerModeleTournees();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Fonction qui permet de lister les modèle de tournees dans le tableau quand on selectionne une centrale dans la combobox
	 */
	public void ListerModeleTournees(){
		Ajouter.setVisible(true);
		ObservableList<ModeleTournee> modeleTournee=ModeleTourneeController.loadModeleTournee(listeCentrale.getValue().getId());
		Nom.setCellValueFactory(new PropertyValueFactory<ModeleTournee, String>("nom"));
		ID.setCellValueFactory(new PropertyValueFactory<ModeleTournee, Integer>("id"));
		Description.setCellValueFactory(new PropertyValueFactory<ModeleTournee, String>("description"));
		tableModeleTournee.setItems(modeleTournee);
	}
}
