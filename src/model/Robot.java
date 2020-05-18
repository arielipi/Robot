 

package model;

import com.jogamp.opengl.util.gl2.GLUT;
import cameras.FirstPersonCamera;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Robot {

	private static int BODY = 1;
	private static int LEGS = 2;
	private static int HEAD = 3;
	private static int ARM = 4;
	private static int HAND_PALM = 5;
	private static int FOREARM = 6;
	
	

	public float HEAD_Y_POS = 1.1f;
	public float HEAD_Y_ANGLE_MAX = 1.3f;
	public float HEAD_Z_ANGLE_MAX = 0.5f;
	public float HEAD_Z_MAX = 0.5f;
	public float PALM_ANGLE_MAX = 40.0f;
	public float FOREARM_ANGLE_MAX = 110.0f;

	public Vector3D position;
	private Vector3D robotDirection;
	public Vector3D headDirection;

	private GL2 gl;
	private GLU glu;
	private GLUT glut;

	private float headAngleY = 0.0f;
	private float headAngleZ = 0.0f;
	private float rightArmAngle = 0.0f;
	private float leftArmAngle = 0.0f;
	private float rightForearmAngle = 0.0f;
	private float leftForearmAngle = 0.0f;
	private float rightPalmAngle = 10.0f;
	private float leftPalmAngle = 10.0f;

	public float moveSpeed;
	public float angleSpeed;

	private FirstPersonCamera firstPersonCamera;
	private Materials materials;

	public Robot(GLAutoDrawable drawable) {
		firstPersonCamera = new FirstPersonCamera();
		gl = drawable.getGL().getGL2();
		glu = new GLU();
		glut = new GLUT();

		moveSpeed = 0.05f;
		angleSpeed = 3.0f;

		headAngleY = 0.0f;
		headAngleZ = 0.0f;
		rightArmAngle = 0.0f;
		leftArmAngle = 0.0f;
		rightForearmAngle = 0.0f;
		leftForearmAngle = 0.0f;
		rightPalmAngle = 10.0f;
		leftPalmAngle = 10.0f;

		position = Consts.ROBOT_START_POSTION;
		headDirection = new Vector3D(1.0f, 0.0f, 0.0f);
		robotDirection = new Vector3D(1.0f, 0.0f, 0.0f);

		materials = new Materials(gl);
	}

	public FirstPersonCamera getCamera() {
		return firstPersonCamera;
	}

	public void move(float speed) {
		position.setX(position.getX() + robotDirection.getX() * speed);
		position.setZ(position.getZ() + robotDirection.getZ() * speed);
	}

	public void strafe(float speed) {
		// Orthogonal vector for the view vector
		Vector3D vOrtho = new Vector3D();
		vOrtho.setX(-robotDirection.getZ());
		vOrtho.setZ(robotDirection.getX());

		// update position
		position.setX(position.getX() + vOrtho.getX() * speed);
		position.setZ(position.getZ() + vOrtho.getZ() * speed);
	}

	public void yaw(float speed) {
		Vector3D vView = new Vector3D(robotDirection);

		robotDirection.setX((float) (Math.cos(speed) * vView.getX() - Math.sin(speed) * vView.getZ()));
		robotDirection.setZ((float) (Math.sin(speed) * vView.getX() + Math.cos(speed) * vView.getZ()));
		robotDirection.normalize();

		vView = headDirection;
		headDirection.setX((float) (Math.cos(speed) * vView.getX() - Math.sin(speed) * vView.getZ()));
		headDirection.setZ((float) (Math.sin(speed) * vView.getX() + Math.cos(speed) * vView.getZ()));
		headDirection.normalize();
	}

	public void moveRightArm(float speed) {
		rightArmAngle = (float) ((int) (rightArmAngle + speed) % 360);
	}

	public void moveLeftArm(float speed) {
		leftArmAngle = (float) ((int) (leftArmAngle + speed) % 360);
	}

	public void moveRightForearm(float speed) {
		if (rightForearmAngle > FOREARM_ANGLE_MAX) {
			rightForearmAngle = FOREARM_ANGLE_MAX;
		}
		if (rightForearmAngle < -FOREARM_ANGLE_MAX) {
			rightForearmAngle = -FOREARM_ANGLE_MAX;
		}
		rightForearmAngle = rightForearmAngle + speed;
	}

	public void moveRightPalm(float speed) {
		if (rightPalmAngle > PALM_ANGLE_MAX) {
			rightPalmAngle = PALM_ANGLE_MAX;
		}
		if (rightPalmAngle < -PALM_ANGLE_MAX) {
			rightPalmAngle = -PALM_ANGLE_MAX;
		}
		rightPalmAngle = rightPalmAngle + speed;
	}

	public void moveLeftForearm(float speed) {
		if (leftForearmAngle > FOREARM_ANGLE_MAX) {
			leftForearmAngle = FOREARM_ANGLE_MAX;
		}
		if (leftForearmAngle < -FOREARM_ANGLE_MAX) {
			leftForearmAngle = -FOREARM_ANGLE_MAX;
		}
		leftForearmAngle = leftForearmAngle + speed;
	}

	public void moveLeftPalm(float speed) {
		if (leftPalmAngle > PALM_ANGLE_MAX) {
			leftPalmAngle = PALM_ANGLE_MAX;
		}
		if (leftPalmAngle < -PALM_ANGLE_MAX) {
			leftPalmAngle = -PALM_ANGLE_MAX;
		}
		leftPalmAngle = leftPalmAngle + speed;
	}

	public void moveHeadY(float speed) {
		// Restrict movement around Y axis
		if (headAngleY > HEAD_Y_ANGLE_MAX * Consts._180_OVER_PI) {
			headAngleY = HEAD_Y_ANGLE_MAX * Consts._180_OVER_PI;
		}

		// Restrict movement around Y axis
		if (headAngleY < -HEAD_Y_ANGLE_MAX * Consts._180_OVER_PI) {
			headAngleY = -HEAD_Y_ANGLE_MAX * Consts._180_OVER_PI;
		}

		// Update rotation angle for display
		headAngleY = headAngleY + speed;

		headDirection.setX((float) (Math.cos(headAngleY / Consts._180_OVER_PI) * robotDirection.getX()
				- Math.sin(headAngleY / Consts._180_OVER_PI) * robotDirection.getZ()));
		headDirection.setZ((float) (Math.sin(headAngleY / Consts._180_OVER_PI) * robotDirection.getX()
				+ Math.cos(headAngleY / Consts._180_OVER_PI) * robotDirection.getZ()));
		headDirection.normalize();
	}

	public void moveHeadZ(float speed) {

		headDirection.setY(headDirection.getY() + speed);
		// Restrict movement around Z axis
		if ((headDirection.getY() < -HEAD_Z_MAX)) {
			headDirection.setY(-HEAD_Z_MAX);
		}
		// Restrict movement around Z axis
		if ((headDirection.getY() > HEAD_Z_MAX)) {
			headDirection.setY(HEAD_Z_MAX);
		}

		// Update rotation angle for display
		headAngleZ = (float) (Math.asin(headDirection.getY() / headDirection.length()) * Consts._180_OVER_PI);
	}

	public void initStructures() {
		gl.glGenLists(20);
		buildHead();
		buildBody();
		buildLegs();
		buildArms();
		buildForearm();
		buildHandPalm();
	}

	public void drawRobot() {

		gl.glPushAttrib(GL2.GL_ALL_ATTRIB_BITS);
		gl.glPushMatrix();
		gl.glTranslatef(position.getX(), 0.80f, position.getZ());
		gl.glRotatef(-robotDirection.angleSigned(new Vector3D(1, 0, 0)) * Consts._180_OVER_PI, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(90f, 0f, 1, 0f);
		gl.glScalef(0.3f, 0.3f, 0.3f);

		// Head and BODY and LEGS
		gl.glPushMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.5f, 0.0f);
		gl.glRotatef(-headAngleZ, 1f, 0f, 0f);
		gl.glRotatef(-headAngleY, 0f, 1f, 0f);
		gl.glTranslatef(0.0f, -0.5f, 0.0f);
		gl.glCallList(HEAD);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(BODY);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(LEGS);
		gl.glPopMatrix();
		
		
		// RIGHT ARM, FOREARM and HAND_PALM
		gl.glPushMatrix();
		gl.glTranslatef(-1.1f, 0.25f, 0f);
		gl.glRotatef(rightArmAngle, 1f, 0f, 0f);
		gl.glTranslatef(0f, -0.25f, 0f);
		gl.glPushMatrix();
		gl.glCallList(ARM);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -0.7f, 0f);
		gl.glRotatef(rightForearmAngle, 1f, 0f, 0f);
		gl.glTranslatef(0.0f, 0.7f, 0f);
		gl.glPushMatrix();
		gl.glCallList(FOREARM);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -1.65f, 0f);
		gl.glRotatef(rightPalmAngle, 0, 0, 1);
		gl.glCallList(HAND_PALM);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// LEFT ARM, FOREARM and PALM
		gl.glPushMatrix();
		gl.glTranslatef(1.1f, 0.25f, 0);
		gl.glRotatef(leftArmAngle, 1, 0, 0);
		gl.glTranslatef(0f, -0.25f, 0);
		gl.glPushMatrix();
		gl.glCallList(ARM);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -0.7f, 0f);
		gl.glRotatef(leftForearmAngle, 1, 0, 0);
		gl.glTranslatef(0.0f, 0.7f, 0f);
		gl.glPushMatrix();
		gl.glCallList(FOREARM);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -1.65f, 0);
		gl.glRotatef(-leftPalmAngle, 0, 0, 1);
		gl.glCallList(HAND_PALM);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopAttrib();

	}

	private void buildLegs() {
		gl.glNewList(LEGS, GL2.GL_COMPILE);
		
		materials.setGray();
		gl.glPushMatrix();
		gl.glTranslatef(-0.5f, -3.0f, 0.0f);
		gl.glScalef(0.5f, 6.0f, 1.2f);
		glut.glutSolidCube(1.0f);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0.5f, -3.0f, 0.0f);
		gl.glScalef(0.5f, 6.0f, 1.2f);
		glut.glutSolidCube(1.0f);
		gl.glPopMatrix();
		
		gl.glEndList();
	}

	private void buildBody() {
		gl.glNewList(BODY, GL2.GL_COMPILE);

		materials.setBlue();
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 1f, 0f, 0f);
		gl.glScalef(1.8f, 1.0f, 1.0f);
		glut.glutSolidCube(1f);
		gl.glPopMatrix();
	
		gl.glEndList();
	}

	private void buildHead() {
		gl.glNewList(HEAD, GL2.GL_COMPILE);
		gl.glScalef(1.8f, 0.2f, 1.0f);
		materials.setGreen();
		gl.glTranslatef(0.0f, 4.5f, 0.0f);
		gl.glScalef(0.3f, 2.0f, 0.5f);

		glut.glutSolidSphere(1.2, 30, 30);
		materials.setYellow();
		glut.glutSolidCone(1.0, 2.0, 25, 25);

		gl.glEndList();
	}

	private void buildArms() {
		gl.glNewList(ARM, GL2.GL_COMPILE);
		materials.setBlack();
		gl.glTranslatef(0f, 0.25f, 0.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		glut.glutSolidSphere(0.6, 30, 30);

		gl.glTranslatef(0.0f, -1.10f, 0.0f);
		gl.glScalef(0.5f, 1.5f, 0.5f);
		materials.setGray();
		glut.glutSolidCube(1.0f);

		gl.glEndList();

	}

	public void setViewByMouse(int x, int y, int prevX, int prevY, float sensX, float sensY) {
		float angleY = 0.0f;
		float angleZ = 0.0f;
		angleY = (float) ((prevX - x)) / sensX;
		angleZ = (float) ((prevY - y)) / sensY;
		Vector3D vViewPrXZ = new Vector3D(headDirection.getX(), 0.0f, headDirection.getZ());
		Vector3D vDirPrXZ = new Vector3D(robotDirection.getX(), 0.0f, robotDirection.getZ());
		float angleYV = vViewPrXZ.angleSigned(vDirPrXZ);
		if ((angleYV < -HEAD_Y_ANGLE_MAX) && (angleY < 0))
			angleY = 0;
		if ((angleYV > HEAD_Y_ANGLE_MAX) && (angleY > 0))
			angleY = 0;
		headAngleY = angleYV * Consts._180_OVER_PI;
		Vector3D vView = new Vector3D(headDirection);
		headDirection.setX((float) (Math.cos(angleY) * vView.getX() - Math.sin(angleY) * vView.getZ()));
		headDirection.setZ((float) (Math.sin(angleY) * vView.getX() + Math.cos(angleY) * vView.getZ()));
		headDirection.normalize();

		
		
		// Restrict movement around Z axis
		if ((headDirection.getY() < -HEAD_Z_MAX)) {
			headDirection.setY(-HEAD_Z_MAX);
		}

		// Restrict movement around Z axis
		if ((headDirection.getY() > HEAD_Z_MAX)) {
			headDirection.setY(HEAD_Z_MAX);
		}
		headDirection.setY(headDirection.getY() + (y - prevY) / sensY);

		// Calculate head angle
		headAngleZ = (float) (Math.asin(headDirection.getY() / headDirection.length()) * Consts._180_OVER_PI);
	}

	private void buildForearm() {
		gl.glNewList(FOREARM, GL2.GL_COMPILE);
		gl.glTranslatef(0.0f, -0.7f, 0.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		materials.setBlack();
		glut.glutSolidSphere(0.6, 30, 30);
		gl.glTranslatef(0.0f, -1.2f, 0.0f);
		gl.glScalef(0.8f, 2.0f, 1.0f);
		materials.setPurple();
		glut.glutSolidCube(0.9f);
		gl.glEndList();

	}

	public Vector3D getHeadDirection() {
		return headDirection;
	}

	public void setLook() {
		firstPersonCamera.lookAtPointFrom(glu, firstPersonCamera.calcCameraPostion(this),
				firstPersonCamera.calcCameraLookAtPoint(this));
	}

	private void buildHandPalm() {
		gl.glNewList(HAND_PALM, GL2.GL_COMPILE);
		materials.setRed();
		gl.glScalef(0.25f, 0.5f, 0.8f);
		gl.glTranslatef(0.0f, -0.4f, 0.0f);
		glut.glutSolidCube(0.6f);

		gl.glTranslatef(0.0f, -0.25f, 0.0f);
		gl.glScalef(1.0f, 2.0f, 1.0f);
		glut.glutSolidCube(0.3f);
		gl.glEndList();
	}

}
