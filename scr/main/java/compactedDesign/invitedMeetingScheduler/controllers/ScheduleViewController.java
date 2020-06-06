package compactedDesign.invitedMeetingScheduler.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.zxing.WriterException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import compactedDesign.invitedMeetingScheduler.controllers.popup.StudentListPopUpViewController;
import compactedDesign.invitedMeetingScheduler.data.Student;
import compactedDesign.invitedMeetingScheduler.data.StudentIdComparator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ScheduleViewController {
	
	@FXML
	private Button backButton, createGroupsButton, createScheduleDocumentsButton, searchButton, scheduleDirectoryButton, confirmButton,
	rot1ButtonUp, rot2ButtonUp, rot3ButtonUp, rot4ButtonUp, rot1ButtonDown, rot2ButtonDown, rot3ButtonDown, rot4ButtonDown,
	openScheduleDirectoryButton;
	private Button[] rotButtonsUp = new Button[4], rotButtonsDown = new Button[4];
	@FXML
	private Pane root;
	@FXML
	private Label gen1Label, gen2Label, gen3Label, gen4Label, smc1Label, smc2Label, smc3Label, smc4Label, hum1Label, hum2Label, hum3Label, hum4Label, glo1Label, glo2Label, glo3Label, glo4Label, 
	searchErrorsLabel, scheduleDirectoryLabel, idLabel, firstNameLabel, lastNameLabel, schoolLabel;
	@FXML
	private GridPane studentGrid, rotationGrid;
	@FXML
	private TextField searchField;
	
	private Label[] genLabels = new Label[4], smcLabels = new Label[4], humLabels = new Label[4], gloLabels = new Label[4];
	
	private String valid = "-fx-background-color: lightgreen;", invalid = "-fx-background-color: red;";
	
	private Student target;
	
	@FXML
	private CheckBox idCheck;
	
	private boolean enterKeyPressed = false;
	
	@FXML
	private void initialize() {
		//Thread thread = new Thread(new ViewThread());
		genLabels[0] = gen1Label; genLabels[1] = gen2Label; genLabels[2] = gen3Label; genLabels[3] = gen4Label;
		smcLabels[0] = smc1Label; smcLabels[1] = smc2Label; smcLabels[2] = smc3Label; smcLabels[3] = smc4Label;
		humLabels[0] = hum1Label; humLabels[1] = hum2Label; humLabels[2] = hum3Label; humLabels[3] = hum4Label;
		gloLabels[0] = glo1Label; gloLabels[1] = glo2Label; gloLabels[2] = glo3Label; gloLabels[3] = glo4Label;
		scheduleDirectoryLabel.setText("Schedule Directory: " + IMSLauncher.getDl().getSchedulePath());
		
		rotButtonsUp[0] = rot1ButtonUp; rotButtonsUp[1] = rot2ButtonUp; rotButtonsUp[2] = rot3ButtonUp; rotButtonsUp[3] = rot4ButtonUp;
		rotButtonsDown[0] = rot1ButtonDown; rotButtonsDown[1] = rot2ButtonDown; rotButtonsDown[2] = rot3ButtonDown; rotButtonsDown[3] = rot4ButtonDown;
	}
	
	@FXML
	private void backButtonClick() throws IOException {
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
	}
	
	@FXML
	private void createGroupsButtonClick() throws IOException, InvalidFormatException {
		IMSLauncher.getDl().groupSchedules();
		setRotationLabels();
	}
	
	@FXML
	private void createScheduleDocumentsButtonClick() throws InvalidFormatException, IOException, WriterException {
		IMSLauncher.getDl().createSchedules();
	}
	
	/*private class ViewThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	@FXML
	private void searchFieldHandleKeyPress(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {
			enterKeyPressed = true;
		}
	}
	
	@FXML
	private void searchFieldHandleKeyRelease(KeyEvent event) throws IOException {
		if(event.getCode().equals(KeyCode.ENTER) && enterKeyPressed) {
			enterKeyPressed = false;
			searchButtonClick();
		}
		event.consume();
	}
	
	@FXML
	private void searchButtonClick() throws IOException {
		searchErrorsLabel.setText("");
		if(IMSLauncher.getDl().getStudents() == null) {
			Stage popUp = new Stage();
			popUp.setTitle("No Groups Avaliable");
			popUp.setResizable(false);
			Pane popUpRoot = FXMLLoader.load(getClass().getResource("/views/popupViews/SchedulePopUpView.fxml"));
			Scene popUpScene = new Scene(popUpRoot);
			popUp.setScene(popUpScene);
			popUp.show();
			return;
		}else if(searchField.getText().equals("")) {
			searchErrorsLabel.setText("Please Enter an ID or Name");
			return;
		}
		target = null;
		try {
			int value = Integer.parseInt(searchField.getText().trim());
			if(searchField.getText().trim().length() != 6) {
				searchErrorsLabel.setText("ID should be 6 digits");
				return;
			}
			for(Student student: IMSLauncher.getDl().getStudents()) {
				if(student.getId() == value) {
					target = student;
					break;
				}
			}
		} catch (Exception e) {
			String name1 = searchField.getText().trim(), name2 = null;
			if(searchField.getText().contains(" ") && searchField.getText().split(" ").length >= 2) {
				name1 = searchField.getText().split(" ")[0];
				name2 = searchField.getText().split(" ")[1];
				int counter = 2;
				while(name2 != null && name2.equals("")) {
					if(searchField.getText().split(" ").length > counter) {
						name2 =searchField.getText().split(" ")[counter];
						counter++;
					}else {
						name2 = null;
					}
				}
			}
			for(Student student: IMSLauncher.getDl().getStudents()) {
				if(name2 != null) {
					if((student.getFirstName().equals(name1) && student.getLastName().equals(name2)) || (student.getFirstName().equals(name2) && student.getLastName().equals(name1))) {
						target = student;
						break;
					}else if(target == null && ((student.getFirstName().contains(name1) && student.getLastName().contains(name2)) || (student.getFirstName().contains(name2) && student.getLastName().contains(name1)))) {
						target = student;
					}
				}else {
					if(student.getFirstName().equals(name1) || student.getLastName().equals(name1)) {
						target = student;
						break;
					}else if(target == null && (student.getFirstName().contains(name1) || student.getLastName().contains(name1))) {
						target = student;
					}
				}
			}
		}
		if(target == null) {
			searchErrorsLabel.setText("No Matches. If you have data for this person, please enter it into the system, then click the create groups button.");
			return;
		}
		for(int i = 0; i < rotButtonsUp.length; i++) {
			rotButtonsDown[i].setVisible(false);
			rotButtonsUp[i].setVisible(false);
			rotButtonsDown[i].setDisable(true);
			rotButtonsUp[i].setDisable(true);
		}
		idLabel.setText(target.getId()+"");
		firstNameLabel.setText(target.getFirstName());
		lastNameLabel.setText(target.getLastName());
		schoolLabel.setText(target.getSchoolName());
		for(int i = 0; i < target.getRots().length; i++) {
			if(target.getRots()[i].equals("n/a")) {
				rotButtonsUp[i].setText("n/a");
				rotButtonsDown[i].setText("n/a");
			}else {
				if(target.getRots()[i].equals("GE")) {
					rotButtonsUp[i].setText("General");
					rotButtonsDown[i].setText("General");
				}else if(target.getRots()[i].equals("GL")) {
					rotButtonsUp[i].setText("Global");
					rotButtonsDown[i].setText("Global");
				}else if(target.getRots()[i].equals("H")) {
					rotButtonsUp[i].setText("Humanities");
					rotButtonsDown[i].setText("Humanities");
				}else if(target.getRots()[i].equals("S")) {
					rotButtonsUp[i].setText("SMCS");
					rotButtonsDown[i].setText("SMCS");
				}
				rotButtonsUp[i].setVisible(true);
				rotButtonsUp[i].setDisable(false);
			}
		}
	}
	
	@FXML
	private void scheduleDirectoryButtonClick() throws IOException {
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Schedule Directory");
		File defaultDirectory = new File(IMSLauncher.getDl().getSchedulePath());
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(root.getScene().getWindow());
		if(selectedDirectory != null) {
			IMSLauncher.getDl().setSchedulePath(selectedDirectory.getAbsolutePath());
			scheduleDirectoryLabel.setText("Schedule Directory: "+ selectedDirectory.getAbsolutePath());
		}
	}
	
	@FXML
	private void confirmButtonClick() throws IOException {
		String rots[] = new String[4];
		for(int i = 0; i < rots.length; i++) {
			if(rotButtonsUp[i].getText().equals("General")) {
				rots[i] = "GE";
			}else if(rotButtonsUp[i].getText().equals("Global")) {
				rots[i] = "GL";
			}else if(rotButtonsUp[i].getText().equals("Humanities")) {
				rots[i] = "H";
			}else if(rotButtonsUp[i].getText().equals("SMCS")) {
				rots[i] = "S";
			}else {
				rots[i] = rotButtonsUp[i].getText();
			}
		}
		IMSLauncher.getDl().changeStudentSchedule(rots, target);
		setRotationLabels();
	}
	
	private void setRotationLabels() {
		for(int i = 0; i < 4; i++) {
			genLabels[i].setText(IMSLauncher.getDl().getGenGroups()[i].getStudents().size() + " students");
			if(IMSLauncher.getDl().getGenGroups()[i].getStudents().size() > 70 || (IMSLauncher.getDl().getGenGroups()[i].getStudents().size() < 10 && IMSLauncher.getDl().getGenGroups()[i].getStudents().size() > 0)) {
				genLabels[i].setStyle(invalid);
			}else {
				genLabels[i].setStyle(valid);
			}
			smcLabels[i].setText(IMSLauncher.getDl().getSmcsGroups()[i].getStudents().size() + " students");
			if(IMSLauncher.getDl().getSmcsGroups()[i].getStudents().size() > 50 || (IMSLauncher.getDl().getSmcsGroups()[i].getStudents().size() < 10 && IMSLauncher.getDl().getSmcsGroups()[i].getStudents().size() > 0)) {
				smcLabels[i].setStyle(invalid);
			}else {
				smcLabels[i].setStyle(valid);
			}
			humLabels[i].setText(IMSLauncher.getDl().getHumGroups()[i].getStudents().size() + " students");
			if(IMSLauncher.getDl().getHumGroups()[i].getStudents().size() > 50 || (IMSLauncher.getDl().getHumGroups()[i].getStudents().size() < 10 && IMSLauncher.getDl().getHumGroups()[i].getStudents().size() > 0)) {
				humLabels[i].setStyle(invalid);
			}else {
				humLabels[i].setStyle(valid);
			}
			gloLabels[i].setText(IMSLauncher.getDl().getGloGroups()[i].getStudents().size() + " students");
			if(IMSLauncher.getDl().getGloGroups()[i].getStudents().size() > 50 || (IMSLauncher.getDl().getGloGroups()[i].getStudents().size() < 10 && IMSLauncher.getDl().getGloGroups()[i].getStudents().size() > 0)) {
				gloLabels[i].setStyle(invalid);
			}else {
				gloLabels[i].setStyle(valid);
			}
		}
	}
	
	@FXML
	private void rot4ButtonDownClick() {
		switchUpAndDown(3, false);
	}
	
	@FXML
	private void rot3ButtonDownClick() { 
		switchUpAndDown(2, false);
	}
	
	@FXML
	private void rot2ButtonDownClick() {
		switchUpAndDown(1, false);
	}
	
	@FXML
	private void rot1ButtonDownClick() {
		switchUpAndDown(0, false);
	}
	
	@FXML
	private void rot4ButtonUpClick() {
		int index;
		if((index = rotButtonDownDisable()) == -1) {
			switchUpAndDown(3, true);
		}else {
			swapLeftRight(3, index);
		}
	}
	
	@FXML
	private void rot3ButtonUpClick() {
		int index;
		if((index = rotButtonDownDisable()) == -1) {
			switchUpAndDown(2, true);
		}else {
			swapLeftRight(2, index);
		}
	}
	
	@FXML
	private void rot2ButtonUpClick() {
		int index;
		if((index = rotButtonDownDisable()) == -1) {
			switchUpAndDown(1, true);
		}else {
			swapLeftRight(1, index);
		}
	}
	
	@FXML
	private void rot1ButtonUpClick() {
		int index;
		if((index = rotButtonDownDisable()) == -1) {
			switchUpAndDown(0, true);
		}else {
			swapLeftRight(0, index);
		}
	}
	
	private int rotButtonDownDisable() {
		for(int i = 0; i < rotButtonsDown.length; i++) {
			if(rotButtonsDown[i].isVisible()) {
				return i;
			}
		}
		return -1;
	}
	private void switchUpAndDown(int index, boolean isUp) {
		rotButtonsUp[index].setDisable(isUp);
		rotButtonsUp[index].setVisible(!isUp);
		rotButtonsDown[index].setDisable(!isUp);
		rotButtonsDown[index].setVisible(isUp);
	}
	
	private void swapLeftRight(int indexVisable,int indexDisabled) {
		rotButtonsUp[indexDisabled].setText(rotButtonsUp[indexVisable].getText());
		rotButtonsUp[indexVisable].setText(rotButtonsDown[indexDisabled].getText());
		rotButtonsDown[indexDisabled].setText(rotButtonsUp[indexDisabled].getText());
		rotButtonsDown[indexVisable].setText(rotButtonsUp[indexVisable].getText());
		
		switchUpAndDown(indexDisabled, false);
	}
	
	@FXML
	private void openScheduleDirectoryButtonClick() throws IOException {
		Desktop.getDesktop().open(new File(IMSLauncher.getDl().getSchedulePath()));
	}
	
	private void openStudentList(List<Student> students, String title) throws IOException {
		if(idCheck.isSelected()) {
			Collections.sort(students, new StudentIdComparator());
		}else {
			Collections.sort(students);
		}
		Stage popUp = new Stage();
		popUp.setTitle(title);
		popUp.setResizable(false);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/popupViews/StudentListPopUpView.fxml"));
		Pane popUpRoot = (Pane)loader.load();
		StudentListPopUpViewController controller = loader.<StudentListPopUpViewController>getController();
		controller.setStudents(students, title);
		Scene popUpScene = new Scene(popUpRoot);
		popUp.setScene(popUpScene);
		popUp.show();
	}
	
	@FXML
	private void gen1Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGenGroups()[0].getStudents(), "General Rotation 1");
	}
	@FXML
	private void gen2Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGenGroups()[1].getStudents(), "General Rotation 2");
	}
	@FXML
	private void gen3Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGenGroups()[2].getStudents(), "General Rotation 3");
	}
	@FXML
	private void gen4Click() throws IOException {
		openStudentList(IMSLauncher.getDl().getGenGroups()[3].getStudents(), "General Rotation 4");
	}
	
	
	@FXML
	private void smc1Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getSmcsGroups()[0].getStudents(), "SMCS Rotation 1");
	}
	@FXML
	private void smc2Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getSmcsGroups()[1].getStudents(), "SMCS Rotation 2");
	}
	@FXML
	private void smc3Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getSmcsGroups()[2].getStudents(), "SMCS Rotation 3");
	}
	@FXML
	private void smc4Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getSmcsGroups()[3].getStudents(), "SMCS Rotation 4");
	}
	
	
	@FXML
	private void hum1Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getHumGroups()[0].getStudents(),"Humanities Rotation 1");
	}
	@FXML
	private void hum2Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getHumGroups()[1].getStudents(),"Humanities Rotation 2");
	}
	@FXML
	private void hum3Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getHumGroups()[2].getStudents(),"Humanities Rotation 3");
	}
	@FXML
	private void hum4Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getHumGroups()[3].getStudents(),"Humanities Rotation 4");
	}
	
	@FXML
	private void glo1Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGloGroups()[0].getStudents(),"Global Rotation 1");
	}
	@FXML
	private void glo2Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGloGroups()[1].getStudents(),"Global Rotation 2");
	}
	@FXML
	private void glo3Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGloGroups()[2].getStudents(),"Global Rotation 3");
	}
	@FXML
	private void glo4Click() throws IOException{
		openStudentList(IMSLauncher.getDl().getGloGroups()[3].getStudents(),"Global Rotation 4");
	}
	
	
}
