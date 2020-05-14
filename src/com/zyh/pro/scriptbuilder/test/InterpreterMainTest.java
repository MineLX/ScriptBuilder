package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.ScriptInterpreter;

import java.util.Scanner;

public class InterpreterMainTest {
	public static void main(String[] args) {
		ScriptInterpreter interpreter = new ScriptInterpreter(System.out);
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.print(">>> ");
			String command = scanner.nextLine();
			System.out.println("command = " + command);
			interpreter.interpret(command).execute();
		}
	}
}
