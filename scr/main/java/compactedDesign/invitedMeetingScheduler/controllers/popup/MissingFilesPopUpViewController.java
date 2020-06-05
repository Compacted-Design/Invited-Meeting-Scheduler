package compactedDesign.invitedMeetingScheduler.controllers.popup;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MissingFilesPopUpViewController {
	
	@FXML
	private Label missingFilesLabel;
	
	@FXML
	private void initialize() {
		missingFilesLabel.setText(IMSLauncher.getDl().getMissingFiles());
	}

}
