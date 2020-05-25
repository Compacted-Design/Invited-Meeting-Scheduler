package compactedDesign.invitedMeetingScheduler.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
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

	public DataLoader() throws IOException {
		FileReader rotationText = new FileReader(new File("scr/main/resources/data/RotationNames.txt"));
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
	}

	//TODO: Where the manual and sheet entry data will be transfered to
	public void loadData() {
		
	}
	
	public void groupSchedules() throws IOException {
		FileInputStream in = new FileInputStream(new File("scr/main/resources/data/StudentData.xlsx"));
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
		RotationGroup smcs1 = new RotationGroup("S", 1), smcs2 = new RotationGroup("S", 2), smcs3 = new RotationGroup("S", 3), smcs4 = new RotationGroup("S", 4);
		RotationGroup gen1 = new RotationGroup("GE", 1), gen2 = new RotationGroup("GE", 2), gen3 = new RotationGroup("GE", 3), gen4 = new RotationGroup("GE", 4);
		RotationGroup glo1 = new RotationGroup("GL", 1), glo2 = new RotationGroup("GL", 2), glo3 = new RotationGroup("GL", 3), glo4 = new RotationGroup("GL", 4);
		RotationGroup hum1 = new RotationGroup("H", 1), hum2 = new RotationGroup("H", 2), hum3 = new RotationGroup("H", 3), hum4 = new RotationGroup("H", 4);
		
		boolean even = false;
		for(Student student : studentsS) {
			if(even) {
				even = false;
				addStudent(student, gen1);
				addStudent(student, smcs2);
			}else {
				even = true;
				addStudent(student, gen2);
				addStudent(student, smcs1);
			}
		}
		for(Student student : studentsH) {
			if(even) {
				even = false;
				addStudent(student, gen1);
				addStudent(student, hum2);
			}else {
				even = true;
				addStudent(student, gen2);
				addStudent(student, hum1);
			}
		}
		for(Student student : studentsG) {
			if(even) {
				even = false;
				addStudent(student, gen1);
				addStudent(student, glo2);
			}else {
				even = true;
				addStudent(student, gen2);
				addStudent(student, glo1);
			}
		}
		
		
		//TODO: optimize the final rotation to be based on the block in which the most people are in.
		if(Math.max(smcs, Math.max(global,hum)) == global) {
			for(Student student : studentsSGH) {
				if(even) {
					addStudent(student, gen3);
					addStudent(student, glo4);
					even = false;
				}else {
					addStudent(student, gen4);
					addStudent(student, glo3);
					even = true;
				}
			}
		}else if(Math.max(smcs, Math.max(global,hum)) == hum) {
			for(Student student : studentsSGH) {
				if(even) {
					addStudent(student, gen3);
					addStudent(student, hum4);
					even = false;
				}else {
					addStudent(student, gen4);
					addStudent(student, hum3);
					even = true;
				}
			}
		}else {
			for(Student student : studentsSGH) {
				if(even) {
					addStudent(student, gen3);
					addStudent(student, smcs4);
					even = false;
				}else {
					addStudent(student, gen4);
					addStudent(student, smcs3);
					even = true;
				}
			}
		}
		
		
		for(Student student : studentsGH) {
			if(glo1.getStudents().size() < 50) {
				addStudent(student, glo1);
			}else if(glo3.getStudents().size() < 50) {
				addStudent(student, glo3);
			}else {
				addStudent(student, glo2);
			}
			
			if(student.getRot2().equals("") && gen2.getStudents().size() < 65) {
				addStudent(student, gen2);
			}else if(student.getRot1().equals("") && gen1.getStudents().size() < 65) {
				addStudent(student, gen1);
			}else{
				addStudent(student, gen3);
			}
			
			if(student.getRot1().equals("")) {
				addStudent(student, hum1);
			}else if(student.getRot2().equals("")) {
				addStudent(student, hum2);
			}else {
				addStudent(student, hum3);
			}
		}
		
		
		for(Student student : studentsSG) {
			if(glo2.getStudents().size() < 50) {
				addStudent(student, glo2);
			}else if(glo3.getStudents().size() < 50) {
				addStudent(student, glo3);
			}else {
				addStudent(student, glo1);
			}
			
			if(student.getRot1().equals("") && gen1.getStudents().size() < 65) {
				addStudent(student, gen1);
			}else if(student.getRot3().equals("") && gen3.getStudents().size() < 65) {
				addStudent(student, gen3);
			}else{
				addStudent(student, gen2);
			}
			
			if(student.getRot1().equals("")) {
				addStudent(student, smcs1);
			}else if(student.getRot2().equals("")) {
				addStudent(student, smcs2);
			}else {
				addStudent(student, smcs3);
			}
		}
		
		
		for(Student student : studentsSH) {
			if(student.getRot3().equals("") && gen3.getStudents().size() < 65) {
				addStudent(student, gen3);
			}else if(student.getRot2().equals("") && gen2.getStudents().size() < 65) {
				addStudent(student, gen2);
			}else{
				addStudent(student, gen1);
			}
			
			if(student.getRot1().equals("") && smcs1.getStudents().size() < 50) {
				addStudent(student, smcs1);
			}else if(student.getRot2().equals("") && smcs2.getStudents().size() < 50) {
				addStudent(student, smcs2);
			}else {
				addStudent(student, smcs3);
			}
			
			if(student.getRot1().equals("")) {
				addStudent(student, hum1);
			}else if(student.getRot2().equals("")) {
				addStudent(student, hum2);
			}else {
				addStudent(student, hum3);
			}
		}
		for(Student student : studentsSGH) {
			if(smcs2.getStudents().size() < 50) {
				addStudent(student, smcs2);
				addStudent(student, hum1);
			}else {
				addStudent(student, smcs1);
				addStudent(student, hum2);
			}
		}
		
		wb.removeSheetAt(1);
		wb.createSheet("ScheduleData");
		Sheet schedule = wb.getSheet("ScheduleData");
		schedule.createRow(0);
		Row titleRow = schedule.getRow(0);
		titleRow.createCell(0); titleRow.getCell(0).setCellValue("ROT");
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
			row.createCell(1); row.getCell(1).setCellValue(student.getId());
			row.createCell(2); row.getCell(2).setCellValue(student.getFirstName());
			row.createCell(3); row.getCell(3).setCellValue(student.getLastName());
			row.createCell(4); row.getCell(4).setCellValue(student.getRot1());
			row.createCell(5); row.getCell(5).setCellValue(student.getRot2());
			row.createCell(6); if(!student.getRot3().equals("n/a")) row.getCell(6).setCellValue(student.getRot3());
			row.createCell(7); if(!student.getRot4().equals("n/a")) row.getCell(7).setCellValue(student.getRot4());
			rowIndex++;
		}
		FileOutputStream out = new FileOutputStream(new File("scr/main/resources/data/StudentData.xlsx"));
		wb.write(out);
		wb.close();
		in.close();
		out.close();
		
	}
	
	public void createSchedules() throws IOException, InvalidFormatException {
		// There will be issues if the rots leave the tables
		FileInputStream in = new FileInputStream(new File("scr/main/resources/data/StudentData.xlsx"));
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("ScheduleData");
		int i = 1;
		for (; s.getRow(i) != null; i++) {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open("scr/main/resources/data/BlankSchedule.docx"));
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
					"scr/main/resources/data/schedules/" + s.getRow(i).getCell(2).getStringCellValue()
							+ s.getRow(i).getCell(3).getStringCellValue() + ".docx"));
		}
		in.close();
		wb.close();
	}

	private void addStudent(Student student, RotationGroup rotationGroup) {
		rotationGroup.getStudents().add(student);
		student.setRot(rotationGroup.getRotNum(), rotationGroup.getName());
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

}
