package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modele.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Controleur relatif aux Stations et qui permet la gestion de la BD en rapport avec les Stations
 */
public class StationController 
{

	/**
	 * Fonction permettant de r�cuperer tous les objets Station dans la base de donn�e pour un �quipement donn�
	 * @param idEquipement id de l'�quipement dont on veut connaitre les stations
	 * @return
	 */
	public static ObservableList<Station> loadStation(int idEquipement){
		
		ObservableList<Station> stations=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT * FROM station WHERE idEquipement='" + idEquipement + "'");
			while(resultat.next()){
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"),resultat.getInt("FrequenceControle"),
						resultat.getInt("seuilHaut"),resultat.getInt("seuilBas"));
				stations.add(station);
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
		return stations;
	}
	/**
	 * Fonction qui permet d'ajouter une station a la base de donn�e.
	 * on indique tous les parem�tre sauf l'id qui est en autoincr�ment.
	 * attention particuli�re a l'idUnit�/idEquipement, en effet l'utilisateur ne saisit que les nom de ces deux variables
	 * n'oublier pas avant d'ajouter de r�cuperer l'id
	 * @param nom nom de la station
	 * @param instructionCourte instruction courte de la station
	 * @param instructionLongue instruction longue de la station
	 * @param idUnite id de l'unit� de controle de la station
	 * @param frequence frequence de controle de la station ( sert a �tablir les planning de toun�es )
	 * @param seuilHaut Seuil maximum pour le relev�
	 * @param seuilBas seuil minimum pour le relev�
	 * @param idEquipement id de l'�quipement auquel est ratach� la station
	 */
	public static void addStation(String nom, String instructionCourte, String instructionLongue, int idUnite, int frequence,
			Integer seuilHaut, Integer seuilBas,int idEquipement)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "station(nomStation,instructionsCourtes,"
					+ "instructionsLongues,idUnite,FrequenceControle,"
					+ "seuilHaut,seuilBas,idEquipement) "
					+ "VALUES(?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, instructionCourte);
			preparedStatement.setString(3, instructionLongue);
			preparedStatement.setInt(4, idUnite);
			preparedStatement.setInt(5, frequence);
			if (seuilHaut==null){
				preparedStatement.setString(6, "NULL");
			}else{
				preparedStatement.setInt(6, seuilHaut);
			}
			if (seuilHaut==null){
				preparedStatement.setString(7, "NULL");
			}else{
				preparedStatement.setInt(7, seuilBas);
			}
			preparedStatement.setInt(8, idEquipement);
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
