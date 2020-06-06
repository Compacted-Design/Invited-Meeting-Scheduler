package compactedDesign.invitedMeetingScheduler.controllers.popup;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.zxing.WriterException;

import compactedDesign.invitedMeetingScheduler.IMSLauncher;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class CreateSchedulesPopUpViewController {
	
	@FXML
	private Label informationLabel;
	
	@FXML
	private Pane root;
	
	@FXML
	private Canvas loadingBar;
	
	private GraphicsContext gc;
	
	@FXML
	private void initialize()  {
		gc = loadingBar.getGraphicsContext2D();
		Thread threadULB = new Thread(new UpdateLoadingBar());
		threadULB.start();
		Thread threadCS = new Thread(new CreateSchedulesThread());
		threadCS.start();
	}
	
	private class UpdateLoadingBar implements Runnable {
		
		private final double UPDATE_CAP = 1.0/59.95; //this caps updates and rendering to 60 times a second
		private int cap = -1;

		
		public void init() {
			cap = IMSLauncher.getDl().getStudents().size();
		}
		
		@Override
		public void run() {
			
			init();
			
			boolean render = false;
			double currentTime = 0 , previousTime = System.nanoTime() / Math.pow(10,9); //nano to seconds 
			double elapsedTime = 0 , unprocessedTime = 0;
			
			//counting frames and frame rate 
			double frameTime = 0;
			gc.setFill(Paint.valueOf("Green"));
			
			while(IMSLauncher.getDl().getSchedulesCompleted() < cap) {
				render = false; //resets render  | change this to true to see effect when the fps isn't locked
				
				//Time between processes
				currentTime = System.nanoTime() / Math.pow(10,9);
				elapsedTime = currentTime - previousTime;
				previousTime = currentTime;
				
				unprocessedTime += elapsedTime;
				frameTime += elapsedTime;
				
				while(unprocessedTime >= UPDATE_CAP) {
					unprocessedTime -= UPDATE_CAP; //resents unprocessedTime
					render = true;
					
					
					if(frameTime >= 1.0) { //resets frames after 60
						frameTime = 0;
					}
				}
					
				if(render) {
					gc.clearRect(0, 0, loadingBar.getWidth(), loadingBar.getHeight());
					gc.setFill(Paint.valueOf("Cyan"));
					gc.fillRect(0, 0, loadingBar.getWidth()*(IMSLauncher.getDl().getSchedulesCompleted()), loadingBar.getHeight());
					gc.setFill(Paint.valueOf("Green"));
					gc.fillRect(0, 0, loadingBar.getWidth()*(IMSLauncher.getDl().getSchedulesCompleted()*1.0/cap), loadingBar.getHeight());
						
				}else {
					try {Thread.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
					//puts the thread to sleep for 1 millisecond to free up the processor a bit. This is because there is nothing to do, nothing is being rendered.
				}
				
			}
			informationLabel.setVisible(false);
			informationLabel.setDisable(true);
			gc.clearRect(0, 0, loadingBar.getWidth(), loadingBar.getHeight());
			gc.setFill(Paint.valueOf("Black"));
			gc.setFont(new Font(14));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText("Schedules have been Created", 100, 10);
			
		}
		
	}
	
	private class CreateSchedulesThread implements Runnable{

		@Override
		public void run() {
			try {
				IMSLauncher.getDl().createSchedules();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
