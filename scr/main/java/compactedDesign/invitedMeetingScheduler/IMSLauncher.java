package compactedDesign.invitedMeetingScheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * <h1> IMSLauncher </h1>
 * 
 * <p>The Invited Meeting Scheduler Launcher program initializes 
 * the application.</p>
 * 
 * @author Jeffrey Jiang
 * @version 0.2
 * @since 2020-05-08
 */
public class IMSLauncher extends Application {
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		//Setting up the window
		primaryStage.setTitle("Invited Meeting Scheduler");//sets the title of the window
		primaryStage.setOnCloseRequest(e -> System.exit(0)); // stops the program upon closing the window
		primaryStage.setResizable(false); // maybe change later
		
		Pane root = FXMLLoader.load(getClass().getResource("/views/StartView.fxml"));
		Scene scene = new Scene(root,600,300);
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}
	
	/**
	 * Starting method. 
	 * @param args Used for launch method
	 */
	public static void main(String[] args) {launch(args);}

}
