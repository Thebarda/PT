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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
	TableColumn<Releve, Label> Analyse;
	
	@FXML
	TableColumn<Releve,String> Tendance;
	
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
		Releve.setCellValueFactory(new PropertyValueFactory<Releve, String>("valeur"));
		Analyse.setCellValueFactory(new Callback<CellDataFeatures<Releve, Label>, ObservableValue<Label>>() {
		     
			
			public ObservableValue<Label> call(CellDataFeatures<Releve, Label> p) {
		    	 Label result;
		    	 String style="";
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
		    	 boolean estCorrecteSeuilBas=releve>=seuilBas;
		    	 boolean estCorrecteSeuilHaut=releve<=seuilHaut;
		    	 boolean estEgaleValeurOptimale=releve.equals(valeurOptimale);
		    	 boolean estValeurOptimale=false;
		    	 boolean estValeurNormale=false;
		    	 boolean estValeurAnormaleHaute=false;
		    	 boolean estValeurAnormaleBasse=false;

		    	 if(existeSeuilBas && existeSeuilHaut){
		    		 if(estCorrecteSeuilBas && estCorrecteSeuilHaut){
		    			 if(existeValeurOptimale && estEgaleValeurOptimale){
		    				 estValeurOptimale=true;
		    			 }
		    			 else{
		    				 estValeurNormale=true;
		    			 }
		    		 }
		    		 else if(estCorrecteSeuilHaut || (!estCorrecteSeuilBas && !existeSeuilHaut)){
		    			 estValeurAnormaleBasse=true;
		    		 }
		    		 else if(estCorrecteSeuilBas || (!estCorrecteSeuilHaut && !existeSeuilBas)){
		    			 estValeurAnormaleHaute=true;
		    		 }
		    	 }
		    	 else if((existeSeuilBas && estCorrecteSeuilBas) || (existeSeuilHaut && estCorrecteSeuilHaut) || (!existeSeuilBas && !existeSeuilHaut)){
		    		 if(existeValeurOptimale && estEgaleValeurOptimale){
	    				 estValeurOptimale=true;
	    			 }
	    			 else{
	    				 estValeurNormale=true;
	    			 }
		    	 }
		    	 else{
		    		 if(estCorrecteSeuilHaut || (!estCorrecteSeuilBas && !existeSeuilHaut)){
		    			 estValeurAnormaleBasse=true;
		    		 }
		    		 else if(estCorrecteSeuilBas || (!estCorrecteSeuilHaut && !existeSeuilBas)){
		    			 estValeurAnormaleHaute=true;
		    		 }
		    	 }
		    	 
		    	 if(estValeurOptimale){
		    		analyse="Valeur Optimale";
	 				style="-fx-background-color: #3CAD13;";
		    	 }
		    	 else if(estValeurNormale){
		    		analyse="Valeur Normale";
	 				style="-fx-background-color: #9BEB7F;";
		    	 }
		    	 else if(estValeurAnormaleBasse){
		    		analyse="Valeur Anormale : trop basse";
		 			style="-fx-background-color: #35BEE8;";
		    	 }
		    	 else if(estValeurAnormaleHaute){
		    		analyse="Valeur Anormale : trop haute";
		 			style="-fx-background-color: #E6A83E;";
		    	 }

		    	 
		    	 result=new Label(analyse);
		    	 result.setStyle(style);
		    	 result.setPrefHeight(100);
		    	 return new SimpleObjectProperty<Label>(result);
		     }
			

			
		  });
		tableAnalyse.setItems(releve);
		Analyse.setCellFactory(new Callback<TableColumn<Releve,Label>,TableCell<Releve,Label>>(){
			@Override
		    public TableCell<Releve, Label> call(TableColumn<Releve, Label> param) {
		        return new TableCell<Releve, Label>() {

		            @Override
		            protected void updateItem(Label item, boolean empty) {
		            	super.updateItem(item, empty);
		            	setText(empty ? null : getLabel().getText());
			             if (!empty) {
			            	 setStyle(item.getStyle());
			             }
		            }
		            
		            private Label getLabel() {
                        return getItem() == null ? new Label() : getItem();
                    }
		        };
		    }
		});
		Tendance.setCellValueFactory(new Callback<CellDataFeatures<Releve, String>, ObservableValue<String>>() {
		     
			
			public ObservableValue<String> call(CellDataFeatures<Releve, String> p) {
				String tendance="";
				Releve releveAvant;
				int posReleve;
				ObservableList<Releve> releves=ReleveController.loadRelevesForStation(idStationSelected);
				posReleve=releves.indexOf(p.getValue());
				if(posReleve!=releves.size()-1&&posReleve!=-1){
					releveAvant=releves.get(posReleve+1);
					if(releveAvant.getValeur()>p.getValue().getValeur()){
						tendance="\u2198";
					}
					else if(releveAvant.getValeur()<p.getValue().getValeur()){
						tendance="\u2197";
					}
					else{
						tendance="\u2192";
					}
				}
				
				return new SimpleObjectProperty<String>(tendance);
			}
		});
	}
}
