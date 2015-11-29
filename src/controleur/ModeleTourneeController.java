package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.ModeleTournee;
import modele.Station;
import modele.Tournee;

public class ModeleTourneeController {
	
	/**
	 * Fonction permettant de rï¿½cuperer tous les modï¿½les de tournï¿½e pour une centrale dans la base de donnï¿½e
	 * @param idCentrale id de la centrale dont on veut rï¿½cupï¿½rer les modï¿½les de tournï¿½e
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
			resultat = statut.executeQuery("SELECT idModele, nomModele, descriptionModele, t0, numExport FROM modele_tournee mt "
										+ "WHERE " + idCentrale + " = ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "WHERE asm.idModele=mt.idModele "
										+ "limit 1)");
			while(resultat.next()){
				//public ModeleTournee(int id, String nom, String description, int t0,int numExport) 
				ModeleTournee modeleTournee = new ModeleTournee(resultat.getInt("idModele"),
						resultat.getString("nomModele"),
						resultat.getString("descriptionModele"),
						resultat.getInt("t0"),
						resultat.getInt("numExport"));
				
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
	 * Fonction qui permet d'ajouter un modele de tournï¿½e a la base de donnï¿½e.
	 * on indique tous les paremï¿½tre sauf l'id qui est en autoincrï¿½ment.
	 * @param nomModele le nom du modele de tournï¿½e
	 * @param descriptionModele description du modï¿½le de tournï¿½e
	 */
	public static void addModeleTournee(String nomModele, String descriptionModele, Map<Integer, Station> stations, int t0)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "modele_tournee(nomModele,descriptionModele,t0) "
					+ "VALUES(?,?,?)");
			preparedStatement.setString(1, nomModele);
			preparedStatement.setString(2, descriptionModele);
			preparedStatement.setInt(3, t0);
			preparedStatement.executeUpdate();
			
			// On recupï¿½re l'id du modï¿½le crï¿½ï¿½
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
			modifierNumExport(id, 1);
			ModeleTournee modele = new ModeleTournee(id, nomModele, descriptionModele, t0, 1);
			modele.genererProchaineTournee();
			
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
	 * Fonction permettant de rï¿½cuperer toutes les station d'un modï¿½le de tournï¿½e
	 * et les ajoute au modele de tournï¿½e
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
										+ "s.idUnite, s.FrequenceControle, s.seuilHaut, s.seuilBas, s.valeurNormale, s.paramFonc, s.MISH, asm.ordre FROM station s "
										+ "INNER JOIN asso_station_modele asm ON s.idStation=asm.idStation "
										+ "WHERE asm.idModele=" + modele.getId());
			
			while(resultat.next()){
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"),resultat.getInt("FrequenceControle"),
						resultat.getInt("seuilHaut"),resultat.getInt("seuilBas"),resultat.getInt("valeurNormale"),resultat.getString("paramFonc"),resultat.getString("MISH"));
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
	 * Cette fonction permet de modifier pour un modele donné son numéro d'export en base de donnée
	 * @param idModeleTournee id du modele sur lequel faire la modification
	 * @param numExport nouveau num d'export après la modification
	 */
	public static void modifierNumExport(int idModeleTournee ,int numExport)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			/*UPDATE table
			SET nom_colonne_1 = 'nouvelle valeur'
			WHERE condition*/
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE modele_tournee SET numExport = ? WHERE idModele = ?");
			preparedStatement.setInt(1, numExport);
			preparedStatement.setInt(2, idModeleTournee);
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
