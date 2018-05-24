package com.mwos.ebochs.core.build;

import org.eclipse.cdt.managedbuilder.core.IManagedIsToolChainSupported;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.osgi.framework.Version;

public class ToolChainSupport implements IManagedIsToolChainSupported {

	@Override
	public boolean isSupported(IToolChain toolChain, Version version, String instance) {
		// TODO Auto-generated method stub
		return false;
	}

}
