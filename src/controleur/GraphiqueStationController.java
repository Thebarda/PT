package controleur;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		listeStation.setItems(stations);
		series = new XYChart.Series<>();
		series.setName("Valeurs");
	}
	public void Graphique(){
		ObservableList<Releve> releves = ReleveController.loadReleves(0);
		NumberAxis yAxis = new NumberAxis();
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Valeurs");
		series = new XYChart.Series<Number, Number>();
		series
	}
}
