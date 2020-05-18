 

package mmn17;

import model.Consts;

public class KeyboardHandler {
	
	private Scene scene;
	
	public KeyboardHandler(Scene scene) {
		this.scene = scene;
	}

	public void handle() {
		if (scene.keyStates['`']) {
			scene.fillArray(scene.keyStates, 256, false);
		}

		if ((scene.keyStates[','] != scene.prevKeyStates[',']) && (scene.keyStates[','] == true)) {
			System.out.println("SWITCH CAMERA " + scene.keyStates[','] + ' ' + scene.prevKeyStates[',']);
			scene.switchCamera();
		}
		// ESC
		if (scene.keyStates[27]) {
			System.exit(0);
		}

		// Robot movement
		if (scene.keyStates['W']) {
			scene.getRobot().move(scene.getRobot().moveSpeed);
		}
		if (scene.keyStates['S']) {
			scene.getRobot().move(-scene.getRobot().moveSpeed);
		}
		if (scene.keyStates['A']) {
			scene.getRobot().strafe(-scene.getRobot().moveSpeed);
		}
		if (scene.keyStates['D']) {
			scene.getRobot().strafe(scene.getRobot().moveSpeed);
		}
		// Robot spin
		if (scene.keyStates['Q']) {
			scene.getRobot().yaw(-scene.getRobot().moveSpeed);
		}
		if (scene.keyStates['E']) {
			scene.getRobot().yaw(scene.getRobot().moveSpeed);
		}
		
		// Arm
		// Left
		if (scene.keyStates['G']) {
			scene.getRobot().moveLeftArm(scene.getRobot().angleSpeed);
		}
		if (scene.keyStates['T']) {
			scene.getRobot().moveLeftArm(-scene.getRobot().angleSpeed);
		}
		// Right
		if (scene.keyStates['H']) {
			scene.getRobot().moveRightArm(scene.getRobot().angleSpeed);
		}
		if (scene.keyStates['Y']) {
			scene.getRobot().moveRightArm(-scene.getRobot().angleSpeed);
		}
		// End arm

		// Forearm
		// Left
		if (scene.keyStates['F']) {
			scene.getRobot().moveLeftForearm(scene.getRobot().angleSpeed);
		}
		if (scene.keyStates['R']) {
			scene.getRobot().moveLeftForearm(-scene.getRobot().angleSpeed);
		}
		// Right
		if (scene.keyStates['J']) {
			scene.getRobot().moveRightForearm(scene.getRobot().angleSpeed);
		}
		if (scene.keyStates['U']) {
			scene.getRobot().moveRightForearm(-scene.getRobot().angleSpeed);
		}
		// End forearm
		
		// Palm
		// Left
		if (scene.keyStates['4']) {
			scene.getRobot().moveLeftPalm(scene.getRobot().angleSpeed);
		}
		if (scene.keyStates['5']) {
			scene.getRobot().moveLeftPalm(-scene.getRobot().angleSpeed);
		}
		// Right
		if (scene.keyStates['7']) {
			scene.getRobot().moveRightPalm(scene.getRobot().angleSpeed);
		}
		if (scene.keyStates['6']) {
			scene.getRobot().moveRightPalm(-scene.getRobot().angleSpeed);
		}
		// End palm
		
		// Head
		// Axis y
		if (scene.keyStates['K']) {
			scene.getRobot().moveHeadY(-scene.getRobot().angleSpeed);
		}
		if (scene.keyStates[';']) {
			scene.getRobot().moveHeadY(scene.getRobot().angleSpeed);
		}
		// Axis x
		if (scene.keyStates['L']) {
			scene.getRobot().moveHeadZ(-scene.getRobot().moveSpeed);
		}
		if (scene.keyStates['O']) {
			scene.getRobot().moveHeadZ(scene.getRobot().moveSpeed);
		}
		// End head

		// Camera movement
		if (scene.keyStates['Z']) {
			scene.moveCurrentCameraWithMouse(-5, 0, 0, 0);
		}
		if (scene.keyStates['X']) {
			scene.moveCurrentCameraWithMouse(5, 0, 0, 0);
		}
		if (scene.keyStates['C']) {
			scene.moveCurrentCameraWithMouse(0, 5, 0, 0);
		}
		if (scene.keyStates['V']) {
			scene.moveCurrentCameraWithMouse(0, -5, 0, 0);
		}
		// UP ARROW ZOOM
		if (scene.keyStates[150]) {
			scene.getThirdPersonCamera().move(scene.camera3P.getMoveSpeed());
		}
		// DOWN ARROW ZOOM OUT
		if (scene.keyStates[152]) {
			scene.getThirdPersonCamera().move(-scene.camera3P.getMoveSpeed());
		}
		// LEFT ARROW PAN LEFT
		if (scene.keyStates[149]) {
			scene.getThirdPersonCamera().strafe(-scene.camera3P.getMoveSpeed());
		}
		// RIGHT ARROW PAN RIGHT
		if (scene.keyStates[151]) {
			scene.getThirdPersonCamera().strafe(scene.camera3P.getMoveSpeed());
		}
		if (scene.keyStates['=']) {
			scene.getThirdPersonCamera().up(scene.camera3P.getMoveSpeed());
		}
		if (scene.keyStates['-']) {
			scene.getThirdPersonCamera().up(-scene.camera3P.getMoveSpeed());
		}

		// F11
		if (scene.keyStates[107]) {
			scene.setCameraMode(Consts.VIEW_SPOT_LIGHT);
		}

		// Handling ambient light
		float[] newLightParams;
		if (scene.getCameraMode() == Consts.VIEW_SPOT_LIGHT) {
			newLightParams = handleLight(scene.getSpotLight().getDiffuseParams().returnAsArray());
			scene.getSpotLight().setDiffuseParams(newLightParams);
		} else {
			newLightParams = handleLight(scene.getGloablLight().getAmbientLightParams().returnAsArray());
			scene.getGloablLight().updatetAmbientLight(newLightParams);
		}
	}

	public float[] handleLight(float[] color) {
		float changeSpeed = 0.01f;
		float[] floatToReturn = new float[color.length];

		for (int i = 0; i < floatToReturn.length; i++) {
			floatToReturn[i] = color[i];
		}

		// F1
		if (scene.keyStates[97]) {
			scene.getInfoOverlay().displayHelp();
			scene.keyStates[97] = false;
		}
		
		// F12
		if (scene.keyStates[108]) {
			scene.getInfoOverlay().displayAbout();
			scene.keyStates[108] = false;
		}
		
		// F2
		if (scene.keyStates[98]) {
			floatToReturn[0] -= changeSpeed;
			if (floatToReturn[0] < 0.0) {
				floatToReturn[0] = 0.0f;
			}
			if (floatToReturn[0] > 1.0) {
				floatToReturn[0] = 1.0f;
			}

			floatToReturn[1] -= changeSpeed;
			if (floatToReturn[1] < 0.0) {
				floatToReturn[1] = 0.0f;
			}
			if (floatToReturn[1] > 1.0) {
				floatToReturn[1] = 1.0f;
			}

			floatToReturn[2] -= changeSpeed;
			if (floatToReturn[2] < 0.0) {
				floatToReturn[2] = 0.0f;
			}
			if (floatToReturn[2] > 1.0) {
				floatToReturn[2] = 1.0f;
			}
		}

		// F3
		if (scene.keyStates[99]) {
			floatToReturn[0] += changeSpeed;
			if (floatToReturn[0] < 0.0) {
				floatToReturn[0] = 0.0f;
			}
			if (floatToReturn[0] > 1.0) {
				floatToReturn[0] = 1.0f;
			}

			floatToReturn[1] += changeSpeed;
			if (floatToReturn[1] < 0.0) {
				floatToReturn[1] = 0.0f;
			}
			if (floatToReturn[1] > 1.0) {
				floatToReturn[1] = 1.0f;
			}

			floatToReturn[2] += changeSpeed;
			if (floatToReturn[2] < 0.0) {
				floatToReturn[2] = 0.0f;
			}
			if (floatToReturn[2] > 1.0) {
				floatToReturn[2] = 1.0f;
			}
		}

		// Red
		// F4
		if (scene.keyStates[100]) {
			floatToReturn[0] -= changeSpeed;
			if (floatToReturn[0] < 0.0)
				floatToReturn[0] = 0.0f;
		}
		// F5
		if (scene.keyStates[101]) {
			floatToReturn[0] += changeSpeed;
			if (floatToReturn[0] > 1.0)
				floatToReturn[0] = 1.0f;
		}

		// Green
		// F6
		if (scene.keyStates[102]) {
			floatToReturn[1] -= changeSpeed;
			if (floatToReturn[1] < 0.0)
				floatToReturn[1] = 0.0f;
		}
		// F7
		if (scene.keyStates[103]) {
			floatToReturn[1] += changeSpeed;
			if (floatToReturn[1] > 1.0)
				floatToReturn[1] = 1.0f;
		}

		// Blue
		// F8
		if (scene.keyStates[104]) {
			floatToReturn[2] -= changeSpeed;
			if (floatToReturn[2] < 0.0)
				floatToReturn[2] = 0.0f;
		}
		// F9
		if (scene.keyStates[105]) {
			floatToReturn[2] += changeSpeed;
			if (floatToReturn[2] > 1.0)
				floatToReturn[2] = 1.0f;
		}

		return floatToReturn;
	}
}
