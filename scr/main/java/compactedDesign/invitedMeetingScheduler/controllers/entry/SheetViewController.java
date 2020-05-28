package compactedDesign.invitedMeetingScheduler.controllers.entry;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.Pane;

public class SheetViewController {
	
	@FXML
	private Pane root;
	@FXML
	private TextField urlTextBox;
	
	@FXML
	private void handleDragOver(DragEvent event) {
		Dragboard db = event.getDragboard();
		if(db.hasFiles()) {
			
		}
	}

}
