package com.mode.game;

public abstract class Licence {
	
	private LicenceType licence;

	public Licence(LicenceType l) {
		this.setLicence(l);
	}

	public String getLicenceString() {
		String licenceString = null;
		switch (licence) {
		case C:
			licenceString  = "C";
			break;
		case CC_BY:
			licenceString  = "CC_BY";
			break;
		case PD:
			licenceString  = "PD";
			break;
		case CC_BY_SA:
			licenceString  = "CC_BY_SA";
			break;
		case CC_BY_SA_NC:
			licenceString  = "CC_BY_NC_SA";
			break;
		case GFDL:
			licenceString  = "GFDL";
			break;
		default:
			licenceString = null;
			break;
		}
		return licenceString;
	}

	public abstract boolean canBeRemixed(Licence licence);

	public LicenceType getLicence() {
		return licence;
	}

	public void setLicence(LicenceType licence) {
		this.licence = licence;
	}
}
