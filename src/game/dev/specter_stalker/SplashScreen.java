package game.dev.specter_stalker;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 */

import game.dev.specter_stalker.R;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/*
 * SplashScreen is the first called activity when the app starts and is used to switch to the main activity
 */
public class SplashScreen extends ActionBarActivity {
	
	/*
	 * (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 * 
	 * Upon creation of the activity...
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Super the onCreate of the current
		super.onCreate(savedInstanceState);
		// Get rid of the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Get rid of the bottom bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Set the current view to the Splash Screen (.xml file)
		setContentView(R.layout.splashscreen);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 * 
	 * Upon touching the screen when the Splash Screen is active...
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Get the action of the touch event and...
		switch (event.getAction()) {
		// If it is down then...
		case MotionEvent.ACTION_DOWN:
			// Switch to the MainScreen (found in the Manifest file)
			startActivity(new Intent("game.dev.CLEARSCREEN"));
			// Finish off this activity (close it)
			finish();
			return true;
		// If it is moved then...
		case MotionEvent.ACTION_MOVE:
			// Do nothing
			break;
		// If it is up then...
		case MotionEvent.ACTION_UP:
			// Do nothing
			break;
		// Otherwise, return false
		default:
			return false;
		}
		// Return true if this has been completed
		return true;
	}
}
