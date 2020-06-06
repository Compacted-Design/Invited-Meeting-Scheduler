package compactedDesign.invitedMeetingScheduler.controllers.popup;

import java.util.List;

import compactedDesign.invitedMeetingScheduler.data.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StudentListPopUpViewController {
	
	@FXML
	private GridPane studentGrid;
	
	@FXML
	private Label titleLabel;
	
	public void setStudents(List<Student> students, String title) {
		titleLabel.setText(title);
		int counter = 1;
		for(Student student : students) {
			Label counterLabel = new Label(counter+"");
			studentGrid.add(counterLabel, 0, counter);
			Label idLabel = new Label(student.getId()+"");
			studentGrid.add(idLabel, 1, counter);
			Label nameLabel = new Label(student.getFirstName()+ " " + student.getLastName());
			studentGrid.add(nameLabel, 2, counter);
			Label schoolLabel = new Label(student.getSchoolName());
			studentGrid.add(schoolLabel,3,counter);
			for(int i = 0; i < student.getRots().length; i++) {
				if(student.getRots()[i].equals("n/a")) {
					break;
				}
				Label rotationLabel = new Label(student.getRots()[i]);
				studentGrid.add(rotationLabel,4+i,counter);
			}
			counter++;
		}
	}

}
