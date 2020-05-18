 

package model;

public class Vector3D {

	private float x;
	private float y;
	private float z;

	public static Vector3D Y_AXIS = new Vector3D(0.0f, 1.0f, 0.0f);

	public Vector3D() {
		set(0, 0, 0);
	}

	public Vector3D(float x, float y, float z) {
		set(x, y, z);
	}

	public Vector3D(Vector3D vector) {
		set(vector.x, vector.y, vector.z);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void set(float x, float y, float z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}

	public float angleSigned(Vector3D vec) {
		Vector3D vC = cross(vec);
		float angle = (float) Math.atan2(vC.length(), dot(vec));
		angle = vC.dot(Vector3D.Y_AXIS) < 0.0 ? -angle : angle;
		return angle;
	}

	public Vector3D cross(Vector3D vec) {
		return new Vector3D(y * vec.z - z * vec.y, z * vec.x - x * vec.z, x * vec.y - y * vec.x);
	}

	public float dot(Vector3D vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	public Vector3D minus(Vector3D vec) {
		return new Vector3D(x - vec.x, y - vec.y, z - vec.z);
	}

	public Vector3D add(Vector3D vec) {
		return new Vector3D(x + vec.x, y + vec.y, z + vec.z);
	}

	public Vector3D multiplyScalar(float s) {
		return new Vector3D(x * s, y * s, z * s);
	}

	public void normalize() {
		float len = length();
		if (len > 0) {
			x /= len;
			y /= len;
			z /= len;
		}
	}
}
