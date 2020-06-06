package compactedDesign.invitedMeetingScheduler.controllers.entry;

import java.io.File;
import java.io.IOException;

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
			for(File file : db.getFiles()) {
				if(file.getAbsolutePath().substring(file.getAbsolutePath().length()-5).equals(".xlsx")) {
					event.acceptTransferModes(TransferMode.COPY);
					break;
				}
			}
		}
		event.consume();
	}
	
	@FXML
	private void handleDragDropped(DragEvent event) throws IOException {
		if(fileChooserOpen) {
			return;
		}
		Dragboard db = event.getDragboard();
		boolean hasExcel = false;
		if (db.hasFiles()) {
			for(File file : db.getFiles()) {
				if(file.getAbsolutePath().substring(file.getAbsolutePath().length()-5).equals(".xlsx")) {
					fileInput(file);
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
	private void selectFileButtonClick() throws IOException {
		fileChooserOpen = true;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Excel File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Files", "*.xlsx"));
		File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
		if(selectedFile != null) {
			fileInput(selectedFile);
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
	
	private void fileInput(File file) throws IOException {
		try {
			IMSLauncher.getDl().loadDataInput(file);
		} catch (Exception e) {
			Node dataInputNode = FXMLLoader.load(getClass().getResource("/views/entryViews/DataNotInputedView.fxml"));
			((GridPane)root.getParent()).add(dataInputNode, 0, 1);
			((Pane)root.getParent()).getChildren().remove(root);
		}
	}

}
