package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modele.Centrale;
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
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM centrale");
			while(resultat.next()){
				Centrale centrale = new Centrale(resultat.getInt("idCentrale"), resultat.getString("nomCentrale"), resultat.getString("localisation"));
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
	 * Ajoute une centrale dans la bd en fonction des paramètres
	 * On spécifie juste le nom et la localisation de la Centrale, l'id étant en auto-increment
	 * @param nom
	 * 			Nom de la Centrale à ajouter
	 * @param lieu
	 * 			Lieu de la centrale à ajouter
	 */
	public static void addCentrale(String nom, String lieu)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO centrale(nomCentrale, localisation)VALUES(?,?)");
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, lieu);
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
	 * Supprime de la BD la centrale spécifié en paramètres
	 * @param centrale
	 * 		Centrale à supprimer de la BD
	 */
	public static void supprimerCentrale(Centrale centrale){
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("DELETE FROM centrale WHERE  id="+centrale.getId());
			resultat.clearWarnings();
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
	 * Recherche une centrale en fonction de son nom ou du lieu de la centrale
	 * Un des champs peut-être NULL, la recherche se fait alors uniquement en fonction de l'autre champs
	 * @param nom
	 * 		Nom de la centrale recherche 
	 * @param lieu
	 * 		Lieu de la centrale recherche
	 */
	public static Centrale rechercherCentrale(String nom, String lieu){
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		Centrale centrale = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT nomCentrale, localisation FROM centrale WHERE nomCentrale = '"+nom+"' AND localisation = '"+lieu+"'");
			while(resultat.next()){
				centrale=new Centrale(resultat.getInt("Id"),resultat.getString("nomCentrale"),resultat.getString("localisation"));
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
