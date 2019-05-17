package com.mwos.editor.bin.model;

import java.math.BigInteger;

public class Byte16Item {
	private int offset;
	private Byte[] data;
	private int dirtyByte = 0x0000;

	public Byte16Item(int offset, Byte[] data) {
		this.offset = offset;
		this.data = data;
	}

	public Byte getByte(int position) {
		return data[position];
	}
	
	public Byte[] getBytes() {
		return data;
	}

	public BigInteger getValues() {
		byte[] d = new byte[16];
		for (int i = 0; i < 16; i++) {
			if (data[i]!=null) {
				d[i] = data[i];
			}

		}
		return new BigInteger(1, d);
	}

	public void setValue(Byte value, int position) {
		setValue(value, position, true);
	}

	public void setValue(Byte value, int position, boolean isDirty) {
		dirtyByte |= (1 << position);
		data[position] = value;
	}


	public int getDirtyState() {
		return dirtyByte;
	}

	public boolean isDirty(int position) {
		return (dirtyByte & (1 << position)) == 0 ? false : true;
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public String toString() {
		int i = 0;
		byte str[] = new byte[data.length];
		for (; i < data.length; i++) {
			str[i] = data[i] != null ? data[i] : 0;
		}
		return new String(str);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Byte16Item) {
			Byte16Item _obj = (Byte16Item) obj;
			if (this.offset == _obj.offset) {
				for (int i = 0; i < 16; i++) {
					if (this.data[i] != _obj.data[i]) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}

}
