package com.zyh.pro.scriptbuilder.main;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class Function {
	protected final ScriptContext context;
	private final String name;
	private final List<String> modelParams;

	public Function(ScriptContext context, String name) {
		this(context, name, emptyList());
	}

	public Function(ScriptContext context, String name, List<String> modelParams) {
		this.context = context;
		this.name = name;
		this.modelParams = modelParams;
	}

	public IValue execute(Params params) {
		return null;
	}

	public String getName() {
		return name;
	}
}
