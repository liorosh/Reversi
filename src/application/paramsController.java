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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
public class paramsController {

	ObservableList<String> difficultyList = FXCollections.observableArrayList(
			"2", "4", "6", "8");

	ObservableList<String> AIfunctionList = FXCollections.observableArrayList(
			"MiniMax", "Alpha-Beta");

	ObservableList<String> HeuristicList = FXCollections.observableArrayList(
			"Maximum Disks","Corners", "Mobility");

    @FXML
    private ChoiceBox<String> AIfunctionP2;

    @FXML
    private ChoiceBox<String> AIfunctionP1;

    @FXML
    private ToggleGroup SelectPlayer;

    @FXML
    private Pane player1;

    @FXML
    private Button Start;

    @FXML
    private Pane player2;
    @FXML
    private RadioButton PlayWPlayer;

    @FXML
    private ChoiceBox<String> Difficulty;

    @FXML
    private ChoiceBox<String> Heuristic2;

    @FXML
    private RadioButton computerWcomputer;

    @FXML
    private ChoiceBox<String> Heuristic1;

    @FXML
    private RadioButton PlayWComputer;

	@FXML
	private void initialize() {

		Difficulty.setItems(difficultyList);
		Difficulty.setValue("2");
		AIfunctionP1.setItems(AIfunctionList);
		AIfunctionP1.setValue("MiniMax");
		AIfunctionP2.setItems(AIfunctionList);
		AIfunctionP2.setValue("MiniMax");
		Heuristic1.setItems(HeuristicList);
		Heuristic2.setItems(HeuristicList);
		Heuristic1.setValue("Maximum Disks");
		Heuristic2.setValue("Maximum Disks");
	}
	 @FXML
	    void onclick(ActionEvent event) {
		 String playerOrComputer=SelectPlayer.getSelectedToggle()
					.toString()
					.substring(
							SelectPlayer.getSelectedToggle().toString()
									.lastIndexOf("id=") + 3).split(",")[0];
			switch(playerOrComputer){
			case "PlayWComputer":
			 /*Heuristic1.setDisable(true);
			 Heuristic2.setDisable(false);
			 AIfunctionP1.setDisable(true);*/
				this.player2.setDisable(false);
				this.player1.setDisable(true);
				this.Start.setDisable(false);
				this.Difficulty.setDisable(false);
			 break;
			case "PlayWPlayer":
				/*Heuristic1.setDisable(true);
				Heuristic2.setDisable(true);
				AIfunctionP1.setDisable(true);*/
				this.player1.setDisable(true);
				this.player2.setDisable(true);
				this.Start.setDisable(false);
				this.Difficulty.setDisable(true);
				break;
			case "computerWcomputer":
				/*Heuristic1.setDisable(false);
				Heuristic2.setDisable(false);
				AIfunction.setDisable(false);*/
				this.player1.setDisable(false);
				this.player2.setDisable(false);
				this.Start.setDisable(false);
				this.Difficulty.setDisable(false);
				break;

			}
	    }

	@FXML
	void handleStart(ActionEvent event) throws IOException {
		String playerOrComputer = null;
		int nplayerOrComputer = 0;
		String DifficultyChoice = Difficulty.getValue().toString();
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
		reversicontroller.getAiFunction(AIfunctionP1.getValue());
		reversicontroller.getAiFunction2(AIfunctionP2.getValue());
		reversicontroller.GetHeuristic1(Heuristic1.getValue());
		reversicontroller.GetHeuristic2(Heuristic2.getValue());
		reversicontroller.initializeBoard();
		Scene board = new Scene(home_page_parent);
		Stage board_stage = (Stage) ((Node) event.getSource()).getScene()
				.getWindow();
		board_stage.setScene(board);
		board_stage.show();

	}

}
