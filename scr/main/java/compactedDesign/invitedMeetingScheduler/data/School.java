package compactedDesign.invitedMeetingScheduler.data;

public class School {
	
	private String name;
	private int count;
	
	public School(String name) {
		this.name = name;
		count++;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}
	
	

}
