package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.main.TrimmedScanner;

import java.util.Optional;

import static com.zyh.pro.scanner.main.Matcher.functional;

public class OperationDispatcher {

	private final SearchTree<OperationInterpreter, IScanner> dealerSearchTree;

	public OperationDispatcher(ScriptContext context) {
		SearchTree.Builder<OperationInterpreter, IScanner> builder = new SearchTree.Builder<>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha));

		builder.path(functional(scanner -> scanner.exists("("), scanner -> scanner.between('(', ')')))
				.end(new InvokeFunctionInterpreter(context), functional(scanner -> scanner.exists(";"), IScanner::nextChar));

		dealerSearchTree = builder.path(functional(scanner -> scanner.exists("="), IScanner::nextChar))
				.path(functional(scanner -> true, scanner -> scanner.til(';')))
				.end(new AssignInterpreter(context), functional(scanner -> scanner.exists(";"), IScanner::nextChar)).build();
	}

	public OperationInterpreter getDealer(String command) {
		return dealerSearchTree.search(new TrimmedScanner(new Scanner(command)));
	}

	public IOperation getOperation(String operationAsCommand) {
		return Optional.ofNullable(getDealer(operationAsCommand))
				.orElse(OperationInterpreter.empty())
				.interpret(operationAsCommand);
	}
}
