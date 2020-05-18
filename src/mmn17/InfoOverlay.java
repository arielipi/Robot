 

package mmn17;

import javax.swing.JOptionPane;

import model.Consts;
import model.Vector3D;

public class InfoOverlay {

	public void displayOverlayText(Scene scene) {
		displayCameraText(scene);
		displayLightText(scene);
	}

	private void displayLightText(Scene scene) {
		String text = "Selected Light = ";
		if (scene.getCameraMode() == Consts.VIEW_SPOT_LIGHT) {
			text += "Spot (Diffuse)" + ", Red = " + round(scene.getSpotLight().getDiffuseParams().getX()) + ", Green = "
					+ round(scene.getSpotLight().getDiffuseParams().getY()) + ", Blue = "
					+ round(scene.getSpotLight().getDiffuseParams().getZ());
			text += "| (Change Camera 3D person to change the Global Light)";
		} else {
			text += "Global (Ambient)" + ", Red = " + round(scene.getGloablLight().getAmbientLightParams().getX()) + ", Green = "
					+ round(scene.getGloablLight().getAmbientLightParams().getY()) + ", Blue = "
					+ round(scene.getGloablLight().getAmbientLightParams().getZ());
			text += "| (Change Camera to Spot Light to change the Spot Light)";
		}

		scene.displayText(0, 130, text, 0, 1.0f, 0);
	}

	void displayCameraText(Scene scene) {
		String text = "";
		String cameraModeText = "";
		if (scene.getCameraMode() == Consts.VIEW_1P) {
			cameraModeText = "Current Camera Mode: First Person";
		}
		if (scene.getCameraMode() == Consts.VIEW_3P) {
			cameraModeText = "Current Camera Mode: Third Person";
		} else if (scene.getCameraMode() == Consts.VIEW_SPOT_LIGHT) {
			cameraModeText = "Current Camera Mode: From Spot Light";
		}

		text = text + cameraModeText;
		text += ", Position: (" + round(getCurrentCameraPostion(scene).getX()) + ","
				+ round(getCurrentCameraPostion(scene).getY()) + "," + round(getCurrentCameraPostion(scene).getZ()) + ")";

		text += ", Look At Point: (" + round(getCurrentCameraLookAtPoint(scene).getX()) + ","
				+ round(getCurrentCameraLookAtPoint(scene).getY()) + "," + round(getCurrentCameraLookAtPoint(scene).getZ()) + ")";
		scene.displayText(0, 100, text, 0, 1.0f, 0);
	}

	Vector3D getCurrentCameraPostion(Scene scene) {
		return scene.getCurrentCamera().getLastPosition();

	}

	Vector3D getCurrentCameraLookAtPoint(Scene scene) {
		return scene.getCurrentCamera().getLastLookAtPoint();

	}

	public static float round(float a) {
		return (float) (Math.round(a * 100.0) / 100.0);
	}

	public void displayHelp() {
		String helpText = "Mouse control:\n"
						+ "Left/Right click - control camera/robot viewing direction\n"
						+ "\n"
						+ "Keyboard control:\n"
						+ "Robot control:\n"
						+ "w, s - move forward/backward\n"
						+ "a, d - move left/right (Strafe left/right)\n"
						+ "q, e - rotate CCW, CW\n"
						+ "k, ; - rotate head left, right\n"
						+ "o, l - rotate head up, down\n"
						+ "y, h (t, g) - rotate right arm (left arm)\n"
						+ "u, j (r, f) - rotate right forearm (left forearm)\n"
						+ "6, 7 (4, 5) NOT NUMPAD - rotate right palm (left palm) \n"
						+ "\n"
						+ "Camera control:\n"
						+ "Arrow Up - move forward\n"
						+ "Arrow Down - move backward\n"
						+ "Arrow Left - move left (Strafe left)\n"
						+ "Arrow Right - move right (Strafe right)\n"
						+ "- (minue), = (equal) NOT NUMPAD  - move camera downward/upward\n"
						+ "home, end - rotate camera down/up\n"
						+ "delete, pgdwn - rotate camera left/right\n\n"
						+ "Color edit control\n"
						+ "F2, F3 - decrease/increase bright (all colors)\n"
						+ "F4, F5 - decrease/increase red color\n"
						+ "F6, F7 - decrease/increase green color\n"
						+ "F8, F9 - decrease/increase blue color\n"
						+ "\n"
						+ "Miscellaneous:\n"
						+ "ESC - quit program\n"
						+ ", - change camera\n"
						+ "` - release held keys\n"
						+ "F1 - help menu\n"
						+ "F12 - About\n"
						+ "F10 - highlight main menu\n"
						+ "F11 - change to spot camera";
		JOptionPane.showMessageDialog(null, helpText);
	}
	
	public void displayAbout() {
		String aboutText = "Open University - Course: Computer Graphics 20562 \n"
						+ "Semester 2017 B\n"
						+ "\n"
						+ "Created By:\n"
						+ "Natan Rubinstein 066511353\n"
						+ "Ariel Pinchover 203437587\n";
		JOptionPane.showMessageDialog(null, aboutText);
	}

}
