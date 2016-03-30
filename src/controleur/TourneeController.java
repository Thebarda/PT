package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
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
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idTournee, dateExport, t.idModele, estExportee, estTerminee, nomTournee, dateReleve FROM tournee t "
										+ "INNER JOIN modele_tournee mdt ON mdt.idModele = t.idModele "
										+ "WHERE ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "INNER JOIN modele_tournee mt ON asm.idModele=mt.idModele "
										+ "WHERE mt.idModele=t.idModele "
										+ "limit 1 "
										+ ")= " + idCentrale + " AND estTerminee = 0 AND mdt.estSupprime = 0");
			while(resultat.next()){
				int  idTournee = resultat.getInt("idTournee");
				String dateExport = resultat.getString("dateExport");
				String nomTournee = resultat.getString("nomTournee");
				int idModele = resultat.getInt("idModele");
				boolean estExportee = resultat.getBoolean("estExportee");
				boolean estTerminee = resultat.getBoolean("estTerminee");
				String dateReleve = resultat.getString("dateReleve");
	
				HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
				
				statutStations = connexion.createStatement();
				
				resultatStations = statutStations.executeQuery("SELECT s.idStation, nomStation, instructionsCourtes, "
													+ "instructionsLongues, marqueur, seuilBas, seuilHaut, "
													+ "idEquipement, idUnite, paramFonc, valeurNormale, "
													+ "MISH, estSupprime from station s "
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
							seuilHaut,seuilBas,valeurNormale,resultatStations.getString("paramFonc"),resultatStations.getBoolean("MISH"),resultatStations.getBoolean("estSupprime"));
					stations.put(i, station);
					i++;
				}
				Tournee tournee = new Tournee(idTournee,
						nomTournee,
						idModele,
						stations,
						estExportee,
						estTerminee,
						dateExport,
						dateReleve);
				
				tournees.add(tournee);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
				if(resultatStations!=null){
					resultatStations.close();
				}
				if(resultat!=null){
		             resultat.close();
				}
				if(statut!=null){
		             statut.close();
				}
				if(statutStations!=null){
		             statutStations.close();
				}
				if(connexion!=null){
		             connexion.close(); 
				}
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
		return tournees;
	}
	
	public static ObservableList<Tournee> loadTourneesTerminees(int idCentrale){
		
		ObservableList<Tournee> tournees=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		ResultSet resultatStations = null;
		Statement statut = null;
		Statement statutStations = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idTournee, dateExport, idModele, estExportee, estTerminee, nomTournee, dateReleve FROM tournee t "
										+ "WHERE ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "INNER JOIN modele_tournee mt ON asm.idModele=mt.idModele "
										+ "WHERE mt.idModele=t.idModele "
										+ "limit 1 "
										+ ")= " + idCentrale + " AND estTerminee = 1");
			while(resultat.next()){
				int  idTournee = resultat.getInt("idTournee");
				String dateExport = resultat.getString("dateExport");
				String nomTournee = resultat.getString("nomTournee");
				int idModele = resultat.getInt("idModele");
				boolean estExportee = resultat.getBoolean("estExportee");
				boolean estTerminee = resultat.getBoolean("estTerminee");
				String dateReleve = resultat.getString("dateReleve");
	
				HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
				
				statutStations = connexion.createStatement();
				
				resultatStations = statutStations.executeQuery("SELECT s.idStation, nomStation, instructionsCourtes, "
													+ "instructionsLongues, marqueur, seuilBas, seuilHaut, "
													+ "idEquipement, idUnite, paramFonc, valeurNormale, "
													+ "MISH, estSupprime from station s "
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
							seuilHaut,seuilBas,valeurNormale,resultatStations.getString("paramFonc"),resultatStations.getBoolean("MISH"),resultatStations.getBoolean("estSupprime"));
					stations.put(i, station);
					i++;
				}
				Tournee tournee = new Tournee(idTournee,
						nomTournee,
						idModele,
						stations,
						estExportee,
						estTerminee,
						dateExport,
						dateReleve);
				
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
		tournees.sort(new Comparator<Tournee>(){
			@Override
			public int compare(Tournee t1, Tournee t2){
				String date1 = t1.getDateReleve();
				String date2 = t2.getDateReleve();
				int annee1 = Integer.parseInt(date1.substring(6,10));
				int annee2 = Integer.parseInt(date2.substring(6,10));
				int mois1  = Integer.parseInt(date1.substring(3,5));
				int mois2  = Integer.parseInt(date2.substring(3,5));
				int jour1  = Integer.parseInt(date1.substring(0,2));
				int jour2  = Integer.parseInt(date2.substring(0,2));
				int heure1 = Integer.parseInt(date1.substring(11,13));
				int heure2 = Integer.parseInt(date2.substring(11,13));
				int min1 = Integer.parseInt(date1.substring(14,16));
				int min2 = Integer.parseInt(date2.substring(14,16));
				
				if(annee1 > annee2)
					return -1;
				if(annee1 < annee2)
					return 1;
				
				if(mois1 > mois2)
					return -1;
				if(mois1 < mois2)
					return 1;
				
				if(jour1 > jour2)
					return -1;
				if(jour1 < jour2)
					return 1;
				
				if(heure1 > heure2)
					return -1;
				if(heure1 < heure2)
					return 1;
				
				if(min1 > min2)
					return -1;
				if(min1 < min2)
					return 1;
				
				return 0;
			}
		});
		return tournees;
	}
	
	public static void addTournee(Tournee tournee)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "tournee(nomTournee,dateExport,idModele,estExportee,estTerminee) "
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
			
			/*// On recup�re l'id de la tourn�e cr�e
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
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
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
	
	public static void setExportee(int idTournee, int export) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE tournee "
					+ "SET estExportee = ? "
					+ "WHERE idTournee = ?");
			preparedStatement.setInt(1, export);
			preparedStatement.setInt(2, idTournee);
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
	
	public static void setDateExport(int idTournee, String dateExport) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE tournee "
					+ "SET dateExport = ? "
					+ "WHERE idTournee = ?");
			preparedStatement.setString(1, dateExport);
			preparedStatement.setInt(2, idTournee);
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
	
	public static void setDateReleve(int idTournee, String dateReleve) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE tournee "
					+ "SET dateReleve = ? "
					+ "WHERE idTournee = ?");
			preparedStatement.setString(1, dateReleve);
			preparedStatement.setInt(2, idTournee);
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
	
	public static Tournee loadTourneeById(int idTournee){
		
		Tournee tournee = null;
		Connection connexion = null;
		ResultSet resultat = null;
		ResultSet resultatStations = null;
		Statement statut = null;
		Statement statutStations = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idTournee, dateExport, idModele, estExportee, estTerminee, nomTournee, dateReleve FROM tournee t "
										+ "WHERE idTournee = " + idTournee);
			while(resultat.next()){
				String dateExport = resultat.getString("dateExport");
				String nomTournee = resultat.getString("nomTournee");
				int idModele = resultat.getInt("idModele");
				boolean estExportee = resultat.getBoolean("estExportee");
				boolean estTerminee = resultat.getBoolean("estTerminee");
				String dateReleve = resultat.getString("dateReleve");
	
				HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
				
				statutStations = connexion.createStatement();
				
				resultatStations = statutStations.executeQuery("SELECT s.idStation, nomStation, instructionsCourtes, "
													+ "instructionsLongues, marqueur, seuilBas, seuilHaut, "
													+ "idEquipement, idUnite, paramFonc, valeurNormale, "
													+ "MISH, estSupprime from station s "
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
							seuilHaut,seuilBas,valeurNormale,resultatStations.getString("paramFonc"),resultatStations.getBoolean("MISH"), resultatStations.getBoolean("estSupprime"));
					stations.put(i, station);
					i++;
				}
				tournee = new Tournee(idTournee,
						nomTournee,
						idModele,
						stations,
						estExportee,
						estTerminee,
						dateExport,
						dateReleve);
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
		return tournee;
	}
	
	public static int getExport(int idTournee){
		
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		int export = -1;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT estExportee FROM tournee t "
										+ "WHERE idTournee = " + idTournee);
			resultat.next();
			
			export = resultat.getInt("estExportee");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
	             resultat.close();
	             statut.close();
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
		return export;
	}
}
