package game.dev.specter_stalker.other;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/*
 * Class for a single Particle that is used in the Explosion.java class
 */
public class Particle {

	/*
	 * Initialized variables for use in later code
	 */

	// If the Particle is alive
	public static final int STATE_ALIVE = 0;
	// If the Particle is dead
	public static final int STATE_DEAD = 1;
	// Lifetime for the Particle
	public static final int DEFAULT_LIFETIME = 200;
	// Largest a particle can be
	public static final int MAX_DIMENSION = 5;
	// Fastest a particle can be
	public static final int MAX_SPEED = 10;

	// Current state
	private int state;
	// Width of particle
	private float width;
	// Height of particle
	private float height;
	// X and Y position
	private float x, y;
	// X and Y velocity
	private double xv, yv;
	// Age of Particle
	private int age;
	// Lifetime of Particle
	private int lifetime;
	// Color of Particle
	private int color;
	// Paint color
	private Paint paint;

	/*
	 * Constructor that takes an X and Y position
	 */
	public Particle(int x, int y) {
		// Places the particle at that X and Y
		this.x = x;
		this.y = y;
		// Makes it alive
		this.state = Particle.STATE_ALIVE;
		// Makes the height and width some integer between 1 and the max
		// dimension
		this.width = rndInt(1, MAX_DIMENSION);
		this.height = this.width;
		// Puts on the lifetime
		this.lifetime = DEFAULT_LIFETIME;
		// Sets the age to 0
		this.age = 0;
		// Makes the x and y velocity some double between 0 and twice the max
		// speed minus the max speed
		this.xv = (rndDbl(0, MAX_SPEED * 2) - MAX_SPEED);
		this.yv = (rndDbl(0, MAX_SPEED * 2) - MAX_SPEED);
		// Corrects the x and y velocity if too large
		if (xv * xv + yv * yv > MAX_SPEED * MAX_SPEED) {
			xv *= 0.7;
			yv *= 0.7;
		}
		// Sets the color to a random color with full opacity
		this.color = Color.argb(255, rndInt(0, 255), rndInt(0, 255),
				rndInt(0, 255));
		// Makes this color into a paint
		this.paint = new Paint(this.color);
	}

	/*
	 * Used to create a random integer between 2 numbers
	 */
	static int rndInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	/*
	 * Used to create a random double between 2 numbers
	 */
	static double rndDbl(double min, double max) {
		return min + (max - min) * Math.random();
	}

	/*
	 * Used to update the current state of the Particle
	 */
	public void update() {
		// If the state isn't dead...
		if (this.state != STATE_DEAD) {
			// Change the position with the velocity
			this.x += this.xv;
			this.y += this.yv;

			// int color = 0xff336699
			// int alpha = color >>> 24; (Opacity)
			// int red = color >>> 16 & 0xFF; (Red Hue)
			// int green = color >>> 8 & 0xFF; (Green Hue)
			// int blue = color & 0xFF; (Blue Hue)

			// Get the current opacity
			int a = this.color >>> 24;
			// Subtract from that (Make it more transparent)
			a -= 2;
			// If the opacity is less than or equal to 0 (completely
			// transparent)...
			if (a <= 0) {
				// The particle is now dead
				this.state = STATE_DEAD;
				// Otherwise...
			} else {
				// Get the current color and change the opacity to -2 from
				// before
				this.color = (this.color & 0x00ffffff) + (a << 24);
			}
			// If the age is greater than the lifetime...
			if (this.age >= this.lifetime) {
				// The particle is now dead
				this.state = STATE_DEAD;
			}
		}
	}

	/*
	 * Updates given a container (the screen)
	 */
	public void update(Rect container) {
		// If it is alive...
		if (this.isAlive()) {
			// If it has gone out of range of the screen
			if (this.x <= container.left
					|| this.x >= container.right - this.width) {
				// Turn it around
				this.xv *= -1;
			}
			// if it has gone out of range of the screen
			if (this.y <= container.top
					|| this.y >= container.bottom - this.height) {
				// Turn it around
				this.yv *= -1;
			}
		}
		// Update the particle
		update();
	}

	/*
	 * Used to draw the Particle on the canvas
	 */
	public void draw(Canvas canvas) {
		paint.setColor(this.color);
		canvas.drawRect(this.x, this.y, this.x + this.width, this.y
				+ this.height, paint);
	}
	
	/*
	 * Sets the current state to alive
	 */
	public boolean isAlive() {
		return this.state == STATE_ALIVE;
	}
	
	/*
	 * Sets the current state to dead
	 */
	public boolean isDead() {
		return this.state == STATE_DEAD;
	}

	/*
	 * Getters and Setters
	 */
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
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

	public double getXv() {
		return xv;
	}

	public void setXv(double xv) {
		this.xv = xv;
	}

	public double getYv() {
		return yv;
	}

	public void setYv(double yv) {
		this.yv = yv;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

}
