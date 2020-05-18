
package cameras;

import com.jogamp.opengl.glu.GLU;

import model.Vector3D;

public class Camera {
	public Vector3D upVector = Vector3D.Y_AXIS;
	public Vector3D cameraPosition;
	public Vector3D lookAtPoint;
	public Vector3D lastPosition;
	public Vector3D lastLookAtPoint;

	public Camera() {
		cameraPosition = new Vector3D();
		lookAtPoint = new Vector3D();
		lastPosition = new Vector3D();
		lastLookAtPoint = new Vector3D();
	}
	
	public Vector3D getLastPosition() {
		return lastPosition;
	}

	public Vector3D getLastLookAtPoint() {
		return lastLookAtPoint;
	}

	public void lookAtPointFrom(GLU glu, Vector3D fromPostion, Vector3D lookAtPoint) {
		this.lastPosition = fromPostion;
		this.lastLookAtPoint = lookAtPoint;

		glu.gluLookAt(fromPostion.getX(), fromPostion.getY(), fromPostion.getZ(), 
					  lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), 
					  0.0f,			 1.0f, 			0.0);
	}
}
