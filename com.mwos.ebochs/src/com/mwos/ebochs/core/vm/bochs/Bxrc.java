package com.mwos.ebochs.core.vm.bochs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.Localizable;

public class Bxrc implements Localizable {
	private Properties properties;
	private String bochs;

	public Bxrc(File temp, String bochs) {
		properties = new Properties();
		this.bochs = bochs;
		try {
			properties.load(new FileReader(temp));
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}
	
	public Bxrc(InputStream in, String bochs) {
		properties = new Properties();
		this.bochs = bochs;
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
		// Activator.getDefault().getBundle().getResource("com/mwos/ebochs/core/bochsrc.bxrc");
	}

	private void init() {
		properties.setProperty("romimage", "file=\"" + bochs + "/BIOS-bochs-latest\", address=0x0, options=none");
		properties.setProperty("vgaromimage", "file=\"" + bochs + "/VGABIOS-lgpl-latest\"");
	}

	@Override
	public void localize(File out) {
		try {
			Writer w = new FileWriter(out);
			for(Object key:properties.keySet()) {
				w.write(key+": "+properties.getProperty((String) key)+"\r\n");
			}
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBoot(String value) {
		properties.setProperty("boot", value);
	}

	public void setFloppya(String value) {
		properties.setProperty("floppya", "type=1_44, 1_44=\"" + FileUtil.formatPath(value) + "\", status=inserted, write_protected=0");
	}

}
