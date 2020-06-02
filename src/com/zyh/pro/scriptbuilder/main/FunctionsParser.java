package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.CompositeToResult;
import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

import java.util.List;

public class FunctionsParser implements ReturnMatcher<IOperation, IStringScanner> {

	private final ScriptContext context;

	private final CompositeToResult<String, IStringScanner> paramsTokenizer;

	public FunctionsParser(ScriptContext context) {
		this.context = context;

		paramsTokenizer = new CompositeToResult<String, IStringScanner>()
				.add(new PageMatcher())
				.add(new SingleMatcher(","));
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists("function");
	}

	@Override
	public IOperation onMatched(IStringScanner scanner) {
		scanner.pass("function");
		scanner.trim();
		String functionName = scanner.nextPage();

		// params
		scanner.nextChar(); // '('
		List<String> modelParamNames = scanner.sequence(paramsTokenizer).split(",").toList();
		scanner.nextChar(); // ')'

		scanner.nextChar(); // '{'
		IOperation operations = new StatementsParser(context).onMatched(scanner);
		FunctionDeclareOperation result = new FunctionDeclareOperation(context, functionName, operations, modelParamNames);
		scanner.nextChar(); // '}'
		return result;
	}
}
