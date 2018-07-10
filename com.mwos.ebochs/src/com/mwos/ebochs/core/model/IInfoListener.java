package com.mwos.ebochs.core.model;

import java.util.Set;

public abstract interface IInfoListener {

	public void notify(Object info);

	public void notify(String cmd, Object info);
	
	public default Set<String> getCare(){
		return null;
	}
}
