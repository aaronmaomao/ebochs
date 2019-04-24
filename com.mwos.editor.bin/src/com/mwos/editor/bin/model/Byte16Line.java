package com.mwos.editor.bin.model;

/**
 * 
 * @author maozhengjun
 * @time 2019年4月24日 上午10:14:06
 */
public class Byte16Line {
	private Byte[] byte16;
	private int offset;

	public Byte16Line(int offset, byte[] value, int len) {
		byte16 = new Byte[len];
		for (int i = 0; i < len; i++) {
			byte16[i] = value[i];
		}
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public Integer getValue(int position) {
		if (position >=0 && position < byte16.length) {
			return byte16[position]&0xff;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		int i = 0;
		byte str[] = new byte[byte16.length];
		for (; i < byte16.length; i++) {
			str[i] = byte16[i];
		}

		return new String(str);
	}
}
