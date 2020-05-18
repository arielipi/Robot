 

package model;

public class Point4D {
	private float x;
	private float y;
	private float z;
	private float h;
	private float[] asArray = new float[4];

	public Point4D() {
		set(0, 0, 0, 0);
	}

	public Point4D(float x, float y, float z, float h) {
		set(x, y, z, h);
	}

	public Point4D(Point4D vector) {
		set(vector.x, vector.y, vector.z, vector.h);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		asArray[0] = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		asArray[1] = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
		asArray[2] = z;
	}
	
	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
		asArray[3] = h;
	}

	public void set(float x, float y, float z, float h) {
		setX(x);
		setY(y);
		setZ(z);
		setH(h);
	}
	
	public void set(float[] params) {
		setX(params[0]);
		setY(params[1]);
		setZ(params[2]);
		setH(params[3]);
	}
	
	public float[] returnAsArray() {
		return asArray;
	}
	
}
