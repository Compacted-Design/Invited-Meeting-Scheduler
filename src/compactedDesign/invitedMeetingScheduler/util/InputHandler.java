package compactedDesign.invitedMeetingScheduler.util;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
/**
 * <h1> Input Handler </h1>
 * The InputHandler program implements an object to 
 * handle mouse and keyboard based inputs with javafx.
 * <p>
 * 
 * @author Jeffrey Jiang
 * @version 1.3
 * @since 2020-01-12
 */
//Class I copied from one of my other programs.
public class InputHandler {
	private Set<KeyCode> pressedKeys; //Stores unique keys
	private Set<MouseButton> pressedMouses; //Stores unique mouse buttons
	private double mouseX;
	private double mouseY;
	/**
	 * Constructor of InputHandler.
	 * @param scene Scene used to get input from
	 */
	public InputHandler(Scene scene) {
		
		pressedKeys = new HashSet<>();
		pressedMouses = new HashSet<>();
		
		//Key Listener
		scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
		scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
		        
		//Mouse Listener
		scene.setOnMousePressed(e -> pressedMouses.add(e.getButton()));
		scene.setOnMouseReleased(e -> pressedMouses.remove(e.getButton()));
		scene.setOnMouseMoved(e -> {
			mouseX = e.getX();
			mouseY = e.getY();
		});
		scene.setOnMouseDragged(e ->{ 
			mouseX = e.getX();
			mouseY = e.getY();	
		});
		
	}
	/**
	 * Method returns whether or not a specified key is pressed.
	 * @param e Key checked for
	 * @return boolean Whether the key is pressed or not
	 */
	public boolean isKeyPressed(KeyCode e) {
		return pressedKeys.contains(e);
	}
	/**
	 * Method returns whether or not a specified mouse button is pressed.
	 * @param e Mouse button checked for
	 * @return boolean Whether the mouse button is pressed or not.
	 */
	public boolean isMousePressed(MouseButton e) {
		return pressedMouses.contains(e);
	}
	/**
	 * Method returns the mouse x position.
	 * @return double The mouse x position
	 */
	public double getMouseX() {
		return mouseX;
	}
	/**
	 * Method returns the mouse y position.
	 * @return double The mouse y position
	 */
	public double getMouseY() {
		return mouseY;
	}
	/**
	 * Method returns whether or not the mouse is in
	 * a specified area.
	 * @param x This is used as the base x value
	 * @param y This is used as the base y value
	 * @param offX This is used as the x offset
	 * @param offY This is used as the y offset
	 * @return boolean whether or not the mouse is 
	 * in a specified area
	 */
	public boolean isInLocation(double x,double y, double offX, double offY) {
		boolean returnValue;
		if(offX < 0) { //checks if offX is position or negative
			returnValue = mouseX <= x && mouseX >= x+offX;
		}else {
			returnValue = mouseX >= x && mouseX <= x+offX;
		}
		if(offX < 0) {//checks if offY is position or negative
			returnValue = returnValue && mouseY <= y && mouseY >= y+offY;
		}else {
			returnValue = returnValue &&mouseY >= y && mouseY <= y+offY;
		}
		return returnValue;
	}

}

