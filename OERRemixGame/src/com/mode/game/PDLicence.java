package com.mode.game;

public class PDLicence extends Licence{
	public PDLicence(LicenceType licence) {
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
