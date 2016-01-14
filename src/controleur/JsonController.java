package controleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;

import modele.ModeleTournee;
import modele.Releve;
import modele.Station;
import modele.Tournee;

public class JsonController {

	public static void exporterTournee(String fichier, Tournee tournee){
		
		JsonArrayBuilder builderStations = Json.createArrayBuilder();
		
		HashMap<Integer, Station> stations = tournee.getStations();
		for (int i=1; i<=stations.size(); i++) {
			Station station = stations.get(i);
			ArrayList<Releve> releves = ReleveController.load5LastReleve(station.getId());
			
			JsonArrayBuilder histoBuilder = Json.createArrayBuilder();
			for(Releve releve : releves){
				JsonObject JsonReleve = Json.createObjectBuilder()
						.add("date", releve.getDate())
						.add("valeur", releve.getValeur())
						.build();
				
				histoBuilder.add(JsonReleve);
			}
			
			JsonArray JsonHistoriques = histoBuilder.build();
			
			JsonObject JsonStation = Json.createObjectBuilder()
					.add("idStation", station.getId())
					.add("nomStation", station.getNom())
					.add("instructionsCourtes", station.getInstructionCourte())
					.add("instructionsLongues", station.getInstructionLongue())
					.add("seuilBas", station.getSeuilBas())
					.add("seuilHaut", station.getSeuilHaut())
					.add("idEquipement", StationController.loadStationIdEquipement(station.getId()))
					.add("unite", UniteController.idVersNom(station.getIdUnite()))
					.add("marqueur", station.getMarqueur())
					.add("paramFonc", station.getParamFonc())
					.add("valeurNormale", station.getValeurNormale())
					.add("MISH", station.getMishEntier())
					.add("nbHistorique", releves.size())
					.add("historiques", JsonHistoriques)
					.build();
			
			builderStations.add(JsonStation);
	    }
		
		JsonArray JsonStations = builderStations.build();
		
		
		
		String format = "dd/MM/yyyy H:mm";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
		java.util.Date date = new java.util.Date();
		
		ModeleTournee modele = ModeleTourneeController.loadModeleTournee(tournee.getIdModele());
		
		TourneeController.setDateExport(tournee.getId(), formater.format( date ).toString());
		
		JsonArray jsonReleves = Json.createArrayBuilder()
				.build();
		
		JsonObject JsonTournee = Json.createObjectBuilder()
				.add("nomApp", "relevEDF")
				.add("idTournee", tournee.getId())
				.add("nomModele", modele.getNom())
				.add("descModele", modele.getDescription())
				.add("dateExport", formater.format( date ).toString())
				.add("estComplete", 0)
				.add("nbStations", stations.size())
				.add("stations", JsonStations)
				.add("Releves", jsonReleves)
				.build();
		
		try {
			Map<String, Object> config = new HashMap<String, Object>();
	        //if you need pretty printing
	        config.put("javax.json.stream.JsonGenerator.prettyPrinting", Boolean.valueOf(true));
	        JsonWriterFactory factory = Json.createWriterFactory(config);
	        
			JsonWriter writer;
			writer = factory.createWriter(new FileOutputStream(fichier));
		    writer.writeObject(JsonTournee);
		    writer.close();
		    TourneeController.setExportee(tournee.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void importerTournee(String fichier){
		JsonReader reader;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			JsonArray releves = tournee.getJsonArray("Releves");

			JsonObject[] tabReleves;
			tabReleves = releves.toArray(new JsonObject[0]);
			
			TourneeController.setTerminee(tournee.getInt("idTournee"));
			for(JsonObject jo : tabReleves){
				ReleveController.ajouterReleve(jo.getJsonNumber("valeur").doubleValue(), jo.getString("commentaire"), jo.getInt("idStation"), tournee.getInt("idTournee"));
			}
			Tournee tourneeBdd = TourneeController.loadTourneeById(tournee.getInt("idTournee"));
			ModeleTournee modele = ModeleTourneeController.loadModeleTournee(tourneeBdd.getIdModele());
			TourneeController.setDateExport(tournee.getInt("idTournee"), tournee.getString("dateExport"));
			TourneeController.setDateReleve(tournee.getInt("idTournee"), tournee.getString("dateReleve"));
			modele.genererTournee();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean estCompleteTournee(String fichier){
		JsonReader reader;
		boolean estComplete = false;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			if(tournee.getInt("estComplete") == 1)
				estComplete = true;
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return estComplete;
	}
	
	public static boolean estPareilTournee(String fichier, Tournee tournee){
		JsonReader reader;
		boolean estPareil = false;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject jsonTournee = reader.readObject();
			if(jsonTournee.getInt("idTournee") == tournee.getId())
				estPareil = true;
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return estPareil;
	}
	
	public static int getIdTournee(String fichier){
		JsonReader reader;
		int idTournee = 0;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			idTournee = tournee.getInt("idTournee");
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return idTournee;
	}
}
