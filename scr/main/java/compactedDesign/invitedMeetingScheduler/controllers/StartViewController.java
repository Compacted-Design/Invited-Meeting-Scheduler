package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * <h1>StartViewController</h1>
 * <p>The controller of StartView</p>
 * @author Jeffrey Jiang
 * @version 0.1
 * @since 2020-05-08
 */
public class StartViewController {
	
	@FXML
	private Button startButton, guideButton, dataEntryButton;
	@FXML
	private Pane root;
	
	/**
	 * Action which occurs upon clicking the startButton
	 */
	@FXML
	private void startButtonClick() {
		System.out.println("hey");
	}
	
	/**
	 * Action which occurs upon clicking the guideButton. 
	 * Changes the root node of the scene to the GuideView.
	 * @throws IOException
	 */
	@FXML
	private void guideButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/GuideView.fxml")));
	}
	/**
	 * Action which occurs upon clicking the dataEntryButton
	 * @throws IOException 
	 */
	@FXML
	private void dataEntryButtonClick() throws IOException {
		Stage dataEntryChoiceStage = new Stage();
		dataEntryChoiceStage.setTitle("Invited Meeting Scheduler"); //maybe change later
		dataEntryChoiceStage.setResizable(false); // maybe change later
		Parent decsRoot = FXMLLoader.load(getClass().getResource("/views/GuideView.fxml"));
		Scene decsScene = new Scene(decsRoot,200,200);
		dataEntryChoiceStage.setScene(decsScene);
		dataEntryChoiceStage.show();
		
	}

}
