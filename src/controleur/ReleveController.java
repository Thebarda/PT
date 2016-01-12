package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
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

	public static void ajouterReleve(double doubleValue, String string, int int1, int int2) {
		// TODO Auto-generated method stub
		
	}
}
