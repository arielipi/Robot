 

package lights;

import com.jogamp.opengl.GL2;

import mmn17.Scene;
import model.Point4D;

public class SpotLight extends PointLight {

	public SpotLight() {
		diffuse = new Point4D(0.2f, 0.8f, 0.1f, 1.0f);
		specular = new Point4D(0.0f, 0.5f, 0.0f, 1.0f);
		direction = new Point4D(1.0f, -1.0f, 1.0f, 0.0f);
		position = new Point4D(-10.0f, 15.0f, -10.0f, 1.0f);

	}

	public void showSpotLight(GL2 gl) {
		// Setup The Ambient Light
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, Scene.createFloatBufferFromArr(ambient.returnAsArray()));
		// Setup The Diffuse Light
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, Scene.createFloatBufferFromArr(diffuse.returnAsArray()));
		// Setup The Specular Light
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, Scene.createFloatBufferFromArr(specular.returnAsArray()));
		// Position The Light
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, Scene.createFloatBufferFromArr(position.returnAsArray()));
		// Direction The Light
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, Scene.createFloatBufferFromArr(direction.returnAsArray()));
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 10.0f);
		gl.glLighti(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 64);
		gl.glEnable(GL2.GL_LIGHT1);
	}
}
