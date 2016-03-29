package controleur;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modele.Centrale;
import modele.Equipement;
/**
 * Controleur relatif à l'interface de création d'équipement
 */
public class CreationEquipementController {
		
	@FXML
	private TextField nom;
	
	@FXML
	private TextArea description ;
	
	@FXML
	private TextField ECSH1;
	
	@FXML
	private TextField ECSH2;
	
	@FXML
	private TextField ECSH3;
	
	@FXML
	private TextField ECSH4;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private Label erreurDesc;
	
	@FXML
	private Label erreurECSH;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private Label titre;	
	
	private int idCentrale;
	
	
	@FXML
	private void initialize() {
		if(GererEquipementController.idModif !=-1){
			titre.setText("Modification de l'�quipement");
			Equipement equipement  = GererEquipementController.equipement;
			nom.setText(equipement.getNom());
			int nb =equipement.getECSH().length()/4;
			if(nb>0){
				ECSH1.setText(equipement.getECSH().substring(0,4));
			}
			if(nb>1){
				ECSH2.setText(equipement.getECSH().substring(4,8));
			}
			if(nb>2){
				ECSH3.setText(equipement.getECSH().substring(4,6));
			}
			ECSH4.setText(equipement.getECSH().substring(8));
			description.setText(equipement.getDescription());
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
	 * Fonction qui permet de valider un équipement, et donc de l'ajouter à la base
	 * On ajoute l'équipement que si les champs nom et description sont non vide
	 */
	public void ValiderEquipement(){
		boolean estValide=true;
		resetErreur();
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(estVide(ECSH1) || estVide(ECSH2) || estVide(ECSH3)|| estVide(ECSH4)){
			erreurECSH.setText("Erreur : le repere n'est pas rempli");
			estValide=false;
		}
		if(description.getText().isEmpty())
		{
			erreurDesc.setText("Erreur : la description est vide");
			estValide=false;
		}
		
		if((estValide==true)&&(GererEquipementController.idModif ==-1))
		{
			EquipementController.addEquipement(idCentrale,nom.getText(), description.getText(),(ECSH1.getText()+ECSH2.getText()+ECSH3.getText()+ECSH4.getText()));
			annuler.getParent().getScene().getWindow().hide();
		}
		if((estValide==true)&&(GererEquipementController.idModif !=-1)){
			Equipement equipement  = GererEquipementController.equipement;
			EquipementController.modifier(equipement.getId(), nom.getText(), description.getText(), (ECSH1.getText()+ECSH2.getText()+ECSH3.getText()+ECSH4.getText()));
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'un équipement, et cache donc la fenêtre correpsondante.
	 * La fênetre est fermé par le controleur gérant la gestion d'équipement
	 */
	public void annulerEquipement()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	/**
	 * Fonction permettant d'initialiser la centrale actuellement choisi dans l'interface de gestion d'équipements.
	 * Cette fonction à pour but de connaitre la centrale actuellement selectionnés, et donc d'ajouter l'équipement à la bonne centrale
	 * @param centrale
	 * 		Centrale actuellement selectionnnés (centrale de l'équipement à ajoutés)
	 */
	public void init(Centrale centrale)
	{
		idCentrale=centrale.getId();
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurDesc.setText("");
		erreurNom.setText("");
		erreurECSH.setText("");
	}
	
}
