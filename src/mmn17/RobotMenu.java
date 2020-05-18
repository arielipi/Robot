 

package mmn17;

import java.awt.*;
import java.awt.event.*;

public class RobotMenu {
	private Frame mainFrame;
	private Scene scene;
	private MenuItemListener menuItemListener;

	public RobotMenu(Frame frame, Scene scene) {
		this.mainFrame = frame;
		this.scene = scene;
		menuItemListener = new MenuItemListener();
		prepareGUI(frame);
	}

	private void prepareGUI(Frame mainFrame) {
		mainFrame.setVisible(true);
	}

	public void createCharMenuItem(Menu menu, String description, Character key) {
		String descriptionWithKey = "";
		Character virtualKey = ' ';
	
		descriptionWithKey = description + "    " + key;
		virtualKey = key;
		

		MenuItem menuItem = new MenuItem(descriptionWithKey);
		menuItem.setActionCommand("" + (int) (virtualKey));

		menuItem.addActionListener(menuItemListener);
		menu.add(menuItem);
	}
	
	void createFunctionMenuItem(Menu menu, String description, int fNum) {
		
		String descriptionWithKey = description + "    " + "F" + fNum;
		Character virtualKey = (char) (97 + (fNum - 1));
		createMenuItem(menu, descriptionWithKey, virtualKey);
	}
	
	void createArrowMenuItem(Menu menu, String description, String arrow) {
		Character arrowKeyCode = ' ';
		String descriptionWithKey = description + " ARROW "+arrow;
		switch (arrow) {
		case "UP":
			arrowKeyCode = 150;
			break;
		case "DOWN":
			arrowKeyCode = 152;
			break;
		case "LEFT":
			arrowKeyCode = 149;
			break;
		case "RIGHT":
			arrowKeyCode = 151;
			break;	

		default:
			break;
		}
		createMenuItem(menu, descriptionWithKey, arrowKeyCode);
	}
	
	

	
	public void createMenuItem(Menu menu, String description, int keyCode) {

		MenuItem menuItem = new MenuItem(description);
		menuItem.setActionCommand("" + keyCode);

		menuItem.addActionListener(menuItemListener);
		menu.add(menuItem);
	}

	public void showMenu() {
		// create a menu bar
		final MenuBar menuBar = new MenuBar();

		// create menus
		Menu movementMenu = new Menu("Robot Movement");
		Menu cameraMenu = new Menu("Camera");
		Menu lightMenu = new Menu("Adjust Light");
		Menu helpMenu = new Menu("Help");
		Menu quitMenu = new Menu("Quit");

		createCharMenuItem(movementMenu, "RELERASE KEYS", '`');
		createCharMenuItem(movementMenu, "Forward", 'W');
		createCharMenuItem(movementMenu, "Backword", 'S');
		createCharMenuItem(movementMenu, "Strafe Left", 'A');
		createCharMenuItem(movementMenu, "Strafe Right", 'D');
		createCharMenuItem(movementMenu, "Rotate Left", 'Q');
		createCharMenuItem(movementMenu, "Rotate Left", 'E');
		createCharMenuItem(movementMenu, "Right Arm Forward", 'Y');
		createCharMenuItem(movementMenu, "Right Arm Backward", 'H');
		createCharMenuItem(movementMenu, "Right Forearm Forward", 'U');
		createCharMenuItem(movementMenu, "Right Forearm Backward", 'J');
		createCharMenuItem(movementMenu, "Right Palm Forward", '7');
		createCharMenuItem(movementMenu, "Right Palm Backward", '6');
		createCharMenuItem(movementMenu, "Left Arm Forward", 'T');
		createCharMenuItem(movementMenu, "Left Arm Backward", 'G');
		createCharMenuItem(movementMenu, "Left Forearm Forward", 'R');
		createCharMenuItem(movementMenu, "Left Forearm Backward", 'F');
		createCharMenuItem(movementMenu, "Left Palm Forward", '4');
		createCharMenuItem(movementMenu, "Left Palm Backward", '5');
		createCharMenuItem(movementMenu, "Head Move Aroud Lift Up", 'O');
		createCharMenuItem(movementMenu, "Head Move Aroud Lift Down", 'L');
		createCharMenuItem(movementMenu, "Head Move Aroud Rotate Left", 'K');
		createCharMenuItem(movementMenu, "Head Move Aroud Rotate right", ';');

		createCharMenuItem(cameraMenu, "RELERASE KEYS", '`');
		createCharMenuItem(cameraMenu, "Switch First Person To Third Person View", ',');
		createCharMenuItem(cameraMenu, "Rotate Current Camera Left", 'Z');
		createCharMenuItem(cameraMenu, "Rotate Current Camera Right", 'X');
		createCharMenuItem(cameraMenu, "Rotate Current Camera Up", 'V');
		createCharMenuItem(cameraMenu, "Rotate Current Camara Down", 'C');
		createCharMenuItem(cameraMenu, "Pan Up", '=');
		createCharMenuItem(cameraMenu, "Pan Down", '-');
		
		createArrowMenuItem(cameraMenu, "Zoom In", "UP");
		createArrowMenuItem(cameraMenu, "Zoom Out", "DOWN");
		createArrowMenuItem(cameraMenu, "Pan Left", "LEFT");
		createArrowMenuItem(cameraMenu, "Pan Right", "RIGHT");

		createCharMenuItem(lightMenu, "RELERASE KEYS", '`');
		createFunctionMenuItem(lightMenu, "Select Spot Light", 11);
		createFunctionMenuItem(lightMenu, "Decrease Light", 2);
		createFunctionMenuItem(lightMenu, "Increase Light", 3);
		createFunctionMenuItem(lightMenu, "Decrease Red Light", 4);
		createFunctionMenuItem(lightMenu, "Increase Red Light", 5);
		createFunctionMenuItem(lightMenu, "Decrease Green Light", 6);
		createFunctionMenuItem(lightMenu, "Increase Green Light", 7);
		createFunctionMenuItem(lightMenu, "Decrease Blue Light", 8);
		createFunctionMenuItem(lightMenu, "Increase Blue Light", 9);

		createMenuItem(quitMenu, "Exit ESC", 27);
		createFunctionMenuItem(helpMenu, "Help Message", 1);
		createFunctionMenuItem(helpMenu, "About", 12);
		
		menuBar.add(helpMenu);
		menuBar.add(movementMenu);
		menuBar.add(cameraMenu);
		menuBar.add(lightMenu);
		menuBar.add(quitMenu);

		// Add Menu Bar to the frame
		mainFrame.setMenuBar(menuBar);
		mainFrame.setVisible(true);
	}

	private void createCharMenuItem(Menu adjustAmbientLightMenu, String description, String functionKey, boolean fkey) {
		String descriptionWithKey = "";
		Character virtualKey = ' ';

		char keyLetter = functionKey.toCharArray()[0];
		char keyNumber = functionKey.toCharArray()[1];

		if (fkey) {
			descriptionWithKey = description + "    " + "F" + functionKey.toCharArray()[1];
			virtualKey = (char) (97 + (keyNumber - '1'));
		} else {
			descriptionWithKey = description + "    " + keyLetter + "" + keyNumber;
			virtualKey = (char) (97 + (keyNumber - '1'));
		}

		MenuItem menuItem = new MenuItem(descriptionWithKey);
		menuItem.setActionCommand("" + (int) (virtualKey));

		menuItem.addActionListener(menuItemListener);
		adjustAmbientLightMenu.add(menuItem);
	}
	
	
	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			Character key = (char) Integer.parseInt(command);
			System.out.println("Menu Item Clicked with Command: " + key);
			scene.prevKeyStates[key] = false;
			scene.keyStates[key] = true;
		}
	}
}
