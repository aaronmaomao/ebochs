package com.mwos.ebochs.core.model.handler;

public class BP {
	private boolean enable = true;
	private String address = "";
	private String localtion = "";
	private String function = "";

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean b) {
		this.enable = b;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
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
