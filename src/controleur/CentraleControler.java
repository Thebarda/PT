package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modele.Centrale;
import modele.Equipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Controleur relatif aux centrales et qui permet la gestion de la BD en rapport avec les centrales
 */
public class CentraleControler 
{
       
	/**
	 * Charge toutes les centrales contenues dans la bases de données 
	 * @return 
	 * 		OservableList contenant des objets Centrales, representant toutes les centrales de la BD
	 */
	public static ObservableList<Centrale> loadCentrales(){
		
		ObservableList<Centrale> centrales=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM centrale");
			while(resultat.next()){
				Centrale centrale = new Centrale(resultat.getInt("idCentrale"), resultat.getString("nomCentrale"), resultat.getString("identiteNationale"), resultat.getBoolean("estSupprime"));
				centrales.add(centrale);
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
		return centrales;
	}
	
	/**
	 * Charge toutes les centrales non supprimees contenues dans la bases de donnees 
	 * @return 
	 * 		OservableList contenant des objets Centrales, representant toutes les centrales de la BD
	 */
	public static ObservableList<Centrale> loadCentralesNonSupprimees(){
		
		ObservableList<Centrale> centrales=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM centrale WHERE estSupprime = 0");
			while(resultat.next()){
				Centrale centrale = new Centrale(resultat.getInt("idCentrale"), resultat.getString("nomCentrale"), resultat.getString("identiteNationale"), resultat.getBoolean("estSupprime"));
				centrales.add(centrale);
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
		return centrales;
	}
	
	/**
	 * Charge la centrale ayant l'id demand�
	 * @return 
	 * 		Centrale demand�e
	 */
	public static Centrale loadCentraleById(int id){
		
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		Centrale centrale = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM centrale WHERE idCentrale = " + id);
			
			centrale = new Centrale(resultat.getInt("idCentrale"), resultat.getString("nomCentrale"), resultat.getString("identiteNationale"), resultat.getBoolean("estSupprime"));
			
			
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
		return centrale;
	}
	
	/**
	 * Ajoute une centrale dans la bd en fonction des paramètres
	 * On spécifie juste le nom et la localisation de la Centrale, l'id étant en auto-increment
	 * @param nom
	 * 			Nom de la Centrale à ajouter
	 * @param lieu
	 * 			Lieu de la centrale à ajouter
	 */
	public static void addCentrale(String nom, String identiteNationale)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO centrale(nomCentrale, identiteNationale)VALUES(?,?)");
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, identiteNationale);
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
	 * definit une centrale comme supprimee
	 * @param centrale
	 * 		Centrale a supprimer de la BD
	 */
	public static void supprimer(int idCentrale) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE centrale "
					+ "SET estSupprime = 1 "
					+ "WHERE idCentrale = ?");
			preparedStatement.setInt(1, idCentrale);
			preparedStatement.executeUpdate();
			
			ObservableList<Equipement> equipements = EquipementController.loadEquipementNonSupprimes(idCentrale);
			for(Equipement e : equipements){
				EquipementController.supprimer(e.getId());
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
	
	public static void modifier(int idCentrale,String nomCentrale,String identiteNationale){
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE centrale "
					+ "SET nomCentrale = ? ,identiteNationale=?"
					+ "WHERE idCentrale = ?");
			preparedStatement.setString(1, nomCentrale);
			preparedStatement.setString(2, identiteNationale);
			preparedStatement.setInt(3, idCentrale);
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
	 * Recherche une centrale en fonction de son nom ou du lieu de la centrale
	 * Un des champs peut-être NULL, la recherche se fait alors uniquement en fonction de l'autre champs
	 * @param nom
	 * 		Nom de la centrale recherche 
	 * @param lieu
	 * 		Lieu de la centrale recherche
	 */
	public static Centrale rechercherCentrale(String nom, String identiteNationale){
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		Centrale centrale = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT nomCentrale, identiteNationale, estSupprime FROM centrale WHERE nomCentrale = '"+nom+"' AND identiteNationale = '"+identiteNationale+"'");
			while(resultat.next()){
				centrale=new Centrale(resultat.getInt("Id"),resultat.getString("nomCentrale"),resultat.getString("identiteNationale"), resultat.getBoolean("estSupprime"));
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
		return centrale;
	}

}
