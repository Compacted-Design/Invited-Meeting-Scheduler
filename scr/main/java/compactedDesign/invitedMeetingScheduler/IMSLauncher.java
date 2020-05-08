package compactedDesign.invitedMeetingScheduler;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * <h1> IMSLauncher </h1>
 * 
 * <p>The Invited Meeting Scheduler Launcher program initializes 
 * the application.</p>
 * 
 * @author Jeffrey Jiang
 * @version 0.1
 * @since 2020-05-07
 */
public class IMSLauncher extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		//Setting up the window
		primaryStage.setTitle("Invited Meeting Scheduler");//sets the title of the window
		primaryStage.setOnCloseRequest(e -> System.exit(0)); // stops the program upon closing the window
		primaryStage.setResizable(false); //change later
		
		Parent root = FXMLLoader.load(getClass().getResource("/views/StartView.fxml"));
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
