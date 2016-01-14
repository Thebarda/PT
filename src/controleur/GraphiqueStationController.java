package controleur;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ComboBox;
import modele.Releve;
import modele.Station;

public class GraphiqueStationController {
	@FXML
	ComboBox<Station> listeStation;
	
	@FXML
	LineChart<Number, Number> graph;
	
	ObservableList<Station> stations=StationController.loadStation(1);
	XYChart.Series<Number, Number> series;
	int idEquipement;
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fÃªnetre)
	 */
	private void initialize() {
		idEquipement = GraphiqueController.idEquipement;
		stations = StationController.loadStation(idEquipement);
		listeStation.setItems(stations);
		graph.setVisible(false);
	}
	public void Graphique(){
		int idStationSelected=listeStation.getSelectionModel().getSelectedItem().getId();//pas ça
		ObservableList<Releve> releve=ReleveController.loadRelevesForStation(idStationSelected);//pas ça
		NumberAxis yAxis = new NumberAxis();
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Date");//pas ça
		yAxis.setLabel("Valeurs");//pas ça
		series = new XYChart.Series<>();
		series.getData().add(new Data<Number, Number>(1, 1));//pas ça
		series.getData().add(new Data<Number, Number>(2, 2));//pas ça
		series.getData().add(new Data<Number, Number>(3, 3));//pas ça
		series.getData().add(new Data<Number, Number>(4, 4));//pas ça
		series.getData().add(new Data<Number, Number>(5, 5));//pas ça
		series.getData().add(new Data<Number, Number>(6, 6));//pas ça non plus
		graph.getData().add(series);
		graph.setVisible(true);
	}
}
