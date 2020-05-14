package com.zyh.pro.scriptbuilder.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionFrame {

	private final Map<String, IValue> params;

	private IValue returnValue;

	public FunctionFrame(List<String> paramNames, List<IValue> paramValues) {
		params = new HashMap<>();
		for (int i = 0; i < paramNames.size(); i++) {
			params.put(paramNames.get(i), paramValues.get(i));
		}
	}

	public IValue getReturnValue() {
		if (returnValue == null)
			return null;
		return new Value(returnValue.asString());
	}

	public IValue getParamValue(String paramName) {
		return params.get(paramName);
	}

	public void setParamValue(String paramName, IValue paramValue) {
		params.put(paramName, paramValue);
	}

	public void setReturnValue(IValue returnValue) {
		this.returnValue = returnValue;
	}

	public boolean modifyParamValue(String paramName, IValue paramValue) {
		if (!params.containsKey(paramName)) return false;

		params.put(paramName, paramValue);
		return true;
	}
}
