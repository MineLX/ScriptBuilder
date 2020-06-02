package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;

import java.util.function.BinaryOperator;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class Operator implements IValue {

	private final IValue left;

	private final IValue right;

	private final BinaryOperator<String> operatorAsFunction;

	private Operator(IValue left, IValue right, BinaryOperator<String> operatorAsFunction) {
		this.left = left;
		this.right = right;
		this.operatorAsFunction = operatorAsFunction;
	}

	@Override
	public String asString() {
		return operatorAsFunction.apply(left.asString(), right.asString());
	}

	public static Operator multi(IValue left, IValue right) {
		return new Operator(left, right, (l, r) -> valueOf(parseInt(l) * parseInt(r)));
	}

	public static Operator reduce(IValue left, IValue right) {
		return new Operator(left, right, (l, r) -> valueOf(parseInt(l) - parseInt(r)));
	}

	public static Operator plus(IValue left, IValue right) {
		return new Operator(left, right, (l, r) -> valueOf(parseInt(l) + parseInt(r)));
	}
}
