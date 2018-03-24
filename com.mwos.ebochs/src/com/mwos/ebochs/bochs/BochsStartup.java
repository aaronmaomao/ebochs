package com.mwos.ebochs.bochs;

import java.io.IOException;

public class BochsStartup {
	private static BochsProcess Startup(BochsConfigure config, boolean debug) {
		Process process = null;
		try {
			if (debug) {
				process = Runtime.getRuntime()
						.exec(new String[] { config.getBochsDbgPath(), "-f", "-q", config.getBxrcPath() });
			} else {
				process = Runtime.getRuntime().exec(new String[] { config.getBochsDbgPath(), config.getBxrcPath() });
			}
			return new BochsProcess(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
