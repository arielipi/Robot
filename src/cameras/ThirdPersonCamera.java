
package cameras;

import com.jogamp.opengl.glu.GLU;

import model.Vector3D;

public class ThirdPersonCamera extends Camera {
	private float moveSpeed;
	private GLU glu;

	private float currentRotX = 0.0f;
	private float lastRotX = 0.0f;

	public ThirdPersonCamera(GLU myGlu) {
		if (!(myGlu instanceof GLU)) {
			System.out.println("Camera did not got GLU");
		}

		this.glu = myGlu;
		setMoveSpeed(0.08f);
		set(0, 0, 0, 1, 0, 0, 0, 1, 0); // Default
	}

	public ThirdPersonCamera(float positionX, float positionY, float positionZ, 
							 float viewX, float viewY, float viewZ,
							 float upVectorX, float upVectorY, float upVectorZ) {
		set(positionX, positionY, positionZ, viewX, viewY, viewZ, upVectorX, upVectorY, upVectorZ);
	}

	public void set(float positionX, float positionY, float positionZ, 
					float viewX, float viewY, float viewZ,
					float upVectorX, float upVectorY, float upVectorZ) {
		
		Vector3D Position = new Vector3D(positionX, positionY, positionZ);
		Vector3D View = new Vector3D(viewX, viewY, viewZ);
		Vector3D UpVector = new Vector3D(upVectorX, upVectorY, upVectorZ);

		cameraPosition = Position;
		lookAtPoint = View;
		upVector = UpVector;
	}

	public void set(Vector3D position, Vector3D view) {
		cameraPosition = position;
		lookAtPoint = view;
		upVector = new Vector3D(0, 1, 0);
	}

	public void setPosition(Vector3D val) {
		cameraPosition = val;
	}

	public Vector3D getPosition() {
		return cameraPosition;
	}

	public Vector3D getLookAtPoint() {
		return lookAtPoint;
	}

	public Vector3D getDirection() {
		return getLookAtPoint().minus(getPosition());
	}

	public void setView(Vector3D val) {
		lookAtPoint = val;
	}

	public void rotateView(float angle, float x, float y, float z) {
		Vector3D vNewView = new Vector3D();
		Vector3D vView = lookAtPoint.minus(cameraPosition);
		float cosTheta = (float) Math.cos(angle);
		float sinTheta = (float) Math.sin(angle);

		vNewView.setX((cosTheta + (1 - cosTheta) * x * x) * vView.getX());
		vNewView.setX(vNewView.getX() + ((1 - cosTheta) * x * y - z * sinTheta) * vView.getY());
		vNewView.setX(vNewView.getX() + ((1 - cosTheta) * x * z + y * sinTheta) * vView.getZ());

		vNewView.setY(((1 - cosTheta) * x * y + z * sinTheta) * vView.getX());
		vNewView.setY(vNewView.getY() + ((cosTheta + (1 - cosTheta) * y * y) * vView.getY()));
		vNewView.setY(vNewView.getY() + ((1 - cosTheta) * y * z - x * sinTheta) * vView.getZ());

		vNewView.setZ(((1 - cosTheta) * x * z - y * sinTheta) * vView.getX());
		vNewView.setZ(vNewView.getZ() + ((1 - cosTheta) * y * z + x * sinTheta) * vView.getY());
		vNewView.setZ(vNewView.getZ() + (cosTheta + (1 - cosTheta) * z * z) * vView.getZ());

		lookAtPoint = cameraPosition.add(vNewView);
	}

	// Moving camera forward / backward
	public void move(float speed) {
		// Get the view vector
		Vector3D vView = lookAtPoint.minus(cameraPosition); 
		vView.normalize();
		cameraPosition = cameraPosition.add(vView.multiplyScalar(speed));
		lookAtPoint = lookAtPoint.add(vView.multiplyScalar(speed));
	}

	public void yaw(float speed) {
		rotateView(speed, 0, 1, 0);
	}

	public void strafe(float speed) {
		Vector3D vStrafe = lookAtPoint.minus(cameraPosition);
		vStrafe = vStrafe.cross(upVector);
		vStrafe.normalize();

		cameraPosition = cameraPosition.add(vStrafe.multiplyScalar(speed));
		lookAtPoint = lookAtPoint.add(vStrafe.multiplyScalar(speed));
	}

	public void pitch(float speed) {
		rotateView(speed, 1, 0, 0);
	}

	public void up(float speed) {
		Vector3D vView = lookAtPoint.minus(cameraPosition);
		Vector3D vTemp = vView.cross(upVector);
		vTemp = vTemp.cross(vView);
		vTemp.normalize();
		cameraPosition = cameraPosition.add(vTemp.multiplyScalar(speed));
		lookAtPoint = lookAtPoint.add(vTemp.multiplyScalar(speed));
	}

	// Mouse camera control
	public void setViewByMouse(int x, int y, int prevX, int prevY, float sensX, float sensY) {
		float angleY = 0.0f;
		float angleZ = 0.0f;
		angleY = (float) ((prevX - x)) / sensX;
		angleZ = (float) ((prevY - y)) / sensY;

		lastRotX = currentRotX;

		currentRotX += angleZ;
		if (currentRotX > 1.0f) {
			currentRotX = 1.0f;
			if (lastRotX != 1.0f) {
				Vector3D vAxis = lookAtPoint.minus(cameraPosition);
				vAxis = vAxis.cross(upVector);
				vAxis.normalize();
				rotateView(1.0f - lastRotX, vAxis.getX(), vAxis.getY(), vAxis.getZ());
			}
		} else if (currentRotX < -1.0f) {
			currentRotX = -1.0f;
			if (lastRotX != -1.0f) {
				Vector3D vAxis = lookAtPoint.minus(cameraPosition);
				vAxis = vAxis.cross(upVector);
				vAxis.normalize();
				rotateView(-1.0f - lastRotX, vAxis.getX(), vAxis.getY(), vAxis.getZ());
			}
		} else {
			Vector3D vAxis = lookAtPoint.minus(cameraPosition);
			vAxis = vAxis.cross(upVector);
			vAxis.normalize();

			rotateView(angleZ, vAxis.getX(), vAxis.getY(), vAxis.getZ());
		}

		rotateView(angleY, 0, 1, 0);
	}

	public void setLookThirdPerson() {
		this.lookAtPointFrom(glu, cameraPosition, lookAtPoint);

	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

}
