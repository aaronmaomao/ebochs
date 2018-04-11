package com.mwos.ebochs.core;

public abstract class BochsDataParse {

	public void reciveData(String data) {
		parse(data);
	}

	public abstract Object parse(String data);

}
