package com.mode.game;

public class CC_BY_SALicence extends Licence {

	public CC_BY_SALicence(LicenceType l) {
		super(l);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBeRemixed(Licence licence) {
		// TODO Auto-generated method stub
		LicenceType type = licence.getLicence();

			switch (type) {
			case C:
			case GFDL:
				return false;
			case CC_BY_SA_NC:
//				if (combinations.contains(this))
//					return true;
//				else  TODO check
					return false;
			default:
				break;
			}
		return true;
	}

}
