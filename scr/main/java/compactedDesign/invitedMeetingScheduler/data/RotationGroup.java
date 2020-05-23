package compactedDesign.invitedMeetingScheduler.data;

import java.util.ArrayList;
import java.util.List;

public class RotationGroup {
	
	private List<School> schoolCount;
	private List<Student> students;
	private RotationID id;
	private String nameID;
	
	public RotationGroup(RotationID id, String nameID) {
		schoolCount = new ArrayList<>();
		students = new ArrayList<>();
		this.id = id;
		this.nameID = nameID;
	}

	public List<School> getSchoolCount() {
		return schoolCount;
	}

	public List<Student> getStudents() {
		return students;
	}

}
