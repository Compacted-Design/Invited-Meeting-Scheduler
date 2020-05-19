package compactedDesign.invitedMeetingScheduler.data;

import java.util.Comparator;

import org.apache.poi.ss.usermodel.Row;

public class RowComparator implements Comparator<Row>{
	
	private static int idColumn = 0;

	@Override
	public int compare(Row o1, Row o2) { //Change to be more adaptive to ID
		return (int) (o1.getCell(idColumn).getNumericCellValue()) - (int) (o2.getCell(idColumn).getNumericCellValue());
	}

	public static void setIdColumn(int idColumn) {
		RowComparator.idColumn = idColumn;
	}

}
