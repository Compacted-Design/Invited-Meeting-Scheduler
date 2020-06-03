package compactedDesign.invitedMeetingScheduler.controllers.entry;

import java.io.FileNotFoundException;
import java.io.IOException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
public class ManualViewController {
	
	@FXML
	private Pane root;
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private TextField idEntry, firstNameEntry, lastNameEntry, middleSchoolEntry;
	
	@FXML
	private CheckBox smcsCheck, globalCheck, humanitiesCheck;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private Label noticeLabel;
	
	@FXML
	private void submitButtonClick() throws FileNotFoundException, IOException {
		//TODO: Move this to DataLoader
		noticeLabel.setText("");
		boolean invalidEntry = false;
		try {
			Integer.parseInt(idEntry.getText());
			if(idEntry.getText().trim().length() != 6) {
				noticeLabel.setText("ID should be 6 digits ");
				invalidEntry = true;
			}
		} catch (Exception e) {
			noticeLabel.setText("ID should be 6 digits");
			invalidEntry = true;
		}
		if(firstNameEntry.getText().trim().equals("") || lastNameEntry.getText().trim().equals("") || middleSchoolEntry.getText().trim().equals("")) {
			noticeLabel.setText(noticeLabel.getText() + "\n"+"Missing required information");
			invalidEntry = true;
		}
		if(invalidEntry) {
			return;
		}
		IMSLauncher.getDl().loadDataInput(Integer.parseInt(idEntry.getText().trim()), firstNameEntry.getText().trim(), lastNameEntry.getText().trim(), middleSchoolEntry.getText().trim(), smcsCheck.isSelected(), globalCheck.isSelected(), humanitiesCheck.isSelected());
		Node dataInputNode = FXMLLoader.load(getClass().getResource("/views/entryViews/DataInputedView.fxml"));
		((GridPane)root.getParent()).add(dataInputNode, 0, 1);
		((GridPane)root.getParent()).getChildren().remove(root);//TODO: Add data entry successful screen
		
	}
	


}
