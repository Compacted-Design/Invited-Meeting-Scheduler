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
		Sheet s = wb.getSheet("ScheduleData");
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
		int smcCount1 = 0, smcCount2 = 0, humCount1 = 0, humCount2 = 0, gloCount1 = 0, gloCount2 = 0, allCount = 0;
		List<Student> studentsS = new ArrayList<>(), studentsG = new ArrayList<>(), studentsH = new ArrayList<>(), studentsSG = new ArrayList<>(), studentsSH = new ArrayList<>(), studentsGH = new ArrayList<>(), studentsSGH = new ArrayList<>();
		for (Student student : students) {
			if(student.isGlobal()) {
				if(student.isHum()) {
					if(student.isSmcs()) {
						studentsSGH.add(student);
						allCount++;
					}else {
						studentsGH.add(student);
						humCount2++;
						gloCount2++;
					}
				}else if(student.isSmcs()) {
					studentsSG.add(student);
					smcCount2++;
					gloCount2++;
				}else {
					studentsG.add(student);
					gloCount1++;
				}
			}else if(student.isHum()) {
				if(student.isSmcs()) {
					studentsSH.add(student);
					smcCount2++;
					humCount2++;
				}else {
					studentsH.add(student);
					humCount1++;
				}
			}else {
				studentsS.add(student);
				smcCount1++;
			}
		}
		//Necessary people in rotations 1 and 2 and with only 1 magnet house
		int neccessarySmcRot2 = smcCount1/2;
		int necessarySmcRot1 = smcCount1 - neccessarySmcRot2;
		int necessaryGloRot2 = gloCount1/2;
		int necessaryGloRot1 = gloCount1 - necessaryGloRot2;
		int necessaryHumRot2 = humCount1/2;
		int necessaryHumRot1 = humCount1 - necessaryHumRot2;
		int necessaryGenRot1 = neccessarySmcRot2 + necessaryGloRot2 + necessaryHumRot2;
		int neccesaryGenRot2 = necessaryGloRot1 + necessaryHumRot1 + necessarySmcRot1;
		//3 magnet Houses
		RotationGroup[] rotations = {new RotationGroup(RotationID.ROT1, "GE"), new RotationGroup(RotationID.ROT1, "H"), 
									 new RotationGroup(RotationID.ROT1, "S"), new RotationGroup(RotationID.ROT1, "GL"),
									 new RotationGroup(RotationID.ROT2, "GE"), new RotationGroup(RotationID.ROT2, "H"), 
									 new RotationGroup(RotationID.ROT2, "S"), new RotationGroup(RotationID.ROT2, "GL"),
									 new RotationGroup(RotationID.ROT3, "GE"), new RotationGroup(RotationID.ROT3, "H"), 
									 new RotationGroup(RotationID.ROT3, "S"), new RotationGroup(RotationID.ROT3, "GL"),
									 new RotationGroup(RotationID.ROT4, "GE"), new RotationGroup(RotationID.ROT4, "H"), 
									 new RotationGroup(RotationID.ROT4, "S"), new RotationGroup(RotationID.ROT4, "GL")};
		for (Student student : studentsS) {
			
		}
		for (Student student : studentsG) {
			
		}
		for (Student student : studentsH) {
	
		}
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
