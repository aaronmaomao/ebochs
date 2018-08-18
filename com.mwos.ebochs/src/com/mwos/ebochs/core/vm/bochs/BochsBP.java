package com.mwos.ebochs.core.vm.bochs;

public class BochsBP {
	private String num;
	private String addr;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public BochsBP(String num, String addr) {
		super();
		this.num = num;
		this.addr = addr;
	}

}
