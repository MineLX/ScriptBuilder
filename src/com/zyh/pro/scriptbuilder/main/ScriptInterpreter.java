package com.zyh.pro.scriptbuilder.main;

import java.io.PrintStream;
import java.util.List;

public class ScriptInterpreter {

	private final ScriptContext context;

	public ScriptInterpreter(PrintStream out) {
		context = new ScriptContext(out);
		context.addFunction(new SumFunction(context));
		context.addFunction(new PrintFunction(context));
	}

	public CompositeOperation interpret(String target) {
		StatementTokenizer tokenizer = new StatementTokenizer(target);
		List<String> statements = tokenizer.map(
				tokens -> tokens.stream().reduce("", (one, another) -> one + another),
				token -> token.equals(";"));

		// parse ...
		CompositeOperation result = new CompositeOperation();
		OperationDispatcher dispatcher = new OperationDispatcher(context);
		statements.stream().map(dispatcher::getOperation).forEach(result::addOperation);
		return result;
	}
}
