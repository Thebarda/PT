package controleur;

import java.io.IOException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modele.Centrale;
import modele.Tournee;
import vue.Main;
import javafx.scene.Scene;

/**
 * Controleur relatif à l'interface de gestion des stations
 */
public class AfficherDernereTournee {

	@FXML
	ComboBox<Centrale> listeCentrale;

	@FXML
	private TableView<Tournee> tabTournee;

	@FXML
	private TableColumn<Tournee, String> date;

	@FXML
	private TableColumn<Tournee, String> ID;

	@FXML
	private TableColumn<Tournee, String> Nom;

	@FXML
	private TableColumn<Tournee, Button> releve;

	ObservableList<Centrale> centrale = CentraleControler.loadCentrales();

	static Tournee tournee;
	
	@FXML
	public void initialize() {

		listeCentrale.setItems(centrale);

	}

	public void listerTournee() {

		ObservableList<Tournee> Tournee = TourneeController.loadTourneesTerminees(listeCentrale.getValue().getId());

		date.setCellValueFactory(new PropertyValueFactory<Tournee, String>("dateReleve"));
		ID.setCellValueFactory(new PropertyValueFactory<Tournee, String>("id"));
		Nom.setCellValueFactory(new PropertyValueFactory<Tournee, String>("nom"));

		releve.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tournee, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(TableColumn.CellDataFeatures<Tournee, Button> p) {
						EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								final Stage dialog = new Stage();
								dialog.initModality(Modality.APPLICATION_MODAL);
								FXMLLoader loader = new FXMLLoader(Main.class.getResource("releve.fxml"));
								AnchorPane page;
								try {
									tournee = p.getValue();
									page = (AnchorPane) loader.load();
									Scene dialogScene = new Scene(page);
									dialog.setScene(dialogScene);
									dialog.show();
									dialog.setOnHidden(new EventHandler<WindowEvent>() {
										public void handle(WindowEvent we) {
											dialog.close();
										}
									});
								} catch (IOException ioe) {
									ioe.printStackTrace();
								}

							}

						};
						Button button = new Button("Releve");
						button.setCenterShape(true);
						button.setMaxWidth(Double.MAX_VALUE);
						button.setOnAction(event);
						return new SimpleObjectProperty<Button>(button);

					}
				});

		tabTournee.setItems(Tournee);

	}

}
