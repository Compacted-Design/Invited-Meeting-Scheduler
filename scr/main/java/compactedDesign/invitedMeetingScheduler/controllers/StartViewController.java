package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
/**
 * <h1>StartViewController</h1>
 * <p>The controller of StartView</p>
 * @author Jeffrey Jiang
 * @version 0.1
 * @since 2020-05-08
 */
public class StartViewController {
	
	public Button startButton, guideButton, dataEntryButton;
	public BorderPane root;
	
	/**
	 * Action which occurs upon clicking the startButton
	 */
	public void startButtonClick() {
		System.out.println("hey");
	}
	
	/**
	 * Action which occurs upon clicking the guideButton. 
	 * Changes the root node of the scene to the GuideView.
	 * @throws IOException
	 */
	public void guideButtonClick() throws IOException {
		IMSLauncher.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/GuideView.fxml")));//change if a better method is found
	}
	
	public void dataEntryButtonClick() {
		
	}

}
