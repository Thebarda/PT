package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Unite;
/**
 * G�re les unit�s
 * @version 1
 *
 */
public class UniteController {
	/**
	 * Ajoute une unit� dans la base
	 * @param id
	 * @param nom
	 */
	public static void addUnite(String nom)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO UNITE(nomUnite)VALUES(?)");
			preparedStatement.setString(1, nom);
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
	 * Charges les unit�s de la base
	 * @return la liste des unit�s
	 */
public static ObservableList<Unite> loadUnites(){
		
		ObservableList<Unite> unites=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		Unite unitVerif = getUnitVerif();
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM UNITE");
			while(resultat.next()){
				Unite unite = new Unite(resultat.getInt("idUnite"), resultat.getString("nomUnite"));
				if(!unite.equals(unitVerif))
					unites.add(unite);
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
		return unites;
	}
	/**
	 * Fonction qui donne le nom d'une unité en fonction de son id
	 * @param id
	 * 		id de l'unité
	 * @return
	 * 		Nom de l'unité
	 */
	public static String idVersNom(int id){
		String nom=null;
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT nomUnite FROM UNITE WHERE idUnite="+id);
			while(resultat.next()){
				nom=resultat.getString("nomUnite");
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
		return nom;
	}
	
	/**
	 * Fonction qui recupere l'unite de verification
	 * @param id
	 * 		id de l'unité
	 * @return
	 * 		Nom de l'unité
	 */
	public static Unite getUnitVerif(){
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		Unite unite = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM UNITE WHERE nomUnite = '__VERIFICATION'");
			while(resultat.next()){
				unite = new Unite(resultat.getInt("idUnite"), resultat.getString("nomUnite"));
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
		return unite;
	}

}
