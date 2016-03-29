package controleur;


import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Centrale;
import modele.Equipement;
import vue.Main;
import javafx.scene.Scene;

/**
 * Controleur relatif à l'interface de gestion des équipements
 */
public class GererEquipementController {
	
	@FXML
	ComboBox<Centrale> listeCentrale;
	
	@FXML
	TableView<Equipement> tableEquipement;
	
	@FXML
	TableColumn<Equipement, Integer> ID;
	
	@FXML
	TableColumn<Equipement, String> Nom;
	
	@FXML
	TableColumn<Equipement, String> Description;
	
	@FXML
	Button Ajouter;
	
	@FXML
	Button supprimer;
	
	@FXML
	Button details;
	
	ObservableList<Centrale> centrale=CentraleControler.loadCentralesNonSupprimees();
	
	static Equipement equipement;
	
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		GererEquipementController.equipement=null;
		GererStationController.station=null;
		GererModeleTourneeController.modele=null;
		GererCentraleController.centrale=null;
		Ajouter.setVisible(false);
		listeCentrale.setItems(centrale);
		supprimer.setVisible(false);
        details.setVisible(false);

		
		tableEquipement.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>()
	    {
	        @Override
	        public void onChanged(Change<? extends Integer> change)
	        {
	            supprimer.setVisible(true);
	            details.setVisible(true);

	            
	        }

	    });
		
	}
	/**
	 * Fonction qui permet d'ouvrir la popup d'ajout d'Equipement.
	 * Une fois que l'utilisateur à annuler l'ajout ou ajouter un équipement , on recharge le tableau et ferme la popup
	 */
	public void ajouterEquipement(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Equipement.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Creation d'une equipement");
	        CreationEquipementController controller = loader.getController();
	        controller.init(listeCentrale.getValue());
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerEquipement();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Fonction qui permet de lister les equipements dans le tableau quand on selectionne une centrale dans la combobox
	 */
	public void ListerEquipement(){
		supprimer.setVisible(false);
        details.setVisible(false);
		Ajouter.setVisible(true);
		ObservableList<Equipement> equip=EquipementController.loadEquipementNonSupprimes(listeCentrale.getValue().getId());
		Nom.setCellValueFactory(new PropertyValueFactory<Equipement, String>("nom"));
		ID.setCellValueFactory(new PropertyValueFactory<Equipement, Integer>("id"));
		Description.setCellValueFactory(new PropertyValueFactory<Equipement, String>("description"));
		tableEquipement.setItems(equip);
	}
	public void supprimer (){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("supprimer.fxml"));
		AnchorPane page;
		try {
			equipement=tableEquipement.getSelectionModel().getSelectedItem();
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Supprimer un equipement");
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	initialize();
	            	dialog.close();
	            	ListerEquipement();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void details (){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("details.fxml"));
		AnchorPane page;
		try {
			equipement=tableEquipement.getSelectionModel().getSelectedItem();
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Plus de details (equipement)");
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	initialize();
	            	dialog.close();
	            	ListerEquipement();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
