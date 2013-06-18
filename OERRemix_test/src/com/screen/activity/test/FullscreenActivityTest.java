package com.screen.activity.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;
import com.mode.game.Card;
import com.screen.activity.FullscreenActivity;
import com.screen.activity.GamePartTwoActivity;
import com.screen.activity.WrongActivity;

public class FullscreenActivityTest extends ActivityInstrumentationTestCase2<FullscreenActivity> {
	
	private Solo solo;
	public FullscreenActivityTest() {
		super(FullscreenActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testFullScreenActivityTrueResult() {
		Log.i("SenaidTest" ,"testFullScreenActivityTrueResult:");

		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<View> views = solo.getViews();
		for(int i = 0; i < views.size(); i++) {
			View v = views.get(i);
			if (v instanceof ImageView) {
				Card card = (Card)v;
				if(card.isTransferable())
					cards.add((Card)v);
			}
		}
		
		ArrayList<Card> result = getPossibleRessult(cards, true);
		if (result == null)
			assertTrue("No possible result", false);
		
		ArrayList<String> licences = new ArrayList<String>();
		for(int i = 0; i < result.size(); i++) {
			licences.add(result.get(i).getLicencesString().replace("_", "-"));
			solo.clickOnView((View)result.get(i), true);
		}
		assertTrue(solo.waitForActivity(GamePartTwoActivity.class.getSimpleName()));
		String correctResult = null;
		
		if(licences.contains("GFDL"))
			correctResult = "GFDL";
		else if(licences.contains("CC-BY-SA-NC"))
			correctResult = "CC-BY-SA-NC";
		else if(licences.contains("CC-BY-SA"))
			correctResult = "CC-BY-SA";
		else if(licences.contains("PD"))
			correctResult = "PD";
		else
			correctResult = "CC-BY";
		
		solo.sleep(2000);
		solo.clickOnText(correctResult);
		solo.sleep(20000);
		solo.clickOnButton("CHECK");
		assertTrue(solo.waitForActivity(FullscreenActivity.class.getSimpleName()));
	}
	
	public void testFullScreenActivityWrongResult() {
		Log.i("SenaidTest" ,"testFullScreenActivityWrongResult:");

		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<View> views = solo.getViews();
		for(int i = 0; i < views.size(); i++) {
			View v = views.get(i);
			if (v instanceof ImageView) {
				Card card = (Card)v;
				if(card.isTransferable())
					cards.add((Card)v);
			}
		}
		
		ArrayList<Card> result = getPossibleRessult(cards, false);
		if (result == null)
			assertTrue("No possible result", false);
		
		for(int i = 0; i < result.size(); i++) {
			solo.clickOnView((View)result.get(i), true);
		}

		assertTrue(solo.waitForActivity(WrongActivity.class.getSimpleName()));
		solo.sleep(2000);
		solo.clickOnButton("Play again");
		assertTrue(solo.waitForActivity(FullscreenActivity.class.getSimpleName()));
		solo.sleep(8000);
	}
	
	public ArrayList<Card> getPossibleRessult(ArrayList<Card> cards, boolean correct) {
		ArrayList<Card> music = new ArrayList<Card>();
		ArrayList<Card> image = new ArrayList<Card>();
		ArrayList<Card> text = new ArrayList<Card>();
		ArrayList<Card> video = new ArrayList<Card>();

		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			switch (card.getCardTypeValue()) {
			case 0:
				music.add(card);
				break;
			case 1:
				image.add(card);
				break;
			case 2:
				text.add(card);
				break;
			case 3:
				video.add(card);
				break;
			default:
				break;
			}
		}
		
		ArrayList<Card> result = new ArrayList<Card>();
		if(correct) {
			for(int i = 0; i < music.size(); i++) {
				Card mc = music.get(i);
				for(int j = 0; j < image.size(); j++) {
					Card ic = image.get(j);
					if(mc.canCombine(ic) && ic.canCombine(mc)) {
						for(int k = 0; k < text.size(); k++) {
							Card tc = text.get(k);
							if(ic.canCombine(tc) && tc.canCombine(mc)) {
								for(int l = 0; l < video.size(); l++) {
									Card vc = video.get(l);
									if(tc.canCombine(vc) && vc.canCombine(ic) && vc.canCombine(mc)) {
										Log.i("SenaidTest" , "Result of combination: "+ mc.getLicencesString() + "  " + ic.getLicencesString() + "  " + tc.getLicencesString() + " " + vc.getLicencesString());
										result.add(mc);
										result.add(ic);
										result.add(tc);
										result.add(vc);
										return result;
									}
								}		
							}
						}
					}
				}
			}
		} else {
			for(int i = 0; i < music.size(); i++) {
				Card mc = music.get(i);
				for(int j = 0; j < image.size(); j++) {
					Card ic = image.get(j);
						for(int k = 0; k < text.size(); k++) {
							Card tc = text.get(k);
								for(int l = 0; l < video.size(); l++) {
									Card vc = video.get(l);
									if(!tc.canCombine(vc) || !vc.canCombine(ic) || !vc.canCombine(mc) || !mc.canCombine(ic) || !mc.canCombine(tc)) {
										Log.i("SenaidTest" , "Result of combination: "+ mc.getLicencesString() + "  " + ic.getLicencesString() + "  " + tc.getLicencesString() + " " + vc.getLicencesString());
										result.add(mc);
										result.add(ic);
										result.add(tc);
										result.add(vc);
										return result;
									}
								}		
							}
						}
					}
				}
		return null;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
