package com.mwos.ebochs.view;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class PersonViewContentProvider implements IStructuredContentProvider,PersonManagerListener {
	PersonManager pm;

	public PersonViewContentProvider(PersonManager pm) {
		this.pm = pm;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return null;
	}

}
