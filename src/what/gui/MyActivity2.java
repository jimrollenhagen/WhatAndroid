package what.gui;

import what.settings.Settings;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

public abstract class MyActivity2 extends SherlockActivity {
	private View v;
	protected Bundle myBundle;
	private String activityName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((Settings.getSettings() == null) | (Settings.getSettingsEditor() == null)) {
			Settings.init(this);
		}

		myBundle = getIntent().getExtras();
		init();
	}

	@Override
	public void setTheme(int resid) {
		super.setTheme(resid);
	}

	/**
	 * Initialize variables and anything that must be done first.
	 */
	public abstract void init();

	/**
	 * Sets the content view with background
	 * 
	 * @param layoutResID
	 *            the layout res id
	 * @param enableBackground
	 *            the enable background
	 */
	public void setContentView(int layoutResID, boolean enableBackground) {
		super.setContentView(layoutResID);
		if (enableBackground) {
			v = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
			if (Settings.getCustomBackground()) {
				loadCustomBackground();
			} else {
				loadDefaultBackground();
			}
		}
		load();
		prepare();
		actionbar();
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(layoutResID, false);
	}

	/**
	 * Find resources, setting additional properties such as gravity or listeners should be done here.
	 */
	public abstract void load();

	/**
	 * Prepare the actionbar for the user.
	 */
	public void actionbar() {

	}

	/**
	 * Prepare the activity for the user, run any code necessary to do that here.
	 */
	public abstract void prepare();

	private void loadDefaultBackground() {
		try {
			BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.renzler));
			bd.setTileModeX(Shader.TileMode.REPEAT);
			bd.setTileModeY(Shader.TileMode.REPEAT);
			v.setBackgroundDrawable(bd);
		} catch (Exception e) {
			e.printStackTrace();
			v.setBackgroundColor(R.color.black);
			Toast.makeText(this, "default background failed", Toast.LENGTH_SHORT).show();
		}
	}

	private void loadCustomBackground() {
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(Settings.getCustomBackgroundPath());
			BitmapDrawable bd = new BitmapDrawable(bitmap);
			if (Settings.getTileBackground()) {
				bd.setTileModeX(Shader.TileMode.REPEAT);
				bd.setTileModeY(Shader.TileMode.REPEAT);
			}
			v.setBackgroundDrawable(bd);
		} catch (Exception e) {
			e.printStackTrace();
			v.setBackgroundColor(R.color.black);
			Toast.makeText(this, "default background failed", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Reload the current activity.
	 */
	public void refresh() {
		finish();
		startActivity(getIntent());
	}

	public void lockScreenRotation() {
		switch (this.getResources().getConfiguration().orientation) {
			case Configuration.ORIENTATION_PORTRAIT:
				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				break;
			case Configuration.ORIENTATION_LANDSCAPE:
				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				break;
		}
	}

	public void unlockScreenRotation() {
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		if (activityName == null) {
			activityName = "";
		}
		return activityName;
	}

	/**
	 * @param activityName
	 *            the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu submenu = menu.addSubMenu(getActivityName());
		submenu.addSubMenu(MenuItems.HOME.toString());
		submenu.addSubMenu(MenuItems.FORUM.toString());
		submenu.addSubMenu(MenuItems.SUBMENU.toString()).addSubMenu(MenuItems.TEST.toString());

		MenuItem subMenuItem = submenu.getItem();
		// subMenuItem.setIcon(R.drawable.ic_title_share_default);
		subMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MenuItems mi = MenuItems.valueOf(item.getTitle().toString().toUpperCase());
		if (MenuItems.containsKey(mi)) {
			Intent intent = new Intent(this, MenuItems.get(mi));
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
