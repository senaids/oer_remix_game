package com.mode.game;

public enum CardType {
	MUSIC(0),
	IMAGE(1),
	TEXT(2),
	VIDEO(3),
	UNKNOWN(4);
	
	private int value;
	private CardType(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int val) {
		this.value = val;
	}
	
	public String getName() {
		switch(value) {
			case 0:
				return "music";
			case 1:
				return "image";
			case 2:
				return "text";
			case 3:
				return "video";
			default:
				return "unknown";
		}
	}
	
	public static CardType createType(int val) {
		switch (val) {
		case 0:
			return CardType.MUSIC;
		case 1:
			return CardType.IMAGE;
		case 2:
			return CardType.TEXT;
		case 3:
			return CardType.VIDEO;
		default:
			return CardType.UNKNOWN;
		}
	}
	
}
