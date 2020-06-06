package compactedDesign.invitedMeetingScheduler.data;

import java.util.Comparator;

public class StudentIdComparator implements Comparator<Student>{

	@Override
	public int compare(Student o1, Student o2) {
		return o1.getId()-o2.getId();
	}

}
