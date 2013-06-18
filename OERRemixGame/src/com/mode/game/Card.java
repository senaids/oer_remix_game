package com.mode.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TableRow;

import com.screen.activity.R;

public class Card extends ImageView{
	// represents the type of the card
	private CardType cardType;
	// represents the image seen by the user
	private Bitmap image;
	// represents the default bottom image, in case that 
	// click was performed on one of upper images 
	private Bitmap defaultImage;
	// old TableRow, represents row its was that the image was on, before placed on
	// bottomRow, needed to place image back
	private TableRow oldRow;
	// id in oldRow
	private int oldRowPosition;
	// represents id of the image, see R.drawable.XXX
	private int uniqueID;
	// true, if card is on this place, false otherwise
	private boolean isUsed;
	/**
	 * true => it is normal card
	 * false => it is bottom card, used as background
	 */
	private boolean isTransferable;
	/**
	 * contains all licences this card has
	 */
	private Licence licence;
	
	public Card(Context context){
		super(context);
		this.cardType = CardType.UNKNOWN;
		this.image = null;
		this.image = null;
		
		licence = null;
		oldRow = null;
		oldRowPosition = -1;
		isUsed = false;
		setTransferable(false);
		setUniqueID(-1);
	}
	
	public Card(Context context, CardType type, Bitmap image, int id, boolean tranfer){
		super(context);
		
		this.cardType = type;
		this.image = image;
		defaultImage = null;
		licence = null;
		oldRow = null;
		oldRowPosition = -1;
		isUsed = false;
		setTransferable(tranfer);
		setUniqueID(id);
	}
	
	public int getDrawableId() {
		String name = cardType.getName();
		String licences = this.getLicencesString().toLowerCase();
		String tmp = name + "_" + licences;
		//Log.i("Senaid", "Final string is " + tmp);
//		String test = "me";
//		if ( test.equals("me"))
//			Log.i("Senaid", "Final2 string is " + tmp);
//		if ( test == "me")
//			Log.i("Senaid", "Final3 string is " + tmp);
		
		if(tmp.equals("image_cc_by")) {
			return R.drawable.image_cc_by;
		} else if(tmp.equals("video_cc_by")) {
			return R.drawable.video_cc_by;
		} else if(tmp.equals("music_cc_by")) {
			return R.drawable.music_cc_by;
		} else if(tmp.equals("text_cc_by")) {
			return R.drawable.text_cc_by;
		} else if(tmp.equals("image_cc_by_nc_sa")) {
			return R.drawable.image_cc_by_nc_sa;
		} else if(tmp.equals("video_cc_by_nc_sa")) {
			return R.drawable.video_cc_by_nc_sa;
		} else if(tmp.equals("music_cc_by_nc_sa")) {
			return R.drawable.music_cc_by_nc_sa;
		} else if(tmp.equals("text_cc_by_nc_sa")) {
			return R.drawable.text_cc_by_nc_sa;
		} else if(tmp.equals("image_cc_by_sa")) {
			return R.drawable.image_cc_by_sa;
		} else if(tmp.equals("video_cc_by_sa")) {
			return R.drawable.video_cc_by_sa;
		} else if(tmp.equals("music_cc_by_sa")) {
			return R.drawable.music_cc_by_sa;
		} else if(tmp.equals("text_cc_by_sa")) {
			return R.drawable.text_cc_by_sa;
		} else if(tmp.equals("image_c")) {
			return R.drawable.image_c;
		} else if(tmp.equals("video_c")) {
			return R.drawable.video_c;
		} else if(tmp.equals("music_c")) {
			return R.drawable.music_c;
		} else if(tmp.equals("text_c")) {
			return R.drawable.text_c;
		} else if(tmp.equals("image_gfdl")) {
			return R.drawable.image_gfdl;
		} else if(tmp.equals("video_gfdl")) {
			return R.drawable.video_gfdl;
		} else if(tmp.equals("music_gfdl")) {
			return R.drawable.music_gfdl;
		} else if(tmp.equals("text_gfdl")) {
			return R.drawable.text_gfdl;
		} else if(tmp.equals("image_pd")) {
			return R.drawable.image_pd;
		} else if(tmp.equals("video_pd")) {
			return R.drawable.video_pd;
		} else if(tmp.equals("music_pd")) {
			return R.drawable.music_pd;
		} else if(tmp.equals("text_pd")) {
			return R.drawable.text_pd;
		}
		Log.i("Senaid", "getDrawableId: Nothing found");

		return -1;
	}
	
	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public TableRow getOldRow() {
		return oldRow;
	}

	public void setOldRow(TableRow oldRow) {
		this.oldRow = oldRow;
	}

	public int getOldRowPosition() {
		return oldRowPosition;
	}

	public void setOldRowPosition(int rowPosition) {
		this.oldRowPosition = rowPosition;
	}

	public void setLicence(Licence l) {
		licence = l;
	}
	
	public Licence getLicence() {
		return licence;
	}
	
	public int getCardTypeValue() {
		return cardType.getValue();
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType id) {
		this.cardType = id;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap img) {
		setImageBitmap(img);
		this.image = img;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public boolean isTransferable() {
		return isTransferable;
	}

	public void setTransferable(boolean isTransferable) {
		this.isTransferable = isTransferable;
	}
	
	public Bitmap getDefaultImage() {
		return defaultImage;
	}
	
	public boolean canCombine(Card other) {
		boolean canBeCombined = true;
			if (!licence.canBeRemixed(other.getLicence()))
				canBeCombined = false;
//			for (int j = 0; j < licences.size(); j++) {
//				if (i != j) {
//					Licence testLicence = licences.get(j);
////					licence.
//				}
//			}
		return canBeCombined;
	}
	
	public String getLicencesString() {
		String result = licence.getLicenceString();
		result = result.replace("-", "_");
		return result;
	}
	
	/**
	 * called when click is performed
	 * => Case that the defaultImage ==  null,
	 * 			means currently bottom card is shown to
	 * 			user, and it would be replaced with normal
	 * 			card, the one that the user clicked on
	 * => Case that the defaultImage !=  null,
	 * 			means that normal card is shown to
	 * 			user, and it would be replaced with bottom
	 * 			card, and the normal card would be transfered 
	 * 			back to it's beginning position 
	 * @param img
	 */
	public void switchCard(Bitmap img, Card otherCard) {
		if (!isTransferable) {
			if(defaultImage == null) { // save prev image and make new
				defaultImage = image;
				this.licence = otherCard.getLicence();
				setUsed(true);
				setImage(img);
			} else {
				setImage(defaultImage); // set prev image as current
				setUsed(false);
				defaultImage = null;
				this.licence = null;
				oldRow = null;
				oldRowPosition = -1;
			}
		}
	}
	
}
