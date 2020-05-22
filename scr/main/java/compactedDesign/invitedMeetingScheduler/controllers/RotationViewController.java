package compactedDesign.invitedMeetingScheduler.controllers;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RotationViewController {
	
	@FXML
	private TextArea genTextBox, gloTextBox, humTextBox, smcTextBox;
	
	@FXML
	private void initialize() {
		genTextBox.setText(IMSLauncher.getDl().getGenString());
		gloTextBox.setText(IMSLauncher.getDl().getGloString());
		humTextBox.setText(IMSLauncher.getDl().getHumString());
		smcTextBox.setText(IMSLauncher.getDl().getSmcString());
	}

}
