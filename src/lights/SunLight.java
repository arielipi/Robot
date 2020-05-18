 

package lights;

import com.jogamp.opengl.GL2;

import mmn17.Scene;
import model.Point4D;

public class SunLight extends PointLight {

	public SunLight() {
		ambient = new Point4D(0.3f, 0.3f, 0.3f, 1.0f);
		diffuse = new Point4D(1.0f, 1.0f, 1.0f, 1.0f);
		specular = new Point4D(0.7f, 0.7f, 0.7f, 1.0f);
		position = new Point4D(11.0f, 16.0f, 19.0f, 1.0f);
	}

	public void showSunLight(GL2 gl) {
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, Scene.createFloatBufferFromArr(ambient.returnAsArray()));
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, Scene.createFloatBufferFromArr(diffuse.returnAsArray()));
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, Scene.createFloatBufferFromArr(specular.returnAsArray()));
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, Scene.createFloatBufferFromArr(position.returnAsArray()));
		gl.glEnable(GL2.GL_LIGHT0);
	}
}
