package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.CompositeToResult;
import com.zyh.pro.scanner.main.Matcher;
import com.zyh.pro.scanner.main.ToResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class SearchTree<ELEMENT, CLUE> implements ToResult<ELEMENT, CLUE> {

	private final List<SearchTree<ELEMENT, CLUE>> children;

	private final Matcher<CLUE> matcher;

	private final ELEMENT leafElement;

	private SearchTree(ELEMENT leafElement, Matcher<CLUE> matcher) {
		this.matcher = matcher;
		this.leafElement = leafElement;
		children = new ArrayList<>();
	}

	@Override
	public ELEMENT get(CLUE clue) {
		if (!matcher.isMatch(clue))
			return null;

		matcher.onMatched(clue);

		ELEMENT self = leafElement;
		if (self != null) // this is a leaf
			return self;

		for (SearchTree<ELEMENT, CLUE> child : children) {
			ELEMENT childSearchResult = child.get(clue);
			if (childSearchResult != null)
				return childSearchResult;
		}
		return null;
	}

	public void addChild(SearchTree<ELEMENT, CLUE> child) {
		children.add(child);
	}

	@Override
	public String toString() {
		String childrenAsText = children.stream().map(SearchTree::toString).collect(Collectors.joining(", "));
		if (children.size() == 0)
			childrenAsText = valueOf(leafElement);
		return "SearchTree(" +
				childrenAsText +
				")";
	}

	// FIXME 2020/4/27  wait for me!!!  move to Tree(Unmodifiable)
	public static class Builder<ELEMENT, CLUE> {

		private final CompositeToResult<ELEMENT, CLUE> root;

		public Builder() {
			root = new CompositeToResult<>();
		}

		public Path path(Matcher<CLUE> matcher) {
			SearchTree<ELEMENT, CLUE> newTreePath = new SearchTree<>(null, matcher);
			root.add(newTreePath);
			return new Path(newTreePath);
		}

		public Builder<ELEMENT, CLUE> end(ELEMENT leafValue, Matcher<CLUE> matcher) {
			root.add(new SearchTree<>(leafValue, matcher));
			return this;
		}

		public ToResult<ELEMENT, CLUE> build() {
			return root;
		}

		public class Path {
			private SearchTree<ELEMENT, CLUE> currentTree;

			private Path(SearchTree<ELEMENT, CLUE> currentTree) {
				this.currentTree = currentTree;
			}

			public Path path(Matcher<CLUE> pathMatcher) {
				SearchTree<ELEMENT, CLUE> nextPath = new SearchTree<>(null, pathMatcher);
				currentTree.addChild(nextPath);
				return new Path(nextPath);
			}

			public Builder<ELEMENT, CLUE> end(ELEMENT leafValue, Matcher<CLUE> endPathMatcher) {
				currentTree.addChild(new SearchTree<>(leafValue, endPathMatcher));
				return Builder.this;
			}
		}
	}
}
