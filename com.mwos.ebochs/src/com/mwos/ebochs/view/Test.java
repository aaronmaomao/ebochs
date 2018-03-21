package com.mwos.ebochs.view;

import java.util.Map;

import org.eclipse.cdt.core.index.IIndexLocationConverter;
import org.eclipse.cdt.core.index.export.IExportProjectProvider;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.runtime.CoreException;

public class Test implements IExportProjectProvider{

	@Override
	public ICProject createProject() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getExportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIndexLocationConverter getLocationConverter(ICProject arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApplicationArguments(String[] arg0) {
		// TODO Auto-generated method stub
		
	}

}
