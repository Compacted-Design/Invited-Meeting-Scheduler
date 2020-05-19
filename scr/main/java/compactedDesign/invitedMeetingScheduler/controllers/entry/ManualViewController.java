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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class ManualViewController {
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private TextField idEntry, firstNameEntry, lastNameEntry, middleSchoolEntry;
	
	@FXML
	private CheckBox smcsCheck, globalCheck, humanitiesCheck;
	
	@FXML
	private Button submitButton;
	
	@SuppressWarnings("resource")
	@FXML
	private void submitButtonClick() throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(new File("scr/main/resources/data/Data.xlsx"));
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("TestData");
		int i = 1;
		
		for(; s.getRow(i) != null; i++) { //TODO: Change the cell values to equal what the cell titles are
			if((""+((int)s.getRow(i).getCell(0).getNumericCellValue())).trim().equals(idEntry.getText().trim())) {
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
				break;
			}
		}
		in.close();
		FileOutputStream out = new FileOutputStream(new File("scr/main/resources/data/RawData.xlsx"));
		wb.write(out);
		out.close();
		wb.close();
	}

}
