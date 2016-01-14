package controleur;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import modele.Releve;
import modele.Station;

public class GraphiqueStationController {
	@FXML
	ComboBox<Station> listeStation;
	
	@FXML
	LineChart<Number, Number> graph;
	
	@FXML
	Button exit;
	
	ObservableList<Station> stations=StationController.loadStation(1);
	XYChart.Series<Number, Number> series;
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		listeStation.setItems(stations);
		graph.setVisible(false);
	}
	public void Graphique(){
		graph.setVisible(true);
		int idStationSelected=listeStation.getSelectionModel().getSelectedItem().getId();//pas �a
		ObservableList<Releve> releve=ReleveController.loadRelevesForStation(idStationSelected);//pas �a
		NumberAxis yAxis = new NumberAxis();
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Date");//pas �a
		yAxis.setLabel("Valeurs");//pas �a
		series = new XYChart.Series<>();
		graph = new LineChart<Number, Number>(xAxis, yAxis);
		series.getData().add(new Data<Number, Number>(1, 1));//pas �a
		series.getData().add(new Data<Number, Number>(2, 2));//pas �a
		series.getData().add(new Data<Number, Number>(3, 3));//pas �a
		series.getData().add(new Data<Number, Number>(4, 4));//pas �a
		series.getData().add(new Data<Number, Number>(5, 5));//pas �a
		series.getData().add(new Data<Number, Number>(6, 6));//pas �a non plus
		graph.getData().add(series);
	}
	
	public void retour(){
		exit.getParent().getScene().getWindow().hide();
	}
}
