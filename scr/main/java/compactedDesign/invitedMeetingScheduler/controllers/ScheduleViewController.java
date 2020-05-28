package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ScheduleViewController {
	
	@FXML
	private Button backButton, createGroupsButton, createScheduleDocumentsButton;
	@FXML
	private Pane root;
	
	@FXML
	private void initialize() {
		//Thread thread = new Thread(new ViewThread());
		
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	
	@FXML
	private void createGroupsButtonClick() throws IOException, InvalidFormatException {
		IMSLauncher.getDl().groupSchedules();
	}
	
	@FXML
	private void createScheduleDocumentsButtonClick() throws InvalidFormatException, IOException {
		IMSLauncher.getDl().createSchedules();
	}
	
	/*private class ViewThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}*/

}
