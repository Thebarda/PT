package controlleur;

import java.awt.Checkbox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vue.Main;

/**
 * Classe qui gere la saisie des releves
 */

public class saisirReleveController {

	public final String NOM_UNITE_CHECK = "__VERIFICATION";
	@FXML
	AnchorPane Stations;

	@FXML
	VBox Historique;

	@FXML
	Label nom;

	@FXML
	Label instrCourte;

	@FXML
	Label unite;

	@FXML
	Label seuil;

	@FXML
	TextField releve;

	@FXML
	ScrollPane scroll;

	@FXML
	TextArea commentaire;

	@FXML
	Label erreur;

	@FXML
	Label avancement;

	@FXML
	Label erreurFin;

	@FXML
	ImageView imageDroite;

	@FXML
	ImageView imageGauche;

	@FXML
	CheckBox checkBox1;

	@FXML
	CheckBox checkBox2;
	
	@FXML
	CheckBox checkBox3;

	@FXML
	CheckBox checkBox4;

	@FXML
	Label textReleve;

	JsonObject[] stations;

	int currentPos;

	List<Label> labels = new ArrayList<Label>();

	static String nomJson;

	static double ecartSeuil;
	static String seuilAff;
	static double releveEff;
	static String commentaireEff;
	static double seuilBas;
	static double seuilHaut;
	static boolean doitChargerSuivant = false;
	static String instruLongue;
	static String paramFonc;

	int nbStations;

	int compteur;

	static int nbReleveEff = 0;

	double x;

	double y;

	/**
	 * Initialisation
	 * si case a cocher:
	 * 0.0 = Anomalie
	 * 1.0 = Conforme
	 * 2.0 = Non Fait
	 * 3.0 = Intervention
	 */
	@FXML
	private void initialize() {
		JsonReader reader;
		double releveDejaSaisi;
		boolean releveDejaSaisiAnormaleBas = false;
		boolean releveDejaSaisiAnormaleHaut = false;
		boolean releveDejaSaisiNormale = false;
		boolean correctBas = false;
		boolean correctHaut = false;
		try {
			nomJson = ImportationController.chemin;
			reader = Json.createReader(new FileInputStream(nomJson));
			JsonObject tournee = reader.readObject();
			nbStations = tournee.getInt("nbStations");
			stations = JsonController.loadStations(nomJson);
			ReleveController.initialize(stations, nomJson);
			compteur = 0;
			imageDroite.setImage(new Image("File:droite.png"));
			imageGauche.setImage(new Image("File:gauche.png"));
			if (nbStations > 4) {
				scroll.setOnMouseReleased(releaseScroll);
				imageGauche.setVisible(false);
			} else {
				imageGauche.setVisible(false);
				imageDroite.setVisible(false);
			}
			while (compteur < nbStations) {
				Label releve = new Label(stations[compteur].getString("nomStation"));
				releve.setPrefHeight(138.0);
				releve.setPrefWidth(175.0);
				releve.setStyle("-fx-border-style: solid;");
				releve.setWrapText(true);
				releve.setId(String.valueOf(compteur));
				if (compteur > 0) {
					releve.setLayoutX((compteur * 175) - 1);
				}
				releve.setAlignment(Pos.CENTER);
				releve.setTextAlignment(TextAlignment.CENTER);
				releve.setOnMousePressed(pressed);
				releve.setOnMouseReleased(release);
				Stations.getChildren().add(releve);
				labels.add(releve);
				if (JsonController.estReleveSaisi(nomJson, stations[compteur].getInt("idStation"))) {
					nbReleveEff++;
					releveDejaSaisi = JsonController.getReleve(nomJson, stations[compteur].getInt("idStation"));
					if (stations[compteur].getString("unite").equals("__VERIFICATION")) {
						System.out.println("verif");
						if (releveDejaSaisi == 1.0) {
							labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n Conforme");
							labels.get(compteur).setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
						}
						if (releveDejaSaisi == 0.0) {
							labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n Anomalie");
							labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
						}
						if (releveDejaSaisi == 2.0) {
							labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n Non Fait");
							labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
						}
						if (releveDejaSaisi == 3.0) {
							labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n Intervention");
							labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
						}

					} else {
						if (ReleveController.existeSeuilBas(compteur)
								&& releveDejaSaisi >= stations[compteur].getInt("seuilBas")) {
							correctBas = true;
						}
						if (ReleveController.existeSeuilHaut(compteur)
								&& releveDejaSaisi <= stations[compteur].getInt("seuilHaut")) {
							correctHaut = true;
						}
						if (ReleveController.existeSeuilBas(compteur) && ReleveController.existeSeuilHaut(compteur)) {
							if (correctBas && correctHaut) {
								releveDejaSaisiNormale = true;
							} else {
								if (correctHaut || (!correctBas && !ReleveController.existeSeuilHaut(compteur))) {
									releveDejaSaisiAnormaleBas = true;
								} else if (correctBas || (!correctHaut && !ReleveController.existeSeuilBas(compteur))) {
									releveDejaSaisiAnormaleHaut = true;
								}
							}
						} else if ((ReleveController.existeSeuilBas(compteur) && correctBas)
								|| (ReleveController.existeSeuilHaut(compteur) && correctHaut)
								|| (!ReleveController.existeSeuilHaut(compteur)
										&& !ReleveController.existeSeuilBas(compteur))) {
							releveDejaSaisiNormale = true;
						} else {
							if (correctHaut || (!correctBas && !ReleveController.existeSeuilHaut(compteur))) {
								releveDejaSaisiAnormaleBas = true;
							} else if (correctBas || (!correctHaut && !ReleveController.existeSeuilBas(compteur))) {
								releveDejaSaisiAnormaleHaut = true;
							}
						}
						if (releveDejaSaisiNormale) {
							labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n"
									+ releveDejaSaisi + " " + unite.getText() + "\nValide");
							labels.get(compteur).setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
							releveDejaSaisiNormale = false;
							correctHaut = false;
							correctBas = false;
						}
						if ((releveDejaSaisiAnormaleBas) || (releveDejaSaisiAnormaleHaut)) {
							labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n"
									+ releveDejaSaisi + " " + unite.getText() + "\nAnormale");
							labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
							releveDejaSaisiAnormaleBas = false;
							correctHaut = false;
							correctBas = false;
						}
					}
				}
				compteur++;
			}
			avancement.setText(nbReleveEff + "\n/\n" + nbStations);
			currentPos = 0;
			charge(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gere l'evenement quand le bouton de la souris est presse on stocke les
	 * coordonn�e du corseur quand la souris est pressee
	 */
	EventHandler<MouseEvent> pressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			x = e.getSceneX();
			y = e.getSceneY();
		}
	};
	/**
	 * Gere l'evenement quand le bouton de la souris est relache quand la souris
	 * est relache, on fait la difference de coordonnee, et l'on charge les
	 * information seulement quand la diff�rence estcomprise dans un carre de
	 * 10px
	 */
	EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			Label label = (Label) e.getSource();
			if (((e.getSceneX() - x) < 10.0) && ((e.getSceneY() - y) < 10.0)) {
				if (((e.getSceneX() - x) > -10.0) && ((e.getSceneY() - y) > -10.0)) {
					currentPos = Integer.parseInt(label.getId());
					charge(Integer.parseInt(label.getId()));

				}
			}
		}
	};

	EventHandler<MouseEvent> releaseScroll = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			ScrollPane scroll = (ScrollPane) e.getSource();
			if (scroll.getHvalue() == 0) {
				imageGauche.setVisible(false);
				imageDroite.setVisible(true);
			}
			if (scroll.getHvalue() == 1) {
				imageDroite.setVisible(false);
				imageGauche.setVisible(true);
			}
			if (scroll.getHvalue() != 1 && scroll.getHvalue() != 0) {
				imageDroite.setVisible(true);
				imageGauche.setVisible(true);
			}
		}
	};

	EventHandler eh = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() instanceof CheckBox) {
				CheckBox chk = (CheckBox) event.getSource();
				if ("Conforme".equals(chk.getText())) {
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
					checkBox4.setSelected(false);			
				} else if ("Anomalie".equals(chk.getText())) {
					checkBox1.setSelected(false);
					checkBox3.setSelected(false);
					checkBox4.setSelected(false);
				}
				else if ("Non Fait".equals(chk.getText())) {
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox4.setSelected(false);
				}
				else if ("Intervention".equals(chk.getText())) {
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
				}
			}
		}
	};

	/**
	 * Charge la station selon son numero
	 * 
	 * @param numStation
	 *            numero de la station que l'on veut charger
	 */
	public void charge(int numStation) {
		unite.setFont(new Font(24.0));
		erreur.setVisible(false);
		erreurFin.setText("");
		seuilBas = stations[numStation].getInt("seuilBas");
		seuilHaut = stations[numStation].getInt("seuilHaut");
		paramFonc = stations[numStation].getString("paramFonc");
		instruLongue = stations[numStation].getString("instructionsLongues");

		nom.setText(stations[numStation].getString("nomStation"));
		instrCourte.setText(stations[numStation].getString("instructionsCourtes"));
		if (stations[numStation].getString("unite").equals("__VERIFICATION")) {
			unite.setVisible(false);
			releve.setVisible(false);
			textReleve.setVisible(false);
			checkBox1.setVisible(true);
			checkBox2.setVisible(true);
			checkBox3.setVisible(true);
			checkBox4.setVisible(true);
			seuil.setVisible(false);
			afficherHistorique(stations[numStation].getInt("idStation"), numStation);
			checkBox1.setOnAction(eh);
			checkBox2.setOnAction(eh);
			checkBox3.setOnAction(eh);
			checkBox4.setOnAction(eh);
			if (JsonController.estReleveSaisi(nomJson, stations[currentPos].getInt("idStation"))) {
				if (JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation")) == 0) {
					checkBox1.setSelected(true);
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
					checkBox4.setSelected(false);
				} 
				if (JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation")) == 1) {
					checkBox1.setSelected(false);
					checkBox2.setSelected(true);
					checkBox3.setSelected(false);
					checkBox4.setSelected(false);
				}
				if (JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation")) == 2) {
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox3.setSelected(true);
					checkBox4.setSelected(false);
				}
				if (JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation")) == 3) {
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
					checkBox4.setSelected(true);
				}
				commentaire.setText(JsonController.getCommentaire(nomJson, stations[currentPos].getInt("idStation")));
			} else {
				checkBox2.setSelected(false);
				checkBox1.setSelected(false);
				checkBox3.setSelected(false);
				checkBox4.setSelected(false);
			}
		} else {
			unite.setVisible(true);
			releve.setVisible(true);
			textReleve.setVisible(true);
			checkBox1.setVisible(false);
			checkBox2.setVisible(false);
			checkBox3.setVisible(false);
			checkBox4.setVisible(false);
			seuil.setVisible(true);
			unite.setText(stations[numStation].getString("unite"));

			if (unite.getText().length() >= 12) {
				unite.setFont(new Font(15));
			}
			seuil.setText(ReleveController.seuil(numStation));
			releve.setText("");
			commentaire.setText("");
			if (JsonController.estReleveSaisi(nomJson, stations[currentPos].getInt("idStation"))) {
				releve.setText(
						Double.toString(JsonController.getReleve(nomJson, stations[currentPos].getInt("idStation"))));
				commentaire.setText(JsonController.getCommentaire(nomJson, stations[currentPos].getInt("idStation")));
			}
			afficherHistorique(stations[numStation].getInt("idStation"),numStation);
		}
	}

	/**
	 * Verifie la saisie et lance les fonction pour l'inscription dans le json
	 */
	public void valider() {
		erreur.setVisible(false);
		erreurFin.setText("");
		String releveStr = releve.getText();
		if (stations[currentPos].getString("unite").equals("__VERIFICATION")) {
			if (checkBox1.isSelected() || checkBox2.isSelected()||checkBox3.isSelected() || checkBox4.isSelected()) {
				if (checkBox1.isSelected()) {
					releveConforme(currentPos);
					ReleveController.controller(currentPos, 1, commentaire.getText());
				} 
				if (checkBox2.isSelected()) {
					releveNonConforme(currentPos);
					ReleveController.controller(currentPos, 0, commentaire.getText());
				}
				if (checkBox3.isSelected()) {
					releveNonFait(currentPos);
					ReleveController.controller(currentPos, 2, commentaire.getText());
				}
				if (checkBox4.isSelected()) {
					releveIntervention(currentPos);
					ReleveController.controller(currentPos, 3, commentaire.getText());
				}
			}
			passerSuivant();
		} else {
			if (releveStr.contains(",")) {
				releveStr = releveStr.replace(",", ".");
			}
			if (!estUnDouble(releveStr)) {
				erreur.setVisible(true);
			} else {
				releveEff = Double.parseDouble(releveStr);
				ecartSeuil = ReleveController.controller(currentPos, releveEff, commentaire.getText());
				seuilAff = ReleveController.seuil2(currentPos);
				if (ecartSeuil != 0) {
					verifierReleve();
				} else {
					releveNormale(currentPos);
					passerSuivant();
				}
			}
		}
	}

	/**
	 * Ouvre la fenetre qui permet de valider ou non une valeur anormale
	 */
	public void verifierReleve() {
		commentaireEff = commentaire.getText();
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("validerReleve.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
			dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Valider un releve");
			dialog.show();
			ValiderReleveController.initialise(stations, currentPos);
			dialog.getIcons().add(new Image("file:logo.png"));
			dialog.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					dialog.close();
					verifChargerSuivantAnormal();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de passer a la station suivante au niveau du scroll pane
	 */
	public void passerSuivant() {
		if (currentPos + 1 != nbStations) {
			avancement.setText(nbReleveEff + "\n/\n" + nbStations);
			currentPos++;
			charge(currentPos);
			releve.setText("");
			commentaire.setText("");
			scroll.setHvalue((double) currentPos / ((double) nbStations - 5));
			if (scroll.getHvalue() == 0 && (nbStations > 4)) {
				imageGauche.setVisible(false);
				imageDroite.setVisible(true);
			}
			if (scroll.getHvalue() == 1 && (nbStations > 4)) {
				imageDroite.setVisible(false);
				imageGauche.setVisible(true);
			}
			if (scroll.getHvalue() != 1 && scroll.getHvalue() != 0 && (nbStations > 4)) {
				imageDroite.setVisible(true);
				imageGauche.setVisible(true);
			}
		} else {
			avancement.setText(nbReleveEff + "\n/\n" + nbStations);
		}
	}

	/**
	 * Charge les valeurs de la station suivante
	 */
	public void verifChargerSuivantAnormal() {
		if (doitChargerSuivant) {
			releveAnormale(currentPos);
			passerSuivant();
			doitChargerSuivant = false;
		}
	}

	/**
	 * Affiche les details de la station en position courante
	 */
	public void afficherDetails() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("detailsReleves.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene dialogScene = new Scene(page);
			dialog.setScene(dialogScene);
			dialog.setResizable(false);
			dialog.setTitle("Detail d'un releve");
			dialog.show();
			dialog.getIcons().add(new Image("file:logo.png"));
			dialog.setOnHiding(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					dialog.close();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifie si la valeur passe en parametre est un double ou pas
	 * 
	 * @param chaine
	 *            chaine a verifier
	 * @return true si la chaine est un double, false sinon
	 */
	public boolean estUnDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * colore en vert la station a une position donnee
	 * 
	 * @param pos
	 *            posisiton de la station a colore
	 */
	public void releveNormale(int pos) {
		labels.get(pos).setText(
				stations[pos].getString("nomStation") + "\n" + releve.getText() + " " + unite.getText() + "\nValide");
		labels.get(pos).setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
	}

	/**
	 * colore en orange la station a une position donnee
	 * 
	 * @param pos
	 *            posisiton de la station a colore
	 */
	public void releveAnormale(int pos) {
		labels.get(pos).setText(
				stations[pos].getString("nomStation") + "\n" + releve.getText() + " " + unite.getText() + "\nAnormale");
		labels.get(pos).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");

	}

	/**
	 * fait le texte et la colorisation a la validation pour les case de r�cap
	 * 
	 * @param pos
	 *            position de la station a colore
	 */
	public void releveConforme(int pos) {
		labels.get(pos).setText(stations[pos].getString("nomStation") + "\nConforme");
		labels.get(pos).setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
	}

	/**
	 * fait le texte et la colorisation a la validation pour les case de r�cap
	 * 
	 * @param pos
	 *            position de la station a colore
	 */
	public void releveNonConforme(int pos) {
		labels.get(pos).setText(stations[pos].getString("nomStation") + "\nAnomalie");
		labels.get(pos).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
	}
	
	public void releveNonFait(int pos) {
		labels.get(pos).setText(stations[pos].getString("nomStation") + "\nNon Fait");
		labels.get(pos).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
	}
	
	public void releveIntervention(int pos) {
		labels.get(pos).setText(stations[pos].getString("nomStation") + "\nIntervention");
		labels.get(pos).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
	}

	/**
	 * Fin des releves et passage vers la fenetre de validation finale
	 */
	public void fin() {
		erreurFin.setText("");
		if (nbReleveEff != 0) {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ValidationFinale.fxml"));
			try {
				AnchorPane page = (AnchorPane) loader.load();
				Scene dialogScene = new Scene(page);
				dialog.setScene(dialogScene);
				dialog.setResizable(false);
				dialog.setTitle("Validation");
				dialog.show();
				dialog.getIcons().add(new Image("file:logo.png"));
				dialog.setOnHiding(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we) {
						dialog.close();
						currentPos = 0;
						charge(0);
						scroll.setHvalue(0.0);
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			erreurFin.setText("Aucun releve saisi");
		}
	}

	public void MettreEnPause() {
		erreurFin.setText("");
		if (nbReleveEff != 0) {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ValidationPause.fxml"));
			try {
				AnchorPane page = (AnchorPane) loader.load();
				Scene dialogScene = new Scene(page);
				dialog.setScene(dialogScene);
				dialog.setResizable(false);
				dialog.setTitle("Validation");
				dialog.show();
				dialog.getIcons().add(new Image("file:logo.png"));
				dialog.setOnHiding(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we) {
						dialog.close();
						currentPos = 0;
						charge(0);
						scroll.setHvalue(0.0);
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			erreurFin.setText("Aucun releve saisi");
		}
	}

	/**
	 * Affiche l'historique pour une station donn�e
	 * 
	 * @param idStation
	 *            id de la station
	 */
	public void afficherHistorique(int idStation, int numStation) {
		Historique.getChildren().clear();
		JsonObject[] historique = JsonController.loadHistoriques(nomJson, idStation);
		for (JsonObject obj : historique) {
			Double valeur = obj.getJsonNumber("valeur").doubleValue();
			Label valhist=null;
			if (stations[numStation].getString("unite").equals("__VERIFICATION")) {
				if (valeur == 0.0) {
					valhist = new Label(obj.getString("date") + "\nAnomalie");
					valhist.setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
				}
				if(valeur ==1.0){
					valhist = new Label(obj.getString("date") + "\nConforme");
					valhist.setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
				}
				if (valeur == 2.0) {
					labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n Non Fait");
					labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
				}
				if (valeur == 3.0) {
					labels.get(compteur).setText(stations[compteur].getString("nomStation") + "\n Intervention");
					labels.get(compteur).setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
				}
			} else {
				valhist = new Label(obj.getString("date") + "\n" + valeur);
				boolean estCorrect = true;
				if (ReleveController.existeSeuilBas(currentPos) && (valeur < seuilBas)) {
					estCorrect = false;
				}
				if ((ReleveController.existeSeuilHaut(currentPos) && (valeur > seuilHaut))) {
					estCorrect = false;
				}
				if (!estCorrect) {
					valhist.setStyle("-fx-background-color: #E6A83E; -fx-border-style: solid;");
				} else {
					valhist.setStyle("-fx-background-color: #9BEB7F; -fx-border-style: solid;");
				}
			}
			valhist.setPrefHeight(80);
			valhist.setPrefWidth(140);
			valhist.setWrapText(true);
			valhist.setFont(new Font(16.0));
			valhist.setId(String.valueOf(compteur));
			valhist.setAlignment(Pos.CENTER);
			valhist.setTextAlignment(TextAlignment.CENTER);
			Historique.getChildren().add(valhist);
		}
	}
}
