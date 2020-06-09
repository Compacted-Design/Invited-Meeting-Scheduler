package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DataEntryViewController {
	
	@FXML
	private Pane root,dataEntryPane;
	@FXML
	private GridPane dataEntryContainerPane;
	@FXML
	private Button backButton, manualButton, spreadsheetButton, clearButton;
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	@FXML
	private void manualButtonClick() throws IOException {
		//dataEntryPane = FXMLLoader.load(getClass().getResource("/views/entryViews/ManualView.fxml"));
		dataEntryContainerPane.getChildren().remove(dataEntryPane);
		dataEntryPane = FXMLLoader.load(getClass().getResource("/views/entryViews/ManualView.fxml"));
		dataEntryContainerPane.add(dataEntryPane, 0, 1);
	}
	/**
	 * 
	 * @throws IOException
	 */
	@FXML
	private void spreadsheetButtonClick() throws IOException {
		dataEntryContainerPane.getChildren().remove(dataEntryPane);
		dataEntryPane = FXMLLoader.load(getClass().getResource("/views/entryViews/SheetView.fxml"));
		dataEntryContainerPane.add(dataEntryPane, 0, 1);
	}
	
	@FXML
	private void clearButtonClick() throws IOException {
		Stage popUp = new Stage();
		popUp.setTitle("Clear?");
		popUp.setResizable(false);
		popUp.getIcons().add(new Image(getClass().getResourceAsStream("/img/IMSIcon.png")));
		Pane popUpRoot = FXMLLoader.load(getClass().getResource("/views/popupViews/ClearDataPopUpView.fxml"));
		Scene popUpScene = new Scene(popUpRoot);
		popUp.setScene(popUpScene);
		popUp.show();
	}

}
