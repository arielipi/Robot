 

package lights;

import com.jogamp.opengl.GL2;

import mmn17.Scene;
import model.Point4D;

public class GlobalLight extends Light {

	public GlobalLight() {
		ambient = new Point4D(0.3f, 0.3f, 0.3f, 1.0f);
	}

	public Point4D getAmbientLightParams() {
		return ambient;
	}

	public void updatetAmbientLight(float[] params) {
		ambient.set(params);
	}

	public void show(GL2 gl) {
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, Scene.createFloatBufferFromArr(ambient.returnAsArray()));
	}

}
