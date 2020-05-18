 

package lights;

import model.Point4D;

public class Light {
	protected Point4D ambient = new Point4D(0.0f, 0.0f, 0.0f, 1.0f);
	protected Point4D diffuse;
	protected Point4D specular;
	protected Point4D position;
	protected Point4D direction;
	
	public Point4D getPosition() {
		return position;
	}
	
	public void setPosition(Point4D position) {
		this.position = position;
	}
	
	public Point4D getDirection() {
		return direction;
	}
	
	public void setDirection(Point4D direction) {
		this.direction = direction;
	}
	
}
