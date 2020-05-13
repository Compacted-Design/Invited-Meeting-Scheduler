package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
/**
 * <h1>StartViewController</h1>
 * <p>The controller of StartView</p>
 * @author Jeffrey Jiang
 * @version 0.1
 * @since 2020-05-08
 */
public class StartViewController {
	
	@FXML
	private Button guideButton, dataEntryButton;
	@FXML
	private Pane root;
	
	
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
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/DataEntryView.fxml")));
	}

}
