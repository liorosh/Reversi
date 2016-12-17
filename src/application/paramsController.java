package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class paramsController {

	ObservableList<String> difficultyList = FXCollections
			.observableArrayList("Easy", "Medium", "hard");

	@FXML
	private Button Start;

	@FXML
	private ChoiceBox<String> Difficulty;
	
	@FXML 
	private void initialize(){
		Difficulty.setValue("Easy");
		Difficulty.setItems(difficultyList);
		
		
	}

	@FXML
	void handleStart(ActionEvent event) throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource(
				"ReversiScene.fxml"));
		Scene board = new Scene(home_page_parent);
		Stage board_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
		board_stage.setScene(board);
		board_stage.show();

	}

}
