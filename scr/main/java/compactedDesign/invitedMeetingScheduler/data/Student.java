package compactedDesign.invitedMeetingScheduler.data;

public class Student {
	private String firstName;
	private String lastName;
	private String schoolName; 
	private int id;
	
	//A = accepted, W = want to observe
	private boolean smcsA;
	private boolean humA;
	private boolean globalA;
	//W values are experimental
	private boolean smcsW; 
	private boolean humW;
	private boolean globalW;
	
	private int rotation;
	
	
	
	
	public Student(String firstName, String lastName, String schoolName, boolean smcsA, boolean humA, boolean globalA, int id, boolean smcsW, boolean humW, boolean globalW) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.schoolName = schoolName;
		this.smcsA = smcsA;
		this.humA = humA;
		this.globalA = globalA;
		this.id = id;
		this.smcsW = smcsW;
		this.humW = humW;
		this.globalW = globalW;
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


	public boolean isSmcsA() {
		return smcsA;
	}


	public void setSmcsA(boolean smcsA) {
		this.smcsA = smcsA;
	}


	public boolean isHumA() {
		return humA;
	}


	public void setHumA(boolean humA) {
		this.humA = humA;
	}


	public boolean isGlobalA() {
		return globalA;
	}


	public void setGlobalA(boolean globalA) {
		this.globalA = globalA;
	}


	public boolean isSmcsW() {
		return smcsW;
	}


	public void setSmcsW(boolean smcsW) {
		this.smcsW = smcsW;
	}


	public boolean isHumW() {
		return humW;
	}


	public void setHumW(boolean humW) {
		this.humW = humW;
	}


	public boolean isGlobalW() {
		return globalW;
	}


	public void setGlobalW(boolean globalW) {
		this.globalW = globalW;
	}
	

}
