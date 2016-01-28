package vue;
	
import java.io.File;
import java.io.IOException;

import controlleur.JsonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


/**
 * Classe permettant de lancer l'application fixe 
 */
public class Main extends Application {
	public static AnchorPane page;
	public static Stage primaryStage;
	@Override
    public void start(Stage primaryStage) {
        File file;
        String fxml;
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Application EDF");
        
        try {
            file = new File(JsonController.def_fichier_tmp);
            System.out.println(file);
            if (!file.exists()){
                fxml="Importation.fxml";
            }
            else{
                fxml="ReprendreSaisie.fxml";
            }
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.getIcons().add(new Image("file:logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		JsonController.init();
		launch(args);
	}
}

