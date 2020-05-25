package compactedDesign.invitedMeetingScheduler.data;

import java.util.ArrayList;
import java.util.List;

public class RotationGroup {
	
	private List<School> schoolCount;
	private List<Student> students;
	private int rotNum;
	private String name;
	
	public RotationGroup(String name, int rotNum) {
		schoolCount = new ArrayList<>();
		students = new ArrayList<>();
		this.name = name;
		this.rotNum = rotNum;
	}

	public List<School> getSchoolCount() {
		return schoolCount;
	}

	public List<Student> getStudents() {
		return students;
	}

	public int getRotNum() {
		return rotNum;
	}

	public String getName() {
		return name;
	}
	
	

}
