package compactedDesign.invitedMeetingScheduler.controllers.entry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class ManualViewController {
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private TextField idEntry, firstNameEntry, lastNameEntry, middleSchoolEntry;
	
	@FXML
	private CheckBox smcsCheck, globalCheck, humanitiesCheck;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private void submitButtonClick() {
		
	}

}
