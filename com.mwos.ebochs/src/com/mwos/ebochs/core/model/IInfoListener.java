package com.mwos.ebochs.core.model;

import java.util.Set;

public interface IInfoListener {

	public void notify(String rec);

	public void notify(String cmd, String rec);
	
	public default Set<String> getCare(){
		return null;
	}
}
