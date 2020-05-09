package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * <h1>GuideViewController</h1>
 * <p>The controller of GuideView</p>
 * @author Jeffrey Jiang
 * @version 0.1
 * @since 2020-05-08
 */
public class GuideViewController {
	public Button backButton;
	
	/**
	 * Action which occurs upon clicking the backButton. 
	 * @throws IOException
	 */
	public void backButtonClick() throws IOException {
		IMSLauncher.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}

}
