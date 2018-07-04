package com.mwos.ebochs.core.vm;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.mwos.ebochs.core.model.BochsReader;
import com.mwos.ebochs.core.model.handler.BP;

public abstract class AbstractVM {

	protected String path;
	protected Process process;
	protected IVMProfile profile;

	public AbstractVM(String path, IVMProfile profile) {
		this.path = path;
		this.profile = profile;
	}

	public String getPath() {
		return path;
	}

	public Process getProcess() {
		return process;
	}

	public abstract Process run() throws Exception;

	public abstract Process debug() throws Exception;

	public boolean isAlive() {
		if (process != null && process.isAlive() && !getSocket().isClosed()) {
			return true;
		}
		return false;
	}

	public abstract String getName();

	public IVMProfile getProfile() {
		return profile;
	}

	public abstract Socket getSocket();

	public abstract void addBp(BP bp);
	public abstract void removeBp(BP bp);

	public abstract List<BP> getBp();

	public synchronized String sendCmd(String cmd) throws IOException {
		this.getSocket().getOutputStream().write(cmd.getBytes());
		this.getSocket().getOutputStream().flush();
		BochsReader br = new BochsReader(this.getSocket().getInputStream());
		String res = br.readResult();
		return res;
	}

}
