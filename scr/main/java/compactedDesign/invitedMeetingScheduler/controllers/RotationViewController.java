package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
		//TODO: Add a confirmation Message
		String rotationNames = "gen:" + genTextBox.getText() + "\n" + 
							   "glo:" + gloTextBox.getText() + "\n" +
							   "hum:" + humTextBox.getText() + "\n" +
							   "smc:" + smcTextBox.getText();
		File fout = new File("scr/main/resources/data/RotationNames.txt");
		FileOutputStream fos = new FileOutputStream(fout); 	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(rotationNames);
		bw.close();
		IMSLauncher.getDl().setGenString(genTextBox.getText());
		IMSLauncher.getDl().setGloString(gloTextBox.getText());
		IMSLauncher.getDl().setHumString(humTextBox.getText());
		IMSLauncher.getDl().setSmcString(smcTextBox.getText());
	}

}
