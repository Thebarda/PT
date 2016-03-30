package controleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class ConfigController {

	public static String bd;
	
	public static void lireFichierConf() {
		
		String configPath="relevEDF.config";
		Properties properties=new Properties();
		try {
			FileInputStream in =new FileInputStream(configPath);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("Fichier de configuration introuvable");
			try{
				System.out.println("Creation du fichier de configuration ...");
				
				FileWriter out = new FileWriter(configPath);
				
				BufferedWriter bWriter = new BufferedWriter(out);
				bWriter.write("#			Fichier de configuration de relevEDF\n"
							+ "\n"
							+ "#Emplacement du fchier de la base de donnee (Chemin complet)\n"
							+ "# Si vous utilisez des '\\', il faut les doubler\n"
							+ "#	Exemple C:\\\\Users\\\\Toto\\\\bd\\\\\n"
							+ "pathBd=\n"
							+ "\n"
							+ "#Nom du fichier de la base de donnée (Avec l'extension)\n"
							+ "nomBd=bdProjetTutEDF.db\n");
				bWriter.flush();
				bWriter.close();
				
				System.out.println("Fichier de configuration créé");
			}
			catch(IOException e2){
				e2.printStackTrace();
				System.out.println("Creation du fichier de configuration impossible");
			}
		}
		bd = properties.getProperty("pathBd", "") + properties.getProperty("nomBd", "bdProjetTutEDF.db");
		File bdFile = new File(bd);
		if(!bdFile.exists()){
			creerBd();
			insertDefaultBd();
		}
	}

	public static void creerBd(){
		Connection co = null;
		Statement statement = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			co = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);

			statement = co.createStatement();
			String sql = "CREATE TABLE CENTRALE ( "
					   + "	`idCentrale`	INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`nomCentrale`	TEXT NOT NULL, "
					   + "	`identiteNationale`	TEXT NOT NULL UNIQUE, "
					   + "	`estSupprime`	NUMERIC DEFAULT 0 "
					   + "); "
					   + "CREATE TABLE EQUIPEMENT ( "
					   + "	`idEquipement`	INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`idCentrale`	INTEGER NOT NULL, "
					   + "	`nomEquipement`	TEXT NOT NULL, "
					   + "	`descriptionEquipement`	TEXT, "
					   + "	`repereECSH`	TEXT, "
					   + "	`estSupprime`	NUMERIC DEFAULT 0, "
					   + "	FOREIGN KEY(`idCentrale`) REFERENCES `CENTRALE`(`idCentrale`) "
					   + "); "
					   + "CREATE TABLE UNITE ( "
					   + "	`idUnite` INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`nomUnite` TEXT NOT NULL "
					   + "); "
					   + "CREATE TABLE STATION ( "
					   + "	`idStation`	INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`nomStation`	TEXT NOT NULL, "
					   + "	`instructionsCourtes`	TEXT, "
					   + "	`instructionsLongues`	TEXT, "
					   + "	`seuilBas`	REAL, "
					   + "	`seuilHaut`	REAL, "
					   + "	`idEquipement`	INTEGER, "
					   + "	`idUnite`	INTEGER, "
					   + "	`paramFonc`	TEXT, "
					   + "	`valeurNormale`	REAL, "
					   + "	`MISH`	NUMERIC, "
					   + "	`marqueur`	TEXT, "
					   + "	`estSupprime`	NUMERIC DEFAULT 0, "
					   + "	FOREIGN KEY(`idEquipement`) REFERENCES `EQUIPEMENT`(`idEquipement`), "
					   + "	FOREIGN KEY(`idUnite`) REFERENCES `UNITE`(`idUnite`) "
					   + "); "
					   + "CREATE TABLE MODELE_TOURNEE ( "
					   + "	`idModele`	INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`nomModele`	TEXT NOT NULL, "
					   + "	`descriptionModele`	TEXT, "
					   + "	`estSupprime`	NUMERIC DEFAULT 0 "
					   + "); "
					   + "CREATE TABLE ASSO_STATION_MODELE ( "
					   + "	`idStation`	INTEGER NOT NULL, "
					   + "	`idModele`	INTEGER NOT NULL, "
					   + "	`ordre`	INTEGER NOT NULL, "
					   + "	PRIMARY KEY(idStation,idModele), "
					   + "	FOREIGN KEY(`idStation`) REFERENCES STATION ( idStation ), "
					   + "	FOREIGN KEY(`idModele`) REFERENCES MODELE_TOURNEE ( idModele ) "
					   + "); "
					   + "CREATE TABLE TOURNEE ( "
					   + "	`idTournee`	INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`dateExport`	TEXT NOT NULL, "
					   + "	`idModele`	INTEGER NOT NULL, "
					   + "	`estExportee`	NUMERIC NOT NULL, "
					   + "	`estTerminee`	NUMERIC NOT NULL, "
					   + "	`nomTournee`	VARCHAR, "
					   + "	`dateReleve`	TEXT, "
					   + "	FOREIGN KEY(`idModele`) REFERENCES MODELE_TOURNEE ( idModele ) "
					   + "); "
					   + "CREATE TABLE RELEVE ( "
					   + "	`idReleve`	INTEGER PRIMARY KEY AUTOINCREMENT, "
					   + "	`valeurReleve`	REAL, "
					   + "	`commentaireReleve`	TEXT, "
					   + "	`idTournee`	INTEGER NOT NULL, "
					   + "	`idStation`	INTEGER NOT NULL, "
					   + "	FOREIGN KEY(`idTournee`) REFERENCES TOURNEE ( idTournee ), "
					   + "	FOREIGN KEY(`idStation`) REFERENCES STATION ( idStation ) "
					   + "); ";
			statement.executeUpdate(sql);
			statement.close();
			co.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	public static void insertDefaultBd(){
		Connection co = null;
		Statement statement = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			co = DriverManager.getConnection("jdbc:sqlite:"+ConfigController.bd);

			statement = co.createStatement();
			String sql = "INSERT INTO UNITE (idUnite, nomUnite) VALUES(0, '__VERIFICATION'); "
					+ "INSERT INTO UNITE (idUnite, nomUnite) VALUES(1, '__TEXTE'); ";
			statement.executeUpdate(sql);
			statement.close();
			co.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
}
