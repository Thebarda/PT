package controleur;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import vue.Main;

/**
 * Controleur relatif au menu vertical de preparer tournées et de ses actions sur le centre de l'application
 */
public class GestionBD {
		
	@FXML
	AnchorPane Centre;
	
	@FXML
	AnchorPane menuVertical;
	
	@FXML
	/**
	 * Fonction qui permet d'initialiser la page en affichant le planning(page par défaut)
	 */
	private void initialize() {
		AfficherDerniereTournee();
	}
	
	/**
	 * Fonction qui permet d'afficher le contenu relatif eu planning
	 */
	public void  AfficherDerniereTournee(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("AfficherDerniereTournee.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Centre.getChildren().removeAll(Centre.getChildren());
			Centre.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet d'afficher le contenu relatif à la gestion de centrale
	 */
	public void  AfficherExport(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("AfficherExport.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Centre.getChildren().removeAll(Centre.getChildren());
			Centre.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet d'afficher le contenu relatif à la gestion d'équipement
	 */
	public void  AfficherImport(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("AfficherImport.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Centre.getChildren().removeAll(Centre.getChildren());
			Centre.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet d'afficher le contenu relatif à la gestion de station
	 */
	public void  AfficherGraphique(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("graphique.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Centre.getChildren().removeAll(Centre.getChildren());
			Centre.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet d'afficher le contenu relatif à la gestion de modele de tournees
	 */
	public void  AfficherAnalyse(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("analyse.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Centre.getChildren().removeAll(Centre.getChildren());
			Centre.getChildren().addAll(page);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Fonction qui permet d'afficher la page dont l'id est utilisé en parametres
	 * @param idPage
	 * 		id de la page souhaitée
	 */
	/*public void init(int idPage) {
		if (idPage==2){
			AfficherGererCentrale();
		}
		if (idPage==5){
			AfficherModeleTournee();
		}
	}*/
}
	
