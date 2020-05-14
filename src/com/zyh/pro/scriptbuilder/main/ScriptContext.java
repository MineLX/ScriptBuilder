package com.zyh.pro.scriptbuilder.main;

import java.io.PrintStream;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class ScriptContext {

	private final List<Function> functions;

	private final PrintStream out;

	private final VarManager varManager;

	public ScriptContext(PrintStream out) {
		this.out = out;
		functions = new ArrayList<>();
		varManager = new VarManager();
	}

	public Function getFunction(String name) {
		return functions.stream()
				.filter(function -> function.getName().equals(name))
				.findFirst().orElse(null);
	}

	public void addFunction(Function function) {
		functions.add(function);
	}

	public PrintStream getOutputStream() {
		return out;
	}

	public void setVariable(String name, IValue value) {
		varManager.setVariable(name, value);
	}

	public IValue getVariable(String name) {
		return varManager.getVariable(name);
	}

	public void pushFunctionFrame(List<String> modelParams, Params realParams) {
		varManager.pushFrame(modelParams, realParams.getParams());
	}

	public FunctionFrame popFunctionFrame() {
		return varManager.popFrame();
	}

	public FunctionFrame getFunctionFrame() {
		return varManager.getCurrentFrame();
	}
}
