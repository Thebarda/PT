package controleur;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modele.Centrale;
import modele.Equipement;
import modele.ModeleTournee;
import modele.Station;
import modele.Unite;
import vue.Main;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class CreationModeleTourneeController {
	
	private Map<Integer,Station> map;
	private int rangActuel=0;
		
	@FXML
	private TextField nom;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private TextArea description;
	
	@FXML 
	private Label erreurDescription;
	
	@FXML 
	private Label erreurStation;
		
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private Button baisserOrdre;
	
	@FXML
	private Button monterOrdre;
	
	@FXML
	private Button supprimer;
	
	@FXML
	TableView<ObservableMap.Entry<Integer,Station>> tableStation;
	
	@FXML
	TableColumn<ObservableMap.Entry<Integer,Station>, Integer> Ordre;
	
	@FXML
	TableColumn<ObservableMap.Entry<Integer,Station>, Station> Station;
	
	private int idCentrale ;
	
	ObservableList<ModeleTournee> data=ModeleTourneeController.loadAllModeleTournee(idCentrale);

	ObservableMap<Integer,Station> Ostations=FXCollections.observableHashMap();
	ObservableList<ObservableMap.Entry<Integer,Station>> rowMaps;
	
	ObservableList<String> mois=FXCollections.observableArrayList("01 - Janvier","02 - Fevrier","03 - Mars","04 - Avril","05 - Mai","06 - Juin","07 - Juillet","08 - Aout","09 - Septembre","10 - Octobre","11 - Novembre","12 - Decembre");
	
	@FXML
	private void initialize(){
		tableStation.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>()
	    {
	        @Override
	        public void onChanged(Change<? extends Integer> change)
	        {
	            afficherBoutonOrdre();
	        }

	    });
		
	}
	
	/**
	 * Fonction qui permet de connaitre la station choisi par le controlleur de gestion de modele de tournee
	 * @param centrale
	 * 		centrale choisie
	 */
	public void init(Centrale centrale)
	{
		idCentrale=centrale.getId();
	}
	
	/**
	 * Fonction qui permet qui permet de connaitre la map contenant la nouvelle stations (modifier lors de l'ajout de stations)
	 * @param nouvelleMap
	 * 		map contenant la nouvelle stations et les anciennes
	 * @param nouveauRang
	 * 		nouveau rang de la map
	 */
	public void initFils(ObservableMap<Integer,Station> nouvelleMap,int nouveauRang)
	{
		Ostations=nouvelleMap;
		rangActuel=nouveauRang;
		
	}
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVideField(TextField texte)
	{
		if(texte.getText().isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextArea) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVide(TextArea textea)
	{
		if(textea.getText().isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Fonction qui permet de valider un modele de tournee , et donc de l'ajouter à la base
	 * On ajoute le modele de tournee que si le champs nom,description, la table de station et le mois de debut sont non vides
	 */
	public void ValiderModeleTournee(){
		boolean estValide=true;
		resetErreur();
		if(estVideField(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(description.getText().isEmpty()){
			erreurDescription.setText("Erreur : la description est vide");
			estValide=false;
		}
		if(tableStation.getItems().size()==0){
			erreurStation.setText("Erreur : il faut des stations pour une tournee ");
			estValide=false;
		}
		
		if(estValide == true)
		{
			
			ModeleTourneeController.addModeleTournee(nom.getText(),description.getText(),Ostations);			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'un modele de tournee, et cacher la fenêtre correspondante
	 * En réalité. La page est fermé par le controller gérant la page de gestion de modele de tournee
	 */
	public void annulerModeleTournee()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurNom.setText("");
		erreurDescription.setText("");
		erreurStation.setText("");
	}
	/**
	 * Fonction qui permet d'ouvrir la fenetre d'ajout de station
	 */
	public void ajouterStationTournee()
	{
		final Stage dialog = new Stage();
        dialog .initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ajout_Station.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
			dialog.setTitle("Ajout d'une station");
	        AjoutStationController controller = loader.getController();
	        controller.init(idCentrale,rangActuel,this,Ostations);
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerStation();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fonction qui permet de lister les stations du modele de tournee dans le tableau
	 */
	public void ListerStation(){
		ObservableList<ObservableMap.Entry<Integer,Station>> rowMaps = FXCollections.observableArrayList(Ostations.entrySet());
		Ordre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getKey()).asObject();
            }
        });
		
		Station.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Station>, ObservableValue<Station>>() {

            @Override
            public ObservableValue<Station> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Station>, Station> p) {
                return new SimpleObjectProperty<Station>(p.getValue().getValue());
            }
        });
		
		data.removeAll(data);
		data=ModeleTourneeController.loadAllModeleTournee(idCentrale);
		tableStation.setItems(rowMaps);
	}
	
	public void afficherBoutonOrdre(){
		baisserOrdre.setDisable(false);
		monterOrdre.setDisable(false);
		supprimer.setDisable(false);
	}
	
	public void monterOrdre(){
		Station stationAInverser;
		Station stationSelect;
		Integer stationAInverserKey;
		Integer stationSelectKey;
		if(Ostations.size()>1){
			stationSelectKey=tableStation.getSelectionModel().getSelectedItem().getKey();
			if(stationSelectKey!=1){
				stationAInverserKey=stationSelectKey-1;
				stationAInverser=Ostations.get(stationAInverserKey);
				stationSelect=Ostations.get(stationSelectKey);
				Ostations.remove(stationSelectKey);
				Ostations.remove(stationAInverserKey);
				Ostations.put(stationSelectKey, stationAInverser);
				Ostations.put(stationAInverserKey, stationSelect);
            	ListerStation();
			}
			
		}
	}
	
	public void baisserOrdre(){
		Station stationAInverser;
		Station stationSelect;
		Integer stationAInverserKey;
		Integer stationSelectKey;
		if(Ostations.size()>1){
			stationSelectKey=tableStation.getSelectionModel().getSelectedItem().getKey();
			if(stationSelectKey!=Ostations.size()){
				stationAInverserKey=stationSelectKey+1;
				stationAInverser=Ostations.get(stationAInverserKey);
				stationSelect=Ostations.get(stationSelectKey);
				Ostations.remove(stationSelectKey);
				Ostations.remove(stationAInverserKey);
				Ostations.put(stationSelectKey, stationAInverser);
				Ostations.put(stationAInverserKey, stationSelect);
            	ListerStation();
			}
			
		}
	}
	
	public void supprimerStation(){
		List<Integer> cleARemplacer;
		List<Station> stationARemplacer;
		int ancienSize;
		if(Ostations.size()>1){
			cleARemplacer=new ArrayList<Integer>();
			stationARemplacer=new ArrayList<Station>();
			if(tableStation.getSelectionModel().getSelectedItem().getKey()!=Ostations.size()+1){
				ancienSize=Ostations.size();
				Ostations.remove(tableStation.getSelectionModel().getSelectedItem().getKey());
				rangActuel--;
				for (int i=tableStation.getSelectionModel().getSelectedItem().getKey()+1;i<=ancienSize;i++){
					cleARemplacer.add(i);
					stationARemplacer.add(Ostations.get(i));
					Ostations.remove(i);
				}
				for(Integer i : cleARemplacer){
					if(i-3<0){
						i=3;
					}
					Ostations.put(cleARemplacer.get(i-3)-1,stationARemplacer.get(i-3));
				}
				ListerStation();
				baisserOrdre.setDisable(true);
				monterOrdre.setDisable(true);
				supprimer.setDisable(true);
			}
			
		}
	}
	
	
}
