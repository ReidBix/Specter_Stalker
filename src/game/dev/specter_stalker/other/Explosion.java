package game.dev.specter_stalker.other;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 */

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/*
 * Class for creating an explosion of particles
 */
public class Explosion {

	/*
	 * Initialized variables for use in later code
	 */

	// Used for displaying errors with methods in this class on LogCat
	private static final String TAG = Explosion.class.getSimpleName();

	// If the explosion is alive (still doing stuff)
	public static final int STATE_ALIVE = 0;
	// If the explosion is dead (done)
	public static final int STATE_DEAD = 1;

	// An array of particles
	private Particle[] particles;
	// X and Y location
	private int x, y;
	// Size of explosion
	private int size;
	// Current state of explosion
	private int state;

	/*
	 * Constructor that takes number of particles and an x,y location
	 */
	public Explosion(int particleNr, int x, int y) {
		Log.d(TAG, "Explosion created at " + x + "," + y);
		// This explosion is now alive
		this.state = STATE_ALIVE;
		// Create a new array of particles that is however many number you
		// wanted big
		this.particles = new Particle[particleNr];
		// For the length of that array
		for (int i = 0; i < this.particles.length; i++) {
			// Add a particle at that starting point
			Particle p = new Particle(x, y);
			// Add that particle to the particle array
			this.particles[i] = p;
		}
		// The size of explosion is the number of particles in it
		this.size = particleNr;
	}

	/*
	 * Used to update the current status of the explosion
	 */
	public void update() {
		// If it is not dead...
		if (this.state != STATE_DEAD) {
			// It is now dead
			boolean isDead = true;
			// For the array of particles...
			for (int i = 0; i < this.particles.length; i++) {
				// If any particle is alive...
				if (this.particles[i].isAlive()) {
					// Update that particle
					this.particles[i].update();
					// Make the explosion alive again
					isDead = false;
				}
			}
			// If the explosion is dead
			if (isDead)
				// Keep it dead
				this.state = STATE_DEAD;
		}
	}

	/*
	 * Used to update, same as before but given a rectangle (container/screen)
	 */
	public void update(Rect container) {
		if (this.state != STATE_DEAD) {
			boolean isDead = true;
			for (int i = 0; i < this.particles.length; i++) {
				if (this.particles[i].isAlive()) {
					this.particles[i].update(container);
					isDead = false;
				}
			}
			if (isDead)
				this.state = STATE_DEAD;
		}
	}

	/*
	 * Used to draw the particles on the canvas
	 */
	public void draw(Canvas canvas) {
		// For the array of particles
		for (int i = 0; i < this.particles.length; i++) {
			// If the particle is alive...
			if (this.particles[i].isAlive()) {
				// Draw it on the canvas
				this.particles[i].draw(canvas);
			}
		}
	}

	/*
	 * Makes the current state alive
	 */
	public boolean isAlive() {
		return this.state == STATE_ALIVE;
	}

	/*
	 * Makes the current state dead
	 */
	public boolean isDead() {
		return this.state == STATE_DEAD;
	}

	/*
	 * Getters and Setters
	 */

	public Particle[] getParticles() {
		return particles;
	}

	public void setParticles(Particle[] particles) {
		this.particles = particles;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
