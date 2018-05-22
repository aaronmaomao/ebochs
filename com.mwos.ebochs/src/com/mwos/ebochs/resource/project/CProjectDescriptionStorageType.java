package com.mwos.ebochs.resource.project;

import org.eclipse.cdt.internal.core.settings.model.AbstractCProjectDescriptionStorage;
import org.eclipse.cdt.internal.core.settings.model.ICProjectDescriptionStorageType;
import org.eclipse.core.resources.IProject;
import org.osgi.framework.Version;

public class CProjectDescriptionStorageType implements ICProjectDescriptionStorageType {

	public CProjectDescriptionStorageType() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public AbstractCProjectDescriptionStorage getProjectDescriptionStorage(CProjectDescriptionStorageTypeProxy type,
			IProject project, Version version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createsCProjectXMLFile() {
		// TODO Auto-generated method stub
		return false;
	}

}
