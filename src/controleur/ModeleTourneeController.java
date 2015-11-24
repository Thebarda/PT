package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.ModeleTournee;
import modele.Station;
import modele.Tournee;

public class ModeleTourneeController {
	
	/**
	 * Fonction permettant de r�cuperer tous les mod�les de tourn�e pour une centrale dans la base de donn�e
	 * @param idCentrale id de la centrale dont on veut r�cup�rer les mod�les de tourn�e
	 * @return
	 */
	public static ObservableList<ModeleTournee> loadAllModeleTournee(int idCentrale){
		
		ObservableList<ModeleTournee> modelesTournee=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idModele, nomModele, descriptionModele, t0 FROM modele_tournee mt "
										+ "WHERE " + idCentrale + " = ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "WHERE asm.idModele=mt.idModele "
										+ "limit 1)");
			while(resultat.next()){
				ModeleTournee modeleTournee = new ModeleTournee(resultat.getInt("idModele"),
						resultat.getString("nomModele"),
						resultat.getString("descriptionModele"),
						resultat.getInt("t0"));
				
				loadStationIntoModeleTournee(modeleTournee);
				
				modelesTournee.add(modeleTournee);
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
		return modelesTournee;
	}
	/**
	 * Fonction qui permet d'ajouter un modele de tourn�e a la base de donn�e.
	 * on indique tous les parem�tre sauf l'id qui est en autoincr�ment.
	 * @param nomModele le nom du modele de tourn�e
	 * @param descriptionModele description du mod�le de tourn�e
	 */
	public static void addModeleTournee(String nomModele, String descriptionModele, Map<Integer, Station> stations, int t0)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "modele_tournee(nomModele,descriptionModele,t0) "
					+ "VALUES(?,?,?)");
			preparedStatement.setString(1, nomModele);
			preparedStatement.setString(2, descriptionModele);
			preparedStatement.setInt(3, t0);
			preparedStatement.executeUpdate();
			
			// On recup�re l'id du mod�le cr��
			int id = preparedStatement.getGeneratedKeys().getInt(1);
			
			// Ajout dans l'association avec les stations
			
			for(int i=1; i<=stations.size();i++){
				Station station = stations.get(i);
				System.out.println(station);
				PreparedStatement preparedStatementAsso = connexion.prepareStatement("INSERT INTO "
					+ "asso_station_modele(idModele, idStation, ordre) "
					+ "VALUES(?,?,?)");
				preparedStatementAsso.setInt(1, id);
				preparedStatementAsso.setInt(2, station.getId());
				preparedStatementAsso.setInt(3, i);
				preparedStatementAsso.executeUpdate();
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
	
	/**
	 * Fonction permettant de r�cuperer toutes les station d'un mod�le de tourn�e
	 * et les ajoute au modele de tourn�e
	 * @return
	 */
	public static void loadStationIntoModeleTournee(ModeleTournee modele)
	{
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statut = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT s.idStation, s.nomStation, s.instructionsCourtes, s.instructionsLongues, "
										+ "s.idUnite, s.FrequenceControle, s.seuilHaut, s.seuilBas, asm.ordre FROM station s "
										+ "INNER JOIN asso_station_modele asm ON s.idStation=asm.idStation "
										+ "WHERE asm.idModele=" + modele.getId());
			
			while(resultat.next()){
				Station station = new Station(resultat.getInt("idStation"),
						resultat.getString("nomStation"),
						resultat.getString("instructionsCourtes"),
						resultat.getString("instructionsLongues"),
						resultat.getInt("idUnite"),resultat.getInt("FrequenceControle"),
						resultat.getInt("seuilHaut"),resultat.getInt("seuilBas"),resultat.getInt("valeurNormale"),resultat.getString("paramFonc"),resultat.getString("MISH"));
				int ordre = resultat.getInt("ordre");
				modele.ajouterStation(station, ordre);
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
	}
	/**
	 * cette fonction permet de generer la tourn�e suivante d'un modele
	 * @param modele
	 * @return
	 * Pas encore Finie, on touche pas (Quentin)
	 */
	public Tournee genererProchaineTournee(ModeleTournee modele)
	{
		// t0 du mod�le
		int t0 = modele.getT0();
		
		// mois courant 
		GregorianCalendar date = new GregorianCalendar();
		int mois = date.get(Calendar.MONTH);
		
		int typeTournee = mois-t0;
		if(typeTournee < 0)
		{
			typeTournee+=11;
		}
		//typeTourn�e = ecart entre le t0 et le moi actuel
		
		switch(typeTournee)
		{
		case 12:
			
			break;
		case 9 :
			break;
		case 6 : 
			break;
		case 3 :
			break;
		default :
			break;
		}
		return null;
	}
	/**
	 * cette fonction permet de filtrer les stations avec une fr�quence max
	 * si la frequence de la station est <= a la fr�quenceMax alors elle est ajout�e a une nouvelle HashMap<key,Station>
	 * @param stations Stations initiale du mod�le
	 * @param frequenceMax fr�quence maximum voulu pour les stations
	 * @return une sous HashMap de la HashMap du modele avec seulement les stations avec une fr�quence <= FrequenceIndiqu�e
	 */
	public HashMap<Integer, Station> extraireStations(HashMap<Integer, Station> stations,int frequenceMax)
	{
		int currentPos = 1;
		int numStation =1; 
		Station station;
		
		HashMap<Integer, Station> stationFiltrees = new HashMap<Integer, Station>();
		while((station = stations.get(numStation)) != null)
		{
			if(station.getFrequence() <= frequenceMax)
			{
				stationFiltrees.put(currentPos, station);
				currentPos++;
			}
			numStation++;
		}
		return stationFiltrees;	
	}
}
