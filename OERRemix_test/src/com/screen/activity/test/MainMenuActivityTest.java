package com.screen.activity.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import com.jayway.android.robotium.solo.Solo;
import com.screen.activity.FullscreenActivity;
import com.screen.activity.InfoscreenActivity;
import com.screen.activity.MainMenuActivity;
import com.screen.activity.RulesActivity;

public class MainMenuActivityTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

	private Solo solo;
	
	public MainMenuActivityTest() {
		super(MainMenuActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testButtons() {
		solo.clickOnButton("INFO");
		assertTrue(solo.waitForActivity(InfoscreenActivity.class.getSimpleName()));
		solo.sleep(1000);
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(1000);
		assertTrue(solo.waitForActivity(MainMenuActivity.class.getSimpleName()));
		solo.sleep(1000);
		
		solo.clickOnButton("RULES");
		solo.sleep(1000);
		assertTrue(solo.waitForActivity(RulesActivity.class.getSimpleName()));
		solo.sleep(1000);
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(1000);
		assertTrue(solo.waitForActivity(MainMenuActivity.class.getSimpleName()));
		
		solo.clickOnButton("PLAY");
		solo.sleep(1000);
		assertTrue(solo.waitForActivity(FullscreenActivity.class.getSimpleName()));
		solo.sleep(1000);
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(1000);
		assertTrue(solo.waitForActivity(MainMenuActivity.class.getSimpleName()));
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
