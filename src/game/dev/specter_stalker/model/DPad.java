package game.dev.specter_stalker.model;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

/*
 * Class used to create a DPad
 */
public class DPad {

	/*
	 * Initialized variables for use in later code
	 */

	// Bitmap for the DPad sprite
	private Bitmap bitmap;
	// Location in the x direction
	private int x;
	// Location in the y direction
	private int y;
	// If the DPad is touched or not
	private boolean touched;
	// Direction that it is currently oriented in
	// 0 = None, 1 = Right, 2 = Left, 3 = Up, 4 = Down
	private int direction;

	// Constructor for the DPad, takes a sprite and an x and y position
	public DPad(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		// Set the direction to none
		this.setDirection(0);
	}

	// Constructor for the DPad, takes only a sprite
	public DPad(Bitmap bitmap) {
		this.bitmap = bitmap;
		// Set the direction to none
		this.setDirection(0);
	}

	/*
	 * Used for drawing the DPad on the canvas given a sprite
	 */
	public void draw(Canvas canvas) {
		// Position - (Width or Height) / 2 = Middle of the Sprite
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
				y - (bitmap.getHeight() / 2), null);
	}

	/*
	 * Used for handling a touch event that is down
	 */
	public void handleActionDown(int eventX, int eventY) {
		// If the event is within the sprite's range...
		if (eventX >= (x - bitmap.getWidth() / 2)
				&& (eventX <= (x + bitmap.getWidth() / 2))) {
			if (eventY >= (y - bitmap.getHeight() / 2)
					&& (eventY <= (y + bitmap.getHeight() / 2))) {
				// It is touched
				setTouched(true);
				// Otherwise...
			} else {
				// It is not touched
				setTouched(false);
			}
			// Otherwise...
		} else {
			// It is not touched
			setTouched(false);
		}
	}

	/*
	 * Getters and Setters
	 */

	public int None() {
		setDirection(0);
		return this.direction;
	}

	public int Right() {
		setDirection(1);
		return this.direction;
	}

	public int Left() {
		setDirection(2);
		return this.direction;
	}

	public int Up() {
		setDirection(3);
		return this.direction;
	}

	public int Down() {
		setDirection(4);
		return this.direction;
	}

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

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
