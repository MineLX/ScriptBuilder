package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.CompositeToResult;
import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;
import com.zyh.pro.scriptbuilder.main.operation.CompositeOperation;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.parser.FunctionsParser;
import com.zyh.pro.scriptbuilder.main.parser.StatementsParser;

import java.io.PrintStream;

public class ScriptInterpreter {

	private final CompositeToResult<IOperation, IStringScanner> parsers;

	public ScriptInterpreter(PrintStream out) {
		ScriptContext context = new ScriptContext(out);
		context.addFunction(new PrintFunction(context));
		context.addFunction(new SumFunction(context));

		parsers = new CompositeToResult<IOperation, IStringScanner>()
				.add(new FunctionsParser(context))
				.add(new StatementsParser(context));
	}

	public IOperation interpret(String command) {
		TrimmedStringScanner scanner = new TrimmedStringScanner(new StringScanner(command));
		return new CompositeOperation().addAll(scanner.sequence(parsers).toList());
	}
}
