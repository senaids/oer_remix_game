package com.screen.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WrongActivity extends Activity {

	private LinearLayout linearLayout;
	private Point windowSize;
	private Button playAgain;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wrong);
		
		Bundle b = getIntent().getExtras();
		ArrayList<String> reasons = b.getStringArrayList("reasons");
		
        windowSize = new Point();
        
 		Display display = getWindow().getWindowManager().getDefaultDisplay();
 		windowSize.x = display.getWidth(); // This get's are deprecated
 		windowSize.y = display.getHeight();
     		
    	int height = (windowSize.y * 70) / 100; 

		linearLayout = (LinearLayout) findViewById(R.id.linearLayoutWrong);
    	
    	height = (windowSize.y * 10) / 100;
    	
    	ImageView imageView = new ImageView(linearLayout.getContext());
    	imageView.setLayoutParams(new LayoutParams(windowSize.x, height));
    	
        Bitmap newcard = BitmapFactory.decodeResource(getResources(), R.drawable.wrong);
    	Bitmap image = Bitmap.createScaledBitmap(newcard, windowSize.x, height, true);
    	
    	imageView.setImageBitmap(image);
    	linearLayout.addView(imageView);
    	
    	float textSize = 20;
    	TextView text = new TextView(linearLayout.getContext());
		text.setText(R.string.rule1);
		text.setTextSize(textSize);
		text.setTextColor(Color.BLACK);
		text.setBackgroundColor(Color.WHITE);
		text.setPadding(10, 10, 10, 10);
		linearLayout.addView(text);

		text = new TextView(linearLayout.getContext());
		text.setText(R.string.rule2);
		text.setTextSize(textSize);
		text.setTextColor(Color.BLACK);
		text.setBackgroundColor(Color.WHITE);
		text.setPadding(10, 10, 10, 10);
		linearLayout.addView(text);
		
		text = new TextView(linearLayout.getContext());
		text.setText(R.string.rule3);
		text.setTextSize(textSize);
		text.setTextColor(Color.BLACK);
		text.setBackgroundColor(Color.WHITE);
		text.setPadding(10, 10, 10, 10);
		linearLayout.addView(text);
		
		text = new TextView(linearLayout.getContext());
		text.setText(R.string.rule4);
		text.setTextSize(textSize);
		text.setTextColor(Color.BLACK);
		text.setBackgroundColor(Color.WHITE);
		text.setPadding(10, 10, 10, 10);
		linearLayout.addView(text);
		
		text = new TextView(linearLayout.getContext());
		text.setText(R.string.rule5);
		text.setTextSize(textSize);
		text.setTextColor(Color.BLACK);
		text.setBackgroundColor(Color.WHITE);
		text.setPadding(10, 10, 10, 10);
		linearLayout.addView(text);
    	
    	height = (windowSize.y * 10) / 100;
    	playAgain = new Button(linearLayout.getContext());
    	playAgain.setBackgroundColor(Color.LTGRAY);
    	playAgain.setText("Play again");
    	playAgain.setPadding(0, 0, 0, 10);
    	playAgain.setHeight(height);
    	linearLayout.addView(playAgain);
    	
    	addListeners();
		Log.i(DISPLAY_SERVICE, " I have output " + reasons.size());
	}
	
	  public void addListeners() {
	    	OnClickListener playAgainListener =  new View.OnClickListener() {
	    		public void onClick(View view) {
	    	    	playAgain.setBackgroundColor(Color.DKGRAY);
	    	    	linearLayout.removeAllViews();
	    			finish();
	    		}; 
	    	};
	    	playAgain.setOnClickListener(playAgainListener);
	    }
}
