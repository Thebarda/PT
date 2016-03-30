package controleur;



import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Releve;
import modele.Tournee;

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
		if(AccueilController.tourneeSelect!=null){
			tournee=AccueilController.tourneeSelect;
			valeur.setEditable(true);
			comm.setEditable(true);
			
		}
		else{
			tournee=AfficherDernereTournee.tournee;
		}
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
	public void Valider(){
	
			valider.getParent().getScene().getWindow().hide();
		
	}

}
