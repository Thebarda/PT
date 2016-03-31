package controleur;


import java.io.IOException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import modele.Centrale;
import modele.Equipement;
import modele.Station;
import modele.Tournee;
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
	
	@FXML
	Button modifier;
	
	@FXML
	Button details;
	
	ObservableList<Centrale> centrale=CentraleControler.loadCentralesNonSupprimees();
	
	static Station station;

	static int idStation;
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
		modifier.setVisible(false);
		tableStation.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>()
	    {
	        @Override
	        public void onChanged(Change<? extends Integer> change)
	        {
	        	if(tableStation.getItems()  != null){
	        		supprimer.setVisible(true);
	        		details.setVisible(true);
	        		modifier.setVisible(true);
	        	}     
	        }
	    });
	}
	/**
	 * Fonction qui permet d'ouvrir la popup d'ajout de Stations.
	 * Une fois que l'utilisateur à annuler l'ajout ou ajouter une stations, on recharge le tableau et ferme la popup
	 */
	public void ajouterStation(){
		idStation=-1;
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
	
	public void modifier(){
		station=tableStation.getSelectionModel().getSelectedItem();
		idStation=station.getId();
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Station.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Modification d'une station");
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
			Unite.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Station, String>, ObservableValue<String>>(){
				
	            @Override
	            public ObservableValue<String> call(TableColumn.CellDataFeatures<Station, String> p) {
	            	String uniteAff="";
	            	String nomUnite=p.getValue().getNomUnite();
	            	if (nomUnite.equals("__VERIFICATION")){
	            		uniteAff="Fait/Anomalie";
	            	}
	            	else if(nomUnite.equals("__TEXTE")){
	            		uniteAff="Champ de texte";
	            	}
	            	else{
	            		uniteAff=nomUnite;
	            	}
	            	return new SimpleObjectProperty<String>(uniteAff);
	            	
	            }
			});
			tableStation.setItems(stations);
		}
	}
	
	/**
	 * Fonction qui permet de lister les équipements dans la combobox quand on selectionne une centrale dans la combobox correspondante
	 */
	public void ListerEquipement(){
		Ajouter.setVisible(false);
		modifier.setVisible(false);
		details.setVisible(false);
		supprimer.setVisible(false);
		ObservableList<Equipement> equipements=EquipementController.loadEquipementNonSupprimes(listeCentrale.getValue().getId());
		tableStation.setItems(null);
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
	            	dialog.close();
	            	ListerStations();
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
			station=tableStation.getSelectionModel().getSelectedItem();
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Plus de details (station)");
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
