package compactedDesign.invitedMeetingScheduler.controllers.popup;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InvalidGroupsPopUpViewController {
	
	@FXML
	private Pane root;
	
	@FXML
	private Button yesButton, noButton;
	
	@FXML
	private void yesButtonClick() throws IOException{
		Stage popUp = new Stage();
		popUp.setTitle("Creating Schedules");
		popUp.setResizable(false);
		Pane popUpRoot = new Pane();
		Scene popUpScene = new Scene(popUpRoot, 400, 100);
		popUp.setScene(popUpScene);
		popUp.show();
		popUpRoot.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/popupViews/CreateSchedulesPopUpView.fxml")));
		((Stage)root.getScene().getWindow()).close();
	}
	
	@FXML
	private void noButtonClick() {
		((Stage)root.getScene().getWindow()).close();
	}

}
