package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import java.io.PrintStream;

import static com.zyh.pro.scanner.main.ReturnMatcher.accepter;
import static com.zyh.pro.scanner.main.ReturnMatcher.functional;

public class ScriptInterpreter {

	private final ScriptContext context;

	private final ToResult<ToResult<IOperation, IStringScanner>, IStringScanner> parserMatcher;

	public ScriptInterpreter(PrintStream out) {
		context = new ScriptContext(out);
		context.addFunction(new PrintFunction(context));
		context.addFunction(new SumFunction(context));

		parserMatcher = new CompositeToResult<ToResult<IOperation, IStringScanner>, IStringScanner>()
				.add(functional(scanner -> scanner.exists("function"), scanner -> new FunctionsParser(context)))
				.add(accepter(new StatementsParser(context)));
	}

	public IOperation interpret(String command) {
		TrimmedStringScanner scanner = new TrimmedStringScanner(new StringScanner(command));

		CompositeOperation result = new CompositeOperation();
		while (scanner.hasNext()) {
			ToResult<IOperation, IStringScanner> parser = parserMatcher.get(scanner.copy());
			IOperation operation = parser.get(scanner);
			result.add(operation);
		}
		return result;
	}
}
