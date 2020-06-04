package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.zxing.WriterException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InformationSheetViewController {
	
	@FXML
	private Button confirmButton, backButton;
	
	@FXML
	private Pane root;
	
	@FXML
	private TextField clubLinkField, busLinkField, webLinkField, sportLinkField, comLinkField, colLinkField;
	
	@FXML
	private TextArea clubCapArea, busCapArea, webCapArea, sportCapArea, comCapArea, colCapArea;
	
	@FXML
	private ImageView clubImageView, busImageView, webImageView, sportImageView, comImageView, colImageView;
	
	@FXML
	private GridPane informationGrid;
	
	
	
	@FXML
	private void initialize() throws FileNotFoundException {
		clubCapArea.setText(IMSLauncher.getDl().getClubCap());
		clubLinkField.setText(IMSLauncher.getDl().getClubLink());
		
		busCapArea.setText(IMSLauncher.getDl().getBusrCap());
		busLinkField.setText(IMSLauncher.getDl().getBusrLink());
		
		webCapArea.setText(IMSLauncher.getDl().getPhswCap());
		webLinkField.setText(IMSLauncher.getDl().getPhswLink());
		
		sportCapArea.setText(IMSLauncher.getDl().getSporCap());
		sportLinkField.setText(IMSLauncher.getDl().getSporLink());
		
		comCapArea.setText(IMSLauncher.getDl().getComnCap());
		comLinkField.setText(IMSLauncher.getDl().getComnLink());
		
		colCapArea.setText(IMSLauncher.getDl().getColgCap());
		colLinkField.setText(IMSLauncher.getDl().getColgLink());
		
		setImages();
	}
	
	
	@FXML
	private void confirmButtonClick() throws WriterException, IOException {
		IMSLauncher.getDl().setInformation(clubCapArea.getText(), comCapArea.getText(), webCapArea.getText(), sportCapArea.getText(), busCapArea.getText(), colCapArea.getText(), 
										   clubLinkField.getText(), comLinkField.getText(), webLinkField.getText(), sportLinkField.getText(), busLinkField.getText(), colLinkField.getText());
		setImages();
	}
	
	private void setImages() throws FileNotFoundException {
		clubImageView.setImage(IMSLauncher.getDl().getClubImage());
		busImageView.setImage(IMSLauncher.getDl().getBusImage());
		webImageView.setImage(IMSLauncher.getDl().getWebImage());
		sportImageView.setImage(IMSLauncher.getDl().getSportImage());
		comImageView.setImage(IMSLauncher.getDl().getComImage());
		colImageView.setImage(IMSLauncher.getDl().getColImage());
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}

}
