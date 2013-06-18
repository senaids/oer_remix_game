package com.screen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmenu);
	}

	public void startFullscreenActivity(View view) {
		Button button = (Button) view;
		button.setPressed(true);
		Intent intent = new Intent(this, FullscreenActivity.class);
		startActivity(intent);
	}

	public void startRulesActivity(View view) {
		Button button = (Button) view;
		button.setPressed(true);
		Intent intent = new Intent(this, RulesActivity.class);
		startActivity(intent);
	}
	
	public void startInfoscreenActivity(View view) {
		Button button = (Button) view;
		button.setPressed(true);
		Intent intent = new Intent(this, InfoscreenActivity.class);
		startActivity(intent);
	}
	
}
