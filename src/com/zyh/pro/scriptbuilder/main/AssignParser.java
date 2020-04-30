package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

public class AssignParser {
	public Assignment parseAssignment(String assignmentAsText) {
		IStringScanner scanner = new TrimmedStringScanner(new StringScanner(assignmentAsText));
		String left = scanner.nextPage();
		scanner.trim();
		scanner.nextChar();
		scanner.trim();
		return new Assignment(left, scanner.til(';'));
	}
}
