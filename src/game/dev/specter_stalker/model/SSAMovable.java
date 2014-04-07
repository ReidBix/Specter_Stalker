package game.dev.specter_stalker.model;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 */

import game.dev.specter_stalker.model.components.Speed;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/*
 * Class for a movable, animated sprite
 */
public class SSAMovable {

	/*
	 * Initialized variables for use in later code
	 */
	
	// Used for displaying errors with methods in this class on LogCat
	@SuppressWarnings("unused")
	private static final String TAG = SSAnimated.class.getSimpleName();

	// Exactly the same as SpecterStalker.java
	private Bitmap bitmap;
	private int x;
	private int y;
	private boolean touched;
	private Speed speed;

	// Exactly the same as SSAnimated.java
	private Rect sourceRect;
	private int frameNr;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	private int spriteWidth;
	private int spriteHeight;

	/*
	 * Constructor that takes a sprite map, x and y location, width and height
	 * of sprites, frames per second, and count of frames (Look at both
	 * SpecterStalker.java and SSAnimated.java
	 */
	public SSAMovable(Bitmap bitmap, int x, int y, int width, int height,
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
		this.speed = new Speed(0, 0);
	}

	/*
	 * Used to draw the given sprite from the sprite map (From SSAnimated.java)
	 */
	public void draw(Canvas canvas) {
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY()
				+ spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		
		// Used to draw whole sprite map (From SSAnimated.java)
		// canvas.drawBitmap(bitmap, 20, 150, null);
		// Paint paint = new Paint();
		// paint.setARGB(50, 0, 255, 0);
		// canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 +
		// (currentFrame * destRect.width()) + destRect.width(), 150 +
		// destRect.height(), paint);
	}

	/*
	 * Used to handle a touch event (From SpecterStalker.java)
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2)
				&& (eventX <= (x + bitmap.getWidth() / 2))) {
			if (eventY >= (y - bitmap.getHeight() / 2)
					&& (eventY <= (y + bitmap.getHeight() / 2))) {
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}

	/*
	 * Used to update the current SSAM (Look at BOTH SpecterStalker.java and
	 * SSAnimated.java)
	 */
	public void update(long gameTime) {
		if (!touched) {
			x += (speed.getXv() * speed.getxDirection());
			y += (speed.getYv() * speed.getyDirection());
		}
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}

	/*
	 * Getters and Setters
	 */
	
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
