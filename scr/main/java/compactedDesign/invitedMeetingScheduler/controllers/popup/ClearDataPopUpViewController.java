package compactedDesign.invitedMeetingScheduler.controllers.popup;

import java.io.IOException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClearDataPopUpViewController {
	
	@FXML
	private Button yesButton, noButton;
	
	@FXML
	private Pane root;
	
	@FXML
	private void yesButtonClick() throws IOException {
		IMSLauncher.getDl().clearData();
		((Stage)root.getScene().getWindow()).close();
	}
	
	@FXML
	private void noButtonClick() {
		((Stage)root.getScene().getWindow()).close();
	}

}
