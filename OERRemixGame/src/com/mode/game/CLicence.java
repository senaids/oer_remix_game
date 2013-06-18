package com.mode.game;

public class CLicence extends Licence{
	public CLicence(LicenceType licence) {
		super(licence);
	}
	@Override
	public boolean canBeRemixed(Licence licence) {
		// TODO Auto-generated method stub
		return false;
	}
}
