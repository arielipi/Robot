 

package mmn17;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import cameras.Camera;
import cameras.ThirdPersonCamera;
import lights.GlobalLight;
import lights.SpotLight;
import lights.SunLight;
import model.Consts;
import model.Materials;
import model.Robot;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import java.util.Arrays;

public class Scene implements GLEventListener {

	public Boolean keyStates[] = new Boolean[256];
	public Boolean prevKeyStates[] = new Boolean[256];
	
	public ThirdPersonCamera camera3P;
	public ThirdPersonCamera cameraSpotLight;
	
	private Texture floorTexture;
	private Texture heinenkenBeerBarrelTexture;

	private KeyboardHandler keyboardHandler;

	private Robot robot;
	private GLU glu;
	private GLUT glut;

	private int prevMouseX = -1;
	private int prevMouseY = -1;

	static int runtimeWidth = 800;
	static int runtimeHeight = 600;


	GL2 gl;
	private int floorListNum, barrelListNum;
	
	private int cameraMode = Consts.VIEW_3P;

	private InfoOverlay infoOverlay;
	private GlobalLight gloablLight;
	private SpotLight spotLight;
	private SunLight sunLight;
	private Materials materials; 

	public Scene() {
		keyboardHandler = new KeyboardHandler(this);
		setInfoOverlay(new InfoOverlay());
		floorTexture = new Texture(0);
		heinenkenBeerBarrelTexture = new Texture(0);
		setGloablLight(new GlobalLight());
		setSpotLight(new SpotLight());
		sunLight = new SunLight();
	}

	public void displayText(int x, int y, String s, float r, float g, float b) {
		gl.glPushAttrib(GL2.GL_CURRENT_BIT | GL2.GL_LIGHTING_BIT);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrtho(0.0, runtimeWidth, 0.0, runtimeHeight, -1.0, 1.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glColor3f(r, g, b);
		gl.glRasterPos2i(x, y);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, s);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPopAttrib();
	}

	public void fillArray(Boolean arr[], int size, Boolean value) {
		for (int i = 0; i < size; i++) {
			arr[i] = value;
		}
	}

	private void initGl(GLAutoDrawable drawable) {

		gl = drawable.getGL().getGL2();
		printCapabilities(drawable);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		setRobot(new Robot(drawable));
		getRobot().initStructures();
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		gl.glEnable(GL2.GL_NORMALIZE);

		camera3P.set(6,11, -15, 0, 0, 0, 0, 1, 0);

		try {   
			floorTexture = TextureIO.newTexture(new File("src/mmn17/grass.tga"), true);
		} catch (GLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

		try {
			heinenkenBeerBarrelTexture = TextureIO.newTexture(new File("src/mmn17/heineken_beer_barrel.jpg"), true);
		} catch (GLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		materials = new Materials(gl);
		
		structFloor();
		structBeerBarrel();

	}

	public void switchCamera() {
		if (getCameraMode() == Consts.VIEW_3P) {
			setCameraMode(Consts.VIEW_1P);
			gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 1);
		} else if (getCameraMode() == Consts.VIEW_1P) {
			setCameraMode(Consts.VIEW_SPOT_LIGHT);
			gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 0);
		} else {
			setCameraMode(Consts.VIEW_3P);
			gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 0);
		}
	}




	private void drawHeinekenBeerBarrel() { 
		gl.glPushAttrib(GL2.GL_TEXTURE_BIT | GL2.GL_CURRENT_BIT | GL2.GL_ENABLE_BIT | GL2.GL_LIGHTING_BIT);
		gl.glCallList(barrelListNum);
		gl.glPopAttrib();
	}

	private void structBeerBarrel() {
		GLUquadric cylinder = glu.gluNewQuadric();
		glu.gluQuadricNormals(cylinder, GLU.GLU_SMOOTH);
		glu.gluQuadricTexture(cylinder, true);
		GLUquadric disk = glu.gluNewQuadric();
		glu.gluQuadricNormals(disk, GLU.GLU_SMOOTH);
		glu.gluQuadricTexture(disk, true);

		barrelListNum = gl.glGenLists(1);
		gl.glNewList(barrelListNum, GL2.GL_COMPILE);

		
		materials.setBarrel();
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL, GL2.GL_SEPARATE_SPECULAR_COLOR);
		if (heinenkenBeerBarrelTexture.getTextureObject() != 0) {
			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, heinenkenBeerBarrelTexture.getTextureObject());
		}
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.5f, 0.0f);
		gl.glRotatef(-90.0f, 1, 0, 0);
		glu.gluDisk(disk, 0.0, 0.2, 30, 30);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90.0f, 1, 0, 0);
		glu.gluDisk(disk, 0.0, 0.2, 30, 30);
		gl.glPopMatrix();

		if (heinenkenBeerBarrelTexture.getTextureObject() != 0) {
			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, heinenkenBeerBarrelTexture.getTextureObject());
		}
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		gl.glRotatef(-90.0f, 1, 0, 0);
		glu.gluCylinder(cylinder, 0.2, 0.2, .5, 30, 30);
		gl.glPopMatrix();
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL, GL2.GL_SINGLE_COLOR);
		gl.glEndList();
	}

	private void structFloor() {
		float x0 = -10, z0 = -10, x1 = 1, z1 = 1;
		int count = 15; // Number of division of the floor block
		int repeat = 20; // Repeat number of floor block

		floorListNum = gl.glGenLists(1);
		gl.glNewList(floorListNum, GL2.GL_COMPILE);
		materials.setFloor();

		if (floorTexture.getTextureObject() != 0) {
			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, floorTexture.getTextureObject()); // Select Texture
		}

		gl.glBegin(GL2.GL_QUADS);

		gl.glNormal3f(0.f, 1.f, 0.f);
		for (int jj = 1; jj <= repeat; jj++) {
			for (int ii = 1; ii <= repeat; ii++) {
				for (int j = 0; j < count; j++) {
					for (int i = 0; i < count; i++) {
						gl.glTexCoord2f(i * 1.f / count, 
										j * 1.f / count);
						gl.glVertex3f(x0 + x1 * ii + i * x1 * 1.f / count, 
									  0.0f, 
									  z0 + z1 * jj + j * z1 * -1.f / count);
						
						gl.glTexCoord2f((i + 1) * 1.f / count, 
										j * 1.f / count);
						gl.glVertex3f(x0 + x1 * ii + (i + 1) * x1 * 1.f / count,
									  0.0f,
									  z0 + z1 * jj + j * z1 * -1.f / count);
						
						gl.glTexCoord2f((i + 1) * 1.f / count, 
										(j + 1) * 1.f / count);
						gl.glVertex3f(x0 + x1 * ii + (i + 1) * x1 * 1.f / count, 
									  0.0f,
									  z0 + z1 * jj + (j + 1) * z1 * -1.f / count);
						
						gl.glTexCoord2f(i * 1.f / count, 
										(j + 1) * 1.f / count);
						gl.glVertex3f(x0 + x1 * ii + i * x1 * 1.f / count, 
									  0.0f,
									  z0 + z1 * jj + (j + 1) * z1 * -1.f / count);
					}
				}
			}
		}
		gl.glEnd();

		if (floorTexture.getTextureObject() == 0) {
			System.out.println("Floor texture is disabled");
			gl.glDisable(GL2.GL_TEXTURE_2D);
		}
		gl.glEndList();
	}

	private void printCapabilities(GLAutoDrawable drawable) {
		System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
		System.err.println("INIT GL IS: " + gl.getClass().getName());
		System.err.println("GL_VENDOR: " + gl.glGetString(GL2.GL_VENDOR));
		System.err.println("GL_RENDERER: " + gl.glGetString(GL2.GL_RENDERER));
		System.err.println("GL_VERSION: " + gl.glGetString(GL2.GL_VERSION));
	}


	private void initLogic() {
		fillArray(keyStates, 256, false);
		fillArray(prevKeyStates, 256, false);

		cameraSpotLight.set(getSpotLight().getPosition().getX(), // position x
							getSpotLight().getPosition().getY(), // position y
							getSpotLight().getPosition().getZ(), // position z
							getSpotLight().getPosition().getX() + getSpotLight().getDirection().getX(), // view x  
							getSpotLight().getPosition().getY() + getSpotLight().getDirection().getY(), // view y
							getSpotLight().getPosition().getZ() + getSpotLight().getDirection().getZ(), // view z
							0, 1, 0); // up vector

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		glu = new GLU();
		glut = new GLUT();

		camera3P = new ThirdPersonCamera(glu);
		cameraSpotLight = new ThirdPersonCamera(glu);

		initLogic();
		initGl(drawable);

		registerCallbacks(drawable);
	}

	private void registerCallbacks(GLAutoDrawable drawable) {
		RobotMouseAdapter mouseAdapter = new RobotMouseAdapter();
		KeyListener robotKeysAdapter = new RobotKeyAdapter();
		if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
			java.awt.Component comp = (java.awt.Component) drawable;
			new AWTMouseAdapter(mouseAdapter, drawable).addTo(comp);
			new AWTKeyAdapter(robotKeysAdapter, drawable).addTo(comp);
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int newWidth, int newHeight) {
		gl.glViewport(0, 0, newWidth, newHeight);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		if (newHeight == 0) {
			newHeight = 1;
		}
		glu.gluPerspective(30.0f, newWidth / newHeight, 0.1, 100.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		runtimeWidth = newWidth;
		runtimeHeight = newHeight;
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		keyboardHandler.handle();
		prevKeyStates = Arrays.copyOf(keyStates, 256);
		getGloablLight().show(gl);
		getSpotLight().showSpotLight(gl);

		gl.glLoadIdentity(); // Reset The Modelview Matrix

		if (getCameraMode() == Consts.VIEW_1P) {
			getRobot().setLook();
		} else if (getCameraMode() == Consts.VIEW_3P) {
			camera3P.setLookThirdPerson();
		} else if (getCameraMode() == Consts.VIEW_SPOT_LIGHT) {
			getSpotLight().setPosition(cameraSpotLight.getPosition());
			getSpotLight().setDirection(cameraSpotLight.getDirection());
			cameraSpotLight.setLookThirdPerson();
		}

		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
		gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);

		getRobot().drawRobot();
		gl.glPushMatrix();
		drawWorld();
		gl.glPopMatrix();
		sunLight.showSunLight(gl);

		getInfoOverlay().displayOverlayText(this);

	}

	private void drawWorld() {
		gl.glPushMatrix();
		gl.glPushAttrib(GL2.GL_TEXTURE_BIT | GL2.GL_CURRENT_BIT | GL2.GL_ENABLE_BIT | GL2.GL_LIGHTING_BIT);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL, GL2.GL_SEPARATE_SPECULAR_COLOR);
		gl.glCallList(floorListNum);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL, GL2.GL_SINGLE_COLOR);
		gl.glPopAttrib();
		gl.glPopMatrix();

		
		gl.glPushMatrix();
		gl.glTranslatef(1.0f, 0.0f, 1.0f);
		drawHeinekenBeerBarrel();
		gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		gl.glTranslatef(-1.0f, 0.0f, -1.0f);
		drawHeinekenBeerBarrel();
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(-5.0f, 0.0f, -6.0f);
		drawHeinekenBeerBarrel();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(4.0f, 0.0f, -4.0f);
		drawHeinekenBeerBarrel();
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(4.2f, 0.0f, -1.2f);
		drawHeinekenBeerBarrel();
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		drawTeapot();
		gl.glPopMatrix();
		

	}
	
	void drawTeapot() {
		materials.setBlue();
		gl.glTranslatef(-5f, 1.0f, 5.f);
		glut.glutSolidTeapot(1);
	}

	class RobotMouseAdapter extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			prevMouseX = e.getX();
			prevMouseY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {

			final int mouseX = e.getX();

			final int mouseY = e.getY();
			if (prevMouseX == -1) {
				prevMouseX = mouseX;
				prevMouseY = mouseY;
			}

			moveCurrentCameraWithMouse(mouseX, mouseY, prevMouseX, prevMouseY);

			prevMouseX = mouseX;
			prevMouseY = mouseY;

		}
	}

	public Camera getCurrentCamera() {
		if (getCameraMode() == Consts.VIEW_1P) {
			return getRobot().getCamera();
		} else {
			return getThirdPersonCamera();
		}
	}

	public ThirdPersonCamera getThirdPersonCamera() {
		if (getCameraMode() == Consts.VIEW_3P) {
			return camera3P;
		} else if (getCameraMode() == Consts.VIEW_SPOT_LIGHT) {
			return cameraSpotLight;
		}
		return camera3P;
	}

	public void moveCurrentCameraWithMouse(int mouseX, int mouseY, int prevMouseX, int prevMouseY) {
		if (getCameraMode() == Consts.VIEW_3P) {
			camera3P.setViewByMouse(mouseX, mouseY, prevMouseX, prevMouseY, 300, 100);
		} else if (getCameraMode() == Consts.VIEW_1P) {
			getRobot().setViewByMouse(mouseX, mouseY, prevMouseX, prevMouseY, 1000, 1000);
		} else if (getCameraMode() == Consts.VIEW_SPOT_LIGHT) {
			cameraSpotLight.setViewByMouse(mouseX, mouseY, prevMouseX, prevMouseY, 300, 100);
		}
	}

	class RobotKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int kc = e.getKeyCode();
//			System.out.println("Key Pressed: " + kc);
			keyStates[kc] = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int kc = e.getKeyCode();
			keyStates[kc] = false;
		}
	}

	public static FloatBuffer createFloatBufferFromArr(float[] arr) {
		return FloatBuffer.wrap(arr);
	}

	public void setGivenFloatArray(float[] floatArrayToSet, float[] floatArrayToOnset) {
		for (int i = 0; i < floatArrayToOnset.length; i++) {
			floatArrayToSet[i] = floatArrayToOnset[i];
		}

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	public int getCameraMode() {
		return cameraMode;
	}

	public void setCameraMode(int cameraMode) {
		this.cameraMode = cameraMode;
	}

	public SpotLight getSpotLight() {
		return spotLight;
	}

	public void setSpotLight(SpotLight spotLight) {
		this.spotLight = spotLight;
	}

	public GlobalLight getGloablLight() {
		return gloablLight;
	}

	public void setGloablLight(GlobalLight gloablLight) {
		this.gloablLight = gloablLight;
	}

	public InfoOverlay getInfoOverlay() {
		return infoOverlay;
	}

	public void setInfoOverlay(InfoOverlay infoOverlay) {
		this.infoOverlay = infoOverlay;
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

}
