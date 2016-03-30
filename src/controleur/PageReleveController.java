package controleur;



import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
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
	private TableColumn<Releve, Double> valeur;
	
	@FXML
	private TableColumn<Releve, String> comm;

	@FXML
	private Button valider;
	
	private static Tournee tournee;
	
	
	@FXML
	public void initialize(){
		if(AccueilController.tourneeSelect!=null){
			System.out.println("C VREMENT LOL");
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
	valeur.setCellValueFactory(new PropertyValueFactory<Releve, Double>("valeur"));
	valeur.setCellFactory(TextFieldTableCell.<Releve, Double>forTableColumn(new DoubleStringConverter()));
	valeur.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Releve, Double>>() {
		@Override
		public void handle(CellEditEvent<Releve, Double> t) {
			((Releve)t.getTableView().getItems().get(t.getTablePosition().getRow())).setValeur(t.getNewValue());
		}});
	comm.setCellValueFactory(new PropertyValueFactory<Releve, String>("commentaire"));
	comm.setCellFactory(TextFieldTableCell.forTableColumn());
	comm.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Releve, String>>() {
		@Override
		public void handle(CellEditEvent<Releve, String> t) {
			((Releve)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCommentaire(t.getNewValue());
		}});
	tabStation.setItems(releve);
	System.out.println(comm.isEditable());
	tabStation.setEditable(true);
	}
	/**
	 * Fonction qui permet de valider une unité, et donc de l'ajouter à la base
	 * On ajoute la centrale que si le champs nom est non vide
	 */
	public void Valider(){
		if(AccueilController.tourneeSelect!=null){
			TourneeController.setEtat(tournee.getId(), tournee.getEtat()+1);
			for(Releve releve : tabStation.getItems()){
				ReleveController.modifierReleve(releve.getId(), releve.getValeur(), releve.getCommentaire());
			}
		}
		valider.getParent().getScene().getWindow().hide();
		
	}

}
