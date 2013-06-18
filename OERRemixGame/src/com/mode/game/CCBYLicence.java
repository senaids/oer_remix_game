package com.mode.game;

public class CCBYLicence  extends Licence{
	public CCBYLicence(LicenceType licence) {
		super(licence);
	}

	@Override
	public boolean canBeRemixed(Licence licence) {
		// TODO Auto-generated method stub
		LicenceType type = licence.getLicence();
		if (type == LicenceType.C)
			return false;
		return true;
	}

}
