package compactedDesign.invitedMeetingScheduler.data;

public class Student {
	private String firstName;
	private String lastName;
	private String schoolName; //Change to ID later
	private boolean smcs;
	private boolean hum;
	private boolean global;
	private boolean gen = true;
	private int id;
	//Change to an array?
	private String rot1 = "", rot2 = "", rot3 = "n/a", rot4 = "n/a";
	
	public Student(int id, String firstName, String lastName, String schoolName, boolean smcs, boolean global, boolean hum ) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.schoolName = schoolName;
		this.smcs = smcs;
		this.hum = hum;
		this.global = global;
		this.id = id;
		if(smcs && global && hum) {
			rot3 = ""; rot4 = "";
		}else if((smcs && (global || hum)) || (global && hum)) {
			rot3 = "";
		}
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


	public String getRot1() {
		return rot1;
	}


	public String getRot2() {
		return rot2;
	}


	public String getRot3() {
		return rot3;
	}


	public String getRot4() {
		return rot4;
	}
	
	public boolean setRot(int rotNum, String name) {
		if(name.equals("GE") && gen) {
			gen = false;
		}else if(name.equals("GL") && global) {
			global = false;
		}else if(name.equals("H") && hum) {
			hum = false;
		}else if(name.equals("S") && smcs) {
			smcs = false;
		}else {
			return false;
		}
		if(rotNum == 1) {
			rot1 = name;
			return true;
		}else if(rotNum == 2) {
			rot2 = name;
			return true;
		}else if(!rot3.equals("n/a") && rotNum == 3) {
			rot3 = name;
			return true;
		}else if(!rot4.equals("n/a") && rotNum == 4) {
			rot4 = name;
			return true;
		}
		return false;
	}


	

}
