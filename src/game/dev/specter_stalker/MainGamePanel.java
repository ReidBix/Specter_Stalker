package game.dev.specter_stalker;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 */

import game.dev.specter_stalker.model.DPad;
import game.dev.specter_stalker.model.Polygon;
import game.dev.specter_stalker.model.SSAMovable;
import game.dev.specter_stalker.model.components.Speed;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * Used for previous versions of models or unused methods, leave commented unless you want to see simpler builds of classes or unused methods
 */

//import game.dev.specter_stalker.model.SpecterStalker;
//import game.dev.specter_stalker.model.SSAnimated;
//import game.dev.specter_stalker.other.Explosion;

/*
 * The MainGamePanel is a type of View in that it is not initially used at the start of an application
 * and must be called upon in the MainScreen, contains the majority of code for the game
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	/*
	 * Initialized variables for use in later code
	 */

	// Used for displaying errors with methods in this class on LogCat
	private static final String TAG = MainGamePanel.class.getSimpleName();

	// Used for the starting the MainGamePanel through the MainThread class
	private MainThread thread;

	// Specter Stalker Animated Movable, Main sprite of game
	private SSAMovable SSAM;

	// DPad that appears on touch, used to move SSAM
	private DPad DPad2;
	// Used to prepare DPad2 to be displayed or not displayed
	private boolean DPad2isReady = false;

	// Average FPS used for determining current FPS of game
	private String avgFps;

	// Change for different speed of character
	private static final float movementSpeed = 3;
	// Speed used to go up
	private static final Speed up = new Speed(0, -movementSpeed);
	// Speed used to go down
	private static final Speed down = new Speed(0, movementSpeed);
	// Speed used to go right
	private static final Speed right = new Speed(movementSpeed, 0);
	// Speed used to go left
	private static final Speed left = new Speed(-movementSpeed, 0);
	// Speed used to stop
	private static final Speed stop = new Speed(0, 0);

	// Triangular region on up-part of DPad2
	private Polygon upTriangle;
	// Triangular region on down-part of DPad2
	private Polygon downTriangle;
	// Triangular region on right-part of DPad2
	private Polygon rightTriangle;
	// Triangular region on left-part of DPad2
	private Polygon leftTriangle;
	// Triangular region on middle-part of DPad2
	private Polygon middleSquare;

	// Black color for background canvas
	private int black = Color.BLACK;

	/*
	 * Used for previous versions of models or unused methods, leave commented
	 * unless you want to see simpler builds of classes or unused methods
	 */
	// private SpecterStalker SS; (Non-animated, movable with touch, can bounce
	// off walls of screen)

	// private SSAnimated SSA; (Animated, not movable, cannot bounce off walls)

	// private DPad DPad; (Static position in bottom left corner, doesn't move
	// character anymore(?), no animation)

	// private Explosion explosion; (Large explosion of particles)

	// private static final int EXPLOSION_SIZE = 200; (Changes size of the
	// explosion)

	/*
	 * Main constructor for the MainGamePanel class. Takes a context (MainScreen
	 * in this) and allows it to be changed
	 */
	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int)
	 * 
	 * Currently unused, implements whatever when the surface is changed
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * )
	 * 
	 * Used at the start of implementation (in MainScreen), starts the
	 * MainThread and initializes all of the usable classes
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// Puts in the MainThread in and starts it
		thread = new MainThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();

		// Creates a new SSAM using the first right sprite (50x50 pixels) at
		// location 250, 250, shifting between sprites at 5 frames per second
		// for 4 frames
		SSAM = new SSAMovable(BitmapFactory.decodeResource(getResources(),
				R.drawable.ssrightsprite50x50), 250, 250, 50, 50, 5, 4);

		// Forces SSAM to have only 1 frame (a still image), and stops any
		// movement
		SSAM.setFrameNr(1);
		SSAM.setSpeed(stop);

		// Creates a new DPad using the nothing sprite (a single blank pixel) at
		// 0,0
		DPad2 = new DPad(BitmapFactory.decodeResource(getResources(),
				R.drawable.nothing), 0, 0);

		// Creates a new SS using a slightly larger link sprite at position 50,
		// 50
		// SS = new SpecterStalker(BitmapFactory.decodeResource(getResources(),
		// R.drawable.liiiink), 50, 50);

		// Creates a new SSA using the 50x50 right sprite at location 10,50,
		// shifting between sprites at 5 frames per second for 4 frames
		// SSA = new SSAnimated(BitmapFactory.decodeResource(getResources(),
		// R.drawable.ssrightsprite50x50), 10, 50, 50, 50, 5, 4);

		// Creates a DPad in the bottom left corner
		// DPad = new DPad(BitmapFactory.decodeResource(getResources(),
		// R.drawable.dpad), 135, 617);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 * 
	 * Used when the surface is destroyed, forces retrying going back to join
	 * the thread if possible, and if an error is returned catches it
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * 
	 * When the screen is touched do X, Y, or Z
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Turns the point at which the user touches the screen into X and Y
		// usable ints
		int touchX = (int) event.getX();
		int touchY = (int) event.getY();

		// If the screen is touched once (ACTION_DOWN), Initialises everything
		// for on movement after touching down
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// Checks if the touch is within the range of the SSAM sprite
			SSAM.handleActionDown(touchX, touchY);
			// Checks if the touch is within the range of the DPad (It is
			// because DPad2 will appear where touched
			DPad2.handleActionDown(touchX, touchY);

			// Same as DPad2 for old, unused DPad
			// DPad.handleActionDown(touchX, touchY);

			// Prepares DPad2 to do things
			DPad2isReady = true;
			// Creates a new DPad with a semi-transparent 150x150 pixel DPad
			// with normal orientation at touch point
			DPad2 = new DPad(BitmapFactory.decodeResource(getResources(),
					R.drawable.dpadtransparent150x150normal), touchX, touchY);

			// Used for setting up a 'deadzone' of no movement within a 35x35
			// pixel square from the center of the DPad
			int deadzone = 35;

			// Creating arrays of ints with each pair mapping to a different
			// vertice of a polygon
			int[] upTArrayX = { touchX, touchX - 150, touchX + 150 };
			int[] upTArrayY = { touchY - deadzone, touchY - 150, touchY - 150 };
			upTriangle = new Polygon(upTArrayX, upTArrayY, 3);
			int[] downTArrayX = { touchX, touchX - 150, touchX + 150 };
			int[] downTArrayY = { touchY + deadzone, touchY + 150, touchY + 150 };
			downTriangle = new Polygon(downTArrayX, downTArrayY, 3);
			int[] rightTArrayX = { touchX + deadzone, touchX + 150,
					touchX + 150 };
			int[] rightTArrayY = { touchY, touchY - 150, touchY + 150 };
			rightTriangle = new Polygon(rightTArrayX, rightTArrayY, 3);
			int[] leftTArrayX = { touchX - deadzone, touchX - 150, touchX - 150 };
			int[] leftTArrayY = { touchY, touchY - 150, touchY + 150 };
			leftTriangle = new Polygon(leftTArrayX, leftTArrayY, 3);
			int[] middleSArrayX = { touchX, touchX, touchX + deadzone,
					touchX - deadzone };
			int[] middleSArrayY = { touchY - deadzone, touchY + deadzone,
					touchY, touchY };
			middleSquare = new Polygon(middleSArrayX, middleSArrayY, 4);
		}

		// If the screen has a moving input (finger moving across screen), will
		// ALWAYS use ACTION_DOWN followed by ACTION_MOVE
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// Allows SSAM to be dragged and dropped
			if (SSAM.isTouched()) {
				SSAM.setX(touchX);
				SSAM.setY(touchY);
			}

			// OLD METHOD: If DPad was touched, moved SSAM 2 pixels right
			// if (DPad.isTouched()){
			// int x = SSAM.getX();
			// SSAM.setX(x+2);
			// }

			// If DPad2 is prepared
			if (DPad2isReady == true) {
				// If the location of the movement is within the bounds of the
				// downTriangle...
				// DOWN
				if (downTriangle.contains(touchX, touchY)) {
					// Change the sprite of DPad2 to down
					DPad2.setBitmap(BitmapFactory.decodeResource(
							getResources(),
							R.drawable.dpadtransparent150x150down));
					// Set the total frames of SSAM to 4 (As opposed to 1/Single
					// Image)
					SSAM.setFrameNr(4);
					// Set SSAM into the 4 down sprites
					SSAM.setBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.ssdownsprite50x50));
					// Set SSAM's speed down
					SSAM.setSpeed(down);
				}
				// If the location of the movement is within the bounds of the
				// upTriangle...
				// UP
				if (upTriangle.contains(touchX, touchY)) {
					// Change the sprite of DPad2 to up
					DPad2.setBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.dpadtransparent150x150up));
					// Set the total frames of SSAM to 4 (As opposed to 1/Single
					// Image)
					SSAM.setFrameNr(4);
					// Set SSAM into the 4 up sprites
					SSAM.setBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.ssupsprite50x50));
					// Set SSAM's speed up
					SSAM.setSpeed(up);
				}
				// If the location of the movement is within the bounds of the
				// rightTriangle...
				// RIGHT
				if (rightTriangle.contains(touchX, touchY)) {
					// Change the sprite of DPad2 to right
					DPad2.setBitmap(BitmapFactory.decodeResource(
							getResources(),
							R.drawable.dpadtransparent150x150right));
					// Set the total frames of SSAM to 4 (As opposed to 1/Single
					// Image)
					SSAM.setFrameNr(4);
					// Set SSAM into the 4 right sprites
					SSAM.setBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.ssrightsprite50x50));
					// Set SSAM's speed right
					SSAM.setSpeed(right);
				}
				// If the location of the movement is within the bounds of the
				// leftTriangle...
				// LEFT
				if (leftTriangle.contains(touchX, touchY)) {
					// Change the sprite of DPad2 to left
					DPad2.setBitmap(BitmapFactory.decodeResource(
							getResources(),
							R.drawable.dpadtransparent150x150left));
					// Set the total frames of SSAM to 4 (As opposed to 1/Single
					// Image)
					SSAM.setFrameNr(4);
					// Set SSAM into the 4 left sprites
					SSAM.setBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.ssleftsprite50x50));
					// Set SSAM's speed left
					SSAM.setSpeed(left);
				}
				// If the location of the movement is within the bounds of the
				// middleSquare...
				// DEADZONE
				if (middleSquare.contains(touchX, touchY)) {
					// Change the sprite of DPad2 to normal
					DPad2.setBitmap(BitmapFactory.decodeResource(
							getResources(),
							R.drawable.dpadtransparent150x150normal));
					// Set the total frames of SSAM to 1 (Single Image/Stopped)
					SSAM.setFrameNr(1);
					// Set SSAM's speed to stop
					SSAM.setSpeed(stop);
				}

			}

		}

		// If the user takes input off the screen (removes finger),
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// SSAM is no longer touched (For dragging and dropping SSAM)
			if (SSAM.isTouched()) {
				SSAM.setTouched(false);
			}
			// Set the total frames of SSAM to 1 (Single Image/Stopped)
			SSAM.setFrameNr(1);
			// Set SSAM's speed to stop
			SSAM.setSpeed(stop);
			// Resets the image of DPad2 to nothing (single blank pixel)
			DPad2 = new DPad(BitmapFactory.decodeResource(getResources(),
					R.drawable.nothing), (int) event.getX(), (int) event.getY());
			// Initializes a set of blank coordinates for resetting the
			// triangles/square for next touch
			int[] blank = { 0, 0, 0 };
			upTriangle = new Polygon(blank, blank, 0);
			downTriangle = new Polygon(blank, blank, 0);
			rightTriangle = new Polygon(blank, blank, 0);
			leftTriangle = new Polygon(blank, blank, 0);
			middleSquare = new Polygon(blank, blank, 0);
			// Makes DPad2 no longer ready
			DPad2isReady = false;

		}

		// Closes application if click bottom of screen. Change 0 to larger
		// values for increasing size. 0 means no exiting by this means
		if (event.getY() > getHeight() - 0) {
			thread.setRunning(false);
			((Activity) getContext()).finish();
		} else {
			Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
		}

		// Used to drag and drop original Specter Stalker
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// SS.handleActionDown((int) event.getX(), (int) event.getY());
		// if (event.getY() > getHeight() - 50) {
		// thread.setRunning(false);
		// ((Activity) getContext()).finish();
		// } else {
		// Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
		// }
		// }
		// if (event.getAction() == MotionEvent.ACTION_MOVE) {
		// if (SS.isTouched()) {
		// SS.setX((int) event.getX());
		// SS.setY((int) event.getY());
		// }
		// }
		// if (event.getAction() == MotionEvent.ACTION_UP) {
		// if (SS.isTouched()) {
		// SS.setTouched(false);
		// }
		// }

		// Used to create Explosions on touch
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// if (explosion == null || explosion.getState() ==
		// Explosion.STATE_DEAD) {
		// explosion = new Explosion(EXPLOSION_SIZE, (int)event.getX(),
		// (int)event.getY());
		// }
		//
		// }

		// return true once event is completed
		return true;
	}

	// Draws the objects, images, sprites, etc.
	public void render(Canvas canvas) {
		// Make the canvas black
		canvas.drawColor(black);
		// Draws SSAM
		SSAM.draw(canvas);
		// As long as DPad2 is ready, draw it
		if (DPad2isReady) {
			DPad2.draw(canvas);
		}
		// Draws the FPS in the top right
		displayFps(canvas, avgFps);

		// SSA.draw(canvas);
		// SS.draw(canvas);
		// DPad.draw(canvas);
		// if (explosion != null) {
		// explosion.draw(canvas);
		// }

	}

	/*
	 * Updates any classes with the current time
	 */
	public void update() {
		// Updates the SSAM with the system's current time
		SSAM.update(System.currentTimeMillis());

		// SS.update(); update SS
		// SSA.update(System.currentTimeMillis()); update SSA with current time

		// Useless/Annoying Method: Used to give SSAM a 45 degree angle speed
		// and bounces off walls of screen
		// if (SSAM.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
		// && SSAM.getX() + SSAM.getBitmap().getWidth() / 2 >= getWidth()) {
		// SSAM.getSpeed().toggleXDirection();
		// }
		//
		// if (SSAM.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
		// && SSAM.getX() - SSAM.getBitmap().getWidth() / 2 <= 0) {
		// SSAM.getSpeed().toggleXDirection();
		// }
		//
		// if (SSAM.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
		// && SSAM.getY() + SSAM.getBitmap().getHeight() / 2 >= getHeight()) {
		// SSAM.getSpeed().toggleYDirection();
		// }
		//
		// if (SSAM.getSpeed().getyDirection() == Speed.DIRECTION_UP
		// && SSAM.getY() - SSAM.getBitmap().getHeight() / 2 <= 0) {
		// SSAM.getSpeed().toggleYDirection();
		// }

		// Old Method: Used to give SS a 45 degree angled speed and bounces off
		// walls of screen
		// if (SS.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
		// && SS.getX() + SS.getBitmap().getWidth() / 2 >= getWidth()) {
		// SS.getSpeed().toggleXDirection();
		// }
		//
		// if (SS.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
		// && SS.getX() - SS.getBitmap().getWidth() / 2 <= 0) {
		// SS.getSpeed().toggleXDirection();
		// }
		//
		// if (SS.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
		// && SS.getY() + SS.getBitmap().getHeight() / 2 >= getHeight()) {
		// SS.getSpeed().toggleYDirection();
		// }
		//
		// if (SS.getSpeed().getyDirection() == Speed.DIRECTION_UP
		// && SS.getY() - SS.getBitmap().getHeight() / 2 <= 0) {
		// SS.getSpeed().toggleYDirection();
		// }

		// Used for explosion method
		// if (explosion != null && explosion.isAlive()) {
		// explosion.update(getHolder().getSurfaceFrame());
		// }

	}

	/*
	 * Takes the average FPS and updates it to whatever it is
	 */
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	/*
	 * Used to display the FPS on the canvas
	 */
	private void displayFps(Canvas canvas, String fps) {
		// If there is a canvas and there is an FPS
		if (canvas != null && fps != null) {
			// Create a new white Paint
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			// Draw it in the upper right corner
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}

}
