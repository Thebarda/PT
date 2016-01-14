package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modele.Centrale;
import modele.Equipement;
import modele.ModeleTournee;
import modele.Station;

public class SupprimerController {

	@FXML
	private Label titre;
	
	private Centrale centrale;
	
	private Equipement equipement;
	
	private Station station;
	
	private static ModeleTournee modele;
	
	@FXML
	private Label text;
	
	@FXML
	private Label text2;
	
	@FXML
	Button valider;
	
	@FXML
	Button annuler;
	
	
	public void initialize()
	{
		centrale=GererCentraleController.centrale;
		equipement=GererEquipementController.equipement;
		station=GererStationController.station;
		modele=GererModeleTourneeController.modele;
		
		if(centrale!=null)
		{
			titre.setText("d'une Centrale");
			System.out.println(EquipementController.loadEquipement(centrale.getId()).size());
			if(EquipementController.loadEquipement(centrale.getId()).size()!=0)
			{
				text.setText("il y a des équipements qui dépendent de cette centrale");
				text2.setText("etes vous sur de vouloir supprimer cette centrale ");
			}
		}
		if(equipement!=null)
		{
			titre.setText("d'un equipement");
				if(StationController.loadStation(equipement.getId()).size()!=0)
				{
				text.setText("il y a des station qui dépendent de cet equipement");
				text2.setText("etes vous sur de vouloir supprimer cet equipement ");
				}
		}
		if(station!=null)
		{
			titre.setText("d'une station");
			if(StationController.estUtiliseDansModele(station.getId()))
			{
				text.setText("il y a des station qui dépendent de cet equipement");
				text2.setText("etes vous sur de vouloir supprimer cet equipement ");
			}
		}
		if(modele!=null)
		{
			titre.setText("d'un modele de tournee");
			
			
		}
		
	}
	
	public void valider()
	{
		if(centrale!=null)
		{
			
		}
	}
	
	public void annuler()
	{
		annuler.getParent().getScene().getWindow().hide();	
	}
	
	
	
	
}
