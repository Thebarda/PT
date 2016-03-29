package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modele.Centrale;
import modele.Equipement;
import modele.ModeleTournee;
import modele.Station;

public class DetailController {

	@FXML
	private Label titre;
	
	private Centrale centrale;
	
	private Equipement equipement;
	
	private Station station;
	
	private static ModeleTournee modele;
	
	@FXML
	private Label text;
	
	@FXML
	private Label contenu;
	
	@FXML
	Button quitter;
	
	
	public void initialize()
	{
		int i;
		equipement=GererEquipementController.equipement;
		station=GererStationController.station;
		modele=GererModeleTourneeController.modele;
		centrale=GererCentraleController.centrale;
		if(centrale!=null){
			text.setText("\t\tNom:\n\n\t\tIdentifiant:");
			contenu.setText(centrale.getNom()+"\n\n"+centrale.getIdentiteNationale()+"\n\n");
		}
		if(equipement!=null)
		{
			text.setText("\t\tNom:\n\n\t\tECSH:\n\n\t\tDescription:");
			contenu.setText(equipement.getNom()+"\n\n"+equipement.getECSH()+"\n\n"+equipement.getDescription());
		}
		if(station!=null)
		{
			text.setText("\t\tNom:\n\n\t\tUnite:\n\n\t\tIntruction Courte:\n\n\t\tIntruction Longue:\n\n\t\tSeuil Bas:\n\n\t\tSeuil Haut:");
			contenu.setText(station.getNom()+"\n\n"+station.getNomUnite()+"\n\n"+station.getInstructionCourte()+"\n\n"+station.getInstructionLongue()+"\n\n"+station.getSeuilBas()+"\n\n"+station.getSeuilHaut());
		}
		if(modele!=null)
		{
			text.setText("\t\tNom:"+"\n\n\t\tDescription:");
			contenu.setText(modele.getNom()+"\n\n"+modele.getDescription());
			for(i=1;i<=modele.getStations().size();i++){
				text.setText(text.getText()+"\n\n\t\tStation "+i);
				contenu.setText(contenu.getText()+"\n\n"+modele.getStations().get(i).getNom());
			}
		}
		
	}
	
	public void quitter()
	{
		quitter.getParent().getScene().getWindow().hide();	
	}
	
	
	
	
}
