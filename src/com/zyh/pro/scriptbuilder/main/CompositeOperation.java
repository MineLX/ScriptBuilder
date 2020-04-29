package com.zyh.pro.scriptbuilder.main;

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

	public void addOperation(IOperation operation) {
		operations.add(operation);
	}
}
