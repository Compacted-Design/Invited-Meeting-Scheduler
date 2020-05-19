package compactedDesign.invitedMeetingScheduler.data;

public class Student {
	private String firstName;
	private String lastName;
	private String schoolName; //Change to ID later
	private boolean smcs;
	private boolean hum;
	private boolean global;
	private int id;
	private int rotation;
	
	
	
	
	public Student(String firstName, String lastName, String schoolName, boolean smcs, boolean hum, boolean global, int id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.schoolName = schoolName;
		this.smcs = smcs;
		this.hum = hum;
		this.global = global;
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getSchoolName() {
		return schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}


	public boolean isSmcs() {
		return smcs;
	}


	public void setSmcs(boolean smcs) {
		this.smcs = smcs;
	}


	public boolean isHum() {
		return hum;
	}


	public void setHum(boolean hum) {
		this.hum = hum;
	}


	public boolean isGlobal() {
		return global;
	}


	public void setGlobal(boolean global) {
		this.global = global;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getRotation() {
		return rotation;
	}


	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	

}
