package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.AssignParser;
import com.zyh.pro.scriptbuilder.main.Assignment;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AssignmentParserTest {
	@Test
	public void parse_with_statement() {
		AssignParser parser = new AssignParser();
		Assignment assignment = parser.parseAssignment("a = sum(1, 2);");
		assertThat(assignment.getLeft(), is("a"));
		assertThat(assignment.getRight(), is("sum(1, 2)"));
	}

	@Test
	public void parse() {
		AssignParser parser = new AssignParser();
		Assignment assignment = parser.parseAssignment("a = 6;");
		assertThat(assignment.getLeft(), is("a"));
		assertThat(assignment.getRight(), is("6"));
	}

	@Test
	public void simple_test() {
		Assignment assignment = new Assignment("a", "6");
		assertThat(assignment.getLeft(), is("a"));
		assertThat(assignment.getRight(), is("6"));
	}
}
