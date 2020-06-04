package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MapViewController {
	
	@FXML
	Pane root;
	
	private boolean fileChooserOpen = false;
	
	@FXML
	private Button backButton, selectFileButton;
	
	@FXML
	private ImageView mapImageView;
	
	@FXML
	private void initialize() throws FileNotFoundException {
		mapImageView.setImage(IMSLauncher.getDl().getMapImage());
	}
	
	@FXML
	private void handleDragOver(DragEvent event) throws IOException {
		if(fileChooserOpen) {
			return;
		}
		Dragboard db = event.getDragboard();
		if(db.hasFiles()) {
			for(File file : db.getFiles()) {
				if(ImageIO.read(file) != null) {
					event.acceptTransferModes(TransferMode.COPY);
					break;
				}else {
					event.acceptTransferModes(TransferMode.NONE);
				}
			}
		}
		event.consume();
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	@FXML
	private void handleDragDropped(DragEvent event) throws InvalidFormatException, IOException {
		if(fileChooserOpen) {
			return;
		}
		Dragboard db = event.getDragboard();
		boolean hasImage = false;
		if (db.hasFiles()) {
			for(File file : db.getFiles()) {
				if(ImageIO.read(file) != null) {
					hasImage = true;
					IMSLauncher.getDl().setMap(file);
				}
			}
		}
		event.consume();
		if(hasImage) {
			mapImageView.setImage(IMSLauncher.getDl().getMapImage());
		}
	}
	
	@FXML
	private void selectFileButtonClick() throws InvalidFormatException, IOException, InterruptedException {
		fileChooserOpen = true;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
		if(selectedFile != null) {
			IMSLauncher.getDl().setMap(selectedFile);
			mapImageView.setImage(IMSLauncher.getDl().getMapImage());
			fileChooserOpen = false;
		}else {
			fileChooserOpen = false;
		}
	}
	
	

}
