package com.mwos.ebochs2.model;

public interface IJSONSerial extends ISerial<JSONObject> {
	public static <T extends IJSONSerial> T deSerial(JSONObject obj, Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			t.setSerial(obj);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
