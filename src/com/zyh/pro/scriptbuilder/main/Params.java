package com.zyh.pro.scriptbuilder.main;

import java.util.ArrayList;
import java.util.List;

public class Params {

	private final List<IValue> values;

	public Params() {
		values = new ArrayList<>();
	}

	public IValue get(int at) {
		return values.get(at);
	}

	public void add(IValue value) {
		values.add(value);
	}

	public List<IValue> getParams() {
		return values;
	}

	@Override
	public String toString() {
		return "Params(" + values + ")";
	}
}
