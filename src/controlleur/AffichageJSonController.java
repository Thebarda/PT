package controlleur;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Station;

public class AffichageJSonController {
	@FXML
	TableView<Station> station;
	
	@FXML
	TableColumn<Station, String> nom;
	
	@FXML
	TableColumn<Station, String> instru;
	
	ObservableList<Station> stations;
	
	public void initialize(){
		nom.setCellValueFactory(new PropertyValueFactory<Station, String>("nom"));
		instru.setCellValueFactory(new PropertyValueFactory<Station, String>("Instruction courte"));
	}
}
