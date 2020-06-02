package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.CompositeMatcher;
import com.zyh.pro.scanner.main.CompositeToResult;
import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

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
