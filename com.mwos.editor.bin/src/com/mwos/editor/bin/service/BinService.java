package com.mwos.editor.bin.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.mwos.editor.bin.model.Byte16Line;

/**
 * 
 * @author maozhengjun
 * @time 2019年4月24日 上午10:19:53
 */
public class BinService {
	private File binFile;
	private List<Byte16Line> datas;
	private int size = 0;

	public BinService(File binFile) {
		this.binFile = binFile;
		datas = new ArrayList<>((int) (binFile.length() / 16));
	}

	public Byte16Line[] refresh() {
		size = 0;
		FileInputStream in;
		try {
			in = new FileInputStream(binFile);
			byte buffer[] = new byte[16];
			int len = 0;
			int i = 0;
			while ((len = in.read(buffer)) > 0) {
				datas.add(new Byte16Line(i * 16, buffer, len));
				i++;
				size += len;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas.toArray(new Byte16Line[] {});
	}

	public int getSize() {
		return size;
	}
}
