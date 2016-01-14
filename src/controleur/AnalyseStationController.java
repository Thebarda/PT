package controleur;

import java.io.IOException;

import javax.json.JsonObject;

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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modele.Centrale;
import modele.Equipement;
import modele.Releve;
import modele.Station;
import vue.Main;

public class AnalyseStationController {
	@FXML
	ComboBox<Station> listeStation;
	
	@FXML
	TableView<Releve> tableAnalyse;
	
	@FXML
	TableColumn<Releve, String> Date;
	
	@FXML
	TableColumn<Releve, String> Releve;
	
	@FXML
	TableColumn<Releve, String> Analyse;
	
	int idEquipement;
	
	ObservableList<Station> stations;
	
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		idEquipement=AnalyseController.idEquipement;
		stations=StationController.loadStation(idEquipement);
		listeStation.setItems(stations);
	}

	/**
	 * Fonction qui permet de lister les equipements dans le tableau quand on selectionne une centrale dans la combobox
	 */
	public void ListerReleve(){
		int idStationSelected=listeStation.getSelectionModel().getSelectedItem().getId();
		ObservableList<Releve> releve=ReleveController.loadRelevesForStation(idStationSelected);
		Date.setCellValueFactory(new PropertyValueFactory<Releve, String>("date"));
		Releve.setCellValueFactory(new PropertyValueFactory<Releve, String>("id"));
		Analyse.setCellValueFactory(new Callback<CellDataFeatures<Releve, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Releve, String> p) {
		    	 Station stationSelected=StationController.loadStationById(idStationSelected);
		    	 String marqueur=stationSelected.getMarqueur();
		    	 Double seuilBas=stationSelected.getSeuilBas();
		    	 Double seuilHaut=stationSelected.getSeuilHaut();
		    	 Double valeurOptimale=stationSelected.getValeurNormale();
		    	 Double releve=p.getValue().getValeur();
		    	 String analyse="";
		    	 boolean existeSeuilBas=marqueur.substring(1, 2).equals("0");
		    	 boolean existeSeuilHaut=marqueur.substring(0, 1).equals("0");
		    	 boolean existeValeurOptimale=marqueur.substring(2, 3).equals("0");
		    	 
		    	 if(marqueur.equals("001") || marqueur.equals("000")){
		 			if(releve < seuilBas){
		 				analyse="Valeur Anormale : trop basse";
		 			}
		 			else if(releve > seuilHaut){
		 				analyse="Valeur Anormale : trop haute";
		 			}
		 			else{
		 				if(existeValeurOptimale && releve==valeurOptimale){
		 					analyse="Valeur Optimale";
		 				}
		 				else{
		 					analyse="Valeur Normale";
		 				}
		 				
		 			}
		 		}
		 		else if(!existeSeuilHaut && existeSeuilBas){
		 			if(releve < seuilBas){
		 				analyse="Valeur Anormale : trop basse";
		 			}
		 			else{
		 				if(existeValeurOptimale && releve==valeurOptimale){
		 					analyse="Valeur Optimale";
		 				}
		 				else{
		 					analyse="Valeur Normale";
		 				}
		 			}
		 		}
		 		else if(existeSeuilHaut && !existeSeuilBas){
		 			if(releve > seuilHaut){
		 				analyse="Valeur Anormale : trop haute";
		 			}
		 			else{
		 				if(existeValeurOptimale && releve==valeurOptimale){
		 					analyse="Valeur Optimale";
		 				}
		 				else{
		 					analyse="Valeur Normale";
		 				}
		 			}
		 		} else{
		 			if(existeValeurOptimale && releve==valeurOptimale){
	 					analyse="Valeur Optimale";
	 				}
	 				else{
	 					analyse="Valeur Normale";
	 				}
		 		}
		    	 return new SimpleObjectProperty<String>(analyse);
		     }
		  });
		tableAnalyse.setItems(releve);
	}
}
