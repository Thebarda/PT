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
/**
 * Classe qui permet l'edition d'un fichier json
 */
public class JsonController {
	/**
	 * Fonction qui ecrit un releve dans un fichier json
	 * @param fichier chemin du fichier
	 * @param idStation id de la station 
	 * @param com commentaire a ecrire
	 * @param valeur valeur du releve
	 */
	public static void ecrireReleve(String fichier, int idStation, String com, double valeur) {
		
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
			boolean existe = false;
			for(JsonObject jo : tabReleves){
				if(jo.getInt("idStation")==idStation){
					builder.add(releve);
					existe = true;
				}
				else{
					builder.add(jo);
				}
			}
			if(!existe){
				builder.add(releve);
			}
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
	/**
	 * Fonction qui charge l'historique d'une station donnée
	 * @param fichier chemin du fichier
	 * @param idStation id de la station
	 * @return un JsonObject[], tableau d'onjet Json
	 */
	public static JsonObject[] loadHistoriques(String fichier, int idStation){
		JsonReader reader;
		JsonObject[] tabStations;
		JsonObject[] tabHistorique = null;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			JsonArray stations = tournee.getJsonArray("stations");
			
			tabStations = stations.toArray(new JsonObject[0]);
			for(JsonObject jo : tabStations){
				if(jo.getInt("idStation")==idStation){
					tabHistorique = jo.getJsonArray("historiques").toArray(new JsonObject[0]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tabHistorique;
	}
	/**
	 * Charge toutes les station d'un fichier json
	 * @param fichier chemin du fichier
	 * @return tableau d'objet Json avec la premiere station en indice 0.
	 */
	public static JsonObject[] loadStations(String fichier){
		JsonReader reader;
		JsonObject[] tabStations;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
			JsonArray stations = tournee.getJsonArray("stations");
			
			tabStations = stations.toArray(new JsonObject[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			tabStations = null;
		}
		return tabStations;
	}
	/**
	 * Charge les stations dans une observableList pour l'import
	 * @param fichier fichier chemin du fichier
	 * @return une observableList de toutes les stations présentes
	 */
	public static ObservableList<Station> loadObservableStations(String fichier){
		ObservableList<Station> oStations=FXCollections.observableArrayList();
		JsonObject[] tabStations = loadStations(fichier);
		
		for(JsonObject jo : tabStations){
			oStations.add(new Station(jo.getInt("idStation"), jo.getString("nomStation"), jo.getString("instructionsCourtes"), jo.getString("instructionsLongues"), jo.getString("unite"), jo.getInt("seuilBas"), jo.getInt("seuilHaut"), jo.getInt("valeurNormale"), jo.getString("paramFonc"), jo.getInt("MISH")));
		}

		return oStations;
	}
	/**
	 * Fonction qui permet de changer MISH
	 * @param fichier chemin du fichier
	 * @param idStation id de la station pour laquelle on souhaite le changement
	 * @param mish nouvelle valeur du mish
	 */
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
	/**
	 * Fonction qui permet de changer la valeur de estComplete
	 * @param fichier chemin du fichier
	 * @param estComplete nouvelle valeur de estComplete
	 */
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
	/**
	 * FOnction qui permet de savoir si un releve est saisi pour une station donnée
	 * @param fichier chemin du fichier
	 * @param idStation id de la station
	 * @return booleen true si le releve a ete saisi, false sinon
	 */
	public static boolean estReleveSaisi(String fichier, int idStation){
		JsonReader reader;
		boolean existe = false;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
				
			JsonArray releves = tournee.getJsonArray("Releves");

			JsonObject[] tabReleves;
			tabReleves = releves.toArray(new JsonObject[0]);
			int i=0;
			while(i<tabReleves.length && !existe){
				if(tabReleves[i].getInt("idStation")==idStation){
					existe = true;
				}
				i++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return existe;
	}
	/**
	 * Retourne la valeur du releve pour une station
	 * @param fichier chemin du fichier
	 * @param idStation id de la station
	 * @return double valeur du releve de la station
	 */
	public static double getReleve(String fichier, int idStation){
		JsonReader reader;
		double valeurReleve = 0;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
				
			JsonArray releves = tournee.getJsonArray("Releves");

			JsonObject[] tabReleves;
			tabReleves = releves.toArray(new JsonObject[0]);
			int i=0;
			while(i<tabReleves.length && valeurReleve==0){
				if(tabReleves[i].getInt("idStation")==idStation){
					valeurReleve = tabReleves[i].getJsonNumber("valeur").doubleValue();
				}
				i++;
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return valeurReleve;
	}
	/**
	 * Retourne le commentaire pour une station
	 * @param fichier chemin du fichier
	 * @param idStation id de la station
	 * @return String commentaire pour la station
	 */
	public static String getCommentaire(String fichier, int idStation){
		JsonReader reader;
		String comReleve=null;
		try {
			reader = Json.createReader(new FileInputStream(fichier));
			JsonObject tournee = reader.readObject();
				
			JsonArray releves = tournee.getJsonArray("Releves");

			JsonObject[] tabReleves;
			tabReleves = releves.toArray(new JsonObject[0]);
			int i=0;
			while(i<tabReleves.length && comReleve==null){
				if(tabReleves[i].getInt("idStation")==idStation){
					comReleve = tabReleves[i].getString("commentaire");
				}
				i++;
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return comReleve;
	}
}
