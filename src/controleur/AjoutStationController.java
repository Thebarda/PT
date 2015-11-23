package controleur;



import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modele.ModeleTournee;
import modele.Station;


/**
 * Controleur relatif à l'interface de création de centrale
 */
public class AjoutStationController {
	
	private int idCentrale;
	private int rangActuel;
	private CreationModeleTourneeController controllerParent;
	
	@FXML
	ObservableList<ModeleTournee> data=ModeleTourneeController.loadAllModeleTournee(idCentrale);
	
	@FXML
	Label erreurStation;
	
	@FXML
	private Button valider;
	
	@FXML
	private ComboBox<Station> listeStations;
	
	@FXML 
	private Button annuler;

	ObservableMap<Integer,Station> Ostations=FXCollections.observableHashMap();

	/**
	 * Fonction qui permet de faire la liaison entre le controller parent (modeleTourneeController) et ce controlleur
	 * @param idCentrale
	 * 		id de la centrale precedemment choisi
	 * @param rang
	 * 		rang actuel de la map
	 * @param controllerParent
	 * 		controller parent
	 * @param Ostations
	 * 		ObservableMap de stations
	 */
	public void init(int idCentrale,int rang,CreationModeleTourneeController controllerParent,ObservableMap<Integer,Station> Ostations)
	{
		this.idCentrale = idCentrale;
		rangActuel=rang;
		this.controllerParent=controllerParent;
		ObservableList<Station> station=StationController.loadStationForCentrale(idCentrale);
		listeStations.setItems(station);
		this.Ostations=Ostations;
	}
	/**
	 * Fonction qui permet de valider une station à la map, et donc de l'ajouter au modele de tournee
	 */
	public void ValiderAjoutStation(){
		boolean estValide=true;
		
		if(listeStations.getValue()==null){
			erreurStation.setText("Erreur : une station doit être validée");
			estValide=false;
		}
		if(Ostations.containsValue(listeStations.getValue())){
			erreurStation.setText("Erreur : cette station est dejà contenu dans le modele de tournee");
			estValide=false;
		}
		
		if(estValide == true)
		{
			Ostations.put(rangActuel+1,listeStations.getValue());
			controllerParent.initFils(Ostations, rangActuel+1);
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler l'ajout d'une station, et cache la fenêtre correspondante
	 * En réalité. La page est fermé par le controller gérant la page de gestion de Modele de tournee
	 */
	public void annulerAjoutStation()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	
	
}
