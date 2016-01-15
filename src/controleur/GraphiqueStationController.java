package controleur;

import java.lang.reflect.Field;
import java.util.Comparator;

import javax.swing.event.ListSelectionEvent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
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
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		listeStation.setItems(stations);
	}
	public void Graphique(){
		if(racine.getChildren().contains(graph)){
			racine.getChildren().remove(graph);
		}
		int idStationSelected=listeStation.getSelectionModel().getSelectedItem().getId();//pas �a
		ObservableList<Releve> releves=ReleveController.loadRelevesForStation(idStationSelected);//pas �a
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
            	Tooltip t=new Tooltip(d.getXValue().toString() + "\n" +"Valeur : " + d.getYValue());
            	modifierDelaiToolTip(t,10);
                Tooltip.install(d.getNode(), t);
            }
        }

	}
	
	public void retour(){
		exit.getParent().getScene().getWindow().hide();
	}
	
	public static void modifierDelaiToolTip(Tooltip tooltip,double delay) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(delay)));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
