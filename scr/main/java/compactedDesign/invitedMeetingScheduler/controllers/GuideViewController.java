package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * <h1>GuideViewController</h1>
 * <p>The controller of GuideView</p>
 * @author Jeffrey Jiang
 * @version 0.1
 * @since 2020-05-08
 */
public class GuideViewController {
	@FXML
	private Button backButton;
	@FXML
	private Pane root;
	
	/**
	 * Action which occurs upon clicking the backButton. 
	 * @throws IOException
	 */
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}

}
