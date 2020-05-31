package compactedDesign.invitedMeetingScheduler.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DataLoader {
	//Decide whether it should be static or not
	private String genString;
	private String humString;
	private String gloString;
	private String smcString;
	private static final String STUDENT_DATA_PATH = "data/StudentData.xlsx";
	private static final String ROTATION_NAMES_PATH = "data/RotationNames.txt";
	private static final String TEMPLATE_SCHEDULE_PATH = "data/BlankSchedule.docx";
	private static final int GEN_LIMIT = 70;
	private static final int MAG_LIMIT = 50;
	private RotationGroup[] smcsGroups = new RotationGroup[4];
	private RotationGroup[] humGroups = new RotationGroup[4];
	private RotationGroup[] genGroups = new RotationGroup[4];
	private RotationGroup[] gloGroups = new RotationGroup[4];

	public DataLoader() throws IOException {
		FileReader rotationText = new FileReader(ROTATION_NAMES_PATH);
		BufferedReader br = new BufferedReader(rotationText);
		String line = "";
		while ((line = br.readLine()) != null) {
			if(line.substring(0, 4).equals("gen:")) {
				genString = line.substring(4);
			}else if(line.substring(0, 4).equals("hum:")) {
				humString = line.substring(4);
			}else if(line.substring(0, 4).equals("glo:")) {
				gloString = line.substring(4);
			}else if(line.substring(0, 4).equals("smc:")) {
				smcString = line.substring(4);
			}
		}
		br.close();
	}
	
	public void loadDataInput(int id, String first, String last, String school, boolean smcs, boolean global, boolean hum) throws IOException {
		FileInputStream in = new FileInputStream(new File(STUDENT_DATA_PATH));
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("RawData");
		int left = 1, right = s.getLastRowNum();
		int mid = (left+right)/2;
		boolean added = false;
		while(left <= right && !added) {
			mid = (left+right)/2;
			int currentID = (int) s.getRow(mid).getCell(0).getNumericCellValue();
			if(currentID == id) {
				if((!smcs && !global && !hum)) {
					if(mid == s.getLastRowNum()) {
						s.removeRow(s.getRow(mid));
					}else {
						s.shiftRows(mid+1,s.getLastRowNum()+1, -1);
					}
				}else {
					dataEntry(mid, s, first, last, school, smcs, global, hum);
				}
				added = true;
			}else if(currentID < id) {
				left = mid+1;

			}else {
				right = mid-1;
			}
		}
		if(!added && (smcs || global || hum)) {
			if((int) s.getRow(s.getLastRowNum()).getCell(0).getNumericCellValue() < id) {
				mid = s.getLastRowNum()+1;
			}else if((int) s.getRow(1).getCell(0).getNumericCellValue() > id) {
				mid = 1;
			}else {
				mid = left;
			}
			if(s.getRow(mid) != null) {
				s.shiftRows(mid, s.getLastRowNum()+1, 1);
			}
			s.createRow(mid);
			for(int j = 0; j < 7; j++) {
				s.getRow(mid).createCell(j);
			}
			s.getRow(mid).getCell(0).setCellValue(id);
			dataEntry(mid, s, first, last, school, smcs, global, hum);
		}
		in.close();
		FileOutputStream out = new FileOutputStream(new File(STUDENT_DATA_PATH));
		wb.write(out);
		out.close();
		wb.close();
	}
	
	public void loadDataInput(File inputSheetFile) throws IOException, InvalidFormatException {
		FileInputStream in = new FileInputStream(new File(STUDENT_DATA_PATH));
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("RawData");
		Workbook inputWB = new XSSFWorkbook(inputSheetFile);
		int sheetIndex = 0;
		while(inputWB.getSheetAt(sheetIndex) != null && inputWB.getSheetAt(0).getRow(0) != null) {
			Sheet inputSheet = inputWB.getSheetAt(sheetIndex);
			Row titleRow = inputSheet.getRow(0);
			int idCol = -1;
			int firstCol = -1;
			int lastCol = -1;
			int schoolCol = -1;
			int houseCol = -1;
			for(int i = 0; titleRow.getCell(i) != null; i++) {
				if(idCol == -1 && titleRow.getCell(i).getStringCellValue().contains("ID") && inputSheet.getRow(1).getCell(i).getCellType() == CellType.NUMERIC) {
					idCol = i;
				}else if(firstCol == -1 && titleRow.getCell(i).getStringCellValue().toLowerCase().contains("first")) {
					firstCol = i;
				}else if(lastCol == -1 && titleRow.getCell(i).getStringCellValue().toLowerCase().contains("last")) {
					lastCol = i;
				}else if(schoolCol == -1 && titleRow.getCell(i).getStringCellValue().toLowerCase().contains("school")) {
					schoolCol = i;
				}else if(houseCol == -1 && titleRow.getCell(i).getStringCellValue().toLowerCase().contains("house")){
					houseCol = i;
				}else if(houseCol != -1 && !titleRow.getCell(i).getStringCellValue().toLowerCase().contains("inv")) {
					houseCol = i;
				}
			}
			if(idCol != -1 && firstCol != -1 && lastCol != -1 && schoolCol != -1 && houseCol != -1) {
				for(int rowIndex = 1; inputSheet.getRow(rowIndex) != null; rowIndex++) {
					Row row = inputSheet.getRow(rowIndex);
					String first = row.getCell(firstCol).getStringCellValue().trim();
					String last = row.getCell(lastCol).getStringCellValue().trim();
					String school = row.getCell(schoolCol).getStringCellValue().trim();
					boolean smcs = row.getCell(houseCol).getStringCellValue().toLowerCase().contains("smcs");
					boolean global = row.getCell(houseCol).getStringCellValue().toLowerCase().contains("glo");
					boolean hum = row.getCell(houseCol).getStringCellValue().toLowerCase().contains("hum");
					if(first.equals("") || last.equals("")) {
						continue;
					}
					int id = (int) row.getCell(idCol).getNumericCellValue();
					if(id < 100000 || id > 999999) {
						continue;
					}
					int left = 1, right = s.getLastRowNum();
					int mid = (left+right)/2;
					boolean added = false;
					while(left <= right && !added) {
						mid = (left+right)/2;
						int currentID = (int) s.getRow(mid).getCell(0).getNumericCellValue();
						if(currentID == id) {
							if((!smcs && !global && !hum)) {
								if(mid == s.getLastRowNum()) {
									s.removeRow(s.getRow(mid));
								}else {
									s.shiftRows(mid+1,s.getLastRowNum()+1, -1);
								}
							}else {
								dataEntry(mid, s, first, last, school, smcs, global, hum);
							}
							added = true;
						}else if(currentID < id) {
							left = mid+1;

						}else {
							right = mid-1;
						}
					}
					if(!added && (smcs || global || hum)) {
						if((int) s.getRow(s.getLastRowNum()).getCell(0).getNumericCellValue() < id) {
							mid = s.getLastRowNum()+1;
						}else if((int) s.getRow(1).getCell(0).getNumericCellValue() > id) {
							mid = 1;
						}else {
							mid = left;
						}
						if(s.getRow(mid) != null) {
							s.shiftRows(mid, s.getLastRowNum()+1, 1);
						}
						s.createRow(mid);
						for(int j = 0; j < 7; j++) {
							s.getRow(mid).createCell(j);
						}
						s.getRow(mid).getCell(0).setCellValue(id);
						dataEntry(mid, s, first, last, school, smcs, global, hum);
					}
				}
			}
			sheetIndex++;
		}
		inputWB.close();
		in.close();
		FileOutputStream out = new FileOutputStream(new File(STUDENT_DATA_PATH));
		wb.write(out);
		out.close();
		wb.close();
	}
	
	private void dataEntry(int i, Sheet s, String first, String last, String school, boolean smcs, boolean global, boolean hum) {
		s.getRow(i).getCell(1).setCellValue(first);
		s.getRow(i).getCell(2).setCellValue(last);
		s.getRow(i).getCell(3).setCellValue(school);
		if(smcs) {
			s.getRow(i).getCell(4).setCellValue("Y");
		}else {
			s.getRow(i).getCell(4).setCellValue("N");
		}
		if(global) {
			s.getRow(i).getCell(5).setCellValue("Y");
		}else {
			s.getRow(i).getCell(5).setCellValue("N");
		}
		if(hum) {
			s.getRow(i).getCell(6).setCellValue("Y");
		}else {
			s.getRow(i).getCell(6).setCellValue("N");
		}
	}
	
	//TODO: Compress redundant series of code
	public void groupSchedules() throws IOException, InvalidFormatException {
		FileInputStream in = new FileInputStream(new File(STUDENT_DATA_PATH));
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("RawData");
		int i = 1;
		//TODO: Combine into 1 for loop
		List<Student> students = new ArrayList<>();
		for (; s.getRow(i) != null; i++) {
			students.add(new Student(
					(int)s.getRow(i).getCell(0).getNumericCellValue(), 
					s.getRow(i).getCell(1).getStringCellValue(), 
					s.getRow(i).getCell(2).getStringCellValue(), 
					s.getRow(i).getCell(3).getStringCellValue(),
					s.getRow(i).getCell(4).getStringCellValue().trim().equals("Y"), 
					s.getRow(i).getCell(5).getStringCellValue().trim().equals("Y"), 
					s.getRow(i).getCell(6).getStringCellValue().trim().equals("Y")));
		}
		List<Student> studentsS = new ArrayList<>(), studentsG = new ArrayList<>(), studentsH = new ArrayList<>(), studentsSG = new ArrayList<>(), studentsSH = new ArrayList<>(), studentsGH = new ArrayList<>(), studentsSGH = new ArrayList<>();
		int smcs = 0, hum = 0, global = 0;
		for (Student student : students) {
			if(student.isGlobal()) {
				if(student.isHum()) {
					if(student.isSmcs()) {
						studentsSGH.add(student);
					}else {
						studentsGH.add(student);
						global++;
						hum++;
					}
				}else if(student.isSmcs()) {
					studentsSG.add(student);
					global++;
					smcs++;
				}else {
					studentsG.add(student);
					global++;
				}
			}else if(student.isHum()) {
				if(student.isSmcs()) {
					studentsSH.add(student);
					smcs++;
					hum++;
				}else {
					studentsH.add(student);
					hum++;
				}
			}else {
				studentsS.add(student);
				smcs++;
			}
		}
		
		resetRotationGroups();
		
		boolean even = false;
		for(Student student : studentsS) {
			if(even) {
				even = false;
				addStudent(student, genGroups[0]);
				addStudent(student, smcsGroups[1]);
			}else {
				even = true;
				addStudent(student, genGroups[1]);
				addStudent(student, smcsGroups[0]);
			}
		}
		for(Student student : studentsH) {
			if(even) {
				even = false;
				addStudent(student, genGroups[0]);
				addStudent(student, humGroups[1]);
			}else {
				even = true;
				addStudent(student, genGroups[1]);
				addStudent(student, humGroups[0]);
			}
		}
		for(Student student : studentsG) {
			if(even) {
				even = false;
				addStudent(student, genGroups[0]);
				addStudent(student, gloGroups[1]);
			}else {
				even = true;
				addStudent(student, genGroups[1]);
				addStudent(student, gloGroups[0]);
			}
		}
		
		//TODO: optimize the final rotation to be based on the block in which the most people are in.
		if(Math.max(smcs, Math.max(global,hum)) == global) {
			for(Student student : studentsSGH) {
				if(even) {
					addStudent(student, genGroups[2]);
					addStudent(student, gloGroups[3]);
					even = false;
				}else {
					addStudent(student, genGroups[3]);
					addStudent(student, gloGroups[2]);
					even = true;
				}
				fillLowest(0, student, humGroups, smcsGroups);
			}
		}else if(Math.max(smcs, Math.max(global,hum)) == hum) {
			for(Student student : studentsSGH) {
				if(even) {
					addStudent(student, genGroups[2]);
					addStudent(student, humGroups[3]);
					even = false;
				}else {
					addStudent(student, genGroups[3]);
					addStudent(student, humGroups[2]);
					even = true;
				}
				fillLowest(0, student, gloGroups, smcsGroups);
			}
		}else {
			for(Student student : studentsSGH) {
				if(even) {
					addStudent(student, genGroups[2]);
					addStudent(student, smcsGroups[3]);
					even = false;
				}else {
					addStudent(student, genGroups[3]);
					addStudent(student, smcsGroups[2]);
					even = true;
				}
				fillLowest(0, student, gloGroups, humGroups);
			}
		}
		fillLowest(0, studentsSH, smcsGroups, humGroups, genGroups);
		fillLowest(0, studentsSG, smcsGroups, gloGroups, genGroups);
		fillLowest(0, studentsGH, gloGroups, humGroups, genGroups);
		//TODO: Make Optimization code better
		moveStudents(1, 0, studentsS, smcsGroups, genGroups, true);
		moveStudents(1, 0, studentsG, gloGroups, genGroups, true);
		moveStudents(1, 0, studentsH, humGroups, genGroups, true);
		moveStudents(1, 0, studentsG, gloGroups, genGroups, true);
		moveStudents(1, 0, studentsS, smcsGroups, genGroups, true);
		moveStudents(0, 1, studentsH, humGroups, genGroups, true);
		moveStudents(0, 1, studentsG, gloGroups, genGroups, true);
		moveStudents(0, 1, studentsS, smcsGroups, genGroups, true);
		moveStudents(0, 1, studentsG, gloGroups, genGroups, true);
		moveStudents(0, 1, studentsH, humGroups, genGroups, true);
		
		if(wb.getSheet("ScheduleData") != null) {
			wb.removeSheetAt(1);
		}
		wb.createSheet("ScheduleData");
		Sheet schedule = wb.getSheet("ScheduleData");
		schedule.createRow(0);
		Row titleRow = schedule.getRow(0);
		titleRow.createCell(0); titleRow.getCell(0).setCellValue("Rotations Needed");
		titleRow.createCell(1); titleRow.getCell(1).setCellValue("ID");
		titleRow.createCell(2); titleRow.getCell(2).setCellValue("First");
		titleRow.createCell(3); titleRow.getCell(3).setCellValue("Last");
		titleRow.createCell(4); titleRow.getCell(4).setCellValue("ROT1");
		titleRow.createCell(5); titleRow.getCell(5).setCellValue("ROT2");
		titleRow.createCell(6); titleRow.getCell(6).setCellValue("ROT3");
		titleRow.createCell(7); titleRow.getCell(7).setCellValue("ROT4");
		int rowIndex = 1;
		for(Student student : students) {
			schedule.createRow(rowIndex);
			Row row = schedule.getRow(rowIndex);
			row.createCell(0);
			String rotations = "";
			if(student.isGLOBAL()) {
				rotations += "Global";
			}
			if(student.isHUM()) {
				if(rotations.equals("")) {
					rotations += "Humanities";
				}else {
					rotations += ", Humanities";
				}
			}
			if(student.isSMCS()) {
				if(rotations.equals("")) {
					rotations += "SMCS";
				}else {
					rotations += ", SMCS";
				}
			}
			row.getCell(0).setCellValue(rotations);
			row.createCell(1); row.getCell(1).setCellValue(student.getId());
			row.createCell(2); row.getCell(2).setCellValue(student.getFirstName());
			row.createCell(3); row.getCell(3).setCellValue(student.getLastName());
			row.createCell(4); row.getCell(4).setCellValue(student.getRots()[0]);
			row.createCell(5); row.getCell(5).setCellValue(student.getRots()[1]);
			row.createCell(6); if(!student.getRots()[2].equals("n/a")) row.getCell(6).setCellValue(student.getRots()[2]);
			row.createCell(7); if(!student.getRots()[3].equals("n/a")) row.getCell(7).setCellValue(student.getRots()[3]);
			rowIndex++;
		}
		in.close();
		FileOutputStream out = new FileOutputStream(new File(STUDENT_DATA_PATH));
		wb.write(out);
		wb.close();
		out.close();
		for(int j = 0; j < 4; j++) {
			System.out.println("Rot: "+(j+1)+"	GEN: "+genGroups[j].getStudents().size()+"	GLO: "+gloGroups[j].getStudents().size()
					+"	HUM: "+humGroups[j].getStudents().size()+"	SMCS: "+smcsGroups[j].getStudents().size() );
		}
	}
	
	public void createSchedules() throws IOException, InvalidFormatException {
		// There will be issues if the rots leave the tables
		FileInputStream in = new FileInputStream(new File(STUDENT_DATA_PATH));
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("ScheduleData");
		int i = 1;
		for (; s.getRow(i) != null; i++) {
			FileInputStream docIn = new FileInputStream(new File(TEMPLATE_SCHEDULE_PATH));
			XWPFDocument doc = new XWPFDocument(docIn);
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						if (text != null && text.contains("First")) {
							text = text.replace("First", s.getRow(i).getCell(2).getStringCellValue());
							r.setText(text, 0);
						}
						if (text != null && text.contains("Last")) {
							text = text.replace("Last", s.getRow(i).getCell(3).getStringCellValue());
							r.setText(text, 0);
						}
					}
				}
			}
			for (XWPFTable tbl : doc.getTables()) {
				for (XWPFTableRow row : tbl.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun r : p.getRuns()) {
								String text = r.getText(0);
								if (text != null && text.contains("ROT1")) {
									text = text.replace("ROT1",
											rotMessage(s.getRow(i).getCell(4).getStringCellValue()));
									r.setText(text, 0);
								} else if (text != null && text.contains("ROT2")) {
									text = text.replace("ROT2",
											rotMessage(s.getRow(i).getCell(5).getStringCellValue()));
									r.setText(text, 0);
								} else if (text != null && text.contains("ROT3")) {
									text = text.replace("ROT3",
											rotMessage(s.getRow(i).getCell(6).getStringCellValue()));
									r.setText(text, 0);
								} else if (text != null && text.contains("ROT4")) {
									text = text.replace("ROT4",
											rotMessage(s.getRow(i).getCell(7).getStringCellValue()));
									r.setText(text, 0);
								}
							}
						}
					}
				}
			}
			doc.write(new FileOutputStream(
					"data/schedules/" + (int)s.getRow(i).getCell(1).getNumericCellValue()+s.getRow(i).getCell(2).getStringCellValue()
							+ s.getRow(i).getCell(3).getStringCellValue() + ".docx"));
			doc.close();
		}
		in.close();
		wb.close();
	}

	private boolean addStudent(Student student, RotationGroup rotationGroup) {
		boolean returnValue = student.setRot(rotationGroup.getRotNum(), rotationGroup.getName());
		if(returnValue) {
			rotationGroup.getStudents().add(student);
		}
		return returnValue;
	}
	private boolean removeStudent(Student student, RotationGroup rotationGroup) {
		rotationGroup.getStudents().remove(student);
		return student.removeRot(rotationGroup.getRotNum(), rotationGroup.getName());
	}
	
	private void fillLowest(int rot, List<Student> students, RotationGroup[] groups1, RotationGroup[] groups2, RotationGroup[] genGroups) {
		for(Student student : students) {
			if (Math.min(groups1[rot].getStudents().size(), Math.min(groups2[rot].getStudents().size(), genGroups[rot].getStudents().size()-20)) == groups1[rot].getStudents().size() 
					&& (groups1[rot+1].getStudents().size() > groups1[rot].getStudents().size() || groups2[rot+1].getStudents().size() < groups2[rot].getStudents().size())) {
				addStudent(student, groups1[rot]);
				fillLowest(rot+1, student, genGroups, groups2);
			}else if(Math.min(groups1[rot].getStudents().size(), Math.min(groups2[rot].getStudents().size(), genGroups[rot].getStudents().size()-20)) == groups2[rot].getStudents().size() 
					&& (groups2[rot+1].getStudents().size() > groups2[rot].getStudents().size() || genGroups[rot+1].getStudents().size() < genGroups[rot].getStudents().size())) {
				addStudent(student, groups2[rot]);
				fillLowest(rot+1, student, genGroups, groups1);
			}else {
				addStudent(student, genGroups[rot]);
				fillLowest(rot+1, student, groups2, groups1);
			}
		}
	}
	private void fillLowest(int rot, Student student, RotationGroup[] groups1, RotationGroup[] groups2) {
		if(groups1[rot].getName().equals("GE")) {
			if(groups2[rot].getStudents().size() < groups1[rot].getStudents().size() - 10 && (groups2[rot+1].getStudents().size() > 9 || groups1[rot+1].getStudents().size() < 9)) {
				addStudent(student, groups2[rot]);
				addStudent(student, groups1[rot+1]);
			}else {
				addStudent(student, groups2[rot+1]);
				addStudent(student, groups1[rot]);
			}
		}else {
			if(groups2[rot].getStudents().size() < groups1[rot].getStudents().size() && (groups2[rot+1].getStudents().size() > 9 || groups1[rot+1].getStudents().size() < 9)) {
				addStudent(student, groups2[rot]);
				addStudent(student, groups1[rot+1]);
			}else {
				addStudent(student, groups2[rot+1]);
				addStudent(student, groups1[rot]);
			}
		}
	}
	
	//TODO: Assess whether this is needed
	/*private void optimizeRotations(int rot1, int rot2, List<Student> students, RotationGroup[] group1, RotationGroup[] group2, boolean isGroup2Gen) {
		int counter = 0;
		if(isGroup2Gen) {
			while(group1[rot1].getStudents().size() > 50 && group2[rot1].getStudents().size() < 70 && group1[rot2].getStudents().size() < 50) {
				if(group2[rot1].getStudents().size() < 70 && students.get(counter).getRots()[rot1].equals(group1[rot1].getName())) {
					removeStudent(students.get(counter), group1[rot1]);
					removeStudent(students.get(counter), group2[rot2]);
					addStudent(students.get(counter), group1[rot2]);
					addStudent(students.get(counter), group2[rot1]);
					counter++;
				}else if(group2[rot1].getStudents().size() < 70){
					counter++;
				}
			}
		}else {
			while(group1[rot1].getStudents().size() > 50 && group2[rot1].getStudents().size() < 50 && group1[rot2].getStudents().size() < 50) {
				if(group2[rot1].getStudents().size() < 50 && students.get(counter).getRots()[rot1].equals(group1[rot1].getName())) {
					removeStudent(students.get(counter), group1[rot1]);
					removeStudent(students.get(counter), group2[rot2]);
					addStudent(students.get(counter), group1[rot2]);
					addStudent(students.get(counter), group2[rot1]);
					counter++;
				}else if(group2[rot1].getStudents().size() < 50){
					counter++;
				}
			}
		}
		
	}*/
	
	/**
	 * Switches the rotation block of students with a rotation for group1 and group2
	 * @param rot1 rotation block 1
	 * @param rot2 rotation block 2
	 * @param students
	 * @param group1
	 * @param group2
	 * @param isGroup2Gen
	 */
	private void moveStudents(int rot1, int rot2, List<Student> students, RotationGroup[] group1, RotationGroup[] group2, boolean isGroup2Gen) {
		if(rot1 == rot2) {
			return;
		}
		int counter = 0;
		if(isGroup2Gen) {
			while(group1[rot2].getStudents().size() < MAG_LIMIT && group2[rot1].getStudents().size() < GEN_LIMIT) {
				if(group2[rot1].getStudents().size() < GEN_LIMIT && students.get(counter).getRots()[rot1].equals(group1[rot1].getName())) {
					removeStudent(students.get(counter), group1[rot1]);
					removeStudent(students.get(counter), group2[rot2]);
					addStudent(students.get(counter), group1[rot2]);
					addStudent(students.get(counter), group2[rot1]);
					counter++;
				}else if(group2[rot1].getStudents().size() < GEN_LIMIT){
					counter++;
				}
			}
		}else {
			while(group1[rot2].getStudents().size() < MAG_LIMIT && group2[rot1].getStudents().size() < MAG_LIMIT) {
				if(group2[rot1].getStudents().size() < MAG_LIMIT && students.get(counter).getRots()[rot1].equals(group1[rot1].getName())) {
					removeStudent(students.get(counter), group1[rot1]);
					removeStudent(students.get(counter), group2[rot2]);
					addStudent(students.get(counter), group1[rot2]);
					addStudent(students.get(counter), group2[rot1]);
					counter++;
				}else if(group2[rot1].getStudents().size() < MAG_LIMIT){
					counter++;
				}
			}
		}
	}
	
	private String rotMessage(String rot) {
		if (rot.equals("H")) {
			return humString;
		} else if (rot.equals("GL")) {
			return gloString;
		} else if (rot.equals("S")) {
			return smcString;
		} else if (rot.equals("GE")) {
			return genString;
		}
		return rot;
	}

	public String getGenString() {
		return genString;
	}

	public void setGenString(String genString) {
		this.genString = genString;
	}

	public String getHumString() {
		return humString;
	}

	public void setHumString(String humString) {
		this.humString = humString;
	}

	public String getGloString() {
		return gloString;
	}

	public void setGloString(String gloString) {
		this.gloString = gloString;
	}

	public String getSmcString() {
		return smcString;
	}

	public void setSmcString(String smcString) {
		this.smcString = smcString;
	}
	
	public void setRotationNames(String genName, String gloName, String humName, String smcName) throws IOException {
		setGenString(genName); setGloString(gloName); setHumString(humName); setSmcString(smcName);
		String rotationNames = "gen:" + genName + "\n" + 
				   	   "glo:" + gloName + "\n" +
				   	   "hum:" + humName + "\n" +
				   	   "smc:" + smcName;
		File fout = new File(ROTATION_NAMES_PATH);
		FileOutputStream fos = new FileOutputStream(fout); 	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(rotationNames);
		bw.close();
	}

	public void resetRotationGroups() {
		for(int i = 0; i < 4; i++) {
			smcsGroups[i] = new RotationGroup("S", i);
			humGroups[i] = new RotationGroup("H", i);
			gloGroups[i] = new RotationGroup("GL", i);
			genGroups[i] = new RotationGroup("GE", i);
		}
	}
}
