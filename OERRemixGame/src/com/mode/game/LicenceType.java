package com.mode.game;

public enum LicenceType {
	C(10),
	CC_BY(11),
	PD(12),
	CC_BY_SA(13),
	CC_BY_SA_NC(14),
	GFDL(15),
	UNKNOWN(16);
	private int value;
	private LicenceType(int value) {
		this.value= value;
	}
	
	public int getValue() {
		return value;
	}
	
	// TODO check 
	public void setValue(int val) {
		this.value = val;
	}
	
	public static LicenceType createType(int val) {
		switch (val) {
		case 10:
			return LicenceType.C;
		case 11:
			return LicenceType.CC_BY;
		case 12:
			return LicenceType.PD;
		case 13:
			return LicenceType.CC_BY_SA;
		case 14:
			return LicenceType.CC_BY_SA_NC;
		case 15:
			return LicenceType.GFDL;
		default:
			return LicenceType.UNKNOWN;
		}
	}
}
