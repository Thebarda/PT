package controleur;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class CreationUnitController {
		
	@FXML
	private TextField nom;
	
	
	@FXML 
	private Label erreurNom;
	
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private int idUnit;
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
	 * Fonction qui permet de valider une unité, et donc de l'ajouter à la base
	 * On ajoute la centrale que si le champs nom est non vide
	 */
	public void ValiderUnite(){
		boolean estValide=true;
		resetErreur();
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(estValide == true)
		{
			UniteController.addUnite(nom.getText());
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une unité, et cache la fenêtre correspondante
	 * En réalité. La page est fermé par le controller gérant la page de gestion de Station
	 */
	public void annulerUnite()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurNom.setText("");
	}
}
