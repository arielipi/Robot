 

package lights;

import model.Point4D;
import model.Vector3D;

public class PointLight extends Light {
	
	
	public void setDiffuse(float params[]) {
		this.diffuse.set(params);
	}
	
	public Point4D getDiffuseParams() {
		return diffuse;
	}
	
	public void setDiffuseParams(float params[]) {
		diffuse.set(params);
	}
	
	public void setPosition(Vector3D position) {
		this.position.setX(position.getX());
		this.position.setY(position.getY());
		this.position.setZ(position.getZ());
	}
	
	public void setDirection(Vector3D direction) {
		this.direction.setX(direction.getX());
		this.direction.setY(direction.getY());
		this.direction.setZ(direction.getZ());
	}
}
