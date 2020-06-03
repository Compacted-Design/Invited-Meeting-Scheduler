package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InformationSheetViewController {
	
	@FXML
	private Button backButton;
	
	@FXML
	private Pane root;
	
	@FXML
	private GridPane informationGrid;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}

}
