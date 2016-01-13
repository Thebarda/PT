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
	
	ObservableList<Station> stations=StationController.loadStation(1);
	
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		listeStation.setItems(stations);
	}

	/**
	 * Fonction qui permet de lister les equipements dans le tableau quand on selectionne une centrale dans la combobox
	 */
	public void ListerEquipement(){
		ObservableList<Releve> releve=ReleveController.loadReleves(1);
		Date.setCellValueFactory(new PropertyValueFactory<Releve, String>("date"));
		//Releve.setCellValueFactory(new PropertyValueFactory<Releve, String>("id"));
		tableAnalyse.setItems(releve);
	}
}
