package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.ScriptInterpreter;

import java.util.Scanner;

public class InterpreterMainTest {
	public static void main(String[] args) {
		ScriptInterpreter interpreter = new ScriptInterpreter(System.out);
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("\n>>> ");
			String command = scanner.nextLine();
			if (command.equals("exit"))
				break;
			interpreter.interpret(command).execute();
		}
		scanner.close();
	}
}
