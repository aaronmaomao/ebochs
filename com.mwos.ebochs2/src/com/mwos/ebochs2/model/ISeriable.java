package com.mwos.ebochs2.model;

import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonObject;

public interface ISeriable {
	public JsonObject getSerial();

	public boolean setSerial(JsonObject str);

	public static <E extends ISeriable> E toObject(JsonObject str, Class<E> clazz) {
		try {
			E object = clazz.newInstance();
			if (object.setSerial(str)) {
				return object;
			} else {
				return null;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}