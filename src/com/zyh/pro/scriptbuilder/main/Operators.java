package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;

public enum Operators {
	PLUS(1, false) {
		@Override
		public IValue calculate(IValue left, IValue right) {
			return Operator.plus(left, right);
		}
	},
	REDUCE(1, true) {
		@Override
		public IValue calculate(IValue left, IValue right) {
			return Operator.reduce(left, right);
		}
	},
	MULTI(2, false) {
		@Override
		public IValue calculate(IValue left, IValue right) {
			return Operator.multi(left, right);
		}
	},
	DIVIDE(2, true) {
		@Override
		public IValue calculate(IValue left, IValue right) {
			return null; // FIXME 2020/4/28  wait for me!!!  empty here
		}
	},
	UNKNOWN(0, false) {
		@Override
		public IValue calculate(IValue left, IValue right) {
			return IValue.empty();
		}
	};

	private final int priority;

	private final boolean changeSemanticsIfSwap;

	Operators(int priority, boolean changeSemanticsIfSwap) {
		this.priority = priority;
		this.changeSemanticsIfSwap = changeSemanticsIfSwap;
	}

	public abstract IValue calculate(IValue left, IValue right);

	public boolean biggerThan(Operators another) {
		return priority > another.priority;
	}

	public boolean equalsThan(Operators another) {
		return priority == another.priority;
	}

	public boolean biggerEquals(Operators another) {
		return biggerThan(another) || equalsThan(another);
	}

	public boolean lowerThan(Operators another) {
		return priority < another.priority;
	}

	public boolean lowerEquals(Operators another) {
		return lowerThan(another) || equalsThan(another);
	}

	public boolean changeSemanticsIfSwap() {
		return changeSemanticsIfSwap;
	}

	public static Operators ofString(String operandAsText) {
		if (operandAsText.equals("+"))
			return PLUS;
		if (operandAsText.equals("-"))
			return REDUCE;
		if (operandAsText.equals("*"))
			return MULTI;
		if (operandAsText.equals("/"))
			return DIVIDE;
		return UNKNOWN;
	}
}
