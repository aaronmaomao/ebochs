package com.mwos.ebochs.core.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.UIPlugin;

import com.mwos.ebochs.core.model.cmd.Cmd;

public abstract interface IInfoListener {

	static InfoCenter center = InfoCenter.getInfoCenter();

	public void notify(Object info);

	public void notify(String cmd, Object info);

	public Set<String> getCare();

	public default void sendToCenter(String cmd, Object object) {
		UIPlugin.getDefault().getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				center.send(cmd, object);
			}
		});
	}

}
