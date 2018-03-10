package com.mwos.ebochs.view;

public class PersonManager {
	private static PersonManager pm = new PersonManager();

	public static PersonManager getManager() {
		return pm;
	}
}
