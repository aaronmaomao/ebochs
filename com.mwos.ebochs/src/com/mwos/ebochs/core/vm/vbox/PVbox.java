package com.mwos.ebochs.core.vm.vbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.vm.IVMProfile;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class PVbox implements IVMProfile {

	private OSConfig config;
	private String vboxDir;
	private String text = "";

	public PVbox(OSConfig config, String vboxDir) {
		this.config = config;
		this.vboxDir = vboxDir;
		init();
	}

	@Override
	public void init() {
		InputStream pvboxTemp;
		try {
			pvboxTemp = Activator.getDefault().getBundle().getResource("com/mwos/ebochs/core/vm/vbox/mwos.vbox").openStream();

			String line = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(pvboxTemp));
			while ((line = br.readLine()) != null) {
				text += (line + "\r\n");
			}
			br.close();
			pvboxTemp.close();
			text = text.replace("%name", config.getName());
			text = text.replace("%location", config.getProject().getRawLocation().toString() + "/obj/images/" + config.getImages().get(0).getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public File localize() {
		File f = new File(config.getProject().getRawLocation().toString() + "/obj/" + config.getName() + ".vbox");
		try {
			Writer w = new FileWriter(f);
			w.write(text);
			w.flush();
			w.close();
			return f;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public OSConfig getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
