package compactedDesign.invitedMeetingScheduler.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

	private static String genString = "Media Center - PHS Whole School Magnet: Parents Room 41 - PHS Whole School Magnet: Students";
	private static String humString = "Room 8 - Humanities House";
	private static String gloString = "Room 33 - Global Ecology House";
	private static String smcString = "Room 195 - Science, Math, Computer Science House";

	public void loadData() {

	}

	public static void createSchedule() throws IOException, InvalidFormatException {
		FileInputStream in = new FileInputStream(new File("scr/main/resources/data/Data.xlsx"));
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
						} if (text != null && text.contains("Last")) {
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
									text = text.replace("ROT1", rotMessage(s.getRow(i).getCell(4).getStringCellValue()));
									r.setText(text, 0);
								} else if (text != null && text.contains("ROT2")) {
									text = text.replace("ROT2", rotMessage(s.getRow(i).getCell(5).getStringCellValue()));
									r.setText(text, 0);
								} else if (text != null && text.contains("ROT3")) {
									text = text.replace("ROT3", rotMessage(s.getRow(i).getCell(6).getStringCellValue()));
									r.setText(text, 0);
								} else if (text != null && text.contains("ROT4")) {
									text = text.replace("ROT4", rotMessage(s.getRow(i).getCell(7).getStringCellValue()));
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
		System.exit(0);
	}
	
	private static String rotMessage(String rot) {
		if(rot.equals("H")) {
			return humString;
		}else if(rot.equals("GL")) {
			return gloString;
		}else if(rot.equals("S")) {
			return smcString;
		}
		return genString;
	}

}
