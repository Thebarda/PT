package controlleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modele.Station;

public class JsonController {

	public static void ecrireReleve(String fichier, int idStation, String com, float valeur) {
		
		JsonReader reader;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			
			JsonArray releves = tournee.getJsonArray("Releves");
			
			JsonObject releve = Json.createObjectBuilder()
					.add("idStation", idStation)
					.add("commentaire", com)
					.add("valeur", valeur)
					.build();
			
			JsonArrayBuilder builder = Json.createArrayBuilder();
			
			JsonObject[] tabReleves;
			tabReleves = releves.toArray(new JsonObject[0]);
			for(JsonObject jo : tabReleves){
				builder.add(jo);
			}
			builder.add(releve);
			JsonArray newReleves = builder.build();
			
			JsonObject newTournee = Json.createObjectBuilder()
					.add("nomApp", tournee.getString("nomApp"))
					.add("idTournee", tournee.getInt("idTournee"))
					.add("nomModele", tournee.getString("nomModele"))
					.add("descModele", tournee.getString("descModele"))
					.add("dateExport", tournee.getString("dateExport"))
					.add("estComplete", tournee.getInt("estComplete"))
					.add("nbStations", tournee.getInt("nbStations"))
					.add("stations", tournee.getJsonArray("stations"))
					.add("Releves", newReleves)
					.build();
			
			reader.close();
			
			try {
				JsonWriter writer;
				writer = Json.createWriter(new FileOutputStream(fichier));
			    writer.writeObject(newTournee);
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static ObservableList<JsonObject> loadStations(String fichier){
		JsonReader reader;
		JsonObject[] tabStations;
		ObservableList<JsonObject> oStations=FXCollections.observableArrayList();
		
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			JsonArray stations = tournee.getJsonArray("stations");
			
			tabStations = stations.toArray(new JsonObject[0]);
			
			for(JsonObject jo : tabStations){
				oStations.add(jo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return oStations;
	}
	
	public static void changerMISH(String fichier, int idStation, int mish){
		JsonReader reader;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			
			JsonArray stations = tournee.getJsonArray("stations");
			
			JsonArrayBuilder builder = Json.createArrayBuilder();
			
			JsonObject[] tabStations;
			tabStations = stations.toArray(new JsonObject[0]);
			for(int i=0;i<tabStations.length;i++){
				if(tabStations[i].getInt("idStation")== idStation){
					tabStations[i] = Json.createObjectBuilder()
							.add("idStation", tabStations[i].getInt("idStation"))
							.add("nomStation", tabStations[i].getString("nomStation"))
							.add("instructionsCourtes", tabStations[i].getString("instructionsCourtes"))
							.add("instructionsLongues", tabStations[i].getString("instructionsLongues"))
							.add("seuilBas", tabStations[i].getInt("seuilBas"))
							.add("seuilHaut", tabStations[i].getInt("seuilHaut"))
							.add("idEquipement", tabStations[i].getInt("idEquipement"))
							.add("unite", tabStations[i].getString("unite"))
							.add("paramFonc", tabStations[i].getString("paramFonc"))
							.add("valeurNormale", tabStations[i].getInt("valeurNormale"))
							.add("MISH", mish)
							.build();
				}
				builder.add(tabStations[i]);
			}
			JsonArray newStations = builder.build();
			
			
			JsonObject newTournee = Json.createObjectBuilder()
					.add("nomApp", tournee.getString("nomApp"))
					.add("idTournee", tournee.getInt("idTournee"))
					.add("nomModele", tournee.getString("nomModele"))
					.add("descModele", tournee.getString("descModele"))
					.add("dateExport", tournee.getString("dateExport"))
					.add("estComplete", tournee.getInt("estComplete"))
					.add("nbStations", tournee.getInt("nbStations"))
					.add("stations", newStations)
					.add("Releves", tournee.getJsonArray("Releves"))
					.build();
			
			reader.close();
			
			try {
				JsonWriter writer;
				writer = Json.createWriter(new FileOutputStream("C:\\Users\\Clément\\git\\testJson.json"));
			    writer.writeObject(newTournee);
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void changerEstComplete(String fichier, int estComplete){
		JsonReader reader;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			
			JsonObject newTournee = Json.createObjectBuilder()
					.add("nomApp", tournee.getString("nomApp"))
					.add("idTournee", tournee.getInt("idTournee"))
					.add("nomModele", tournee.getString("nomModele"))
					.add("descModele", tournee.getString("descModele"))
					.add("dateExport", tournee.getString("dateExport"))
					.add("estComplete", estComplete)
					.add("nbStations", tournee.getInt("nbStations"))
					.add("stations", tournee.getJsonArray("stations"))
					.add("Releves", tournee.getJsonArray("Releves"))
					.build();
			
			reader.close();
			
			try {
				JsonWriter writer;
				writer = Json.createWriter(new FileOutputStream("C:\\Users\\Clément\\git\\testJson.json"));
			    writer.writeObject(newTournee);
			    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
