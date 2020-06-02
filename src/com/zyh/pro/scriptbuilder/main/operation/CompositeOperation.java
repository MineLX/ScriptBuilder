package com.zyh.pro.scriptbuilder.main.operation;

import java.util.ArrayList;
import java.util.List;

public class CompositeOperation implements IOperation {

	private final List<IOperation> operations;

	public CompositeOperation() {
		operations = new ArrayList<>();
	}

	@Override
	public void execute() {
		for (IOperation operation : operations)
			operation.execute();
	}

	public void add(IOperation operation) {
		operations.add(operation);
	}

	public CompositeOperation addAll(List<IOperation> addend) {
		operations.addAll(addend);
		return this;
	}
}
