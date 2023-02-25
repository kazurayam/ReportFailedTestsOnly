package com.kazurayam.ks.reporting

import java.util.regex.Matcher
import java.util.regex.Pattern

public class StringBuilderUtil {

	/**
	 * sb given: """<html><body><p>hello</p></body></html>"""
	 * 
	 * pattern: "</html>"
	 * 
	 * should return: """<html><body><p>hello</p></body>"""
	 * 
	 * please note that the string "</html>" is removed
	 * 
	 * sb given: """012abc012abc"
	 * pattern: "012"
	 * should return: "012abcabc"
	 * 
	 * sb given: """012abc012abc"
	 * pattern: "abc"
	 * should return: "012ab012"
	 * 
	 * sb given: """012abc012abc"
	 * pattern: "bc"
	 * should return: "012abc012a"
	 * 
	 * @param sb a StringBuilder instance
	 * @param pattern
	 * @return
	 */
	static StringBuilder removeLastMatch(StringBuilder sb, String pattern) {
		String source = sb.toString()
		StringBuilder result = new StringBuilder()
		result.append(replaceLast(source, pattern, ""))
		return result
	}

	private static String replaceLast(String string, String from, String to) {
		int lastIndex = string.lastIndexOf(from);
		if (lastIndex < 0)
			return string;
		String tail = string.substring(lastIndex).replaceFirst(from, to);
		return string.substring(0, lastIndex) + tail;
	}
}
