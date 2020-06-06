package compactedDesign.invitedMeetingScheduler.data;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DataLoader {
	//Decide whether it should be static or not
	
	private String genString, humString, gloString, smcString;
	
	private static final String STUDENT_DATA_PATH = "data/StudentData.xlsx", STUDENT_DATA_BACKUP_PATH = "/backups/StudentData_Backup.xlsx";
	private static final String ROTATION_NAMES_PATH = "data/RotationNames.txt", ROTATION_NAMES_BACKUP_PATH = "/backups/RotationNames_Backup.txt";
	private static final String TEMPLATE_SCHEDULE_PATH = "data/BlankSchedule.docx";
	private static final String TEMPLATE_INFORMATION_PATH = "data/BlankInfo.docx", TEMPLATE_INFORMATION_BACKUP_PATH = "/backups/BlankInfo_Backup.docx";
	private static final String QRCODE_CAPTIONS_PATH = "data/QRCodeCaptions.txt", QRCODE_CAPTIONS_BACKUP_PATH = "/backups/QRCodeCaptions_Backup.txt";
	private static final String QRCODE_LINKS_PATH = "data/QRCodeLinks.txt", QRCODE_LINKS_BACKUP_PATH = "/backups/QRCodeLinks_Backup.txt";
	private static final String SCHEDULE_INFO_PATH = "data/ScheduleInfo.txt", SCHEDULE_INFO_BACKUP_PATH = "/backups/ScheduleInfo_Backup.txt";
	private static final int GEN_LIMIT = 70;
	private static final int MAG_LIMIT = 50;
	private static final String IMG_PATH = "data/img/";
	private static final String CLUB_CODE = "club.png", COM_CODE = "comn.png", SPORT_CODE = "spor.png", WEB_CODE = "phsw.png", COL_CODE = "colg.png", BUS_CODE = "busr.png";
	private static final String MAP_CODE = "map.png", MAP_BACKUP_PATH = "/backups/img/map_Backup.png";
	private String schedulePath;
	
	private RotationGroup[] smcsGroups = new RotationGroup[4];
	private RotationGroup[] humGroups = new RotationGroup[4];
	private RotationGroup[] genGroups = new RotationGroup[4];
	private RotationGroup[] gloGroups = new RotationGroup[4];
	private List<Student> students, studentsS, studentsG, studentsH, studentsSG, studentsSH, studentsGH, studentsSGH;
	
	private static final int QRCODE_SIDE_LENGTH = 150;
	private static final int MAP_SIDE_LENGTH = 500;
	private static final int MAP_SIDE_HEIGHT = 425;
	
	private String clubCap, busrCap, phswCap, colgCap, sporCap, comnCap;
	private String clubLink, busrLink, phswLink, colgLink, sporLink, comnLink;
	private String missingFiles;

	public DataLoader() throws IOException, WriterException {
		missingFiles = "";
		FileReader rotationText = null;
		try {
			rotationText = new FileReader(ROTATION_NAMES_PATH);
		}catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(ROTATION_NAMES_BACKUP_PATH);
			File source = new File(ROTATION_NAMES_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			rotationText = new FileReader(ROTATION_NAMES_PATH);
			missingFiles += "RotationNames.txt \n";
		}
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
		
		
		FileReader qrCodeCapText = null;
		try {
			qrCodeCapText = new FileReader(QRCODE_CAPTIONS_PATH);
		}catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(QRCODE_CAPTIONS_BACKUP_PATH);
			File source = new File(QRCODE_CAPTIONS_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			qrCodeCapText = new FileReader(QRCODE_CAPTIONS_PATH);
			missingFiles += "QRCodeCaptions.txt \n";
		}
		br = new BufferedReader(qrCodeCapText);
		while((line = br.readLine()) != null) {
			if(line.substring(0, 5).equals("club:")) {
				clubCap = line.substring(5);
			}else if(line.substring(0, 5).equals("busr:")) {
				busrCap = line.substring(5);
			}else if(line.substring(0, 5).equals("phsw:")) {
				phswCap = line.substring(5);
			}else if(line.substring(0, 5).equals("colg:")) {
				colgCap = line.substring(5);
			}else if(line.substring(0, 5).equals("spor:")) {
				sporCap = line.substring(5);
			}else if(line.substring(0, 5).equals("comn:")) {
				comnCap = line.substring(5);
			}
		}
		br.close();
		
		
		FileReader qrCodeLinkText = null;
		try {
			qrCodeLinkText = new FileReader(QRCODE_LINKS_PATH);
		} catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(QRCODE_LINKS_BACKUP_PATH);
			File source = new File(QRCODE_LINKS_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			qrCodeLinkText = new FileReader(QRCODE_LINKS_PATH);
			missingFiles += "QRCodeLinks.txt \n";
		}
		br = new BufferedReader(qrCodeLinkText);
		while((line = br.readLine()) != null) {
			if(line.substring(0, 5).equals("club:")) {
				clubLink = line.substring(5);
			}else if(line.substring(0, 5).equals("busr:")) {
				busrLink = line.substring(5);
			}else if(line.substring(0, 5).equals("phsw:")) {
				phswLink = line.substring(5);
			}else if(line.substring(0, 5).equals("colg:")) {
				colgLink = line.substring(5);
			}else if(line.substring(0, 5).equals("spor:")) {
				sporLink = line.substring(5);
			}else if(line.substring(0, 5).equals("comn:")) {
				comnLink = line.substring(5);
			}
		}
		if(missingFiles.contains("QRCodeLinks")) {
			makeQRCode(clubLink, IMG_PATH+CLUB_CODE);
			makeQRCode(comnLink, IMG_PATH+COM_CODE);
			makeQRCode(phswLink, IMG_PATH+WEB_CODE);
			makeQRCode(sporLink, IMG_PATH+SPORT_CODE);
			makeQRCode(busrLink, IMG_PATH+BUS_CODE);
			makeQRCode(colgLink, IMG_PATH+COL_CODE);
		}
		br.close();
		
		
		FileReader scheduleText = null;
		try {
			scheduleText = new FileReader(SCHEDULE_INFO_PATH);
		} catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(SCHEDULE_INFO_BACKUP_PATH);
			File source = new File(SCHEDULE_INFO_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			scheduleText = new FileReader(SCHEDULE_INFO_PATH);
			missingFiles += "ScheduleInfo.txt";
		}
		br = new BufferedReader(scheduleText);
		while((line = br.readLine()) != null) {
			if(line.substring(0,4).equals("dir:")) {
				schedulePath = line.substring(4);
			}
		}
		File scheduleFilePath = new File(schedulePath);
		schedulePath = scheduleFilePath.getAbsolutePath();
		br.close();
	}
	
	public void openMissingFilesPopup() throws IOException {
		if(missingFiles.equals("")) {
			return;
		}
		Stage popUp = new Stage();
		popUp.setTitle("Missing Files");
		popUp.setResizable(false);
		Pane popUpRoot = FXMLLoader.load(getClass().getResource("/views/popupViews/MissingFilesPopUpView.fxml"));
		Scene popUpScene = new Scene(popUpRoot);
		popUp.setScene(popUpScene);
		popUp.show();
		missingFiles = "";
	}
	
	public void loadDataInput(int id, String first, String last, String school, boolean smcs, boolean global, boolean hum) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(STUDENT_DATA_PATH));
		} catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(STUDENT_DATA_BACKUP_PATH);
			File source = new File(STUDENT_DATA_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			missingFiles = "StudentData.xlsx";
			openMissingFilesPopup();
			in = new FileInputStream(new File(STUDENT_DATA_PATH));
		}
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
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(STUDENT_DATA_PATH));
		} catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(STUDENT_DATA_BACKUP_PATH);
			File source = new File(STUDENT_DATA_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			missingFiles = "StudentData.xlsx";
			in = new FileInputStream(new File(STUDENT_DATA_PATH));
			openMissingFilesPopup();
		}
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("RawData");
		Workbook inputWB = new XSSFWorkbook(inputSheetFile);
		int sheetIndex = 0;
		while(sheetIndex < inputWB.getNumberOfSheets() && inputWB.getSheetAt(0).getRow(0) != null) {
			Sheet inputSheet = inputWB.getSheetAt(sheetIndex);
			Row titleRow = inputSheet.getRow(0);
			int idCol = -1;
			int firstCol = -1;
			int lastCol = -1;
			int schoolCol = -1;
			int houseCol = -1;
			int smcsCol = -1;
			int humCol = -1;
			int gloCol = -1;
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
				}else if(smcsCol == -1 && (titleRow.getCell(i).getStringCellValue().toLowerCase().contains("smc") || titleRow.getCell(i).getStringCellValue().toLowerCase().contains("math") || 
						titleRow.getCell(i).getStringCellValue().toLowerCase().contains("sci"))) {
					smcsCol = i;
				}else if(humCol == -1 && titleRow.getCell(i).getStringCellValue().toLowerCase().contains("hum")) {
					humCol = i;
				}else if(gloCol == -1 && titleRow.getCell(i).getStringCellValue().toLowerCase().contains("glo")) {
					gloCol = i;
				}
			}
			if(idCol != -1 && firstCol != -1 && lastCol != -1 && schoolCol != -1 && (houseCol != -1 || (smcsCol != -1 && humCol != -1 && gloCol != -1))) {
				for(int rowIndex = 1; inputSheet.getRow(rowIndex) != null; rowIndex++) {
					Row row = inputSheet.getRow(rowIndex);
					String first = row.getCell(firstCol).getStringCellValue().trim();
					String last = row.getCell(lastCol).getStringCellValue().trim();
					String school = row.getCell(schoolCol).getStringCellValue().trim();
					boolean smcs = false, global = false, hum = false;
					if(smcsCol != -1 && humCol != -1 && gloCol != -1) {
						if(row.getCell(smcsCol).getCellType() == CellType.STRING) {
							smcs = row.getCell(smcsCol).getStringCellValue().toLowerCase().contains("y") || row.getCell(smcsCol).getStringCellValue().toLowerCase().contains("true");
						}else if(row.getCell(smcsCol).getCellType() == CellType.NUMERIC) {
							smcs = row.getCell(smcsCol).getNumericCellValue() == 1;
						}else if(row.getCell(smcsCol).getCellType() == CellType.BOOLEAN) {
							smcs = row.getCell(smcsCol).getBooleanCellValue();
						}
						
						if(row.getCell(humCol).getCellType() == CellType.STRING) {
							hum = row.getCell(humCol).getStringCellValue().toLowerCase().contains("y") || row.getCell(humCol).getStringCellValue().toLowerCase().contains("true");
						}else if(row.getCell(humCol).getCellType() == CellType.STRING) {
							hum = row.getCell(humCol).getNumericCellValue() == 1;
						}else if(row.getCell(humCol).getCellType() == CellType.STRING) {
							hum = row.getCell(humCol).getBooleanCellValue();
						}
						
						if(row.getCell(gloCol).getCellType() == CellType.STRING) {
							global = row.getCell(gloCol).getStringCellValue().toLowerCase().contains("y") || row.getCell(gloCol).getStringCellValue().toLowerCase().contains("true");
						}else if(row.getCell(gloCol).getCellType() == CellType.STRING) {
							global = row.getCell(gloCol).getNumericCellValue() == 1;
						}else if(row.getCell(gloCol).getCellType() == CellType.STRING) {
							global = row.getCell(gloCol).getBooleanCellValue();
						}
						
						
					}else if(houseCol != -1) {
						smcs = row.getCell(houseCol).getStringCellValue().toLowerCase().contains("smcs") || row.getCell(houseCol).getStringCellValue().toLowerCase().contains("math") ||
								row.getCell(houseCol).getStringCellValue().toLowerCase().contains("sci");
						global = row.getCell(houseCol).getStringCellValue().toLowerCase().contains("glo");
						hum = row.getCell(houseCol).getStringCellValue().toLowerCase().contains("hum");
					}
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
	
	public void makeQRCode(String link, String fileName) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, QRCODE_SIDE_LENGTH, QRCODE_SIDE_LENGTH);
		File temp = new File(IMG_PATH+fileName);
		temp.getParentFile().mkdirs();
		temp.createNewFile();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", new FileOutputStream(new File(IMG_PATH+fileName)));
        
	}
	
	public void clearData() throws IOException {
		students = null; studentsG = null; studentsGH = null; studentsH = null; studentsS = null; studentsSG = null; studentsSGH = null; studentsSH = null;
		smcsGroups = new RotationGroup[4]; humGroups = new RotationGroup[4]; genGroups = new RotationGroup[4]; gloGroups = new RotationGroup[4];
		InputStream backup = getClass().getResourceAsStream(STUDENT_DATA_BACKUP_PATH);
		File source = new File(STUDENT_DATA_PATH);
		source.getParentFile().mkdirs();
		FileUtils.copyInputStreamToFile(backup, source);
		backup.close();
	}
	
	//TODO: Compress redundant series of code
	public void groupSchedules() throws IOException, InvalidFormatException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(STUDENT_DATA_PATH));
		} catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(STUDENT_DATA_BACKUP_PATH);
			File source = new File(STUDENT_DATA_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			missingFiles = "StudentData.xlsx";
			openMissingFilesPopup();
			return;
		}
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("RawData");
		int i = 1;
		//TODO: Combine into 1 for loop
		students = new ArrayList<>();
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
		studentsS = new ArrayList<>(); studentsG = new ArrayList<>(); studentsH = new ArrayList<>(); studentsSG = new ArrayList<>(); studentsSH = new ArrayList<>(); studentsGH = new ArrayList<>(); studentsSGH = new ArrayList<>();
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
		int max = 0;
		//TODO: optimize the final rotation to be based on the block in which the most people are in.
		if(Math.max(smcs, Math.max(global,hum)) == global) {
			max = global;
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
			max = hum;
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
			max = smcs;
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
		moveStudents(0, 1, studentsH, humGroups, genGroups, true);
		moveStudents(0, 1, studentsG, gloGroups, genGroups, true);
		moveStudents(0, 1, studentsS, smcsGroups, genGroups, true);
		moveStudents(0, 1, studentsG, gloGroups, genGroups, true);
		moveStudents(0, 1, studentsH, humGroups, genGroups, true);
		if(max == global) {
			if(smcs+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, smcsGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSG, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, smcsGroups, genGroups, true);
				
				moveStudents(2, 1, studentsSG, smcsGroups, gloGroups, false);
				moveStudents(2, 0, studentsSG, smcsGroups, gloGroups, false);
			}
			if(hum+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, humGroups, genGroups, true);
				
				moveStudents(2, 0, studentsGH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, humGroups, genGroups, true);
				
				moveStudents(2, 1, studentsGH, humGroups, gloGroups, false);
				moveStudents(2, 0, studentsGH, humGroups, gloGroups, false);
			}
		}else if(max == hum) {
			if(smcs+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, smcsGroups, genGroups, true);
				moveStudents(2, 0, studentsSG, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, smcsGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSH, smcsGroups, humGroups, false);
				moveStudents(2, 1, studentsSH, smcsGroups, humGroups, false);
			}
			if(global+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 0, studentsSG, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, gloGroups, genGroups, true);
				
				moveStudents(2, 0, studentsGH, gloGroups, humGroups, false);
				moveStudents(2, 1, studentsGH, gloGroups, humGroups, false);
			}
		}else {
			if(hum+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, humGroups, genGroups, true);
				moveStudents(2, 0, studentsGH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, humGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSH, humGroups, smcsGroups, false);
				moveStudents(2, 1, studentsSH, humGroups, smcsGroups, false);
			}
			if(global+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 0, studentsSG, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, gloGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSG, gloGroups, smcsGroups, false);
				moveStudents(2, 1, studentsSG, gloGroups, smcsGroups, false);
			}
		}
		moveStudents(1, 0, studentsH, humGroups, genGroups, true);
		moveStudents(1, 0, studentsG, gloGroups, genGroups, true);
		moveStudents(1, 0, studentsS, smcsGroups, genGroups, true);
		moveStudents(1, 0, studentsG, gloGroups, genGroups, true);
		moveStudents(1, 0, studentsH, humGroups, genGroups, true);
		if(max == global) {
			if(smcs+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, smcsGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSG, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, smcsGroups, genGroups, true);
				
				moveStudents(2, 1, studentsSG, smcsGroups, gloGroups, false);
				moveStudents(2, 0, studentsSG, smcsGroups, gloGroups, false);
			}
			if(hum+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, humGroups, genGroups, true);
				
				moveStudents(2, 0, studentsGH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, humGroups, genGroups, true);
				
				moveStudents(2, 1, studentsGH, humGroups, gloGroups, false);
				moveStudents(2, 0, studentsGH, humGroups, gloGroups, false);
			}
		}else if(max == hum) {
			if(smcs+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, smcsGroups, genGroups, true);
				moveStudents(2, 0, studentsSG, smcsGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, smcsGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSH, smcsGroups, humGroups, false);
				moveStudents(2, 1, studentsSH, smcsGroups, humGroups, false);
			}
			if(global+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 0, studentsSG, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, gloGroups, genGroups, true);
				
				moveStudents(2, 0, studentsGH, gloGroups, humGroups, false);
				moveStudents(2, 1, studentsGH, gloGroups, humGroups, false);
			}
		}else {
			if(hum+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsSH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsSH, humGroups, genGroups, true);
				moveStudents(2, 0, studentsGH, humGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, humGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSH, humGroups, smcsGroups, false);
				moveStudents(2, 1, studentsSH, humGroups, smcsGroups, false);
			}
			if(global+studentsSGH.size() < 100) {
				moveStudents(2, 0, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsGH, gloGroups, genGroups, true);
				moveStudents(2, 0, studentsSG, gloGroups, genGroups, true);
				moveStudents(2, 1, studentsSG, gloGroups, genGroups, true);
				
				moveStudents(2, 0, studentsSG, gloGroups, smcsGroups, false);
				moveStudents(2, 1, studentsSG, gloGroups, smcsGroups, false);
			}
		}
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
			int blockCounter = 0;
			if(student.isGLOBAL()) {
				rotations += "Global";
				blockCounter++;
			}
			if(student.isHUM()) {
				if(rotations.equals("")) {
					rotations += "Humanities";
				}else {
					rotations += ", Humanities";
				}
				blockCounter++;
			}
			if(student.isSMCS()) {
				if(rotations.equals("")) {
					rotations += "SMCS";
				}else {
					rotations += ", SMCS";
				}
				blockCounter++;
			}
			row.getCell(0).setCellValue(blockCounter+" Rotations: "+rotations);
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
	}
	
	public void createTemplateSchedule() throws IOException, InvalidFormatException, WriterException {
		FileInputStream docIn = null;
		try {
			docIn = new FileInputStream(new File(TEMPLATE_INFORMATION_PATH));
		} catch (IOException e) {
			InputStream backup = getClass().getResourceAsStream(TEMPLATE_INFORMATION_BACKUP_PATH);
			File source = new File(TEMPLATE_INFORMATION_PATH);
			source.getParentFile().mkdirs();
			FileUtils.copyInputStreamToFile(backup, source);
			backup.close();
			missingFiles += "BlankInfo.docx \n";
			docIn = new FileInputStream(new File(TEMPLATE_INFORMATION_PATH));
		}
		XWPFDocument doc = new XWPFDocument(docIn);
		for (XWPFTable tbl : doc.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						String text = p.getText().trim();
						if(text.contains(".png")) {
							InputStream image = null;
							try {
								image = new FileInputStream(IMG_PATH+text);
							} catch (IOException e) {
								if(text.contains("map")) {
									InputStream backup = getClass().getResourceAsStream(MAP_BACKUP_PATH);
									File source = new File(IMG_PATH+MAP_CODE);
									source.getParentFile().mkdirs();
									FileUtils.copyInputStreamToFile(backup, source);
									backup.close();
									missingFiles += "map.png \n";
									image = new FileInputStream(IMG_PATH+MAP_CODE);
								}else {
									makeQRCode(clubLink, CLUB_CODE);
									makeQRCode(comnLink, COM_CODE);
									makeQRCode(phswLink, WEB_CODE);
									makeQRCode(sporLink, SPORT_CODE);
									makeQRCode(busrLink, BUS_CODE);
									makeQRCode(colgLink, COL_CODE);
									image = new FileInputStream(IMG_PATH+text);
								}
							}
							if(text.contains("map")) {
								BufferedImage bimg = ImageIO.read(new File(IMG_PATH+MAP_CODE));
								int width = bimg.getWidth();
								int height = bimg.getHeight();
								if(width > height) {
									p.createRun().addPicture(image, Document.PICTURE_TYPE_PNG, IMG_PATH+MAP_CODE, Units.toEMU(MAP_SIDE_LENGTH), Units.toEMU(MAP_SIDE_LENGTH*(height*1.0/width)));
								}else {
									p.createRun().addPicture(image, Document.PICTURE_TYPE_PNG, IMG_PATH+MAP_CODE, Units.toEMU(MAP_SIDE_HEIGHT*(width*1.0/height)), Units.toEMU(MAP_SIDE_HEIGHT));
								}
							}else {
								p.createRun().addPicture(image, Document.PICTURE_TYPE_PNG, IMG_PATH+text, Units.toEMU(QRCODE_SIDE_LENGTH), Units.toEMU(QRCODE_SIDE_LENGTH));
							}
							p.removeRun(0);
							image.close();
						}else if(text.equals("club")) {
							p.removeRun(0);
							p.createRun().setText(clubCap);
						}else if(text.equals("busr")) {
							p.removeRun(0);
							p.createRun().setText(busrCap);
						}else if(text.equals("phsw")) {
							p.removeRun(0);
							p.createRun().setText(phswCap);
						}else if(text.equals("colg")) {
							p.removeRun(0);
							p.createRun().setText(colgCap);
						}else if(text.equals("spor")) {
							p.removeRun(0);
							p.createRun().setText(sporCap);
						}else if(text.equals("comn")) {
							p.removeRun(0);
							p.createRun().setText(comnCap);
						}
					}
				}
			}
		}
		
		//TODO: fix this up if wanted
		/*XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
		XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.FIRST);
		XWPFParagraph footerParagraph = footer.createParagraph();
		footerParagraph.createRun().setText("	-"+sporCap + " " + sporLink);
		footerParagraph.createRun().addBreak();
		footerParagraph.createRun().setText("	-"+clubCap + " " + clubLink);
		footerParagraph.createRun().addBreak();
		footerParagraph.createRun().setText("	-"+busrCap + " " + busrLink);
		footerParagraph.createRun().addBreak();
		footerParagraph.createRun().setText("	-"+phswCap + " " + phswLink);
		footerParagraph.createRun().addBreak();
		footerParagraph.createRun().setText("	-"+colgCap + " " + colgLink);
		footerParagraph.createRun().addBreak();
		footerParagraph.createRun().setText("	-"+comnCap + " " + comnLink);
		footerParagraph.createRun().addBreak();*/
		
		doc.write(new FileOutputStream(TEMPLATE_SCHEDULE_PATH));
		doc.close();
		docIn.close();
		openMissingFilesPopup();
	}
	
	public void createSchedules() throws IOException, InvalidFormatException, WriterException {
		createTemplateSchedule();
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
					schedulePath+"/"+s.getRow(i).getCell(3).getStringCellValue() + "_"
							+ s.getRow(i).getCell(2).getStringCellValue() + "_" + (int)s.getRow(i).getCell(1).getNumericCellValue() + ".docx"));
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
		boolean returnValue = student.removeRot(rotationGroup.getRotNum(), rotationGroup.getName());
		if(returnValue) {
			rotationGroup.getStudents().remove(student);
		}
		return returnValue;
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
			while(group1[rot2].getStudents().size() < MAG_LIMIT && group2[rot1].getStudents().size() < GEN_LIMIT && counter < students.size()) {
				if(group2[rot1].getStudents().size() < GEN_LIMIT && students.get(counter).getRots()[rot1].equals(group1[rot1].getName()) &&
						students.get(counter).getRots()[rot1].equals(group1[rot1].getName()) && students.get(counter).getRots()[rot2].equals(group2[rot2].getName())) {
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
			while(group1[rot2].getStudents().size() < MAG_LIMIT && group2[rot1].getStudents().size() < MAG_LIMIT && counter < students.size()) {
				if(group2[rot1].getStudents().size() < MAG_LIMIT && students.get(counter).getRots()[rot1].equals(group1[rot1].getName()) &&
						students.get(counter).getRots()[rot1].equals(group1[rot1].getName()) && students.get(counter).getRots()[rot2].equals(group2[rot2].getName())) {
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

	public String getHumString() {
		return humString;
	}

	public String getGloString() {
		return gloString;
	}

	public String getSmcString() {
		return smcString;
	}

	public void setRotationNames(String genName, String gloName, String humName, String smcName) throws IOException {
		genString = genName; gloString = gloName; humString = humName; smcString = smcName;
		String rotationNames = "gen:" + genName + "\n" + 
				   	   "glo:" + gloName + "\n" +
				   	   "hum:" + humName + "\n" +
				   	   "smc:" + smcName;
		File fout = new File(ROTATION_NAMES_PATH);
		fout.getParentFile().mkdirs();
		fout.createNewFile();
		FileOutputStream fos = new FileOutputStream(fout); 	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(rotationNames);
		bw.close();
		fos.close();
	}

	public void resetRotationGroups() {
		for(int i = 0; i < 4; i++) {
			smcsGroups[i] = new RotationGroup("S", i);
			humGroups[i] = new RotationGroup("H", i);
			gloGroups[i] = new RotationGroup("GL", i);
			genGroups[i] = new RotationGroup("GE", i);
		}
	}

	public String getClubCap() {
		return clubCap;
	}

	public String getBusrCap() {
		return busrCap;
	}

	public String getPhswCap() {
		return phswCap;
	}

	public String getColgCap() {
		return colgCap;
	}

	public String getSporCap() {
		return sporCap;
	}

	public String getComnCap() {
		return comnCap;
	}

	public String getClubLink() {
		return clubLink;
	}

	public String getBusrLink() {
		return busrLink;
	}

	public String getPhswLink() {
		return phswLink;
	}

	public String getColgLink() {
		return colgLink;
	}

	public String getSporLink() {
		return sporLink;
	}

	public String getComnLink() {
		return comnLink;
	}

	
	public Image getClubImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+CLUB_CODE));
	}
	public Image getBusImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+BUS_CODE));
	}
	public Image getWebImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+WEB_CODE));
	}
	public Image getSportImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+SPORT_CODE));
	}
	public Image getComImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+COM_CODE));
	}
	public Image getColImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+COL_CODE));
	}
	public Image getMapImage() throws FileNotFoundException {
		return new Image(new FileInputStream(IMG_PATH+MAP_CODE));
	}

	public static String getClubCode() {
		return CLUB_CODE;
	}

	public static String getComCode() {
		return COM_CODE;
	}

	public static String getSportCode() {
		return SPORT_CODE;
	}

	public static String getWebCode() {
		return WEB_CODE;
	}

	public static String getColCode() {
		return COL_CODE;
	}

	public static String getBusCode() {
		return BUS_CODE;
	}
	
	public void setInformation(String clubCap, String comnCap, String phswCap, String sporCap, String busrCap, String colgCap,
							   String clubLink, String comnLink, String phswLink, String sporLink, String busrLink, String colgLink) throws WriterException, IOException {
		this.clubCap = clubCap; this.comnCap = comnCap; this.phswCap = phswCap; this.sporCap = sporCap; this.busrCap = busrCap; this.colgCap = colgCap;
		this.clubLink = clubLink; this.comnLink = comnLink; this.phswLink = phswLink; this.sporLink = sporLink; this.busrLink = busrLink; this.colgLink = colgLink;
		makeQRCode(clubLink, CLUB_CODE); makeQRCode(comnLink, COM_CODE); makeQRCode(phswLink, WEB_CODE); makeQRCode(sporLink, SPORT_CODE); makeQRCode(busrLink, BUS_CODE); makeQRCode(colgLink, COL_CODE);
		String captions = "club:" + clubCap + "\n" +
						  "busr:" + busrCap + "\n" +
						  "phsw:" + phswCap + "\n" +
						  "colg:" + colgCap + "\n" +
						  "spor:" + sporCap + "\n" +
						  "comn:" + comnCap + "\n";
		String links = "club:" + clubLink + "\n" +
				  	   "busr:" + busrLink + "\n" +
				  	   "phsw:" + phswLink + "\n" +
				  	   "colg:" + colgLink + "\n" +
				  	   "spor:" + sporLink + "\n" +
				  	   "comn:" + comnLink + "\n";
		File fout = new File(QRCODE_CAPTIONS_PATH);
		fout.getParentFile().mkdirs();
		fout.createNewFile();
		FileOutputStream fos = new FileOutputStream(fout); 	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(captions);
		bw.close();
		fos.close();
		fout = new File(QRCODE_LINKS_PATH);
		fout.getParentFile().mkdirs();
		fout.createNewFile();
		fos = new FileOutputStream(fout); 	 
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(links);
		bw.close();
		fos.close();
	}


	public void setMap(File mapImage) throws IOException {
		FileInputStream sourceImage = new FileInputStream(mapImage);
		File fout = new File(IMG_PATH+MAP_CODE);
		fout.getParentFile().mkdirs();
		fout.createNewFile();
		FileOutputStream mapLocation = new FileOutputStream(fout);
		BufferedImage inputImage = ImageIO.read(sourceImage);
		ImageIO.write(inputImage, "PNG", mapLocation);
		mapLocation.close();
		sourceImage.close();
	}
	
	public List<Student> getStudents(){
		return students;
	}
	
	public RotationGroup[] getSmcsGroups(){
		return smcsGroups;
	}
	public RotationGroup[] getHumGroups(){
		return humGroups;
	}
	public RotationGroup[] getGloGroups(){
		return gloGroups;
	}
	public RotationGroup[] getGenGroups(){
		return genGroups;
	}

	public String getSchedulePath() {
		return schedulePath;
	}

	public void setSchedulePath(String schedulePath) throws IOException {
		this.schedulePath = schedulePath;
		String scheduleDirectory = "dir:"+schedulePath;
		File fout = new File(SCHEDULE_INFO_PATH);
		fout.getParentFile().mkdirs();
		fout.createNewFile();
		FileOutputStream fos = new FileOutputStream(fout); 	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(scheduleDirectory);
		bw.close();
		fos.close();
	}

	public void changeStudentSchedule(String[] rots, Student student) throws IOException {
		for(int i = 0; i < 4; i++) {
			if(student.getRots()[i].equals("GE")) {
				removeStudent(student, genGroups[i]);
			}else if(student.getRots()[i].equals("GL")) {
				removeStudent(student, gloGroups[i]);
			}else if(student.getRots()[i].equals("H")) {
				removeStudent(student, humGroups[i]);
			}else if(student.getRots()[i].equals("S")) {
				removeStudent(student, smcsGroups[i]);
			}else if(student.getRots()[i].equals("n/a")) {
				break;
			}
		}
		for(int i = 0; i < 4; i++) {
			if(rots[i].equals("GE")) {
				addStudent(student, genGroups[i]);
			}else if(rots[i].equals("GL")) {
				addStudent(student, gloGroups[i]);
			}else if(rots[i].equals("H")) {
				addStudent(student, humGroups[i]);
			}else if(rots[i].equals("S")) {
				addStudent(student, smcsGroups[i]);
			}else if(rots[i].equals("n/a")) {
				break;
			}
		}
		FileInputStream in = new FileInputStream(new File(STUDENT_DATA_PATH));
		Workbook wb = new XSSFWorkbook(in);
		Sheet s = wb.getSheet("ScheduleData");
		int left = 1, right = s.getLastRowNum();
		int mid = (left+right)/2;
		while(left <= right) {
			mid = (left+right)/2;
			int currentID = (int) s.getRow(mid).getCell(1).getNumericCellValue();
			if(currentID == student.getId()) {
				s.getRow(mid).getCell(4).setCellValue(student.getRots()[0]);
				s.getRow(mid).getCell(5).setCellValue(student.getRots()[1]);
				if(!student.getRots()[2].equals("n/a")) s.getRow(mid).getCell(6).setCellValue(student.getRots()[2]);;
				if(!student.getRots()[3].equals("n/a")) s.getRow(mid).getCell(7).setCellValue(student.getRots()[3]);;
				break;
			}else if(currentID < student.getId()) {
				left = mid+1;

			}else {
				right = mid-1;
			}
		}
		in.close();
		FileOutputStream out = new FileOutputStream(new File(STUDENT_DATA_PATH));
		wb.write(out);
		out.close();
		wb.close();
	}

	public String getMissingFiles() {
		return missingFiles;
	}

	public List<Student> getStudentsS() {
		return studentsS;
	}

	public List<Student> getStudentsG() {
		return studentsG;
	}

	public List<Student> getStudentsH() {
		return studentsH;
	}

	public List<Student> getStudentsSG() {
		return studentsSG;
	}

	public List<Student> getStudentsSH() {
		return studentsSH;
	}

	public List<Student> getStudentsGH() {
		return studentsGH;
	}

	public List<Student> getStudentsSGH() {
		return studentsSGH;
	}
}
