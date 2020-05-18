 

package model;

import java.nio.FloatBuffer;
import com.jogamp.opengl.GL2;

public class Materials {
	private FloatBuffer specularYellow;
	private FloatBuffer ambientYellow;
	private FloatBuffer diffuseYellow;
	private FloatBuffer shininessYellow;
	 
	private FloatBuffer specularBlue;
	private FloatBuffer ambientBlue;
	private FloatBuffer diffuseBlue;
	private FloatBuffer shininessBlue;
	
	private FloatBuffer specularGray;
	private FloatBuffer ambientGray;
	private FloatBuffer diffuseGray;
	private FloatBuffer shininessGray;
	  
	private FloatBuffer specularRed;
	private FloatBuffer ambientRed;
	private FloatBuffer diffuseRed;
	private FloatBuffer shininessRed;
	
	private FloatBuffer specularGreen;
	private FloatBuffer ambientGreen;
	private FloatBuffer diffuseGreen;
	private FloatBuffer shininessGreen;
	
	private FloatBuffer specularBlack;
	private FloatBuffer ambientBlack;
	private FloatBuffer diffuseBlack;
	private FloatBuffer shininessBlack;
	
	private FloatBuffer specularPurple;
	private FloatBuffer ambientPurple;
	private FloatBuffer diffusePurple;
	private FloatBuffer shininessPurple;
	
	private GL2 gl;
	
	public Materials(GL2 gl) {
		this.gl = gl;
		specularYellow = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientYellow = createFloatBuffer(1.0f, 1.0f, 0.0f, 1.0f);
		diffuseYellow = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessYellow = createFloatBuffer(128.0f);

		specularBlue = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientBlue = createFloatBuffer(0.0f, 0.0f, 1.0f, 1.0f);
		diffuseBlue = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessBlue = createFloatBuffer(128.0f);
		
		specularGray = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientGray = createFloatBuffer(0.5f, 0.5f, 0.5f, 1.0f);
		diffuseGray = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessGray = createFloatBuffer(128.0f * 0.6f);

		specularRed = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientRed = createFloatBuffer(1.0f, 0.0f, 0.0f, 1.0f);
		diffuseRed = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessRed = createFloatBuffer(128.0f);
		
		specularGreen = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientGreen = createFloatBuffer(0.0f, 1.0f, 0.0f, 1.0f);
		diffuseGreen = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessGreen = createFloatBuffer(128.0f);
		
		specularBlack = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientBlack = createFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f);
		diffuseBlack = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessBlack = createFloatBuffer(128.0f);
		
		specularPurple = createFloatBuffer(0.75f, 0.75f, 0.75f, 1.0f);
		ambientPurple = createFloatBuffer(1.0f, 0.0f, 1.0f, 0.0f);
		diffusePurple = createFloatBuffer(0.50f, 0.50f, 0.50f, 1.0f);
		shininessPurple = createFloatBuffer(128.0f);
	}
	
	
	private FloatBuffer createFloatBuffer(Float... arguments) {
		float[] temp = new float[arguments.length];
		for (int i = 0; i < arguments.length; ++i) {
			temp[i] = arguments[i];
		}
		return FloatBuffer.wrap(temp);
	}
	
	public void setPurple() {
		buildRobotMaterial(specularPurple, ambientPurple, diffusePurple, shininessPurple);
	}
	
	public void setBlack() {
		buildRobotMaterial(specularBlack, ambientBlack, diffuseBlack, shininessBlack);
	}
	
	public void setGray() {
		buildRobotMaterial(specularGray, ambientGray, diffuseGray, shininessGray);
	}
	
	public void setYellow() {
		buildRobotMaterial(specularYellow, ambientYellow, diffuseYellow, shininessYellow);
	}
	
	public void setBlue() {
		buildRobotMaterial(specularBlue, ambientBlue, diffuseBlue, shininessBlue);
	}
	
	public void setRed() {
		buildRobotMaterial(specularRed, ambientRed, diffuseRed, shininessRed);
	}
	
	public void setGreen() {
		buildRobotMaterial(specularGreen, ambientGreen, diffuseGreen, shininessGreen);
	}
	
 	public void setFloor() {
		buildWorldMaterial(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f }, new float[]{ 0.7f, 0.7f, 0.7f, 1.0f} , new float[]{0.4f, 0.4f, 0.4f, 1.0f} , 128);
	}
 	
 	public void setBarrel() {
		buildWorldMaterial(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f }, new float[]{ 0.7f, 0.7f, 0.7f, 1.0f} , new float[]{0.5f, 0.5f, 0.5f, 1.0f} , 128);
	}
		
	private void buildRobotMaterial(FloatBuffer spec, FloatBuffer amb, FloatBuffer diff, FloatBuffer shin) {
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, spec);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, shin);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff);
	}
	
	private void buildWorldMaterial(float spec[], float amb[], float diff[], int shin) {
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, spec, 0);
		gl.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, shin);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
	}
}
