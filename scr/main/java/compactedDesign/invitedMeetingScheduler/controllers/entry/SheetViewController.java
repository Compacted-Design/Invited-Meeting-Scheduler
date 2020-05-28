package compactedDesign.invitedMeetingScheduler.controllers.entry;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
			event.acceptTransferModes(TransferMode.COPY);
		}
		event.consume();
	}
	
	@FXML
	private void handleDragDropped(DragEvent event) throws InvalidFormatException, IOException {
		Dragboard db = event.getDragboard();
		if (db.hasFiles()) {
			for(File file : db.getFiles()) {
				IMSLauncher.getDl().loadDataInput(file);
			}
		}
		event.consume();
		((Pane)root.getParent()).getChildren().remove(root);//TODO: Add data entry successful screen
	}

}
