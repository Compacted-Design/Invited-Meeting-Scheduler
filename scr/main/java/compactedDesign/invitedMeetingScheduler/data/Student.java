package compactedDesign.invitedMeetingScheduler.data;

public class Student {
	private String firstName;
	private String lastName;
	private String schoolName; //Change to ID later
	private boolean smcs;
	private boolean hum;
	private boolean global;
	private final boolean SMCS;
	private final boolean HUM;
	private final boolean GLOBAL;
	private boolean gen = true;
	private int id;
	//Change to an array?
	private String[] rots = {"","","n/a","n/a"};
	
	public Student(int id, String firstName, String lastName, String schoolName, boolean smcs, boolean global, boolean hum ) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.schoolName = schoolName;
		this.smcs = smcs;
		this.hum = hum;
		this.global = global;
		SMCS = smcs;
		HUM = hum;
		GLOBAL = global;
		this.id = id;
		if(smcs && global && hum) {
			rots[2] = ""; rots[3] = "";
		}else if((smcs && (global || hum)) || (global && hum)) {
			rots[2] = "";
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


	public boolean isHum() {
		return hum;
	}


	public boolean isGlobal() {
		return global;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String[] getRots() {
		return rots;
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
		if(rotNum == 0 || rotNum == 1) {
			rots[rotNum] = name;
			return true;
		}else if(!rots[rotNum].equals("n/a")) {
			rots[rotNum] = name;
			return true;
		}
		return false;
	}
	public boolean removeRot(int rotNum, String name) {
		if(name.equals("GE") && !gen && rots[rotNum].equals(name)) {
			gen = true;
			rots[rotNum] = "";
		}else if(name.equals("GL") && !global && GLOBAL && rots[rotNum].equals(name)) {
			global = false;
			rots[rotNum] = "";
		}else if(name.equals("H") && !hum && HUM && rots[rotNum].equals(name)) {
			hum = true;
			rots[rotNum] = "";
		}else if(name.equals("S") && !smcs && SMCS && rots[rotNum].equals(name)) {
			smcs = true;
			rots[rotNum] = "";
		}else {
			return false;
		}
		return true;
		
	}


	@Override
	public String toString() {
		return "Student [id=" + id + "]";
	}
	


	

}
