package com.zyh.pro.scriptbuilder.main.operation;

import com.zyh.pro.scriptbuilder.main.OperationsFunction;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

import java.util.List;

// FIXME 2020/5/3  wait for me!!!  remove it if must
public class FunctionDeclareOperation implements IOperation {

	private final ScriptContext context;
	private final String name;
	private final IOperation operations;
	private List<String> modelParams;

	public FunctionDeclareOperation(ScriptContext context, String name, IOperation operations, List<String> modelParams) {
		this.context = context;
		this.name = name;
		this.operations = operations;
		this.modelParams = modelParams;
	}

	@Override
	public void execute() {
		context.addFunction(new OperationsFunction(context, name, operations, modelParams));
	}
}
