package game.dev.specter_stalker.model;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 */

import game.dev.specter_stalker.model.components.Speed;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/*
 * Class for old, original specter stalker, movable, no animation
 */
public class SpecterStalker {

	/*
	 * Initialized variables for use in later code
	 */

	// Bitmap for the Specter Stalker sprite
	private Bitmap bitmap;
	// Location in the x direction
	private int x;
	// Location in the y direction
	private int y;
	// If the DPad is touched or not
	private boolean touched;
	// Speed of the sprite (from .model.components)
	private Speed speed;

	/*
	 * Constructor for SS that takes the Bitmap/sprite and an x,y position
	 */
	public SpecterStalker(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		// Sets the Speed to a new Speed (look at constructor in Speed.java)
		this.speed = new Speed();
	}

	/*
	 * Used for drawing the sprite, draws at the center of the sprite (look at
	 * DPad.draw)
	 */
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
				y - (bitmap.getHeight() / 2), null);
	}

	/*
	 * Used for handling a touch event within the sprite (look at
	 * DPad.handleActionDown)
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX - 25 >= (x - bitmap.getWidth() / 2)
				&& (eventX - 25 <= (x + bitmap.getWidth() / 2))) {
			if (eventY - 25 >= (y - bitmap.getHeight() / 2)
					&& (eventY - 25 <= (y + bitmap.getHeight() / 2))) {
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}

	/*
	 * As long as SS is not touched, will implement the speed
	 */
	public void update() {
		if (!touched) {
			x += (speed.getXv() * speed.getxDirection());
			y += (speed.getYv() * speed.getyDirection());
		}
	}

	/*
	 * Getters and Setters
	 */

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
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

	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

}
