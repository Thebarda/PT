package controleur;

import java.io.IOException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import modele.Tournee;
import vue.Main;

public class PlanningController {
	
	@FXML
	TableView<Tournee> planning;
	
	@FXML
	TableColumn<Tournee, Integer> ID;
	
	@FXML
	TableColumn<Tournee, String> Nom;
	
	@FXML
	TableColumn<Tournee, Button> Import;
	
	@FXML
	TableColumn<Tournee, Button> Export;
	
	@FXML
	ComboBox<Centrale> listeCentrale;
	
	ObservableList<Centrale> centrale=CentraleControler.loadCentrales();
	
	public static String dateExport;
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		listeCentrale.setItems(centrale);
	}
	
	/**
	 * Fonction qui permet de lister les tournees dans le tableau quand on selectionne une centrale dans la combobox
	 */
	public void ListerEquipement(){
		ObservableList<Tournee> tournees=TourneeController.loadTournee(listeCentrale.getValue().getId());
		ID.setCellValueFactory(new PropertyValueFactory<Tournee, Integer>("id"));
		Nom.setCellValueFactory(new PropertyValueFactory<Tournee, String>("nom"));
		Export.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournee, Button>, ObservableValue<Button>>(){

            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Tournee, Button> p) {
            	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            	    @Override public void handle(ActionEvent e) {
            	    	final Stage dialog = new Stage();
            	        dialog.initModality(Modality.APPLICATION_MODAL);
            	        if (p.getValue().isEstExportee()){
            	        	dateExport=p.getValue().getDate();
            	        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("validerExport.fxml"));
                			AnchorPane page;
                			try {
                				page = (AnchorPane) loader.load();
                				Scene dialogScene = new Scene(page);
                		        dialog.setScene(dialogScene);
                		        dialog.show();
                		        ValiderExportController.init(p.getValue(),dialog);
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
            	        else{
            	        	FXMLLoader loader = new FXMLLoader(Main.class.getResource("Exportation.fxml"));
                			AnchorPane page;
                			try {
                				page = (AnchorPane) loader.load();
                				Scene dialogScene = new Scene(page);
                		        dialog.setScene(dialogScene);
                		        dialog.show();
                		        ExportationController.init(p.getValue(),dialog);
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
            	    }
            	};
            	Button button=new Button("Vers le mobile");
            	if(!p.getValue().isTerminee()){
            		button.setOnAction(event);
            	}
            	else{
            		button.setVisible(false);
            	}
            	return new SimpleObjectProperty<Button>(button);
            }
        });
		Import.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournee, Button>, ObservableValue<Button>>(){

            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Tournee, Button> p) {
            	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            	    @Override public void handle(ActionEvent e) {
            	    	final Stage dialog = new Stage();
            	        dialog.initModality(Modality.APPLICATION_MODAL);
            			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Importation.fxml"));
            			AnchorPane page;
            			try {
            				page = (AnchorPane) loader.load();
            				Scene dialogScene = new Scene(page);
            		        dialog.setScene(dialogScene);
            		        dialog.show();
            		        ImportationController.init(p.getValue());
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
            	Button button=new Button("Importer depuis le mobile");
            	if(!p.getValue().isTerminee() && p.getValue().isEstExportee()){
            		button.setOnAction(event);
            	}
            	else{
            		button.setVisible(false);
            	}
            	return new SimpleObjectProperty<Button>(button);
            }
        });
		
		planning.setItems(tournees);
	}

}
