package controleur;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modele.Centrale;
import modele.ModeleTournee;
import modele.Tournee;
import vue.Main;

/**
 * Controleur relatif à l'affichage de l'accueil et de la barre horizontale
 */
public class AccueilController {
		
	@FXML
	AnchorPane menuVerticale;
	
	@FXML
	AnchorPane Centre;
	
	@FXML
	HBox Appli;
	
	@FXML
	ComboBox<Centrale> listeCentrale;
	
	@FXML
	TableView<Tournee> tableTournee;
	
	@FXML
	TableColumn<Tournee, String> date;
	
	@FXML
	TableColumn<Tournee, Integer> id;
	
	@FXML
	TableColumn<Tournee, String> nom;
	
	@FXML
	TableColumn<Tournee, HBox> button;
	
	ObservableList<Centrale> centrale=CentraleControler.loadCentralesNonSupprimees();

	public static String dateExport;
	
	public static Tournee tourneeSelect;

	@FXML
	private void initialize(){
		listeCentrale.setItems(centrale);
		tourneeSelect=null;
	}
	
	public void ListerTournee(){
		ObservableList<Tournee> tournee=TourneeController.loadTournee(listeCentrale.getValue().getId());
		nom.setCellValueFactory(new PropertyValueFactory<Tournee, String>("nom"));
		id.setCellValueFactory(new PropertyValueFactory<Tournee, Integer>("id"));

		date.setCellValueFactory(new PropertyValueFactory<Tournee, String>("dateExport"));
		
		button.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournee, HBox>, ObservableValue<HBox>>(){

            @Override
            public ObservableValue<HBox> call(TableColumn.CellDataFeatures<Tournee, HBox> p) {
            	tourneeSelect = p.getValue();
            	EventHandler<ActionEvent> eventExport = new EventHandler<ActionEvent>() {
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
                		            	ListerTournee();
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
                		            	ListerTournee();
                		            }
                		        });
                			} catch (IOException ioe) {
                				ioe.printStackTrace();
                			}
            	        }
            	    }
            	};
            	HBox hbox =new HBox();

            	Button buttonExport=new Button("Vers le mobile");
            	if(!p.getValue().isTerminee()){
            		buttonExport.setOnAction(eventExport);
                	hbox.getChildren().add(buttonExport);

            	}
            	
            	EventHandler<ActionEvent> eventImport = new EventHandler<ActionEvent>() {
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
            		            	ListerTournee();
            		            }
            		        });
            			} catch (IOException ioe) {
            				ioe.printStackTrace();
            			}
            	    }
            	};
            	
            	Button buttonImport=new Button("Importer depuis le mobile");
            	if(!p.getValue().isTerminee() && p.getValue().isEstExportee()){
            		buttonImport.setOnAction(eventImport);
                	hbox.getChildren().add(buttonImport);
            	}
            	
            	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						final Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						FXMLLoader loader = new FXMLLoader(Main.class.getResource("releve.fxml"));
						AnchorPane page;
						try {
							page = (AnchorPane) loader.load();
							Scene dialogScene = new Scene(page);
							dialog.setScene(dialogScene);
							dialog.show();
							dialog.setOnHidden(new EventHandler<WindowEvent>() {
								public void handle(WindowEvent we) {
									dialog.close();
								}
							});
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}

					}

				};
            
            	return new SimpleObjectProperty<HBox>(hbox);
            }
        });
		
		tableTournee.setItems(tournee);
	}

	/**
	 * Fonction qui permet de changer le contenu du centre, en lui appliquant le contenu relatif à la préparation de tournées
	 */
	public void  AfficherPreparerTournees(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("PreparerTournees.fxml"));
		HBox page;
		try {
			page = (HBox) loader.load();
			Appli.getChildren().removeAll(Appli.getChildren());
			Appli.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet de changer le contenu du centre, en lui appliquant le contenu relatif à la préparation de tournées, et plus precisemment gerer centrales
	 */
	public void  AfficherGererCentrales(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("PreparerTournees.fxml"));
		HBox page;
		try {
			page = (HBox) loader.load();
			Appli.getChildren().removeAll(Appli.getChildren());
			Appli.getChildren().addAll(page);
			PreparerTourneesController controller = loader.getController();
	        controller.init(2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet de changer le contenu du centre, en lui appliquant le contenu relatif à la préparation de tournées, et plus precisemment gerer les modele de tournee
	 */
	public void  AfficherGererModeleTournee(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("PreparerTournees.fxml"));
		HBox page;
		try {
			page = (HBox) loader.load();
			Appli.getChildren().removeAll(Appli.getChildren());
			Appli.getChildren().addAll(page);
			PreparerTourneesController controller = loader.getController();
	        controller.init(5);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void AfficherGestionBD(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("GestionBD.fxml"));
		HBox page;
		try {
			page = (HBox) loader.load();
			Appli.getChildren().removeAll(Appli.getChildren());
			Appli.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void  AfficherAnalyse(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("GestionBD.fxml"));
		HBox page;
		try {
			page = (HBox) loader.load();
			Appli.getChildren().removeAll(Appli.getChildren());
			Appli.getChildren().addAll(page);
			GestionBD controller = loader.getController();
	        controller.init(2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

	
}
