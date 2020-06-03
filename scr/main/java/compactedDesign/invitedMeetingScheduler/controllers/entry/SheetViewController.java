package compactedDesign.invitedMeetingScheduler.controllers.entry;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SheetViewController {
	
	@FXML
	private Pane root;
	@FXML
	private Button selectFileButton;
	
	private boolean fileChooserOpen = false;
	
	
	@FXML
	private void handleDragOver(DragEvent event) {
		if(fileChooserOpen) {
			return;
		}
		Dragboard db = event.getDragboard();
		if(db.hasFiles()) {
			event.acceptTransferModes(TransferMode.COPY);
		}
		event.consume();
	}
	
	@FXML
	private void handleDragDropped(DragEvent event) throws InvalidFormatException, IOException {
		if(fileChooserOpen) {
			return;
		}
		Dragboard db = event.getDragboard();
		boolean hasExcel = false;
		if (db.hasFiles()) {
			for(File file : db.getFiles()) {
				if(file.getAbsolutePath().substring(file.getAbsolutePath().length()-5).equals(".xlsx")) {
					IMSLauncher.getDl().loadDataInput(file);
					hasExcel = true;
				}
			}
		}
		event.consume();
		if(hasExcel) {
			dataInputed();
		}
	}
	
	@FXML
	private void selectFileButtonClick() throws InvalidFormatException, IOException, InterruptedException {
		fileChooserOpen = true;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Excel File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Files", "*.xlsx"));
		File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
		if(selectedFile != null) {
			IMSLauncher.getDl().loadDataInput(selectedFile);
			fileChooserOpen = false;
			dataInputed();
		}else {
			fileChooserOpen = false;
		}
	}
	
	private void dataInputed() throws IOException {
		Node dataInputNode = FXMLLoader.load(getClass().getResource("/views/entryViews/DataInputedView.fxml"));
		((GridPane)root.getParent()).add(dataInputNode, 0, 1);
		((Pane)root.getParent()).getChildren().remove(root);
	}

}
