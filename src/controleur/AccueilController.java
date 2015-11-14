package controleur;


import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Centrale;
import vue.Main;
import javafx.scene.Scene;

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
	
	

	
}
