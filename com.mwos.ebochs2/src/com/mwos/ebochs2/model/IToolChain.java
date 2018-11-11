package com.mwos.ebochs2.model;

public interface IToolChain {
	public ITool getAssemblers();
	public ITool getCompiler();
	public ITool getLinker();

}
