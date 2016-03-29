package controleur;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.Centrale;

/**
 * Controleur relatif à l'interface de création de centrale
 */
public class CreationCentraleController {
		
	@FXML
	private TextField nom;
	
	@FXML
	private TextField codeIdentiteNationale ;
	
	@FXML 
	private Label erreurNom;
	
	@FXML
	private Label erreurCode;
	
	@FXML
	private Button valider;
	
	@FXML 
	private Button annuler;
	
	@FXML
	private Label titre;
	
	
	@FXML
	private void initialize() {
		Centrale centrale;
		if(GererCentraleController.idModif!=-1){
			titre.setText("Modification de Centrale");
			centrale = GererCentraleController.centrale;
			nom.setText(centrale.getNom());
			codeIdentiteNationale.setText(centrale.getIdentiteNationale());
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
	 * Fonction qui permet de valider une centrale, et donc de l'ajouter à la base
	 * On ajoute la centrale que si les champs nom et identité sont non vide
	 */
	public void ValiderCentrale(){
		boolean estValide=true;
		resetErreur();
		if(estVide(nom)){
			erreurNom.setText("Erreur : le nom est vide");
			estValide=false;
		}
		if(estVide(codeIdentiteNationale))
		{
			erreurCode.setText("Erreur : le code est vide");
			estValide=false;
		}
		System.out.println(GererCentraleController.idModif);
		if((estValide == true)&&(GererCentraleController.idModif==-1))
		{
			System.out.println("passe ajout");
			CentraleControler.addCentrale(nom.getText(), codeIdentiteNationale.getText());
			annuler.getParent().getScene().getWindow().hide();
		}
		if((estValide == true)&&(GererCentraleController.idModif!=-1)){
			System.out.println("passe modif");
			CentraleControler.modifier(GererCentraleController.idModif, nom.getText(), codeIdentiteNationale.getText());
			annuler.getParent().getScene().getWindow().hide();
		}
		
	}
	/**
	 * Fonction qui permet d'annuler la création d'une centrale, et cahce la fenêtre correspondante
	 * En réalité. La page est fermé par le controller gérant la page de gestion de Centrale
	 */
	public void annulerCentrale()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	/**
	 * Fonction qui permet de supprimer tous les messages d'erreur.
	 * Se lance lorsque l'utilisateur appuie sur le bouton valider
	 */
	public void resetErreur(){
		erreurCode.setText("");
		erreurNom.setText("");
	}
	
	
}
