package application;


import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

	Stage window;
	Scene parameters, board;
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		try {
			URL url=getClass().getResource("params.fxml");
			AnchorPane pane = FXMLLoader.load(url);
			//BorderPane root = new BorderPane();
			Scene parameters = new Scene(pane);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(parameters);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
