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
import modele.Station;
import vue.Main;
import javafx.scene.Scene;

/**
 * Controleur relatif à l'interface de gestion des stations
 */
public class GererStationController {
	
	@FXML
	ComboBox<Centrale> listeCentrale;
	
	@FXML
	ComboBox<Equipement> listeEquipement;
	
	@FXML
	TableView<Station> tableStation;
	
	@FXML
	TableColumn<Station, Integer> ID;
	
	@FXML
	TableColumn<Station, String> Nom;
	
	@FXML
	TableColumn<Station, String> Unite;
	
	@FXML
	Button Ajouter;
	@FXML
	Button supprimer;
	
	ObservableList<Centrale> centrale=CentraleControler.loadCentralesNonSupprimees();
	
	static Station station;

	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		Ajouter.setVisible(false);
		listeCentrale.setItems(centrale);
		supprimer.setVisible(false);
		tableStation.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>()
	    {
	        @Override
	        public void onChanged(Change<? extends Integer> change)
	        {
	            supprimer.setVisible(true);
	        }

	    });
	}
	/**
	 * Fonction qui permet d'ouvrir la popup d'ajout de Stations.
	 * Une fois que l'utilisateur à annuler l'ajout ou ajouter une stations, on recharge le tableau et ferme la popup
	 */
	public void ajouterStation(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Station.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Creation d'une station");
	        CreationStationController controller = loader.getController();
	        controller.init(listeEquipement.getValue());
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerStations();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Fonction qui permet de lister les stations dans le tableau quand on selectionne un équipement dans la combobox (après avoir selectionné une centrale)
	 */
	public void ListerStations(){
		if(listeEquipement.getValue()!=null){
			Ajouter.setVisible(true);
			ObservableList<Station> stations=StationController.loadStationNonSupprimees(listeEquipement.getValue().getId());
			ID.setCellValueFactory(new PropertyValueFactory<Station, Integer>("id"));
			Nom.setCellValueFactory(new PropertyValueFactory<Station, String>("nom"));
			Unite.setCellValueFactory(new PropertyValueFactory<Station, String>("nomUnite"));
			tableStation.setItems(stations);
		}
	}
	
	/**
	 * Fonction qui permet de lister les équipements dans la combobox quand on selectionne une centrale dans la combobox correspondante
	 */
	public void ListerEquipement(){
		Ajouter.setVisible(false);
		ObservableList<Equipement> equipements=EquipementController.loadEquipementNonSupprimes(listeCentrale.getValue().getId());
		listeEquipement.setItems(equipements);
	}
	public void supprimer ()
	{
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("supprimer.fxml"));
		AnchorPane page;
		try {
			station=tableStation.getSelectionModel().getSelectedItem();
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Supprimer une station");
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	initialize();
	            	dialog.close();
	            	ListerStations();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
