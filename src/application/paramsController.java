package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class paramsController {

	ObservableList<String> difficultyList = FXCollections.observableArrayList(
			"Easy", "Medium", "hard");

	@FXML
	private Button Start;

	@FXML
	private ChoiceBox<String> Difficulty;

	@FXML
	private RadioButton PlayWComputer;

	@FXML
	private RadioButton PlayWPlayer;

	@FXML
	private RadioButton computerWcomputer;

	@FXML
	private ToggleGroup SelectPlayer;

	@FXML
	private void initialize() {
		Difficulty.setValue("Easy");
		Difficulty.setItems(difficultyList);
		PlayWComputer.setSelected(true);

	}

	@FXML
	void handleStart(ActionEvent event) throws IOException {
		String playerOrComputer = null;
		int nplayerOrComputer = 0;
		String DifficultyChoice = Difficulty.getValue();
		if (SelectPlayer.getSelectedToggle() != null) {
			playerOrComputer = SelectPlayer
					.getSelectedToggle()
					.toString()
					.substring(
							SelectPlayer.getSelectedToggle().toString()
									.lastIndexOf("id=") + 3).split(",")[0];
			if (playerOrComputer.equals("PlayWComputer"))
				nplayerOrComputer = 1;
			if (playerOrComputer.equals("PlayWPlayer"))
				nplayerOrComputer = 2;
			if (playerOrComputer.equals("computerWcomputer"))
				nplayerOrComputer = 3;
		}
		FXMLLoader loader = new FXMLLoader();
		Parent home_page_parent = loader.load(getClass().getResource(
				"ReversiScene.fxml").openStream());
		Reversicontroller reversicontroller = (Reversicontroller) loader
				.getController();
		reversicontroller.getDifficulty(DifficultyChoice);
		reversicontroller.getPlayerOrComputer(nplayerOrComputer);
		Scene board = new Scene(home_page_parent);
		Stage board_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
		board_stage.setScene(board);
		board_stage.show();

	}

}
