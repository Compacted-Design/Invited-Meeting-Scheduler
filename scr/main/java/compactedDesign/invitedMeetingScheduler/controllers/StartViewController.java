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
	
	public Button startButton, guideButton;
	public BorderPane root;
	
	public void startClick() {
		System.out.println("hey");
	}
	
	public void guideButtonClick() throws IOException {
		IMSLauncher.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/GuideView.fxml")));//change if a better method is found
		System.out.println("hi");
	}

}
