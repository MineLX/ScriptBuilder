package com.zyh.pro.scriptbuilder.main.parser;

import com.zyh.pro.scanner.main.CompositeMatcher;
import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scriptbuilder.main.operation.CompositeOperation;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatementsParser implements ReturnMatcher<IOperation, IStringScanner> {

	private final CompositeMatcher<IOperation, IStringScanner> parsers;

	public StatementsParser(ScriptContext context) {
		parsers = new CompositeMatcher<IOperation, IStringScanner>()
				.add(new ReturnParser(context))
				.add(new AssignParser(context))
				.add(new FunctionInvocationParser(context));
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return parsers.isMatch(scanner);
	}

	@Override
	public IOperation onMatched(IStringScanner scanner) {
		return new CompositeOperation().addAll(scanner.sequence(parsers).toList());
	}
}
