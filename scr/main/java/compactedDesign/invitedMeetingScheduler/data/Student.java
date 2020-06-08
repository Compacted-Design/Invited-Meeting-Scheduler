package compactedDesign.invitedMeetingScheduler.data;

public class Student implements Comparable<Student>{
	private String firstName;
	private String lastName;
	private String schoolName; //Change to ID later
	
	//temporary values used to make sure a student doesn't have duplicate rotations.
	private boolean smcs;
	private boolean hum;
	private boolean global;
	private boolean gen = true;
	
	//Final values used to check whether a student should or shouldn't have a given magnet rotation.
	private final boolean SMCS;
	private final boolean HUM;
	private final boolean GLOBAL;
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
	
	/**
	 * Program used to set a student's rotation
	 * @param rotNum specifies which rotation to be set
	 * @param name specifies the name of the rotation to be set
	 * @return if the rotation was successfully set.
	 */
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
	/**
	 * Program used to remove a student's rotation
	 * @param rotNum specifies the rotation number
	 * @param name Used to check if said rotation is in a given slot.
	 * @return if the rotation was successfully removed.
	 */
	public boolean removeRot(int rotNum, String name) {
		if(name.equals("GE") && !gen && rots[rotNum].equals(name)) {
			gen = true;
			rots[rotNum] = "";
		}else if(name.equals("GL") && !global && GLOBAL && rots[rotNum].equals(name)) {
			global = true;
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


	public boolean isSMCS() {
		return SMCS;
	}


	public boolean isHUM() {
		return HUM;
	}


	public boolean isGLOBAL() {
		return GLOBAL;
	}


	@Override
	public int compareTo(Student o) {
		if(this.getLastName().compareToIgnoreCase(o.getLastName()) == 0) {
			return this.getFirstName().compareToIgnoreCase(o.getFirstName());
		}
		return this.getLastName().compareToIgnoreCase(o.getLastName());
	}
	
	


	

}
