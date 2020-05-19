package compactedDesign.invitedMeetingScheduler.controllers.entry;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ManualViewController {
	
	@FXML
	private Pane root;
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private TextField idEntry, firstNameEntry, lastNameEntry, middleSchoolEntry;
	
	@FXML
	private CheckBox smcsCheck, globalCheck, humanitiesCheck;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private Label noticeLabel;
	
	@SuppressWarnings("resource")
	@FXML
	private void submitButtonClick() throws FileNotFoundException, IOException {
		try {
			Integer.parseInt(idEntry.getText());
		} catch (Exception e) {
			noticeLabel.setText("ID should be 6 digits");
			return;
		}
		if(idEntry.getText().trim().length() != 6) {
			noticeLabel.setText("ID should be 6 digits");
			return;
		}
		FileInputStream in = new FileInputStream(new File("scr/main/resources/data/Data.xlsx"));
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("RawData");
		int i = 1;
		boolean added = false;
		for(; s.getRow(i) != null; i++) { //TODO: Change the cell values to equal what the cell titles are
			if((int)s.getRow(i).getCell(0).getNumericCellValue() == Integer.parseInt(idEntry.getText().trim())) {
				dataEntry(i, s);
				added = true;
				break;
			}else if((int)s.getRow(i).getCell(0).getNumericCellValue() > Integer.parseInt(idEntry.getText().trim())) {
				break;
			}
		}
		if(!added) {
			s.createRow(i);
			for(int j = 0; j < 7; j++) {
				s.getRow(i).createCell(j);
			}
			s.getRow(i).getCell(0).setCellValue(Integer.parseInt(idEntry.getText().trim()));
			dataEntry(i, s);
		}
		in.close();
		FileOutputStream out = new FileOutputStream(new File("scr/main/resources/data/Data.xlsx"));
		wb.write(out);
		out.close();
		wb.close();
		
		((Pane)root.getParent()).getChildren().remove(root);//TODO: Add data entry successful screen
	}
	
	private void dataEntry(int i, Sheet s) {
		s.getRow(i).getCell(1).setCellValue(firstNameEntry.getText().trim());
		s.getRow(i).getCell(2).setCellValue(lastNameEntry.getText().trim());
		s.getRow(i).getCell(3).setCellValue(middleSchoolEntry.getText().trim());
		if(smcsCheck.isSelected()) {
			s.getRow(i).getCell(4).setCellValue("Y");
		}else {
			s.getRow(i).getCell(4).setCellValue("N");
		}
		if(globalCheck.isSelected()) {
			s.getRow(i).getCell(5).setCellValue("Y");
		}else {
			s.getRow(i).getCell(5).setCellValue("N");
		}
		if(humanitiesCheck.isSelected()) {
			s.getRow(i).getCell(6).setCellValue("Y");
		}else {
			s.getRow(i).getCell(6).setCellValue("N");
		}
	}


}
