package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

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
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
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
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
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
	
	public static void modifierReleve(int idReleve, double valeur, String com) {
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE releve "
					+ "SET valeurReleve=?, commentaireReleve=? "
					+ "WHERE idReleve=?");
			preparedStatement.setDouble(1, valeur);
			preparedStatement.setString(2, com);
			preparedStatement.setInt(3, idReleve);
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
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
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
	
	public static ObservableList<Releve> loadRelevesForStation(int idStation){
		
		ObservableList<Releve> releves = FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idReleve, commentaireReleve, valeurReleve, dateReleve, r.idStation, nomStation, r.idTournee FROM releve r "
  										+ "INNER JOIN tournee t ON r.idTournee = t.idTournee "
  										+ "INNER JOIN station s ON s.idStation = r.idStation "
										+ "WHERE r.idStation = " + idStation);
			while(resultat.next()){
				int  idReleve = resultat.getInt("idReleve");
				String commentaireReleve = resultat.getString("commentaireReleve");
				double valeurReleve = resultat.getDouble("valeurReleve");
				String date = resultat.getString("dateReleve");
				int idTournee = resultat.getInt("idTournee");
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
		releves.sort(new Comparator<Releve>(){
			@Override
			public int compare(Releve r1, Releve r2){
				String date1 = r1.getDate();
				String date2 = r2.getDate();
				int annee1 = Integer.parseInt(date1.substring(6,10));
				int annee2 = Integer.parseInt(date2.substring(6,10));
				int mois1  = Integer.parseInt(date1.substring(3,5));
				int mois2  = Integer.parseInt(date2.substring(3,5));
				int jour1  = Integer.parseInt(date1.substring(0,2));
				int jour2  = Integer.parseInt(date2.substring(0,2));
				int heure1 = Integer.parseInt(date1.substring(11,13));
				int heure2 = Integer.parseInt(date2.substring(11,13));
				int min1 = Integer.parseInt(date1.substring(14,16));
				int min2 = Integer.parseInt(date2.substring(14,16));
				
				if(annee1 > annee2)
					return -1;
				if(annee1 < annee2)
					return 1;
				
				if(mois1 > mois2)
					return -1;
				if(mois1 < mois2)
					return 1;
				
				if(jour1 > jour2)
					return -1;
				if(jour1 < jour2)
					return 1;
				
				if(heure1 > heure2)
					return -1;
				if(heure1 < heure2)
					return 1;
				
				if(min1 > min2)
					return -1;
				if(min1 < min2)
					return 1;
				
				return 0;
			}
		});
		return releves;
	}
}
