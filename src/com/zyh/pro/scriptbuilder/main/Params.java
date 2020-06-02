package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static Params of(IValue...values) {
		Params result = new Params();
		Arrays.stream(values).forEach(result::add);
		return result;
	}
}
