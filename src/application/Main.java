package application;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url=getClass().getResource("ReversiScene.fxml");
			AnchorPane pane = FXMLLoader.load(url);
			//BorderPane root = new BorderPane();
			Scene scene = new Scene(pane);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
