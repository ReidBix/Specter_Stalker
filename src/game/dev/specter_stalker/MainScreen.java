package game.dev.specter_stalker;

/*
 * Imported objects, classes, etc.
 * android.x = Available through the android interface
 * game.dev.x = Created classes found in different packages
 */

import game.dev.specter_stalker.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/*
 * Used for previous versions of models or unused methods, leave commented unless you want to see simpler builds of classes or unused methods
 */

//import game.dev.specter_stalker.other.DrawingPanel;

// The MainScreen is the activity that is switched to from the splash screen and immediately calls the MainGamePanel view
public class MainScreen extends Activity {

	/*
	 * Initialized variables for use in later code
	 */

	// Used for displaying errors with methods in this class on LogCat
	private static final String TAG = MainScreen.class.getSimpleName();

	// Not really sure what this one does, but it's a part of one of the methods
	// that comes with extending Activity, so I left it in
	private static View rootView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * 
	 * Used when the Activity is started/created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Goes over the current savedInstanceStance(?)
		super.onCreate(savedInstanceState);
		// Removes the title bar from the top
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Removes the bottom bar from the bottom
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Sets the current view to the main activity (which currently has
		// nothing, possible that this is useless)
		setContentView(R.layout.activity_main);
		// Sets the current view to the MainGamePanel, is essential for actually
		// starting the game
		setContentView(new MainGamePanel(this));
		// Puts into LogCat if everything is completed
		Log.d(TAG, "View added");

		// Sets the current view to the DrawingPanel, which is an unused method
		// to display and move text (Legend of Zelda font), pretty sure you have
		// to comment out MainGamePanel for this to work
		// setContentView(new DrawingPanel(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * 
	 * Not really sure what this does, but pretty sure it is useless for now but
	 * it does come with extending Activity
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 * 
	 * Also not really sure what this does, but left it in because it came with
	 * Activity
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * A placeholder fragment containing a simple view. Also not sure what this
	 * does, came with Activity
	 */
	@SuppressLint("NewApi")
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 * 
	 * Method for when the activity is destroyed, logs into LogCat
	 */
	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 * 
	 * Method for when the activity is stopped, logs into LogCat
	 */
	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}

	// Method for overriding the back button (left button on bottom-most bar)
	// OVERRIDES BACK BUTTON
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (android.os.Build.VERSION.SDK_INT <
	// android.os.Build.VERSION_CODES.ECLAIR
	// && keyCode == KeyEvent.KEYCODE_BACK
	// && event.getRepeatCount() == 0)
	// {
	// onBackPressed();
	// }
	// return super.onKeyDown(keyCode, event);
	// }
	// @Override
	// public void onBackPressed() {
	// // do what you want here
	// return;
	// }

	// Method for overriding the home button (middle button on the bottom-most
	// bar)
	// OVERRIDES HOME BUTTON
	// @Override
	// protected void onUserLeaveHint() {
	// super.onUserLeaveHint();
	// finish();
	// exitAppAnimate();
	// }
	// private void exitAppAnimate() {
	// ActivityManager activityManager = (ActivityManager)
	// getSystemService(Context.ACTIVITY_SERVICE);
	// List<RunningTaskInfo> recentTasks =
	// activityManager.getRunningTasks(Integer.MAX_VALUE);
	// for (int i=0; i<recentTasks.size(); i++) {
	// if (i == 1 &&
	// recentTasks.get(i).baseActivity.toShortString().indexOf(getPackageName())
	// > -1) {
	// // home button pressed
	// android.os.Process.killProcess(android.os.Process.myPid());
	// break;
	// }
	// }
	// }

}
