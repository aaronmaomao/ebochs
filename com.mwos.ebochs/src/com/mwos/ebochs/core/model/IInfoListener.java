package com.mwos.ebochs.core.model;

import java.util.HashSet;
import java.util.Set;

import com.mwos.ebochs.core.model.cmd.Cmd;

public abstract interface IInfoListener {

	Set<String> cares = new HashSet<>();

	InfoCenter center = InfoCenter.getInfoCenter();

	public void notify(Object info);

	public void notify(String cmd, Object info);

	public default Set<String> getCare() {
		return cares;
	}

	public default String send(Cmd cmd) {
		return center.send(cmd);
	}

	public default String send(Cmd cmd, Object object) {
		return center.send(cmd, object);
	}
}
