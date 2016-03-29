package controleur;



import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Equipement;
import modele.Station;
import modele.Unite;
import vue.Main;

public class CreationStationController {
		
	@FXML
	private TextField nom;
	
	@FXML
	private TextArea instructionCourtes ;
	
	@FXML
	private TextArea instructionLongues ;
	
	@FXML
	private ComboBox<Unite> listeUnite;
	
	@FXML
	private TextField seuilHaut;
	
	@FXML
	private TextField seuilBas;
	
	@FXML
	private TextField valeurNormale;
	
	@FXML
	private TextField MISH;
	
	@FXML
	private Label erreurMISH;
	
	@FXML
	private TextField paramFonc;
	
	@FXML
	private Label erreurParamFonc;
	
	@FXML 
	private Label erreurValeurNormale;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private Label erreurinstrCourtes;
	
	@FXML
	private Label erreurinstrLongues;
	
	@FXML
	private Label erreurUnite;
	
	@FXML
	private Label erreurSeuilHaut;
	
	@FXML
	private Label erreurSeuilBas;
		
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private int idEquipement;
	
	@FXML
	private RadioButton uniteRadio;
	
	@FXML
	private RadioButton faitRadio;
	
	@FXML
	private ToggleGroup group;
	
	@FXML
	private Button ajouter;
	
	ObservableList<Unite> unite=UniteController.loadUnites();
	
	@FXML
	TableColumn<Unite, Integer> ID;
	
	@FXML
	TableColumn<Unite, String> Nom;
	
	@FXML
	Label titre;
	
	@FXML
	private void initialize() {
		listeUnite.setItems(unite);
		if(GererStationController.idStation !=-1){
			titre.setText("Modification de la station");
			Station station = GererStationController.station;
			nom.setText(station.getNom());
			instructionCourtes.setText(station.getInstructionCourte());
			instructionLongues.setText(station.getInstructionLongue());
			if(station.getNomUnite().equals("__VERIFICATION")){
				faitRadio.setSelected(true);
			}
			else{
				uniteRadio.setSelected(true);
				listeUnite.setValue(UniteController.getUnitById(station.getIdUnite()));
				seuilBas.setText(""+station.getSeuilBas());
				seuilHaut.setText(""+station.getSeuilHaut());
				valeurNormale.setText(""+station.getValeurNormale());
			}
			paramFonc.setText(station.getParamFonc());
			MISH.setText(""+station.getMishEntier());
		}
	}
	
	/**
	 * Fonction qui permet de verifier si un champs de Texte est vide ou non
	 * @param texte
	 * 		Champs texte (ou TextField) à analyser
	 * @return
	 * 		Vrai si le champs est vide, faux sinon
	 */
	public Boolean estVide(TextField texte)
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
	 * Fonction qui permet de determiner si une combobox d'unite est vide
	 * @param combo
	 * 		combobox a tester
	 * @return
	 * 		vrai si elle est vide, faux sinon
	 */
	public Boolean estVideComboBox(ComboBox<Unite> combo)
	{
		if(combo.getValue()==null)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	/**
	 * Fonction qui permet de determiner si une chaine est un entier
	 * @param chaine
	 * 		chaine a tester
	 * @return
	 * 		vrai si c'est un entier, faux sinon
	 */
	public boolean estUnDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	/**
	 * Fonction qui permet de valider une station, et donc de l'ajouter à la base
	 * On ajoute la station que si les champs nom,instructions courtes,unité, seuil haut, seuil bas instruction longues sont non vide
	 */
	public void ValiderStation(){
		boolean estValide=true;
		double haut=0.0;
		double bas=0.0;
		double normale=0.0;
		int unit = -1;
		
		resetErreur();
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(instructionCourtes.getText().isEmpty())
		{
			erreurinstrCourtes.setText("Erreur : l'instruction courte est vide");
			estValide=false;
		}
		if(uniteRadio.isSelected()){
			if(estVideComboBox(listeUnite))
			{
				erreurUnite.setText("Erreur : l'unite est vide");
				estValide=false;
			}
			else{
				unit = listeUnite.getValue().getId();
			}
		}
		else{
			if(faitRadio.isSelected()){
				unit = UniteController.getUnitVerif().getId();
			}
		}
		if(estVide(MISH)){
			erreurMISH.setText("Erreur : MISH est vide");
			estValide=false;
		}
		if(estVide(paramFonc)){
			erreurParamFonc.setText("Erreur : le parametre de fonctionnement est vide");
			estValide=false;
		}
		/*if(!estVide(MISH) && ((!MISH.getText().equals("1")) || (!MISH.getText().equals("0")))){
			erreurMISH.setText("Erreur : MISH doit être un booleen");
			estValide=false;
		}*/
		if(!seuilHaut.getText().isEmpty() && !estUnDouble(seuilHaut.getText())){
			erreurSeuilHaut.setText("Erreur : un nombre doit être saisi");
			estValide=false;
		}
		
		if(!seuilBas.getText().isEmpty() &&!estUnDouble(seuilBas.getText())){
			erreurSeuilBas.setText("Erreur : un nombre doit être saisi");
			estValide=false;
		}
		if(!valeurNormale.getText().isEmpty() &&!estUnDouble(valeurNormale.getText())){
			erreurValeurNormale.setText("Erreur : un nombre doit être saisi");
			estValide=false;
		}
		if(!seuilBas.getText().isEmpty() && estUnDouble(seuilBas.getText()) && !seuilHaut.getText().isEmpty() && estUnDouble(seuilHaut.getText()) && Double.parseDouble(seuilHaut.getText())<Double.parseDouble(seuilBas.getText())){
			erreurSeuilBas.setText("Erreur : le seuil bas doit être inférieur au seuil haut !");
			estValide=false;
		}
		if(!valeurNormale.getText().isEmpty() && estUnDouble(valeurNormale.getText()) && !seuilHaut.getText().isEmpty() && estUnDouble(seuilHaut.getText()) && Double.parseDouble(seuilHaut.getText())<Double.parseDouble(valeurNormale.getText())){
			erreurValeurNormale.setText("Erreur : la valeur doit être inférieur au seuil haut !");
			estValide=false;
		}
		if(!valeurNormale.getText().isEmpty() && estUnDouble(valeurNormale.getText()) && !seuilBas.getText().isEmpty() && estUnDouble(seuilBas.getText()) && Double.parseDouble(seuilBas.getText())>Double.parseDouble(valeurNormale.getText())){
			erreurValeurNormale.setText("Erreur : la valeur doit être supérieur au seuil bas !");
			estValide=false;
		}
		
		if((estValide==true)&&(GererStationController.idStation==-1))
		{
			String m1 = "1", m2 = "1", m3 = "1";
			if(!seuilHaut.getText().isEmpty()){
				haut=Double.parseDouble(seuilHaut.getText());
				m1 = "0";
			}
			if(!seuilBas.getText().isEmpty()){
				bas=Double.parseDouble(seuilBas.getText());
				m2 = "0";
			}
			if(!valeurNormale.getText().isEmpty()){
				normale=Double.parseDouble(valeurNormale.getText());
				m3 = "0";
			}
			StationController.addStation( nom.getText(), instructionCourtes.getText(),instructionLongues.getText(),unit,(m1+m2+m3),haut,bas,idEquipement,paramFonc.getText(),normale,Boolean.valueOf(MISH.getText()));
			annuler.getParent().getScene().getWindow().hide();
		}
		if((estValide==true)&&(GererStationController.idStation!=-1))
		{
			String m1 = "1", m2 = "1", m3 = "1";
			if(!seuilHaut.getText().isEmpty()){
				haut=Double.parseDouble(seuilHaut.getText());
				m1 = "0";
			}
			if(!seuilBas.getText().isEmpty()){
				bas=Double.parseDouble(seuilBas.getText());
				m2 = "0";
			}
			if(!valeurNormale.getText().isEmpty()){
				normale=Double.parseDouble(valeurNormale.getText());
				m3 = "0";
			}
			StationController.modifier(GererStationController.idStation, nom.getText(), instructionCourtes.getText(), instructionLongues.getText(), unit, haut, bas, paramFonc.getText(), normale, Boolean.valueOf(MISH.getText()), (m1+m2+m3));
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une station, et ferme donc la fenêtre correspondante
	 */
	public void annulerStation()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	/**
	 * Fonction qui permet l'initialiser l'id de l'équipement choisi precedemment pour la création d'une station
	 * @param equipement
	 * 		Equipement choisi
	 */
	public void init(Equipement equipement)
	{
		idEquipement=equipement.getId();
	}
	
	/**
	 * Fonction qui permet d'ouvrir la fenêtre pour créer une unité
	 * On gère aussi la fermeture de cette fenêtre, avec la mise à jour de la liste des Unité
	 */
	public void ajouterUnit(){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("creation_Unit.fxml"));
		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
	        dialog.setScene(dialogScene);
	        dialog.show();
	        dialog.setOnHidden(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	ListerUnit();
	            	dialog.close();
	            }
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Fonction qui permet de rafraichir la liste des unités
	 */
	public void ListerUnit(){
		ObservableList<Unite> unit=UniteController.loadUnites();
		listeUnite.setItems(unit);
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurNom.setText("");
		erreurinstrCourtes.setText("");
		erreurUnite.setText("");
		erreurSeuilBas.setText("");
		erreurSeuilHaut.setText("");
		erreurValeurNormale.setText("");
		erreurParamFonc.setText("");
		erreurMISH.setText("");
	}
	
}
