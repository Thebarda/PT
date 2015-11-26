package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Station;
import modele.Tournee;

public class TourneeController {
	
	public static ObservableList<Tournee> loadTournee(int idCentrale, String date){
		
		ObservableList<Tournee> tournees=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		ResultSet resultatStations = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idTournee, dateTournee, idModele, estExportee, estTerminee FROM tournee t "
										+ "WHERE ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "INNER JOIN modele_tournee mt ON asm.idModele=mt.idModele "
										+ "WHERE mt.idModele=t.idModele "
										+ "limit 1 "
										+ ")= " + idCentrale + " AND dateTournee='" + date + "'");
			while(resultat.next()){
				int  idTournee = resultat.getInt("idTournee");
				String dateTournee = resultat.getString("dateTournee");
				int idModele = resultat.getInt("idModele");
				boolean estExportee = resultat.getBoolean("estExportee");
				boolean estTerminee = resultat.getBoolean("estTerminee");
				HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
				resultatStations = statut.executeQuery("SELECT r.idStation, nomStation, instructionsCourtes, "
													+ "instructionsLongues, seuilBas, seuilHaut, frequenceControle, "
													+ "idEquipement, idUnite, paramFonc, valeurNormale, "
													+ "MISH from station s "
													+ "INNER JOIN releve r ON s.idStation=r.idStation "
													+ "WHERE r.idTournee=" + idTournee);
				int i = 1;
				while(resultatStations.next()){
					Station station = new Station(resultatStations.getInt("idStation"),
							resultatStations.getString("nomStation"),
							resultatStations.getString("instructionsCourtes"),
							resultatStations.getString("instructionsLongues"),
							resultatStations.getInt("idUnite"),resultatStations.getInt("FrequenceControle"),
							resultatStations.getInt("seuilHaut"),resultatStations.getInt("seuilBas"),resultatStations.getInt("valeurNormale"),resultatStations.getString("paramFonc"),resultatStations.getString("MISH"));
					stations.put(i, station);
					i++;
				}
				
				Tournee tournee = new Tournee(idTournee,
						dateTournee,
						idModele,
						stations,
						estExportee,
						estTerminee);
				
				tournees.add(tournee);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
				 resultatStations.close();
	             resultat.close();
	             statut.close();  
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
		return tournees;
	}
	
}
