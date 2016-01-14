package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.ModeleTournee;
import modele.Station;

public class ModeleTourneeController {
	
	/**
	 * Fonction permettant de r�cuperer tous les mod�les de tourn�e pour une centrale dans la base de donn�e
	 * @param idCentrale id de la centrale dont on veut r�cup�rer les mod�les de tourn�e
	 * @return
	 */
	public static ObservableList<ModeleTournee> loadAllModeleTournee(int idCentrale){
		
		ObservableList<ModeleTournee> modelesTournee=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idModele, nomModele, descriptionModele, mt.estSupprime FROM modele_tournee mt "
										+ "WHERE " + idCentrale + " = ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "WHERE asm.idModele=mt.idModele "
										+ "limit 1)");
			while(resultat.next()){
				//public ModeleTournee(int id, String nom, String description, int numExport) 
				ModeleTournee modeleTournee = new ModeleTournee(resultat.getInt("idModele"),
						resultat.getString("nomModele"),
						resultat.getString("descriptionModele"), resultat.getBoolean("estSupprime"));
				
				loadStationIntoModeleTournee(modeleTournee);
				
				modelesTournee.add(modeleTournee);
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
		return modelesTournee;
	}
	
	/**
	 * Fonction permettant de r�cuperer tous les mod�les de tourn�e pour une centrale dans la base de donn�e
	 * @param idCentrale id de la centrale dont on veut r�cup�rer les mod�les de tourn�e
	 * @return
	 */
	public static ObservableList<ModeleTournee> loadAllModeleTourneeNonSupprimes(int idCentrale){
		
		ObservableList<ModeleTournee> modelesTournee=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idModele, nomModele, descriptionModele, mt.estSupprime FROM modele_tournee mt "
										+ "WHERE " + idCentrale + " = ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "WHERE asm.idModele=mt.idModele "
										+ "limit 1) AND mt.estSupprime = 0");
			while(resultat.next()){
				//public ModeleTournee(int id, String nom, String description, int numExport) 
				ModeleTournee modeleTournee = new ModeleTournee(resultat.getInt("idModele"),
						resultat.getString("nomModele"),
						resultat.getString("descriptionModele"), resultat.getBoolean("estSupprime"));
				
				loadStationIntoModeleTournee(modeleTournee);
				
				modelesTournee.add(modeleTournee);
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
		return modelesTournee;
	}
	
	/**
	 * Fonction permettant de r�cuperer tous les mod�les de tourn�e pour une centrale dans la base de donn�e
	 * @param idCentrale id de la centrale dont on veut r�cup�rer les mod�les de tourn�e
	 * @return
	 */
	public static ObservableList<ModeleTournee> loadModeleTourneeNonSupprimesStation(int idStation){
		
		ObservableList<ModeleTournee> modelesTournee=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT mt.idModele, nomModele, descriptionModele, estSupprime FROM modele_tournee mt "
										+ "INNER JOIN asso_station_modele asm ON asm.idModele = mt.idModele "
										+ "WHERE asm.idStation = " + idStation);
			while(resultat.next()){
				//public ModeleTournee(int id, String nom, String description, int numExport) 
				ModeleTournee modeleTournee = new ModeleTournee(resultat.getInt("idModele"),
						resultat.getString("nomModele"),
						resultat.getString("descriptionModele"), resultat.getBoolean("estSupprime"));
				
				loadStationIntoModeleTournee(modeleTournee);
				
				modelesTournee.add(modeleTournee);
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
		return modelesTournee;
	}
	
	/**
	 * Fonction qui permet d'ajouter un modele de tourn�e a la base de donn�e.
	 * on indique tous les parem�tre sauf l'id qui est en autoincr�ment.
	 * @param nomModele le nom du modele de tourn�e
	 * @param descriptionModele description du mod�le de tourn�e
	 */
	public static void addModeleTournee(String nomModele, String descriptionModele, Map<Integer, Station> stations)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "modele_tournee(nomModele,descriptionModele) "
					+ "VALUES(?,?)");
			preparedStatement.setString(1, nomModele);
			preparedStatement.setString(2, descriptionModele);
			preparedStatement.executeUpdate();
			
			// On recup�re l'id du mod�le cr��
			int id = preparedStatement.getGeneratedKeys().getInt(1);
			
			// Ajout dans l'association avec les stations
			
			for(int i=1; i<=stations.size();i++){
				Station station = stations.get(i);
				PreparedStatement preparedStatementAsso = connexion.prepareStatement("INSERT INTO "
					+ "asso_station_modele(idModele, idStation, ordre) "
					+ "VALUES(?,?,?)");
				preparedStatementAsso.setInt(1, id);
				preparedStatementAsso.setInt(2, station.getId());
				preparedStatementAsso.setInt(3, i);
				preparedStatementAsso.executeUpdate();
			}
			ModeleTournee modele = new ModeleTournee(id, nomModele, descriptionModele, false);
			loadStationIntoModeleTournee(modele);
			modele.genererTournee();
			
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
	 * Fonction permettant de r�cuperer toutes les station d'un mod�le de tourn�e
	 * et les ajoute au modele de tourn�e
	 * @return
	 */
	public static void loadStationIntoModeleTournee(ModeleTournee modele)
	{
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT s.idStation, s.nomStation, s.instructionsCourtes, s.instructionsLongues, "
										+ "s.idUnite, s.marqueur, s.seuilHaut, s.seuilBas, s.valeurNormale, s.paramFonc, s.MISH, s.estSupprime asm.ordre FROM station s "
										+ "INNER JOIN asso_station_modele asm ON s.idStation=asm.idStation "
										+ "WHERE asm.idModele=" + modele.getId());
			
			while(resultat.next()){
				
				//1er bit = 1   ==>  seuilHaut NULL
				//2eme bit = 1  ==>  seuilBas NULL
				//3eme bit = 1  ==>  valeurNormale NULL
				
				double seuilHaut;
				double seuilBas;
				double valeurNormale;
				
				if (resultat.getString("marqueur").substring(0, 0).equals("1")){
					seuilHaut = 0.0;
				}else{
					seuilHaut = resultat.getDouble("seuilHaut");
				}
				if (resultat.getString("marqueur").substring(1, 1).equals("1")){
					seuilBas = 0.0;
				}else{
					seuilBas = resultat.getDouble("seuilBas");
				}
				if(resultat.getString("marqueur").substring(2, 2).equals("1")){
					valeurNormale = 0.0;
				}else{
					valeurNormale = resultat.getDouble("valeurNormale");
				}
				
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"), resultat.getString("marqueur"),
						seuilHaut,seuilBas,valeurNormale,resultat.getString("paramFonc"),resultat.getBoolean("MISH"),resultat.getBoolean("estSupprime"));
				int ordre = resultat.getInt("ordre");
				modele.ajouterStation(station, ordre);
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
	}
	
	/**
	 * Fonction permettant de recuperer un modele de tournee avec son id
	 * @param idModele id du modele de tournee a recuperer
	 * @return
	 */
	public static ModeleTournee loadModeleTournee(int idModele){
		
		ModeleTournee modeleTournee = null;
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idModele, nomModele, descriptionModele, estSupprime FROM modele_tournee mt "
										+ "WHERE idModele = " + idModele);
			while(resultat.next()){
				modeleTournee = new ModeleTournee(resultat.getInt("idModele"),
						resultat.getString("nomModele"),
						resultat.getString("descriptionModele"),resultat.getBoolean("estSupprime"));
				
				loadStationIntoModeleTournee(modeleTournee);
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
		return modeleTournee;
	}
	
	/**
	 * definit un modele de tournee comme supprime
	 * @param idModele
	 * 		id du modele a supprimer de la BD
	 */
	public static void supprimer(int idModele) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE modele_tournee "
					+ "SET estSupprime = 1 "
					+ "WHERE idModele = ?");
			preparedStatement.setInt(1, idModele);
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
