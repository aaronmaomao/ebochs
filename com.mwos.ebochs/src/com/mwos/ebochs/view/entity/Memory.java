package com.mwos.ebochs.view.entity;

import java.util.ArrayList;
import java.util.List;

public class Memory {
	private long addr;
	private int m0;
	private int m1;
	private int m2;
	private int m3;
	private int m4;
	private int m5;
	private int m6;
	private int m7;
	private int m8;
	private int m9;
	private int ma;
	private int mb;
	private int mc;
	private int md;
	private int me;
	private int mf;

	public Memory() {
		// TODO Auto-generated constructor stub
	}

	public Memory(long addr) {
		this.addr = addr;
	}

	public long getAddr() {
		return addr;
	}

	public void setAddr(long addr) {
		this.addr = addr;
	}

	public int getM0() {
		return m0;
	}

	public void setM0(int m0) {
		this.m0 = m0;
	}

	public int getM1() {
		return m1;
	}

	public void setM1(int m1) {
		this.m1 = m1;
	}

	public int getM2() {
		return m2;
	}

	public void setM2(int m2) {
		this.m2 = m2;
	}

	public int getM3() {
		return m3;
	}

	public void setM3(int m3) {
		this.m3 = m3;
	}

	public int getM4() {
		return m4;
	}

	public void setM4(int m4) {
		this.m4 = m4;
	}

	public int getM5() {
		return m5;
	}

	public void setM5(int m5) {
		this.m5 = m5;
	}

	public int getM6() {
		return m6;
	}

	public void setM6(int m6) {
		this.m6 = m6;
	}

	public int getM7() {
		return m7;
	}

	public void setM7(int m7) {
		this.m7 = m7;
	}

	public int getM8() {
		return m8;
	}

	public void setM8(int m8) {
		this.m8 = m8;
	}

	public int getM9() {
		return m9;
	}

	public void setM9(int m9) {
		this.m9 = m9;
	}

	public int getMa() {
		return ma;
	}

	public void setMa(int ma) {
		this.ma = ma;
	}

	public int getMb() {
		return mb;
	}

	public void setMb(int mb) {
		this.mb = mb;
	}

	public int getMc() {
		return mc;
	}

	public void setMc(int mc) {
		this.mc = mc;
	}

	public int getMd() {
		return md;
	}

	public void setMd(int md) {
		this.md = md;
	}

	public int getMe() {
		return me;
	}

	public void setMe(int me) {
		this.me = me;
	}

	public int getMf() {
		return mf;
	}

	public void setMf(int mf) {
		this.mf = mf;
	}

	public static List<Memory> getMems() {
		List list = new ArrayList<Memory>();
		Memory m = new Memory(0x00007c00);
		m.setM0(0x33);
		m.setM3(0x36);
		m.setM5(0xae);
		list.add(m);
		m = new Memory(0x00007c10);
		m.setM2(0xa6);
		m.setM7(0xcb);
		list.add(m);
		return list;
	}
}
