package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.main.TrimmedScanner;

public class AssignParser {
	public Assignment parseAssignment(String assignmentAsText) {
		IScanner scanner = new TrimmedScanner(new Scanner(assignmentAsText));
		String left = scanner.nextPage();
		scanner.trim();
		scanner.nextChar();
		scanner.trim();
		return new Assignment(left, scanner.til(';'));
	}
}
