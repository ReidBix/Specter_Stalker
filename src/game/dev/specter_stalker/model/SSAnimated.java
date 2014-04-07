package game.dev.specter_stalker.model;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*
 * Class for creating an animated SS that is not movable
 */
public class SSAnimated {

	/*
	 * Initialized variables for use in later code
	 */
	
	// Used for displaying errors with methods in this class on LogCat
	@SuppressWarnings("unused")
	private static final String TAG = SSAnimated.class.getSimpleName();

	// Bitmap for the Specter Stalker sprite
	private Bitmap bitmap;
	// Rectangle for where it is located
	private Rect sourceRect;
	// Number of frames in the sprite
	private int frameNr;
	// What the current frame is at
	private int currentFrame;
	// The time of the last frame update
	private long frameTicker;
	// Milliseconds between each frame
	private int framePeriod;
	// Sprite width in pixels
	private int spriteWidth;
	// Sprite height in pixels
	private int spriteHeight;
	// X position
	private int x;
	// Y position
	private int y;

	/*
	 * Constructor for SSA that takes the Sprite/Bitmap, x and y position, width
	 * and height of sprite and turns the width and height into a sourceRect
	 */
	public SSAnimated(Bitmap bitmap, int x, int y, int width, int height,
			int fps, int frameCount) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 01;
	}

	/*
	 * Method for updating the current frame of the panel in order to get the
	 * correct sprite
	 */
	public void update(long gameTime) {
		// If the gameTime is greater than the time of last frame update and the period of the frame...
		if (gameTime > frameTicker + framePeriod) {
			// The time of last frame update is the current game time
			frameTicker = gameTime;
			// The current frame is changed
			currentFrame++;
			// If the current frame is greater than how many frames there are
			if (currentFrame >= frameNr) {
				// Reset the current frame
				currentFrame = 0;
			}
		}
		// This rectangle's left side is the current frame times the width
		this.sourceRect.left = currentFrame * spriteWidth;
		// This rectangle's right side is the left side plus the width
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}
	
	/*
	 * Used for drawing the SSA
	 */
	public void draw(Canvas canvas) {
		// Makes a destinated rectangle
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY()
				+ spriteHeight);
		// And draws it on the canvas
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		
		// Shows the entire sprite map used and shows how it goes through it
		// canvas.drawBitmap(bitmap, 20, 150, null);
		// Paint paint = new Paint();
		// paint.setARGB(50, 0, 255, 0);
		// canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 +
		// (currentFrame * destRect.width()) + destRect.width(), 150 +
		// destRect.height(), paint);
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

	public Rect getSourceRect() {
		return sourceRect;
	}

	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}

	public int getFrameNr() {
		return frameNr;
	}

	public void setFrameNr(int frameNr) {
		this.frameNr = frameNr;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public long getFrameTicker() {
		return frameTicker;
	}

	public void setFrameTicker(long frameTicker) {
		this.frameTicker = frameTicker;
	}

	public int getFramePeriod() {
		return framePeriod;
	}

	public void setFramePeriod(int framePeriod) {
		this.framePeriod = framePeriod;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
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

}
