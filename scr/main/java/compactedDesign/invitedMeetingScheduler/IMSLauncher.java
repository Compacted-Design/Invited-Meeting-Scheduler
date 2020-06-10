package compactedDesign.invitedMeetingScheduler;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import compactedDesign.invitedMeetingScheduler.data.DataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * <h1> IMSLauncher </h1>
 * 
 * <p>The Invited Meeting Scheduler Launcher program initializes []
 * the application.</p>
 * 
 * @author Jeffrey Jiang
 * @version 0.3
 * @since 2020-06-07
 */
public class IMSLauncher extends Application {
	
	/*
	 * TODO: Change dl to a local variable.
	 */
	private static DataLoader dl;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		dl = new DataLoader();
		
		//Setting up the window
		primaryStage.setTitle("Invited Meeting Scheduler - by Compacted Design");//sets the title of the window
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/IMSIcon.png")));
		primaryStage.setOnCloseRequest(e -> System.exit(0)); // stops the program upon closing the window
		//primaryStage.setResizable(false); // maybe change later
		Pane root = new Pane();
		Scene scene = new Scene(root,1000,800);
		primaryStage.setScene(scene);
		primaryStage.show();
		root.getScene().setRoot(FXMLLoader.load(getClass().getResource("/views/StartView.fxml")));
		
	}
	
	/**
	 * Starting method. 
	 * @param args Used for launch method
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public static void main(String[] args) throws InvalidFormatException, IOException {
		launch(args);
	}
	/**
	 * Gets the DataLoader from the Launcher
	 * @return dataLoader
	 */
	public static DataLoader getDl() {
		return dl;
	}
	
	

}
