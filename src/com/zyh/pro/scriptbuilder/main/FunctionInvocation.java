package com.zyh.pro.scriptbuilder.main;

public class FunctionInvocation {

	private final Params params;
	private final String functionName;

	public FunctionInvocation(String functionName) {
		this.functionName = functionName;
		params = new Params();
	}

	public void addParam(IValue value) {
		params.add(value);
	}

	public void addParam(Params params) {
		params.getParams().forEach(this::addParam);
	}

	public Params getParams() {
		return params;
	}

	public String getFunctionName() {
		return functionName;
	}
}
