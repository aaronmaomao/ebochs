package com.mwos.ebochs2.model.toolchain;

public interface IToolChain {

	public String getName();
	public String getPath();
	public ITool getAssemblers();
	public ITool getCompiler();
	public ITool getLinker();
}
