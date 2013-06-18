package com.game.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import android.annotation.SuppressLint;
import android.content.Context;

import com.mode.game.CCBYLicence;
import com.mode.game.CC_BY_SALicence;
import com.mode.game.CC_BY_SA_NCLicence;
import com.mode.game.CLicence;
import com.mode.game.Card;
import com.mode.game.CardType;
import com.mode.game.GFDLLicence;
import com.mode.game.Licence;
import com.mode.game.LicenceType;
import com.mode.game.PDLicence;

public class Logic {

	private Context context;
	public Logic(Context context) {
		this.context = context;
	}
	
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Card> getNewCombinations(){
	    System.out.println("Card generator");
	    Random randomGenerator = new Random();
	    HashMap<Integer, Card> cards = new HashMap<Integer, Card>();
	    ArrayList<Card> arrayCards =  new ArrayList<Card>();
	    for(int i = 0; i < 12; i++) {
	    	int cardType = randomGenerator.nextInt(4);
	    	Card card = new Card(context);
	    	card.setCardType(CardType.createType(cardType));
    	   int getCCard = randomGenerator.nextInt(10);
    	   boolean ccard = false;
    	   if (getCCard < 3)
    		   ccard = true;
        	
    		Licence licence = null;
    		while(licence == null){
    			int licenceID = randomGenerator.nextInt(6);
    			licenceID += 10;
    			LicenceType licenceType = LicenceType.createType(licenceID);
	    		switch (licenceType) {
				case C:
					if(ccard) {
						licence = new CLicence(licenceType);
					}
					break;
				case CC_BY:
					licence = new CCBYLicence(licenceType);
					break;
				case PD:
					licence = new PDLicence(licenceType);
					break;
				case CC_BY_SA_NC:
					licence = new CC_BY_SA_NCLicence(licenceType);
					break;
				case CC_BY_SA:
					licence = new CC_BY_SALicence(licenceType);
					break;
				case GFDL:
					licence = new GFDLLicence(licenceType);
					break;
				default:
		    		break;
				}
    		}
	    		if ( licence != null) {
	    			//Log.i("Senaid", "Licence " + licence.getLicenceString());
	    			card.setLicence(licence);
	    	}
	    	cards.put(i, card);
	    	arrayCards.add(card);
	    }
	    if(getPossibleRessult(arrayCards) == null)
	    	return getNewCombinations();
	    return cards;
	}
	
	public ArrayList<Card> getPossibleRessult(ArrayList<Card> cards) {
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
		return null;
	}
	
}
