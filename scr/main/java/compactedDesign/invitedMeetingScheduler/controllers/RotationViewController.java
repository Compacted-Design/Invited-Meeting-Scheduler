package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class RotationViewController {
	//TODO: add confirmation buttons for each rotation
	
	@FXML
	private TextArea genTextBox, gloTextBox, humTextBox, smcTextBox;
	
	@FXML
	private Pane root;
	
	@FXML
	private Button backButton, confirmButton;
	
	@FXML
	private void initialize() {
		genTextBox.setText(IMSLauncher.getDl().getGenString());
		gloTextBox.setText(IMSLauncher.getDl().getGloString());
		humTextBox.setText(IMSLauncher.getDl().getHumString());
		smcTextBox.setText(IMSLauncher.getDl().getSmcString());
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	@FXML
	private void confirmButtonClick() throws IOException {
		IMSLauncher.getDl().setRotationNames(genTextBox.getText(), gloTextBox.getText(), humTextBox.getText(), smcTextBox.getText());
	}

}
