package compactedDesign.invitedMeetingScheduler.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
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
	
	private static final String GUIDE_PATH = "Invited Meeting Scheduler User Manual.docx", GUIDE_BACKUP_PATH = "/backups/Invited Meeting Scheduler User Manual Backup.docx";
	
	@FXML
	private void initialize() throws IOException {
		IMSLauncher.getDl().openMissingFilesPopup();
	}
	/**
	 * Action which occurs upon clicking the guideButton. 
	 * Opens the User Manual
	 * @throws IOException
	 */
	@FXML
	private void guideButtonClick() throws IOException {
		//TODO: Remove GuideView and GuideControl
		//root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/GuideView.fxml")));
		try {
			Desktop.getDesktop().open(new File(GUIDE_PATH));
		}catch (Exception e) {
			InputStream backup = getClass().getResourceAsStream(GUIDE_BACKUP_PATH);
			File source = new File(GUIDE_PATH);
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			Desktop.getDesktop().open(new File(GUIDE_PATH));
		}
	}
	/**
	 * Action which occurs upon clicking the dataEntryButton
	 * Opens the data entry window
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
