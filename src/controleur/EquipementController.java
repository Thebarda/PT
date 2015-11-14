package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modele.Equipement;
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
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			String sql = "SELECT * FROM EQUIPEMENT WHERE idCentrale='" + idCentrale + "'";
			resultat = statut.executeQuery(sql);
			while(resultat.next()){
				Equipement equipement = new Equipement(resultat.getInt("idEquipement"), resultat.getString("nomEquipement"), resultat.getString("descriptionEquipement"),resultat.getInt("idCentrale"));
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
	 */
	public static void addEquipement(int idCentrale,String nom, String description)
	{
		Connection connexion = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO equipement(idCentrale,nomEquipement, descriptionEquipement)VALUES(?,?,?)");
			preparedStatement.setInt(1, idCentrale);
			preparedStatement.setString(2, nom);
			preparedStatement.setString(3, description);
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
}

