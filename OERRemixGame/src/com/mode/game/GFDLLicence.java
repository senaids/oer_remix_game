package com.mode.game;

public class GFDLLicence extends Licence {

	public GFDLLicence(LicenceType l) {
		super(l);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBeRemixed(Licence licence) {
		// TODO Auto-generated method stub
		LicenceType type = licence.getLicence();
		switch (type) {
		case C:
		case CC_BY_SA_NC:
		case CC_BY_SA:
			return false;
		default:
			break;
		}
		return true;
	}


}
