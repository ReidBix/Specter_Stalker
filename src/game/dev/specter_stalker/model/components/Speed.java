package game.dev.specter_stalker.model.components;

/*
 * Class that is a component of the SpecterStalker.java and SSAMovable. java classes. Allows for a velocity. Kind of like a class within a class
 */
public class Speed {

	/*
	 * Initialized variables for use in later code
	 */

	// Sets what the directions mean. Positive = Right/Down, Negative = Left/Up
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_UP = -1;
	public static final int DIRECTION_DOWN = 1;
	// The x velocity
	private float xv = 1;
	// The y velocity
	private float yv = 1;
	// Sets the current x Direction
	private int xDirection = DIRECTION_RIGHT;
	// Sets the current y Direction
	private int yDirection = DIRECTION_DOWN;

	/*
	 * Blank constructor that sets the speed to 5 right and 5 down
	 */
	public Speed() {
		this.xv = 5;
		this.yv = 5;
	}

	/*
	 * Constructor that takes an x and y velocity and sets that as a speed
	 */
	public Speed(float xv, float yv) {
		this.xv = xv;
		this.yv = yv;
	}

	/*
	 * Getters and Setters
	 */

	public float getXv() {
		return xv;
	}

	public void setXv(float xv) {
		this.xv = xv;
	}

	public float getYv() {
		return yv;
	}

	public void setYv(float yv) {
		this.yv = yv;
	}

	public int getxDirection() {
		return xDirection;
	}

	public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	public int getyDirection() {
		return yDirection;
	}

	public void setyDirection(int yDirection) {
		this.yDirection = yDirection;
	}

	public void toggleXDirection() {
		xDirection = xDirection * -1;
	}

	public void toggleYDirection() {
		yDirection = yDirection * -1;
	}

}
