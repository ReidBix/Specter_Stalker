package game.dev.specter_stalker;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 * java.x = Available through the Java interface
 */

import java.text.DecimalFormat;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/*
 * The MainThread used for pretty much setting the FPS as well as handling the MainGamePanel as well as keeping statistics and data
 */
public class MainThread extends Thread {

	/*
	 * Initialized variables for use in later code
	 */

	// Used for displaying errors with methods in this class on LogCat
	private static final String TAG = MainThread.class.getSimpleName();

	// Sets the Optimum FPS to 50 (Seems to hover about 40 in-game)
	private final static int MAX_FPS = 50;
	// Sets the Optimum number of Frame Skips before changing something to 5
	private final static int MAX_FRAME_SKIPS = 5;
	// Sets how long the period of the frame is
	private final static int FRAME_PERIOD = 1000 / MAX_FPS;

	/*
	 * Variables for statistics
	 */

	// Sets the interval for how long before stats are updated (Every second =
	// 1000)
	private final static int STAT_INTERVAL = 1000;
	// For storing the average FPS for storing last n FPS
	private final static int FPS_HISTORY_NR = 10;
	// Last time the status was stored
	private long lastStatusStore = 0;
	// The status time counter
	private long statusIntervalTimer = 01;
	// Number of frames skipped since the game started
	@SuppressWarnings("unused")
	private long totalFramesSkipped = 01;
	// Number of frames skipped in a store cycle (1 second)
	private long framesSkippedPerStatCycle = 01;

	// Number of rendered frames in an interval
	private int frameCountPerStatCycle = 0;
	// Total frame count
	@SuppressWarnings("unused")
	private long totalFrameCount = 01;
	// The last FPS values
	private double fpsStore[];
	// Number of times stats has been read
	private long statsCount = 0;
	// The average FPS since the game started
	private double averageFps = 0.0;

	// Decimal format for 2 decimal places
	private DecimalFormat df = new DecimalFormat("0.##");

	// Flag to hold game state
	private boolean running;

	// Instantiating new SurfaceHolders and MainGamePanels
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;

	/*
	 * Constructor for the MainThread, takes in the surfaceHolder and
	 * MainGamePanel and sets to running
	 */
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		this.running = true;
	}

	/*
	 * For holding the game state, sets whether if running or not
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 * 
	 * While the thread runs, do this
	 */
	@Override
	public void run() {
		// Create a new canvas
		Canvas canvas;
		// LogCat
		Log.d(TAG, "Starting game loop");
		// Call the initTimingElements method (further down)
		initTimingElements();

		// Create variables that will be used in this method
		long beginTime;
		long timeDiff;
		int sleepTime;
		int framesSkipped;

		// Sets the sleepTime to 0
		sleepTime = 0;

		// As long as the MainThread is running
		while (running) {
			// The canvas is null
			canvas = null;
			// Try to...
			try {
				// Set the surfaceHolder to the canvas, then lock it in place
				canvas = this.surfaceHolder.lockCanvas();
				// Do this all at the same time (?)
				synchronized (surfaceHolder) {
					// Begin keeping track of time
					beginTime = System.currentTimeMillis();
					// Set the number of frames skipped to 0
					framesSkipped = 0;
					// Update the gamePanel (method in MainGamePanel)
					this.gamePanel.update();
					// Render the gamePanel (method in MainGamePanel)
					this.gamePanel.render(canvas);
					// Finds the difference in current time and begin time
					timeDiff = System.currentTimeMillis() - beginTime;
					// Sets the sleep time to the difference of the frame period
					// and time difference to fix any errors
					sleepTime = (int) (FRAME_PERIOD - timeDiff);

					// If the sleep time is greater than 0...
					if (sleepTime > 0) {
						// Try to...
						try {
							// Make the thread sleep until the sleep time
							// reaches 0
							Thread.sleep(sleepTime);
							// and catch any possible errors
						} catch (InterruptedException e) {
						}
					}

					// As long as the sleep time is less than 0 and the frames
					// that have been skipped is less than the optimum number of
					// max frames skipped (THIS IS IF A FRAME IS SKIPPED)
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// Update the gamePanel (method in MainGamePanel)
						this.gamePanel.update();
						// Add to the sleep time for whatever the frame period
						// is
						sleepTime += FRAME_PERIOD;
						// Add to the frames skipped
						framesSkipped++;
					}

					// If a frame has been skipped...
					if (framesSkipped > 0) {
						// Log it into LogCat
						Log.d(TAG, "Skipped:" + framesSkipped);
					}

					// Add to the frames skipped for the whole statistic cycle
					// (1 second)
					framesSkippedPerStatCycle += framesSkipped;

					// Store all of the stats (Method found further on
					storeStats();
				}
				// Had to add because it would say
				// "Unfortunately, the APP has quit" when trying to click the
				// home or return button
			} catch (NullPointerException n) {
				// And finally...
			} finally {
				// As long as the canvas is there...
				if (canvas != null) {
					// Unlock the canvas and stop editting any pixels
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	/*
	 * Used for storing all stats that happen during the game
	 */
	private void storeStats() {
		// Add to the frame count per stat cycle
		frameCountPerStatCycle++;
		// Add to the total frame count
		totalFrameCount++;

		// Set the interval timer to the difference of the current time and the
		// old interval timer
		statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

		// As long as the interval timer is greater than what the last status
		// store was plus the stat interval...
		if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
			// The actual FPS will be the frame counts per 1 second divided by
			// what the stat interval is divided by 1000 (optimum)
			double actualFps = (double) (frameCountPerStatCycle / (STAT_INTERVAL / 1000));
			// Store whatever value the FPS is
			fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;
			// Add to the stats count
			statsCount++;
			// Make the totalFPS 0
			double totalFps = 0.0;
			// For however long the FPS history is
			for (int i = 0; i < FPS_HISTORY_NR; i++) {
				// Add all of the FPS in the store to the total
				totalFps += fpsStore[i];
			}
			// As long as the stats count is less than the FPS History
			if (statsCount < FPS_HISTORY_NR) {
				// The average FPS is the total divided by the stats count
				averageFps = totalFps / statsCount;
				// Otherwise...
			} else {
				// The average FPS is the total divided by the FPS History
				averageFps = totalFps / FPS_HISTORY_NR;
			}
			// Add to the total frames skipped for however many were skipped in
			// the stat cycle
			totalFramesSkipped += framesSkippedPerStatCycle;
			// Set that to 0 for the next stat store
			framesSkippedPerStatCycle = 0;
			// Set the timer to 0
			statusIntervalTimer = 0;
			// Set the frame count to 0
			frameCountPerStatCycle = 0;

			// Set the intervalTimer to whatever the current time is
			statusIntervalTimer = System.currentTimeMillis();
			// Set the last status store to whatever the current timer is
			lastStatusStore = statusIntervalTimer;
			// Put into the gamePanel the average FPS, in 2 decimal place format
			gamePanel.setAvgFps("FPS: " + df.format(averageFps));
		}
	}

	/*
	 * Method for initiating the timing elements
	 */
	private void initTimingElements() {
		// The store of FPS will be an array of the FPS history
		fpsStore = new double[FPS_HISTORY_NR];
		// For however long that is...
		for (int i = 0; i < FPS_HISTORY_NR; ++i) {
			// All of the FPS in that store are 0
			fpsStore[i] = 0.0;
		}
		// LogCat
		Log.d(TAG + ".initTimingElements()",
				"Timing elements for stats initialised");
	}
}
