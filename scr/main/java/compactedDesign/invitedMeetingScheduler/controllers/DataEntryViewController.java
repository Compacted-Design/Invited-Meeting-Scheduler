package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DataEntryViewController {
	
	@FXML
	private Pane root,dataEntryPane;
	@FXML
	private GridPane dataEntryContainerPane;
	@FXML
	private Button backButton, manualButton, spreadsheetButton;
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	
	@FXML
	private void manualButtonClick() throws IOException {
		//dataEntryPane = FXMLLoader.load(getClass().getResource("/views/entryViews/ManualView.fxml"));
		dataEntryContainerPane.getChildren().remove(dataEntryPane);
		dataEntryContainerPane.add(FXMLLoader.load(getClass().getResource("/views/entryViews/ManualView.fxml")), 0, 1);
	}
	
	@FXML
	private void spreadsheetButtonClick() {
		
	}

}
