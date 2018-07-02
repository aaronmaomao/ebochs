package com.mwos.ebochs.core.vm.bochs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.Localizable;
import com.mwos.ebochs.core.vm.IVMProfile;
import com.mwos.ebochs.resource.config.entity.OSConfig;

public class Bxrc implements IVMProfile {
	private OSConfig config;
	private String bochsDir;
	private Map<String, String> text;

	public Bxrc(OSConfig config, String bochsDir) {
		this.config = config;
		this.bochsDir = bochsDir;
		text = new LinkedHashMap<>();
		init();
	}

	@Override
	public void init() {
		InputStream bochsTemp;
		try {
			bochsTemp = Activator.getDefault().getBundle().getResource("com/mwos/ebochs/core/vm/bochs/default.bxrc").openStream();

			String line = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(bochsTemp));
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (StringUtils.isNotBlank(line)) {
					String temps[] = line.split(":");
					String value="";
					for(int i=1;i<temps.length;i++) {
						value+=temps[i];
					}
					text.put(temps[0].trim(), value);
				}
			}
			br.close();
			bochsTemp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		text.put("romimage", "file=\"" + bochsDir + "/BIOS-bochs-latest\", address=0x0, options=none");
		text.put("vgaromimage", "file=\"" + bochsDir + "/VGABIOS-lgpl-latest\"");
		text.put("boot", config.getImages().get(0).getDevice());
		String img = config.getProject().getLocationURI().getPath() + "/obj/images/" + config.getImages().get(0).getName();
		text.put("floppya", "type=1_44, 1_44=\"" + FileUtil.getRelPath(img) + "\", status=inserted, write_protected=0");
	}

	@Override
	public File localize() {
		File f = new File(config.getProject().getLocationURI().getPath() + "/obj/bochs.bxrc");
		try {
			Writer w = new FileWriter(f);
			for (String key : text.keySet()) {
				w.write(key + ": " + text.get(key) + "\r\n");
			}
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
		return this.config;
	}
}
