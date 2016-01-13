package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
			resultat = statut.executeQuery("SELECT idReleve, commentaireReleve, valeurReleve, dateTournee FROM releve r "
										+ "INNER JOIN tournee t ON r.idTournee = t.idTournee "
										+ "WHERE r.idStation = " + idStation + " "
										+ "ORDER BY t.dateTournee desc "
										+ "limit 5");
			while(resultat.next()){
				int  idReleve = resultat.getInt("idReleve");
				String commentaireReleve = resultat.getString("commentaireReleve");
				double valeurReleve = resultat.getDouble("valeurReleve");
				String date = resultat.getString("dateTournee");
	
				releves.add(new Releve(idReleve, commentaireReleve, valeurReleve, date));
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
}
