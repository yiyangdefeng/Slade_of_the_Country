package com.example.slade_of_the_country;



import java.io.IOException;

import com.example.slade_of_the_country.MyView.MoveButton;

import cn.edu.tsinghua.academic.c00740273.magictower.engine.Engine;



import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private DataManager dm;
	Engine engine;
	protected 		MyView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dm = new DataManager(this);
		engine = new Engine();
		view = new MyView(this);
		setContentView(view);
		
	}

	@Override
	public void onResume() {
		super.onResume(); 
		if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart(); 
		if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}
	
	private void autoSave() {
		if (view.status != Constants.STATUS_GAME) {
			return;
		}
		try {
			dm.SaveSavings(engine,"save2.txt");
		} catch (IOException e) {
		}
	}
	
	@Override
	public void onStop() {
		autoSave();
		super.onStop();
	}
	@Override 
	public void onDestroy() {
		autoSave();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (view.status) {
			case Constants.STATUS_START:
				finish();
				break;
			case Constants.STATUS_GAME:
			case Constants.STATUS_LOAD:
			case Constants.STATUS_INSTRUCTION:
			case Constants.STATUS_GAME_OVER:
			case Constants.STATUS_GAME_WIN:
				view.status = Constants.STATUS_START;
				view.invalidate();
				break;
			case Constants.STATUS_FIGHT:
				view.fh.exec = false;
			case Constants.STATUS_SAVE:
			case Constants.STATUS_DIALOGUE:
			case Constants.STATUS_SHOP:
			case Constants.STATUS_FAILURE_WARNING:
			case Constants.STATUS_ELEVATOR:
			case Constants.STATUS_FIREEYE:
				view.status = Constants.STATUS_GAME;
				view.invalidate();
				break;
			case Constants.STATUS_WARNING:
				view.status = Constants.STATUS_SAVE;
				break;
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		MoveButton button = null;
		if (view.status == Constants.STATUS_GAME) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_DOWN:
				button = MoveButton.DOWN;
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				button = MoveButton.UP;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				button = MoveButton.LEFT;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				button = MoveButton.RIGHT;
				break;
			}
		}
		if (button == null) {
			return super.onKeyUp(keyCode, event);
		} else {
			view.doMove(button);
			view.invalidate();
			return true;
		}
	}

}
