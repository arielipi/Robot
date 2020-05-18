
package cameras;

import model.Robot;
import model.Vector3D;

public class FirstPersonCamera extends Camera {
	
	public Vector3D calcCameraPostion(Robot robot) {
		Vector3D relativeHead = new Vector3D(robot.headDirection.multiplyScalar(0.2f).getX(), 
										   robot.HEAD_Y_POS,
										   robot.headDirection.multiplyScalar(0.2f).getZ());
		return robot.position.add(relativeHead);
	}

	public Vector3D calcCameraLookAtPoint(Robot robot) {
		return calcCameraPostion(robot).add(robot.getHeadDirection());

	}
}
