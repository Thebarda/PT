package controleur;

import java.io.IOException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.util.Callback;
import modele.Centrale;
import modele.Equipement;
import modele.Tournee;
import vue.Main;
import javafx.scene.Scene;

public class AnalyseController 
{	
		@FXML
		ComboBox<Centrale> listeCentrale;
		
		@FXML
		TableView<Equipement> tableEquipement;
		
		@FXML
		TableColumn<Equipement, Integer> ID;
		
		@FXML
		TableColumn<Equipement, String> Nom;
		
		@FXML
		TableColumn<Equipement, Button> Analyser;
		
		ObservableList<Centrale> centrale=CentraleControler.loadCentrales();
		
		static int idEquipement;
		
		@FXML
		/**
		 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
		 */
		private void initialize() {
			listeCentrale.setItems(centrale);
		}

		/**
		 * Fonction qui permet de lister les equipements dans le tableau quand on selectionne une centrale dans la combobox
		 */
		public void ListerEquipement(){
			ObservableList<Equipement> equip=EquipementController.loadEquipement(listeCentrale.getValue().getId());
			Nom.setCellValueFactory(new PropertyValueFactory<Equipement, String>("nom"));
			ID.setCellValueFactory(new PropertyValueFactory<Equipement, Integer>("id"));
			Analyser.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipement, Button>, ObservableValue<Button>>(){

	            @Override
	            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Equipement, Button> p) {
	            	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
	            	    @Override public void handle(ActionEvent e) {
	            	    	final Stage dialog = new Stage();
	            	        dialog.initModality(Modality.APPLICATION_MODAL);
	            			FXMLLoader loader = new FXMLLoader(Main.class.getResource("analyse_Station.fxml"));
	            			AnchorPane page;
	            			try {
	            		        idEquipement=p.getValue().getId();	            		        
	            				page = (AnchorPane) loader.load();
	            				Scene dialogScene = new Scene(page);
	            		        dialog.setScene(dialogScene);
	            		        dialog.show();
	            		        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            		            public void handle(WindowEvent we) {
	            		            	dialog.close();
	            		            	ListerEquipement();
	            		            }
	            		        });
	            			} catch (IOException ioe) {
	            				ioe.printStackTrace();
	            			}
	            	    }
	            	};
	            	Button button=new Button("Analyser");
	            	button.setMaxWidth(Double.MAX_VALUE);
	            	button.setOnAction(event);
	            	return new SimpleObjectProperty<Button>(button);
	            }
	        });
			tableEquipement.setItems(equip);
		}
}

