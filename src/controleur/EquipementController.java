package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modele.Equipement;
import modele.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Controleur relatif à un equipement et qui permet les connexions à la BD en rapport aux équipement
 */
public class EquipementController 
{
       
	/**
	 * Charge toutes les Equipement pour une centrale  donnee
	 * @param idCentrale
	 * 		id de la centrale dont l'on veut charger les equipements 
	 * @return 
	 * 		OservableList contenant des objets Equipement, representant toutes les Equipements de la BD
	 */
	public static ObservableList<Equipement> loadEquipement(int idCentrale)
	{
		
		ObservableList<Equipement> equipements=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			String sql = "SELECT * FROM EQUIPEMENT WHERE idCentrale='" + idCentrale + "'";
			resultat = statut.executeQuery(sql);
			while(resultat.next()){
				Equipement equipement = new Equipement(resultat.getInt("idEquipement"), resultat.getString("nomEquipement"), resultat.getString("descriptionEquipement"),resultat.getInt("idCentrale"),resultat.getString("repereECSH"),resultat.getBoolean("estSupprime"));
				equipements.add(equipement);
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
		return equipements;
	}
	
	/**
	 * Charge toutes les Equipement non supprimes pour une centrale  donnee
	 * @param idCentrale
	 * 		id de la centrale dont l'on veut charger les equipements 
	 * @return 
	 * 		OservableList contenant des objets Equipement, representant toutes les Equipements non supprimes de la BD
	 */
	public static ObservableList<Equipement> loadEquipementNonSupprimes(int idCentrale)
	{
		
		ObservableList<Equipement> equipements=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			String sql = "SELECT * FROM EQUIPEMENT WHERE idCentrale='" + idCentrale + "' AND estSupprime = 0";
			resultat = statut.executeQuery(sql);
			while(resultat.next()){
				Equipement equipement = new Equipement(resultat.getInt("idEquipement"), resultat.getString("nomEquipement"), resultat.getString("descriptionEquipement"),resultat.getInt("idCentrale"),resultat.getString("repereECSH"),resultat.getBoolean("estSupprime"));
				equipements.add(equipement);
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
		return equipements;
	}
	
	/**
	 * charge tous les �quipements contenu dans la base de donn�e
	 * @return une ObservableList contenant tous les �quipements
	 */
	public static ObservableList<Equipement> loadAllEquipement()
	{
		
		ObservableList<Equipement> equipements=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			String sql = "SELECT * FROM EQUIPEMENT";
			resultat = statut.executeQuery(sql);
			while(resultat.next()){
				Equipement equipement = new Equipement(resultat.getInt("idEquipement"), resultat.getString("nomEquipement"), resultat.getString("descriptionEquipement"),resultat.getInt("idCentrale"),resultat.getString("repereECSH"), resultat.getBoolean("estSupprime"));
				equipements.add(equipement);
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
		return equipements;
	}
	
	/**
	 * charge tous les equipements non supprimes contenu dans la base de donnee
	 * @return une ObservableList contenant tous les equipements
	 */
	public static ObservableList<Equipement> loadAllEquipementNonSupprimes()
	{
		
		ObservableList<Equipement> equipements=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			String sql = "SELECT * FROM EQUIPEMENT WHERE estSupprime = 0";
			resultat = statut.executeQuery(sql);
			while(resultat.next()){
				Equipement equipement = new Equipement(resultat.getInt("idEquipement"), resultat.getString("nomEquipement"), resultat.getString("descriptionEquipement"),resultat.getInt("idCentrale"),resultat.getString("repereECSH"), resultat.getBoolean("estSupprime"));
				equipements.add(equipement);
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
		return equipements;
	}
	
	/**
	 * Ajoute une equipement dans la bd en fonction des paramètres
	 * On spécifie juste le nom ,la description de l'equipement, ainsi que l'id de la centrale de cette équipement, l'id de ce dernier étant en auto-increment
	 * @param idCentrale
	 *			id de la centrale de l'équipement
	 * @param nom
	 * 			Nom de l'equipement à ajouter
	 * @param description
	 * 			Lieu de la centrale à ajouter
	 * @param ECSH
	 * 			Clé ECSH de l'équipement 
	 */
	public static void addEquipement(int idCentrale,String nom, String description,String ECSH)
	{
		Connection connexion = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO equipement(idCentrale,nomEquipement, descriptionEquipement,repereECSH)VALUES(?,?,?,?)");
			preparedStatement.setInt(1, idCentrale);
			preparedStatement.setString(2, nom);
			preparedStatement.setString(3, description);
			preparedStatement.setString(4, ECSH);
			preparedStatement.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
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
	 * definit un equipement comme supprime
	 * @param equipement
	 * 		equipement a supprimer de la BD
	 */
	public static void supprimer(int idEquipement) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE equipement "
					+ "SET estSupprime = 1 "
					+ "WHERE idEquipement = ?");
			preparedStatement.setInt(1, idEquipement);
			preparedStatement.executeUpdate();
			
			ObservableList<Station> stations = StationController.loadStationNonSupprimees(idEquipement);
			for(Station s : stations){
				StationController.supprimer(s.getId());
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

