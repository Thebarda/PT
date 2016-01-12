package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Station;
import modele.Tournee;

public class TourneeController {
	
	public static ObservableList<Tournee> loadTournee(int idCentrale){
		
		ObservableList<Tournee> tournees=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		ResultSet resultatStations = null;
		Statement statut = null;
		Statement statutStations = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idTournee, dateTournee, idModele, estExportee, estTerminee, nomTournee FROM tournee t "
										+ "WHERE ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "INNER JOIN modele_tournee mt ON asm.idModele=mt.idModele "
										+ "WHERE mt.idModele=t.idModele "
										+ "limit 1 "
										+ ")= " + idCentrale + " AND estTerminee = 0");
			while(resultat.next()){
				int  idTournee = resultat.getInt("idTournee");
				String dateTournee = resultat.getString("dateTournee");
				String nomTournee = resultat.getString("nomTournee");
				int idModele = resultat.getInt("idModele");
				boolean estExportee = resultat.getBoolean("estExportee");
				boolean estTerminee = resultat.getBoolean("estTerminee");
	
				HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
				
				statutStations = connexion.createStatement();
				
				resultatStations = statutStations.executeQuery("SELECT s.idStation, nomStation, instructionsCourtes, "
													+ "instructionsLongues, marqueur, seuilBas, seuilHaut, "
													+ "idEquipement, idUnite, paramFonc, valeurNormale, "
													+ "MISH from station s "
													+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
													+ "WHERE asm.idModele=" + idModele + " "
													+ "ORDER BY asm.ordre ASC");
				int i = 1;
				while(resultatStations.next()){
					
					//1er bit = 1   ==>  seuilHaut NULL
					//2eme bit = 1  ==>  seuilBas NULL
					//3eme bit = 1  ==>  valeurNormale NULL
					
					double seuilHaut;
					double seuilBas;
					double valeurNormale;
					
					if (resultatStations.getString("marqueur").substring(0, 1).equals("1")){
						seuilHaut = 0.0;
					}else{
						seuilHaut = resultatStations.getDouble("seuilHaut");
					}
					if (resultatStations.getString("marqueur").substring(1, 2).equals("1")){
						seuilBas = 0.0;
					}else{
						seuilBas = resultatStations.getDouble("seuilBas");
					}
					if(resultatStations.getString("marqueur").substring(2, 3).equals("1")){
						valeurNormale = 0.0;
					}else{
						valeurNormale = resultatStations.getDouble("valeurNormale");
					}
					
					Station station = new Station(resultatStations.getInt("idStation"),
							resultatStations.getString("nomStation"),
							resultatStations.getString("instructionsCourtes"),
							resultatStations.getString("instructionsLongues"),
							resultatStations.getInt("idUnite"), resultatStations.getString("marqueur"),
							seuilHaut,seuilBas,valeurNormale,resultatStations.getString("paramFonc"),resultatStations.getBoolean("MISH"));
					stations.put(i, station);
					i++;
				}
				Tournee tournee = new Tournee(idTournee,
						nomTournee,
						idModele,
						stations,
						estExportee,
						estTerminee,
						dateTournee);
				
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
	             statutStations.close();
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
		return tournees;
	}
	
	public static void addTournee(Tournee tournee)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "tournee(nomTournee,dateTournee,idModele,estExportee,estTerminee) "
					+ "VALUES(?,?,?,?,?)");
			preparedStatement.setString(1, tournee.getNom());
			preparedStatement.setString(2, tournee.getDate());
			preparedStatement.setInt(3, tournee.getIdModele());
			int isExportee;
			if(tournee.isEstExportee()){
				isExportee = 1;
			} else {
				isExportee = 0;
			}
			preparedStatement.setInt(4, isExportee);
			int isTerminee;
			if(tournee.isTerminee()){
				isTerminee = 1;
			} else {
				isTerminee = 0;
			}
			preparedStatement.setInt(5, isTerminee);
			preparedStatement.executeUpdate();
			
			/*// On recupère l'id de la tournée crée
			int id = preparedStatement.getGeneratedKeys().getInt(1);
			
			// Ajout dans l'association avec les stations
			HashMap<Integer, Station> stations = tournee.getStations();
			for(int i=1; i<=stations.size();i++){
				Station station = stations.get(i);
				PreparedStatement preparedStatementAsso = connexion.prepareStatement("INSERT INTO "
					+ "releve(idTournee, idStation) "
					+ "VALUES(?,?)");
				preparedStatementAsso.setInt(1, id);
				preparedStatementAsso.setInt(2, station.getId());
				preparedStatementAsso.executeUpdate();
			}*/
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
	}

	public static void setTerminee(int idTournee) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE tournee "
					+ "SET estTerminee = 1 "
					+ "WHERE idTournee = ?");
			preparedStatement.setInt(1, idTournee);
			preparedStatement.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
	}
	
	public static void setExportee(int idTournee) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE tournee "
					+ "SET estExportee = 1 "
					+ "WHERE idTournee = ?");
			preparedStatement.setInt(1, idTournee);
			preparedStatement.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
	}
}
