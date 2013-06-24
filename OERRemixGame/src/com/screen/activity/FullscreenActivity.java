package com.screen.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.game.logic.Logic;
import com.mode.game.Card;
import com.mode.game.CardType;

@SuppressLint("UseSparseArrays")
public class FullscreenActivity extends Activity {
	
	private static final int COLUMN_COUNT = 4;
	
	private static final int DEAL_BUTTON_PERCENTAGE = 11;
	private static final int ROWS_OF_CARDS_PERCENTAGE = 20;
	private static final int ROWS_OF_CARDS_RESULTS_PERCENTAGE = 23;
	
	private TableLayout cardsTable;
	public static HashMap<Integer, Card> cards;
	private HashMap<CardType, Card> bottomBackgrounds;
	
	private Point windowSize;
	int cardCounter;
	int assignedCardsCount;
	int rowItemWidth;
	int rowItemHeight;
	int rowResultWidth;
	int rowResultHeight;
	int dealButtonHeight;
	
	public String correctResult;
	public static TableRow resultsRow;
	
	@SuppressWarnings("deprecation")
	@Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        
        correctResult = null;

        bottomBackgrounds = new HashMap<CardType, Card>(COLUMN_COUNT);
        cardCounter = 0;
        assignedCardsCount = 0;
        
        windowSize = new Point();

   		Display display = getWindow().getWindowManager().getDefaultDisplay();
   		windowSize.x = display.getWidth();
   		windowSize.y = display.getHeight();

        cardsTable = (TableLayout)findViewById(R.id.tableLayoutCards);
        
        Logic l = new Logic(cardsTable.getContext());
        cards = l.getNewCombinations();
        
        dealButtonHeight = calculateHeight(DEAL_BUTTON_PERCENTAGE);
    	
    	Button dealButton = (Button)findViewById(R.id.dealAgain);
    	dealButton.setWidth(windowSize.x);
    	dealButton.setHeight(dealButtonHeight);
    	dealButton.setBackgroundColor(Color.LTGRAY);

    	TableRow row;
    	int height = calculateHeight(ROWS_OF_CARDS_PERCENTAGE);
    	row = (TableRow)findViewById(R.id.tableRow1);
    	row.setLayoutParams(new TableRow.LayoutParams(windowSize.x, height));
    	row = (TableRow)findViewById(R.id.tableRow2);
    	row.setLayoutParams(new TableRow.LayoutParams(windowSize.x, height));
    	row = (TableRow)findViewById(R.id.tableRow3);
    	row.setLayoutParams(new TableRow.LayoutParams(windowSize.x, height));
    	rowItemWidth = windowSize.x / COLUMN_COUNT;
    	rowItemHeight = height;

    	height = calculateHeight(ROWS_OF_CARDS_RESULTS_PERCENTAGE);
    	
    	TableRow rowRes= (TableRow)findViewById(R.id.tableRowResults);
    	rowRes.setLayoutParams(new TableRow.LayoutParams(windowSize.x, height));
//    	rowRes.setBackgroundColor(Color.BLACK);

    	rowResultWidth = windowSize.x / COLUMN_COUNT;
    	rowResultHeight = height;
    	
        loadCardAndLicenceImages();
    	loadBottomImages();
    	addCardListeners();
    	showImagesInRows();
    }
	
	//-----------------------------------------------------------------------------
	public void loadCardAndLicenceImages() {
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			card.setId(1000+i);
			card.setTransferable(true);
			Bitmap cardBitmap = null;
			int id = card.getDrawableId();
			cardBitmap = getBitmap(id, rowItemWidth, rowItemHeight);
			card.setUniqueID(id);
			card.setImage(cardBitmap);
		}
	}
	
	//-----------------------------------------------------------------------------
	public void cardClicked(View view) {
		Card card = (Card) view;
		switch (card.getCardType()) {
		case MUSIC:
			if (bottomBackgrounds.get(CardType.MUSIC).getDefaultImage() != null) return;
			cardTransfer(bottomBackgrounds.get(CardType.MUSIC), card);
			break;
		case IMAGE:
			if (bottomBackgrounds.get(CardType.IMAGE).getDefaultImage() != null) return;
			cardTransfer(bottomBackgrounds.get(CardType.IMAGE), card);
			break;
		case TEXT:
			if (bottomBackgrounds.get(CardType.TEXT).getDefaultImage() != null) return;
			cardTransfer(bottomBackgrounds.get(CardType.TEXT), card);
			break;
		case VIDEO:
			if (bottomBackgrounds.get(CardType.VIDEO).getDefaultImage() != null) return;
			cardTransfer(bottomBackgrounds.get(CardType.VIDEO), card);
			break;
		default:
			return;
		}
		boolean allUsed = true;
		for(int i = 0; i < bottomBackgrounds.size(); i++) {
			if (!bottomBackgrounds.get(CardType.createType(i)).isUsed())
				allUsed = false;
		}
		if ( allUsed)
			checkResult();
	}
	
	//-----------------------------------------------------------------------------
	public void checkResult() {
		boolean resultOK = true;
		ArrayList<String> licences = new ArrayList<String>();
		
		for (int i = 0; i < bottomBackgrounds.size(); i++) {
			Card card = bottomBackgrounds.get(CardType.createType(i));
			licences.add(card.getLicencesString().replace("_", "-"));
			for (int j = 0; j < bottomBackgrounds.size(); j++) {
				if (i != j) {
					Card testCard = bottomBackgrounds.get(CardType.createType(j));
					if(!card.canCombine(testCard))
						resultOK = false;
				}
			}
		}

		if(!resultOK) { // result was wrong
			Intent intent = new Intent(this, WrongActivity.class); // start new activity
			ArrayList<String> reasons = new ArrayList<String>();
			Bundle b = new Bundle();
			b.putStringArrayList("reasons", reasons);
			intent.putExtras(b);
			
			cards.clear();
	    	bottomBackgrounds.clear();
			cardsTable.removeAllViews();
			startActivity(intent);
			
		} else { // correct result
			if(licences.contains("GFDL"))
				correctResult = "GFDL";
			else if(licences.contains("CC-BY-NC-SA"))
				correctResult = "CC-BY-NC-SA";
			else if(licences.contains("CC-BY-SA"))
				correctResult = "CC-BY-SA";
			else if(licences.contains("PD"))
				correctResult = "PD";
			else
				correctResult = "CC-BY";
			resultsRow = (TableRow) findViewById(R.id.tableRowResults);

			for(int i = 0; i < licences.size(); i++)
				Log.i("Senaid", "licence " + i + " is " + licences.get(i));
			Log.i("Senaid", "checkResult: Result is " + correctResult);
			
			Intent intent = new Intent(this, GamePartTwoActivity.class);
			
			cards.clear();
	    	bottomBackgrounds.clear();
			cardsTable.removeAllViews();
			Bundle b = new Bundle();
			b.putString("result", correctResult);
			intent.putExtras(b);
			startActivity(intent);
		}
		finish();
	}

	//-----------------------------------------------------------------------------
	public void cardTransfer(Card bottomCard, Card card) {
		bottomCard.setOldRow(card.getOldRow());
		bottomCard.setOldRowPosition(card.getOldRowPosition());
		Bitmap myBitmap = getBitmap(card.getUniqueID(), rowResultWidth, rowResultHeight);
		bottomCard.switchCard(myBitmap, card);
		card.setVisibility(View.INVISIBLE);
	}
	
	//-----------------------------------------------------------------------------
	public void resultCardClicked(View view) {
		Card card = (Card) view;
		if (card.getDefaultImage() == null) return;
	
		switch (card.getCardType()) {
		case MUSIC:
			bottomCardReSet(bottomBackgrounds.get(CardType.MUSIC));
			break;
		case IMAGE:
			bottomCardReSet(bottomBackgrounds.get(CardType.IMAGE));
			break;
		case TEXT:			
			bottomCardReSet(bottomBackgrounds.get(CardType.TEXT));
			break;
		case VIDEO:
			bottomCardReSet(bottomBackgrounds.get(CardType.VIDEO));
			break;
		default:
			return;
		}
	}
	
	//-----------------------------------------------------------------------------
	public void bottomCardReSet(Card card) {
		TableRow row = card.getOldRow();
		int pos = card.getOldRowPosition();
		row.getChildAt(pos).setVisibility(View.VISIBLE);
		card.switchCard(null, null);
	}
	
	//-----------------------------------------------------------------------------
    public void loadBottomImages() {
    	Context context = cardsTable.getContext();
    	bottomBackgrounds.put(CardType.MUSIC, new Card(context, CardType.MUSIC, getBitmap(R.drawable.music, rowResultWidth, rowResultHeight), R.drawable.music, false));
    	bottomBackgrounds.put(CardType.IMAGE, new Card(context, CardType.IMAGE, getBitmap(R.drawable.image, rowResultWidth, rowResultHeight), R.drawable.image, false));
    	bottomBackgrounds.put(CardType.VIDEO, new Card(context, CardType.VIDEO, getBitmap(R.drawable.video, rowResultWidth, rowResultHeight), R.drawable.video, false));
    	bottomBackgrounds.put(CardType.TEXT, new Card(context, CardType.TEXT, getBitmap(R.drawable.text, rowResultWidth, rowResultHeight), R.drawable.text, false));
    }

    //-----------------------------------------------------------------------------
    public void addCardListeners() {
    	OnClickListener normalCardListener =  new View.OnClickListener() {
    		public void onClick(View view) {
    			print("I am new NORMAL listener ONCLICK");
    			cardClicked(view);
    		}; 
    	};
    	
    	for(int i = 0; i < cards.size(); i++) {
    		((View)cards.get(i)).setOnClickListener(normalCardListener);
    	}

    	OnClickListener resultsCardListener =  new View.OnClickListener() {
    		public void onClick(View view) {
    			print("I am new BOTTOM listener ONCLICK");
    			resultCardClicked(view);
    		}; 
    	};
    	
    	for(int i = 0; i < bottomBackgrounds.size(); i++) {
			CardType type = CardType.createType(i);
    		((View)bottomBackgrounds.get(type)).setOnClickListener(resultsCardListener);
    	}
    }
    
	//-----------------------------------------------------------------------------
    public int calculateHeight(int percentageOfScreen) {
    	int h = (windowSize.y * percentageOfScreen) / 100; // gets percentageOfScreen% of total height
    	return h;
    }

	//-----------------------------------------------------------------------------
    public Bitmap getBitmap(int id, int width, int height) {
    	Bitmap newcard = BitmapFactory.decodeResource(getResources(), id);
	    return Bitmap.createScaledBitmap(newcard, width, height, true); // to false
    }
    
	//-----------------------------------------------------------------------------
	public void showImagesInRows() {
    	TableRow row;
    	row = ((TableRow)findViewById(R.id.tableRow1));
    	showRow(row, false);
    	row = ((TableRow)findViewById(R.id.tableRow2));
    	showRow(row, false);
    	row = ((TableRow)findViewById(R.id.tableRow3));
    	showRow(row, false);

    	row = ((TableRow)findViewById(R.id.tableRowResults));
    	showRow(row, true);
    }
    
	//-----------------------------------------------------------------------------
    public void showRow(TableRow row, boolean isLastRow) {
    	for (int i = 0; i < COLUMN_COUNT; i++) {
    		if (!isLastRow) {
    		Card view = getNextCard();
    		view.setOldRowPosition(i);
    		view.setOldRow(row);
    		row.addView(view); 
    		} else {
    			CardType type = CardType.createType(i);
    			Card view = bottomBackgrounds.get(type);
    			view.setImageBitmap(bottomBackgrounds.get(type).getImage());
    			row.addView(view, i);
    		}
    	}
    }
    
	//-----------------------------------------------------------------------------
    public Card getNextCard() {
    	Card card = cards.get(assignedCardsCount);
    	assignedCardsCount++;
    	return card;
    }

    //-----------------------------------------------------------------------------
    public int getNextCardID() {
    	return cardCounter++;
    }
    
	//-----------------------------------------------------------------------------
    public void print(String msg) {
    	Log.i(DISPLAY_SERVICE, msg);
    }
    
	//-----------------------------------------------------------------------------
    public void restartGame(View view) {
    	Button dealButton = (Button)findViewById(R.id.dealAgain);
    	dealButton.setBackgroundColor(Color.DKGRAY);
    	
    	cardsTable.destroyDrawingCache();
    	cards.clear();
    	bottomBackgrounds.clear();
    	Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

	//-----------------------------------------------------------------------------
	public void startGamePartTwoActivity(View view) {
		// http://developer.android.com/training/basics/firstapp/starting-activity.html
		Intent intent = new Intent(this, GamePartTwoActivity.class);
		startActivity(intent);
		finish();
	}

    
}