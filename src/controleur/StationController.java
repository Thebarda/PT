package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modele.ModeleTournee;
import modele.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Controleur relatif aux Stations et qui permet la gestion de la BD en rapport avec les Stations
 */
public class StationController 
{

	/**
	 * Fonction permettant de r�cuperer tous les objets Station dans la base de donn�e pour un �quipement donn�
	 * @param idEquipement id de l'�quipement dont on veut connaitre les stations
	 * @return
	 */
	public static ObservableList<Station> loadStation(int idEquipement){
		
		ObservableList<Station> stations=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM station WHERE idEquipement='" + idEquipement + "'");
			while(resultat.next()){
				
				//1er bit = 1   ==>  seuilHaut NULL
				//2eme bit = 1  ==>  seuilBas NULL
				//3eme bit = 1  ==>  valeurNormale NULL
				
				double seuilHaut;
				double seuilBas;
				double valeurNormale;
				
				if (resultat.getString("marqueur").substring(0, 1).equals("1")){
					seuilHaut = 0.0;
				}else{
					seuilHaut = resultat.getDouble("seuilHaut");
				}
				if (resultat.getString("marqueur").substring(1, 2).equals("1")){
					seuilBas = 0.0;
				}else{
					seuilBas = resultat.getDouble("seuilBas");
				}
				if(resultat.getString("marqueur").substring(2, 3).equals("1")){
					valeurNormale = 0.0;
				}else{
					valeurNormale = resultat.getDouble("valeurNormale");
				}
				
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"), resultat.getString("marqueur"),
						seuilHaut, seuilBas, valeurNormale,resultat.getString("paramFonc"),resultat.getBoolean("MISH"), resultat.getBoolean("estSupprime"));
				stations.add(station);
			}
			
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
		return stations;
	}
	
	/**
	 * Fonction permettant de recuperer tous les objets Station non supprimes dans la base de donnee pour un equipement donne
	 * @param idEquipement id de l'equipement dont on veut connaitre les stations
	 * @return
	 */
	public static ObservableList<Station> loadStationNonSupprimees(int idEquipement){
		
		ObservableList<Station> stations=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM station WHERE idEquipement='" + idEquipement + "' AND estSupprime = 0");
			while(resultat.next()){
				
				//1er bit = 1   ==>  seuilHaut NULL
				//2eme bit = 1  ==>  seuilBas NULL
				//3eme bit = 1  ==>  valeurNormale NULL
				
				double seuilHaut;
				double seuilBas;
				double valeurNormale;
				
				if (resultat.getString("marqueur").substring(0, 1).equals("1")){
					seuilHaut = 0.0;
				}else{
					seuilHaut = resultat.getDouble("seuilHaut");
				}
				if (resultat.getString("marqueur").substring(1, 2).equals("1")){
					seuilBas = 0.0;
				}else{
					seuilBas = resultat.getDouble("seuilBas");
				}
				if(resultat.getString("marqueur").substring(2, 3).equals("1")){
					valeurNormale = 0.0;
				}else{
					valeurNormale = resultat.getDouble("valeurNormale");
				}
				
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"), resultat.getString("marqueur"),
						seuilHaut, seuilBas, valeurNormale,resultat.getString("paramFonc"),resultat.getBoolean("MISH"), resultat.getBoolean("estSupprime"));
				stations.add(station);
			}
			
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
		return stations;
	}
	
	/**
	 * Fonction permettant de r�cuperer tous les objets Station dans la base de donn�e pour une centrale donn�e
	 * @param idEquipement id de l'�quipement dont on veut connaitre les stations
	 * @return
	 */
	public static ObservableList<Station> loadStationForCentrale(int idCentrale){
		
		ObservableList<Station> stations=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idStation, nomStation, instructionsCourtes, instructionsLongues, idUnite, marqueur, paramFonc, valeurNormale, seuilHaut, seuilBas, MISH, s.estSupprime FROM station s "
										+ "INNER JOIN Equipement e ON e.idEquipement=s.idEquipement "
										+ "WHERE e.idCentrale='" + idCentrale + "'");
			while(resultat.next()){
				
				//1er bit = 1   ==>  seuilHaut NULL
				//2eme bit = 1  ==>  seuilBas NULL
				//3eme bit = 1  ==>  valeurNormale NULL
				
				double seuilHaut;
				double seuilBas;
				double valeurNormale;
				
				if (resultat.getString("marqueur").substring(0, 1).equals("1")){
					seuilHaut = 0.0;
				}else{
					seuilHaut = resultat.getDouble("seuilHaut");
				}
				if (resultat.getString("marqueur").substring(1, 2).equals("1")){
					seuilBas = 0.0;
				}else{
					seuilBas = resultat.getDouble("seuilBas");
				}
				if(resultat.getString("marqueur").substring(2, 3).equals("1")){
					valeurNormale = 0.0;
				}else{
					valeurNormale = resultat.getDouble("valeurNormale");
				}
				
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"), resultat.getString("marqueur"),
						seuilHaut,seuilBas,valeurNormale,resultat.getString("paramFonc"),resultat.getBoolean("MISH"), resultat.getBoolean("estSupprime"));
				stations.add(station);
			}
			
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
		return stations;
	}
	
	/**
	 * Fonction permettant de recuperer tous les objets Station non supprimes dans la base de donnee pour une centrale donnee
	 * @param idEquipement id de l'equipement dont on veut connaitre les stations
	 * @return
	 */
	public static ObservableList<Station> loadStationNonSupprimeesForCentrale(int idCentrale){
		
		ObservableList<Station> stations=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM station s "
										+ "INNER JOIN Equipement e ON e.idEquipement=s.idEquipement "
										+ "WHERE e.idCentrale='" + idCentrale + "' AND s.estSupprime = 0");
			while(resultat.next()){
				
				//1er bit = 1   ==>  seuilHaut NULL
				//2eme bit = 1  ==>  seuilBas NULL
				//3eme bit = 1  ==>  valeurNormale NULL
				
				double seuilHaut;
				double seuilBas;
				double valeurNormale;
				
				if (resultat.getString("marqueur").substring(0, 1).equals("1")){
					seuilHaut = 0.0;
				}else{
					seuilHaut = resultat.getDouble("seuilHaut");
				}
				if (resultat.getString("marqueur").substring(1, 2).equals("1")){
					seuilBas = 0.0;
				}else{
					seuilBas = resultat.getDouble("seuilBas");
				}
				if(resultat.getString("marqueur").substring(2, 3).equals("1")){
					valeurNormale = 0.0;
				}else{
					valeurNormale = resultat.getDouble("valeurNormale");
				}
				
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"), resultat.getString("marqueur"),
						seuilHaut,seuilBas,valeurNormale,resultat.getString("paramFonc"),resultat.getBoolean("MISH"), resultat.getBoolean("s.estSupprime"));
				stations.add(station);
			}
			
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
		return stations;
	}
	
	/**
	 * Fonction qui permet d'ajouter une station a la base de donn�e.
	 * on indique tous les parem�tre sauf l'id qui est en autoincr�ment.
	 * attention particuli�re a l'idUnit�/idEquipement, en effet l'utilisateur ne saisit que les nom de ces deux variables
	 * n'oublier pas avant d'ajouter de r�cuperer l'id
	 * @param nom nom de la station
	 * @param instructionCourte instruction courte de la station
	 * @param instructionLongue instruction longue de la station
	 * @param idUnite id de l'unit� de controle de la station
	 * @param frequence frequence de controle de la station ( sert a �tablir les planning de toun�es )
	 * @param seuilHaut Seuil maximum pour le relev�
	 * @param seuilBas seuil minimum pour le relev�
	 * @param idEquipement id de l'�quipement auquel est ratach� la station
	 */
	public static void addStation(String nom, String instructionCourte, String instructionLongue, int idUnite, String marqueur,
			double seuilHaut, double seuilBas,int idEquipement,String paramFonc,double valeurNormale,boolean MISH)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "station(nomStation,instructionsCourtes,"
					+ "instructionsLongues,idUnite,"
					+ "seuilHaut,seuilBas,idEquipement,paramFonc,valeurNormale,MISH, marqueur) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, instructionCourte);
			preparedStatement.setString(3, instructionLongue);
			preparedStatement.setInt(4, idUnite);
			

			//1er bit = 1   ==>  seuilHaut NULL
			//2eme bit = 1  ==>  seuilBas NULL
			//3eme bit = 1  ==>  valeurNormale NULL
			
			if (marqueur.substring(0, 1).equals("1")){
				preparedStatement.setString(5, "NULL");
			}else{
				preparedStatement.setDouble(5, seuilHaut);
			}
			if (marqueur.substring(1, 2).equals("1")){
				preparedStatement.setString(6, "NULL");
			}else{
				preparedStatement.setDouble(6, seuilBas);
			}
			if(marqueur.substring(2, 3).equals("1")){
				preparedStatement.setString(9, "NULL");
			}else{
				preparedStatement.setDouble(9,valeurNormale);
			}
			preparedStatement.setInt(7, idEquipement);
			preparedStatement.setString(8,paramFonc);
			preparedStatement.setBoolean(10,MISH);
			preparedStatement.setString(11, marqueur);
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
	
	/**
	 * Fonction qui donne l'id de l'�quipement auquel la station est ratach�e
	 * @param id
	 * 		id de la station
	 * @return
	 * 		id de l'equipement
	 */
	public static int loadStationIdEquipement(int idStation){
		int idE = 0;
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idEquipement FROM STATION WHERE idStation="+idStation);
			while(resultat.next()){
				idE=resultat.getInt("idEquipement");
			}
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
		return idE;
	}
	
	/**
	 * Fonction permettant de r�cuperer tous les objets Station dans la base de donn�e pour un �quipement donn�
	 * @param idEquipement id de l'�quipement dont on veut connaitre les stations
	 * @return
	 */
	public static Station loadStationById(int idStation){
		
		Station station = null;
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM station WHERE idStation='" + idStation + "'");
			while(resultat.next()){
				
				//1er bit = 1   ==>  seuilHaut NULL
				//2eme bit = 1  ==>  seuilBas NULL
				//3eme bit = 1  ==>  valeurNormale NULL
				
				double seuilHaut;
				double seuilBas;
				double valeurNormale;
				
				if (resultat.getString("marqueur").substring(0, 1).equals("1")){
					seuilHaut = 0.0;
				}else{
					seuilHaut = resultat.getDouble("seuilHaut");
				}
				if (resultat.getString("marqueur").substring(1, 2).equals("1")){
					seuilBas = 0.0;
				}else{
					seuilBas = resultat.getDouble("seuilBas");
				}
				if(resultat.getString("marqueur").substring(2, 3).equals("1")){
					valeurNormale = 0.0;
				}else{
					valeurNormale = resultat.getDouble("valeurNormale");
				}
				
				station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"), resultat.getString("marqueur"),
						seuilHaut, seuilBas, valeurNormale,resultat.getString("paramFonc"),resultat.getBoolean("MISH"),resultat.getBoolean("estSupprime"));
			}
			
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
		return station;
	}
	
	public static boolean estUtiliseDansModele(int idStation){
		boolean estUtilise = false;
		
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM asso_station_modele WHERE idStation='" + idStation + "'");
			while(resultat.next()){
				estUtilise = true;
			}
			
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
		
		return estUtilise;
	}
	
	/**
	 * definit une station comme supprime
	 * @param idStation
	 * 		id station a supprimer de la BD
	 */
	public static void supprimer(int idStation) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE station "
					+ "SET estSupprime = 1 "
					+ "WHERE idStation = ?");
			preparedStatement.setInt(1, idStation);
			preparedStatement.executeUpdate();
			
			ObservableList<ModeleTournee> modeles = ModeleTourneeController.loadModeleTourneeNonSupprimesStation(idStation);
			for(ModeleTournee mt : modeles){
				ModeleTourneeController.supprimer(mt.getId());
			}
			
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
