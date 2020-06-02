package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;

import java.util.*;

public class VarManager {

	private final Map<String, IValue> globals;

	private final Stack<FunctionFrame> frames;

	public VarManager() {
		globals = new HashMap<>();
		frames = new Stack<>();
	}

	public void setGlobal(String name, IValue value) {
		globals.put(name, value);
	}

	public IValue global(String name) {
		return globals.get(name);
	}

	public void pushFrame(List<String> names, List<IValue> values) {
		frames.push(new FunctionFrame(names, values));
	}

	public IValue temporary(String name) {
		if (!hasFrame())
			return null;
		return getCurrentFrame().getParamValue(name);
	}

	public IValue getVariable(String name) {
		IValue temporary = temporary(name);
		if (temporary != null)
			return temporary;
		return global(name);
	}

	public void setVariable(String name, IValue value) {
		if (hasFrame() &&
				getCurrentFrame().modifyParamValue(name, value))
			return;
		setGlobal(name, value);
	}

	public FunctionFrame getCurrentFrame() {
		if (!hasFrame())
			return null;
		return frames.peek();
	}

	public FunctionFrame popFrame() {
		return frames.pop();
	}

	private boolean hasFrame() {
		return !frames.isEmpty();
	}
}
