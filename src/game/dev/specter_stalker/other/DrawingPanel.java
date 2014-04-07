package game.dev.specter_stalker.other;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 */

import game.dev.specter_stalker.R;
import game.dev.specter_stalker.model.Glyphs;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * Panel for testing the Glyphs.java, replaces the MainGamePanel.java in the MainScreen.java
 */
public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback {

	/*
	 * Initialized variables for use in later code
	 */

	// Canvas
	private Canvas canvas;
	// Glyphs
	private Glyphs glyphs;

	/*
	 * Constructor that takes a given context
	 */
	public DrawingPanel(Context context) {
		// Supers that context
		super(context);
		getHolder().addCallback(this);
		// And loads the text resources
		loadResources();
		setFocusable(true);
	}

	/*
	 * Loads the sprite map of the Legend of Zelda 18x18 pixel glyphs
	 */
	private void loadResources() {
		this.glyphs = new Glyphs(BitmapFactory.decodeResource(getResources(),
				R.drawable.glyphs_white18x18));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int)
	 * 
	 * Look at MainGamePanel.surfaceChanged
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * )
	 * 
	 * Look at MainGamePanel.surfaceCreated
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 * 
	 * Look at MainGamePanel.surfaceDestroyed
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * 
	 * Upon touching the screen...
	 */
	public boolean onTouchEvent(MotionEvent event) {
		// Try to...
		try {
			// Lock the canvas as the current canvas
			canvas = getHolder().lockCanvas();
			// And do...
			synchronized (getHolder()) {
				// If the touch is a down event or a moving event
				if (event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_MOVE) {
					// Make the canvas background black
					canvas.drawColor(Color.BLACK);
					// Draw this string at the point
					glyphs.drawString(canvas, "Drawing string at "
							+ (int) event.getX() + " " + (int) event.getY(),
							(int) event.getX(), (int) event.getY());
				}
				// If the touch is an up event
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// Make the canvas background black
					canvas.drawColor(Color.BLACK);
					// Draw this string at the point
					glyphs.drawString(canvas,
							"Drawn string at " + (int) event.getX() + " "
									+ (int) event.getY(), (int) event.getX(),
							(int) event.getY());
				}
			}
			// And finally...
		} finally {
			// If the canvas is there
			if (canvas != null) {
				// Unlock it and stop editing
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
		return true;
	}
}
