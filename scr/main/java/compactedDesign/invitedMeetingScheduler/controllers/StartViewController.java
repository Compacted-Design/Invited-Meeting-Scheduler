package compactedDesign.invitedMeetingScheduler.controllers;

import java.awt.Desktop;
import java.io.File;
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
	private Button guideButton, dataEntryButton, rotationButton, scheduleButton, informationSheetButton, mapButton;
	@FXML
	private Pane root;
	
	
	/**
	 * Action which occurs upon clicking the guideButton. 
	 * Changes the root node of the scene to the GuideView.
	 * @throws IOException
	 */
	@FXML
	private void guideButtonClick() throws IOException {
		//TODO: Remove GuideView and GuideControl
		//root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/GuideView.fxml")));
		Desktop.getDesktop().open(new File("Guide.docx"));
	}
	/**
	 * Action which occurs upon clicking the dataEntryButton
	 * @throws IOException 
	 */
	@FXML
	private void dataEntryButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/DataEntryView.fxml")));
	}
	
	@FXML
	private void rotationButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/RotationView.fxml")));
	}
	
	@FXML
	private void scheduleButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/ScheduleView.fxml")));
	}
	
	@FXML
	private void informationSheetButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/InformationSheetView.fxml")));
	}
	@FXML
	private void mapButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/MapView.fxml")));
	}

}
