package com.screen.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GamePartTwoActivity extends Activity {

	private static final String FINAL_QUESTION = "Select all licenses the final product can be:";

	private static final int CARDS_PERCENTAGE = 8;
	private static final int QUESTION_PERCENTAGE = 12;
	private static final int RESULT_PERCENTAGE = 11;

	private static final int TEXT_ID = 10;
	private static final int CHECK_ID = 11;
	
	private String correctAnswer;
	private TableRow resultsRow;
	private Point windowSize;
	private Button checkButton;
	private LinearLayout linearLayout;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game2);

        windowSize = new Point();
        
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		windowSize.x = display.getWidth(); // TODO check => This get's are deprecated
		windowSize.y = display.getHeight();
        
    	int height = (windowSize.y * 71) / 100;
    	linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
    	linearLayout.setLayoutParams(new LinearLayout.LayoutParams(windowSize.x, height));
    	linearLayout.setOrientation(LinearLayout.VERTICAL);
    	
    	correctResultMode();
    	
    	resultsRow = (TableRow)findViewById(R.id.tableRowPartTwoResults);
//    	resultsRow.setBackgroundColor(Color.BLACK);
    	
    	height = FullscreenActivity.resultsRow.getHeight();
    	int count = FullscreenActivity.resultsRow.getChildCount()-1;
		for(int i = count; i >= 0; i--) {
	    	Log.i(DISPLAY_SERVICE, "IN GAME i " + i);
			View v = FullscreenActivity.resultsRow.getChildAt(0);
			FullscreenActivity.resultsRow.removeViewAt(0);
			v.setClickable(false);
			resultsRow.addView(v);
		}
    	resultsRow.setLayoutParams(new TableRow.LayoutParams(windowSize.x, height));
    	
    	Bundle b = getIntent().getExtras();
		String content = b.getString("result");
		correctAnswer = content != null ? content : null;
	}
	
	public void  correctResultMode() {
		CheckBox btn = null;
		TextView text = new TextView(linearLayout.getContext());
		text.setId(TEXT_ID);
		text.setText(FINAL_QUESTION);
		text.setTextSize((float) 25.0);
    	int height = (windowSize.y * QUESTION_PERCENTAGE) / 100;
		text.setLayoutParams(new LayoutParams(windowSize.x, height));
		text.setTextColor(Color.BLACK);
		text.setBackgroundColor(Color.WHITE);
		linearLayout.addView(text);
		
    	height = (windowSize.y * CARDS_PERCENTAGE) / 100;
    	
    	btn = new CheckBox(linearLayout.getContext());
    	btn.setText(R.string.c);
    	btn.setSoundEffectsEnabled(true);
    	btn.setLayoutParams(new LayoutParams(windowSize.x, height));
    	linearLayout.addView(btn);
    	btn = new CheckBox(linearLayout.getContext());
    	btn.setText(R.string.cc_by);
    	btn.setSoundEffectsEnabled(true);
    	btn.setLayoutParams(new LayoutParams(windowSize.x, height));
    	linearLayout.addView(btn);
    	btn = new CheckBox(linearLayout.getContext());
    	btn.setText(R.string.pd);
    	btn.setSoundEffectsEnabled(true);
    	btn.setLayoutParams(new LayoutParams(windowSize.x, height));
    	linearLayout.addView(btn);
    	btn = new CheckBox(linearLayout.getContext());
    	btn.setText(R.string.cc_by_sa);
    	btn.setSoundEffectsEnabled(true);
    	btn.setLayoutParams(new LayoutParams(windowSize.x, height));
    	linearLayout.addView(btn);
    	btn = new CheckBox(linearLayout.getContext());
    	btn.setText(R.string.cc_by_sa_nc);
    	btn.setSoundEffectsEnabled(true);
    	btn.setLayoutParams(new LayoutParams(windowSize.x, height));
    	linearLayout.addView(btn);
    	btn = new CheckBox(linearLayout.getContext());
    	btn.setText(R.string.gfdl);
    	btn.setSoundEffectsEnabled(true);
    	btn.setLayoutParams(new LayoutParams(windowSize.x, height));
    	linearLayout.addView(btn);

	    checkButton = new Button(linearLayout.getContext());
	    checkButton.setId(CHECK_ID);
		checkButton.setText("CHECK");
//		checkButton.setTextAlignment(Gravity.CENTER);
		checkButton.setTextSize((float)24.0);
    	height = (windowSize.y * RESULT_PERCENTAGE) / 100;
		checkButton.setLayoutParams(new LayoutParams(windowSize.x, height));
		addListeners();
		linearLayout.addView(checkButton);
	}
	
	  public void addListeners() {
	    	OnClickListener checkButtonListener =  new View.OnClickListener() {

	    		public void onClick(View view) {
			    	ArrayList<String> selections = new ArrayList<String>();
	    			for(int i = 0; i < linearLayout.getChildCount(); i++) {
	    				if( linearLayout.getChildAt(i).getId() != TEXT_ID && linearLayout.getChildAt(i).getId() != CHECK_ID) {
	    					CheckBox box = (CheckBox)linearLayout.getChildAt(i);
	    			    	Log.i(DISPLAY_SERVICE, "CHECKBOC on CLICK " + box.getText());
	    			    	if(box.isChecked())	{
	    			    		Log.i(DISPLAY_SERVICE, "checked is " + box.getText());
	    			    		selections.add((String) box.getText());
	    			    	}
	    				}
	    			}
	    			if (selections.isEmpty())
	    				return;
			    	finish();
	    			linearLayout.removeAllViews();
	    			resultsRow.removeAllViews();
	    			finish();
	    			if(selections.size() > 1 || !selections.contains(correctAnswer)) {
	    				Log.i("Senaid", "result is wrong " + correctAnswer + " and size: " + selections.size());
	    				Intent intent = new Intent(getWindow().getContext(), WrongActivity.class);
	    				ArrayList<String> reasons = new ArrayList<String>();
	    				Bundle b = new Bundle();
	    				b.putStringArrayList("reasons", reasons);
	    				intent.putExtras(b);
	    				startActivity(intent);
	    			}
	    		}; 
	    	};
	    	checkButton.setOnClickListener(checkButtonListener);
	    }
}
