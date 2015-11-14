package vue;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPaneBuilder;


/**
 * Classe permettant de lancer l'application fixe
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Application EDF");
		
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("accueil.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
