package controleur;

import java.lang.reflect.Field;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
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
	
	ObservableList<Station> stations;
	XYChart.Series<String, Number> series;
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la combobox de Centrale (se demarre au lancement de la fênetre)
	 */
	private void initialize() {
		stations=StationController.loadStation(GraphiqueController.idEquipement);
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
	    Series<String, Number> seriesSeuilHaut = new XYChart.Series<String, Number>();
	    Series<String, Number> seriesSeuilBas = new XYChart.Series<String, Number>();
	    Series<String, Number> seriesNormal = new XYChart.Series<String, Number>();
	    series.setName("Releve");
	    for(int i=releves.size()-1;i>=0;i--){
	    	series.getData().add(new XYChart.Data<String, Number>(releves.get(i).getDate(), releves.get(i).getValeur()));
	    	if(i==releves.size()-1 || i==0){
	    		seriesSeuilHaut.getData().add(new XYChart.Data<String, Number>(releves.get(i).getDate(), listeStation.getSelectionModel().getSelectedItem().getSeuilHaut()));
		    	seriesSeuilBas.getData().add(new XYChart.Data<String, Number>(releves.get(i).getDate(), listeStation.getSelectionModel().getSelectedItem().getSeuilBas()));
		    	seriesNormal.getData().add(new XYChart.Data<String, Number>(releves.get(i).getDate(), listeStation.getSelectionModel().getSelectedItem().getValeurNormale()));
	    	}
	    	
	    }
	    graph.setLegendVisible(false);
	    graph.getData().add(series);
	    graph.getData().add(seriesSeuilHaut);
	    graph.getData().add(seriesNormal);
	    graph.getData().add(seriesSeuilBas);
	    
	    graph.getData().get(1).getNode().setStyle("-fx-stroke: red; -fx-stroke-width: 2; -fx-stroke-dash-array: 6 12; ");
	    graph.getData().get(3).getNode().setStyle("-fx-stroke: #5CE3D1; -fx-stroke-width: 2; -fx-stroke-dash-array: 6 12;");
	    graph.getData().get(2).getNode().setStyle("-fx-stroke: #37CA20; -fx-stroke-width: 2; -fx-stroke-dash-array: 6 12;");
	    
	    for(int i=0;i<2;i++){
	    	graph.getData().get(1).getData().get(i).getNode().setVisible(false);
	    	graph.getData().get(2).getData().get(i).getNode().setVisible(false);
	    	graph.getData().get(3).getData().get(i).getNode().setVisible(false);
	    }
	    
	    if(listeStation.getSelectionModel().getSelectedItem().getMarqueur().substring(0,1).equals("1")){
	    	graph.getData().remove(seriesSeuilHaut);
	    }
	    if(listeStation.getSelectionModel().getSelectedItem().getMarqueur().substring(1,2).equals("1")){
	    	graph.getData().remove(seriesSeuilBas);
	    }
	    if(listeStation.getSelectionModel().getSelectedItem().getMarqueur().substring(2,3).equals("1")){
	    	graph.getData().remove(seriesNormal);
	    }
	    
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
