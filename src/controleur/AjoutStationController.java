package controleur;



import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.Centrale;
import modele.Equipement;
import modele.ModeleTournee;
import modele.Station;
import modele.Unite;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class AjoutStationController {
	
	private int idCentrale;
	private Map<Integer,Station> map;
	private int rangActuel;
	private CreationModeleTourneeController controllerParent;
	
	@FXML
	ObservableList<ModeleTournee> data=ModeleTourneeController.loadAllModeleTournee(idCentrale);
	
	@FXML
	private Button valider;
	
	@FXML
	private ComboBox<Station> listeStations;
	
	@FXML 
	private Button annuler;

	ObservableMap<Integer,Station> Ostations=FXCollections.observableHashMap();

	public void init(int idCentrale,Map<Integer,Station> nouvelleMap,int rang,CreationModeleTourneeController controllerParent,ObservableMap<Integer,Station> ostatio)
	{
		this.idCentrale = idCentrale;
		map=nouvelleMap;
		rangActuel=rang;
		this.controllerParent=controllerParent;
		ObservableList<Station> station=StationController.loadStationForCentrale(idCentrale);
		listeStations.setItems(station);
	}
	/**
	 * Fonction qui permet de valider une centrale, et donc de l'ajouter à la base
	 * On ajoute la centrale que si les champs nom et localisation sont non vide
	 */
	public void ValiderAjoutStation(){
		boolean estValide=true;
		
		if(estValide == true)
		{
			Ostations.put(rangActuel+1,listeStations.getValue());
			controllerParent.initFils(Ostations, rangActuel+1);
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une centrale, et cahce la fenêtre correspondante
	 * En réalité. La page est fermé par le controller gérant la page de gestion de Centrale
	 */
	public void annulerAjoutStation()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	
	
}
