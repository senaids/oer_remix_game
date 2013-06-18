package com.mode.game;

public class CC_BY_SA_NCLicence extends Licence {

	public CC_BY_SA_NCLicence(LicenceType l) {
		super(l);
	}

	@Override
	public boolean canBeRemixed(Licence licence) {
		LicenceType type = licence.getLicence();
			switch (type) {
			case C:
			case GFDL:
				return false;
			case CC_BY_SA:
//				if (combinations.contains(this))
//					return true;
//				else // TODO check
					return false;
			default:
				break;
		}
		return true;
	}

}
