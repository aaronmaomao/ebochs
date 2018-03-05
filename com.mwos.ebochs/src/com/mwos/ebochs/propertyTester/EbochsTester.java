package com.mwos.ebochs.propertyTester;

import org.eclipse.core.expressions.PropertyTester;

public class EbochsTester extends PropertyTester {

	//µÃµ½ÓÒ»÷itemµÄ¾ä±ú
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (property.equals("isEbochs")) {
			System.out.println("is ebochs");
			return false;
		}

		if (property.equals("notEbochs")) {
			System.out.println("not ebochs");
			return true;
		}
		return false;
	}
}
