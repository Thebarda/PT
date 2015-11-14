package controleur;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.Centrale;
/**
 * Controleur relatif à l'interface de création d'équipement
 */
public class CreationEquipementController {
		
	@FXML
	private TextField nom;
	
	@FXML
	private TextField description ;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private Label erreurDesc;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	
	private int idCentrale;
	
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
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(estVide(description))
		{
			erreurDesc.setText("Erreur : la description est vide");
			estValide=false;
		}
		
		if(estValide==true)
		{
			EquipementController.addEquipement(idCentrale,nom.getText(), description.getText());
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
	
	
	
}
