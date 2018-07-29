package com.mwos.ebochs.core.model;

import org.eclipse.cdt.debug.internal.core.breakpoints.CLineBreakpoint;
import org.eclipse.core.runtime.CoreException;

public class BP {
	private boolean enable = true;
	private Long address = 0L;
	private String localtion = "";
	private String function = "";

	public boolean getEnable() {
		return enable;
	}

	public BP(CLineBreakpoint cbp) {
		try {
			enable = cbp.isEnabled();
			localtion = cbp.getMarker().getResource().getProjectRelativePath() + ":" + cbp.getLineNumber();
			function = cbp.getFunction();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public void setEnable(boolean b) {
		this.enable = b;
	}

	public Long getAddress() {
		return address;
	}

	public void setAddress(Long address) {
		this.address = address;
	}

	public String getLocaltion() {
		return localtion;
	}

	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

}
