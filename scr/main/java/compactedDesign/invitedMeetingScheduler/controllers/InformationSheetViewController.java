package compactedDesign.invitedMeetingScheduler.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.zxing.WriterException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import compactedDesign.invitedMeetingScheduler.data.DataLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InformationSheetViewController {
	
	@FXML
	private Button backButton, confirmButton;
	
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
		clubImageView.setImage(IMSLauncher.getDl().getClubImage());
		
		busCapArea.setText(IMSLauncher.getDl().getBusrCap());
		busLinkField.setText(IMSLauncher.getDl().getBusrLink());
		busImageView.setImage(IMSLauncher.getDl().getBusImage());
		
		webCapArea.setText(IMSLauncher.getDl().getPhswCap());
		webLinkField.setText(IMSLauncher.getDl().getPhswLink());
		webImageView.setImage(IMSLauncher.getDl().getWebImage());
		
		sportCapArea.setText(IMSLauncher.getDl().getSporCap());
		sportLinkField.setText(IMSLauncher.getDl().getSporLink());
		sportImageView.setImage(IMSLauncher.getDl().getSportImage());
		
		comCapArea.setText(IMSLauncher.getDl().getComnCap());
		comLinkField.setText(IMSLauncher.getDl().getComnLink());
		comImageView.setImage(IMSLauncher.getDl().getComImage());
		
		colCapArea.setText(IMSLauncher.getDl().getColgCap());
		colLinkField.setText(IMSLauncher.getDl().getColgLink());
		colImageView.setImage(IMSLauncher.getDl().getColImage());
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	
	@FXML
	private void confirmButtonClick() throws WriterException, IOException {
		
	}

}
