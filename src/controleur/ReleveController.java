package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Releve;

public class ReleveController {

	public static ArrayList<Releve> load5LastReleve(int idStation){
		
		ArrayList<Releve> releves = new ArrayList<Releve>();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idReleve, commentaireReleve, valeurReleve, dateReleve, r.idStation, nomStation, r.idTournee FROM releve r "
										+ "INNER JOIN tournee t ON r.idTournee = t.idTournee "
										+ "INNER JOIN station s ON s.idStation = r.idStation "
										+ "WHERE r.idStation = " + idStation + " "
										+ "ORDER BY t.dateReleve desc "
										+ "limit 5");
			while(resultat.next()){
				int  idReleve = resultat.getInt("idReleve");
				String commentaireReleve = resultat.getString("commentaireReleve");
				double valeurReleve = resultat.getDouble("valeurReleve");
				String date = resultat.getString("dateReleve");
				String nomStation = resultat.getString("nomStation");
				int idTournee = resultat.getInt("idTournee");
	
				releves.add(new Releve(idReleve, commentaireReleve, valeurReleve, date, idStation, nomStation, idTournee));
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
		return releves;
	}

	public static void ajouterReleve(double valeur, String com, int idStation, int idTournee) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "releve(valeurReleve,commentaireReleve,"
					+ "idStation,idTournee) "
					+ "VALUES(?,?,?,?)");
			preparedStatement.setDouble(1, valeur);
			preparedStatement.setString(2, com);
			preparedStatement.setInt(3, idStation);
			preparedStatement.setInt(4, idTournee);
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
	
	public static ObservableList<Releve> loadReleves(int idTournee){
		
		ObservableList<Releve> releves = FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idReleve, commentaireReleve, valeurReleve, dateReleve, r.idStation, nomStation, r.idTournee FROM releve r "
										+ "INNER JOIN tournee t ON r.idTournee = t.idTournee "
										+ "INNER JOIN station s ON s.idStation = r.idStation "
										+ "WHERE r.idTournee = " + idTournee);
			while(resultat.next()){
				int  idReleve = resultat.getInt("idReleve");
				String commentaireReleve = resultat.getString("commentaireReleve");
				double valeurReleve = resultat.getDouble("valeurReleve");
				String date = resultat.getString("dateReleve");
				int idStation = resultat.getInt("idStation");
				String nomStation = resultat.getString("nomStation");
	
				releves.add(new Releve(idReleve, commentaireReleve, valeurReleve, date, idStation, nomStation, idTournee));
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
		return releves;
	}
}
