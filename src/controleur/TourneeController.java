package controleur;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Station;
import modele.Tournee;
import modele.Releve;

public class TourneeController {
	
	public static ObservableList<Tournee> loadTournee(int idCentrale){
		
		ObservableList<Tournee> tournees=FXCollections.observableArrayList();
		Connection connexion = null;
		ResultSet resultat = null;
		ResultSet resultatStations = null;
		Statement statut = null;
		Statement statutStations = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			statut = connexion.createStatement();
			resultat = statut.executeQuery("SELECT idTournee, dateTournee, idModele, estExportee, estTerminee, nomTournee FROM tournee t "
										+ "WHERE ( "
										+ "Select idCentrale from equipement e "
										+ "INNER JOIN station s ON e.idEquipement=s.idEquipement "
										+ "INNER JOIN asso_station_modele asm ON asm.idStation=s.idStation "
										+ "INNER JOIN modele_tournee mt ON asm.idModele=mt.idModele "
										+ "WHERE mt.idModele=t.idModele "
										+ "limit 1 "
										+ ")= " + idCentrale + " AND estTerminee = false");
			while(resultat.next()){
				int  idTournee = resultat.getInt("idTournee");
				String dateTournee = resultat.getString("dateTournee");
				String nomTournee = resultat.getString("nomTournee");
				int idModele = resultat.getInt("idModele");
				boolean estExportee = resultat.getBoolean("estExportee");
				boolean estTerminee = resultat.getBoolean("estTerminee");
	
				HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
				
				statutStations = connexion.createStatement();
				
				resultatStations = statutStations.executeQuery("SELECT r.idStation, nomStation, instructionsCourtes, "
													+ "instructionsLongues, seuilBas, seuilHaut, frequenceControle, "
													+ "idEquipement, idUnite, paramFonc, valeurNormale, "
													+ "MISH from station s "
													+ "INNER JOIN releve r ON s.idStation=r.idStation "
													+ "INNER JOIN asso_station_modele asm ON asm.idStation=r.idStation "
													+ "WHERE r.idTournee=" + idTournee + " "
													+ "ORDER BY asm.ordre ASC");
				int i = 1;
				while(resultatStations.next()){
					Station station = new Station(resultatStations.getInt("idStation"),
							resultatStations.getString("nomStation"),
							resultatStations.getString("instructionsCourtes"),
							resultatStations.getString("instructionsLongues"),
							resultatStations.getInt("idUnite"),
							resultatStations.getInt("seuilHaut"),resultatStations.getInt("seuilBas"),resultatStations.getInt("valeurNormale"),resultatStations.getString("paramFonc"),resultatStations.getBoolean("MISH"));
					stations.put(i, station);
					i++;
				}
				Tournee tournee = new Tournee(idTournee,
						nomTournee,
						idModele,
						stations,
						estExportee,
						estTerminee,
						dateTournee);
				
				tournees.add(tournee);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try 
	         {  
				 resultatStations.close();
	             resultat.close();
	             statut.close();
	             statutStations.close();
	             connexion.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
		}
		return tournees;
	}
	
	public static void addTournee(Tournee tournee)
	{
		Connection connexion = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connexion = DriverManager.getConnection("jdbc:sqlite:bdProjetTutEDF.db");
			
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO "
					+ "tournee(nomTournee,dateTournee,idModele,estExportee,estTerminee) "
					+ "VALUES(?,?,?,?,?)");
			preparedStatement.setString(1, tournee.getNom());
			preparedStatement.setString(2, tournee.getDate());
			preparedStatement.setInt(3, tournee.getIdModele());
			int isExportee;
			if(tournee.isEstExportee()){
				isExportee = 1;
			} else {
				isExportee = 0;
			}
			preparedStatement.setInt(4, isExportee);
			int isTerminee;
			if(tournee.isTerminee()){
				isTerminee = 1;
			} else {
				isTerminee = 0;
			}
			preparedStatement.setInt(5, isTerminee);
			preparedStatement.executeUpdate();
			
			// On recupère l'id de la tournée crée
			int id = preparedStatement.getGeneratedKeys().getInt(1);
			
			// Ajout dans l'association avec les stations
			HashMap<Integer, Station> stations = tournee.getStations();
			for(int i=1; i<=stations.size();i++){
				Station station = stations.get(i);
				PreparedStatement preparedStatementAsso = connexion.prepareStatement("INSERT INTO "
					+ "releve(idTournee, idStation) "
					+ "VALUES(?,?)");
				preparedStatementAsso.setInt(1, id);
				preparedStatementAsso.setInt(2, station.getId());
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
	
	
	/*
	
	
	public static void genererJsonTournee(int idTournee, String location){
		
		Tournee tournee = loadTourneeById(idTournee);

		JsonArray jsonStations;
		
		JsonArray jsonHistoriqueStation = (JsonArray) Json.createArrayBuilder();
		
		// Chargement des stations de la tournée en Json
		HashMap<Integer, Station> stations = tournee.getStations();
		for(int i=1; i<=stations.size();i++){
			Station station = stations.get(i);
			
			for(int j=1; j<=5; j++){
				stations = loadStations
				jsonHistoriqueStation = (JsonArray) Json.createArrayBuilder();
			}
			
			JsonObject jsonStation= Json.createObjectBuilder()
					.add("idStation", station.getId())
					.add("Nom", station.getNom())
					.add("Instruction courte", station.getInstructionCourte())
					.add("Instruction longue", station.getInstructionLongue())
					.add("Historique", jsonHistoriqueStation)
					.add("Unite", station.getNomUnite())
					.build();
			
			// Ajout dans le JsonArray des station
			jsonStations = (JsonArray) Json.createArrayBuilder()
					.add(jsonStation);
		}
		
		jsonStations = (JsonArray) Json.createArrayBuilder()
				.build();
		
		JsonObject jsonTournee= Json.createObjectBuilder()
				.add("Nom", "Station Boitier")
				.add("Instruction courte", "C'est rigolo")
				.add("Instruction longue", "C'est VRAIMENT rigolo")
				.add("Historique", historique)
				.add("Equipement", "Boitier")
				.add("Unite", "Volt")
				.add("Nb Releve", "0")
				.add("Accepte releve Multiple", "1")
				.build();
		
		

		
	}

	
	
	
	
	private static JsonObject addHistoriqueForStation(JsonObject jo, Releve releve){
		
		JsonObject jsonAncienReleve= Json.createObjectBuilder()
			.add("dateReleve", releve.getDate())
			.add("Nom", releve.getValeur())
			.build();

		jo=jsonObjectToBuilder(jo).add("Releve", jsonAncienReleve).build();
		
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter writer = Json.createWriter(stringWriter)) {
			writer.writeObject(jo);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jo;
	}

	private static JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
	    JsonObjectBuilder job = Json.createObjectBuilder();

	    for (Entry<String, JsonValue> entry : jo.entrySet()) {
	        job.add(entry.getKey(), entry.getValue());
	    }

	    return job;
	}


*/
	
	
}
