package controleur;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import modele.ModeleTournee;
import modele.Releve;
import modele.Station;
import modele.Tournee;

public class JsonController {

	public static void exporterTournee(String fichier, Tournee tournee){
		
		JsonArrayBuilder builderStations = Json.createArrayBuilder();
		
		HashMap<Integer, Station> stations = tournee.getStations();
		for (int i=1; i<stations.size(); i++) {
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
		
		JsonObject JsonTournee = Json.createObjectBuilder()
				.add("nomApp", "relevEDF")
				.add("idTournee", tournee.getId())
				.add("nomModele", modele.getNom())
				.add("descModele", modele.getDescription())
				.add("dateExport", date.toString())
				.add("estComplete", 0)
				.add("nbStations", stations.size())
				.add("stations", JsonStations)
				.build();
		
		try {
			JsonWriter writer;
			writer = Json.createWriter(new FileOutputStream(fichier));
		    writer.writeObject(JsonTournee);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
