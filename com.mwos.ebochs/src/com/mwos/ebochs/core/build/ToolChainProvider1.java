package com.mwos.ebochs.core.build;

import org.eclipse.cdt.core.build.IToolChain;
import org.eclipse.cdt.core.build.IToolChainManager;
import org.eclipse.cdt.core.build.IToolChainProvider;
import org.eclipse.core.runtime.CoreException;

public class ToolChainProvider1 implements IToolChainProvider {

	public ToolChainProvider1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IToolChain getToolChain(String id, String version) throws CoreException {
		// TODO Auto-generated method stub
		return IToolChainProvider.super.getToolChain(id, version);
	}
	
	@Override
	public void init(IToolChainManager manager) throws CoreException {
		// TODO Auto-generated method stub
		IToolChainProvider.super.init(manager);
	}
	
}
