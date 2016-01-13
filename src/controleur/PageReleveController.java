package controleur;



import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Centrale;
import modele.Releve;
import modele.Station;
import modele.Tournee;
import modele.Unite;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class PageReleveController {
		
	@FXML
	private TableView<Releve> tabStation;

	@FXML
	private TableColumn<Releve, String> nom;

	@FXML
	private TableColumn<Releve, String> ID;
	
	@FXML
	private TableColumn<Releve, String> valeur;
	
	@FXML
	private TableColumn<Releve, String> comm;

	@FXML
	private Button valider;
	
	private static Tournee tournee;
	
	
	@FXML
	public void initialize(){
		tournee=AfficherDernereTournee.tournee;
		listerReleve();
	}
	
	public void listerReleve()
	{

	ObservableList<Releve> releve = ReleveController.loadReleves(tournee.getId());

	nom.setCellValueFactory(new PropertyValueFactory<Releve, String>("nomStation"));
	ID.setCellValueFactory(new PropertyValueFactory<Releve, String>("idStation"));
	valeur.setCellValueFactory(new PropertyValueFactory<Releve, String>("valeur"));
	comm.setCellValueFactory(new PropertyValueFactory<Releve, String>("commentaire"));
	
	tabStation.setItems(releve);
	
	}
	/**
	 * Fonction qui permet de valider une unité, et donc de l'ajouter à la base
	 * On ajoute la centrale que si le champs nom est non vide
	 */
	public void ValiderUnite(){
	
			valider.getParent().getScene().getWindow().hide();
		
	}

}
