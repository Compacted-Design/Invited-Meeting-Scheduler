package compactedDesign.invitedMeetingScheduler;

public class AppLoop implements Runnable{
	private boolean running = false; //tells the program whether the program is running or not.
	private final double UPDATE_CAP = 1.0/59.95; //this caps updates and rendering to 60 times a second
	
	public AppLoop() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initiation sequence for the main thread of the program
	 */
	public void init() {
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
	}
	
	/**
	 * Stop sequence for the main thread of the program
	 */
	public void stop() {
		running = false;
		System.exit(0);
	}

}
