package controleur;

import java.util.Comparator;

import javax.swing.event.ListSelectionEvent;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import modele.Releve;
import modele.Station;

public class GraphiqueStationController {
	@FXML
	AnchorPane racine;
	
	@FXML
	ComboBox<Station> listeStation;
	
	@FXML
	LineChart<String,Number> graph;
	
	@FXML
	Button exit;
	
	ObservableList<Station> stations=StationController.loadStation(1);
	XYChart.Series<String, Number> series;
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fÃªnetre)
	 */
	private void initialize() {
		listeStation.setItems(stations);
	}
	public void Graphique(){
		if(racine.getChildren().contains(graph)){
			racine.getChildren().remove(graph);
		}
		int idStationSelected=listeStation.getSelectionModel().getSelectedItem().getId();//pas ça
		ObservableList<Releve> releves=ReleveController.loadRelevesForStation(idStationSelected);//pas ça
		final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    xAxis.setLabel("Date");
	    yAxis.setLabel("Valeurs");
	    graph = new LineChart<String,Number>(xAxis, yAxis);

	    graph.setTitle("Graphique "+listeStation.getSelectionModel().getSelectedItem().getNom());
	    Series<String, Number> series = new XYChart.Series<String, Number>();
	    series.setName("Releve");
	    for(int i=releves.size()-1;i>=0;i--){
	    	series.getData().add(new XYChart.Data<String, Number>(releves.get(i).getDate(), releves.get(i).getValeur()));
	    }
	    graph.getData().add(series);
	    graph.setLayoutY(134.0);
	    graph.setPrefHeight(316.0);
	    graph.setPrefWidth(583.0);
	    racine.getChildren().add(graph);
	    
	    for (XYChart.Series<String, Number> s : graph.getData()) {
            for (Data<String, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(
                        d.getXValue().toString() + "\n" + 
                "Valeur : " + d.getYValue()));

                d.getNode().setOnMouseEntered(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        d.getNode().getStyleClass().add("onHover");                     
                    }
                });

                d.getNode().setOnMouseExited(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        d.getNode().getStyleClass().remove("onHover");      
                    }
                });
            }
        }

	}
	
	public void retour(){
		exit.getParent().getScene().getWindow().hide();
	}
}
