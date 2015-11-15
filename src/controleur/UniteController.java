package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Centrale;
import modele.Unite;
/**
 * Gère les unités
 * @version 1
 *
 */
public class UniteController {
	/**
	 * Ajoute une unité dans la base
	 * @param id
	 * @param nom
	 */
	public static void addUnite(int id, String nom)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO UNITE(idUnite, nomUnite)VALUES(?,?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, nom);
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
	 * Charges les unités de la base
	 * @return la liste des unités
	 */
public static ObservableList<Unite> loadUnites(){
		
		ObservableList<Unite> unites=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM UNITE");
			while(resultat.next()){
				Unite unite = new Unite(resultat.getInt("idUnite"), resultat.getString("nomUnite"));
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
}
