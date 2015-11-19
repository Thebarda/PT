package controleur;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import vue.Main;

/**
 * Controleur relatif au menu vertical de preparer tournées et de ses actions sur le centre de l'application
 */
public class PreparerTourneesController {
		
	@FXML
	AnchorPane Centre;
	
	@FXML
	AnchorPane menuVertical;
	
	
	
	/**
	 * Fonction qui permet d'afficher le contenu relatif à la gestion de centrale
	 */
	public void  AfficherGererCentrale(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("Gerer_Centrale.fxml"));
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
	public void  AfficherGererEquipement(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("Gerer_Equipement.fxml"));
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
	public void  AfficherGererStation(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("Gerer_Station.fxml"));
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
	public void  AfficherModeleTournee(){
		FXMLLoader loader =new FXMLLoader(Main.class.getResource("Gerer_ModeleTournee.fxml"));
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
	public void init(int idPage) {
		if (idPage==2){
			AfficherGererCentrale();
		}
		if (idPage==5){
			AfficherModeleTournee();
		}
	}
}
	
