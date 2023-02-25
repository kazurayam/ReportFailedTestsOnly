package com.kazurayam.ks.reporting

import static org.junit.Assert.*

import org.junit.Test

public class StringBuilderUtilTest {

	@Test
	void test_removeLastMatch_one() {
		StringBuilder sb = new StringBuilder()
		sb.append("012abc012abc")
		String pattern = "abc"
		StringBuilder modified = StringBuilderUtil.removeLastMatch(sb, pattern)
		assertEquals("012abc012", modified.toString())
	}

	@Test
	void test_removeLastMatch_endHtml() {
		StringBuilder sb = new StringBuilder()
		sb.append("""<html>
<body>
<p>hello</p>
</body>
</html>""")
		StringBuilder mod1 = StringBuilderUtil.removeLastMatch(sb, "</html>")
		println "mod1: " + mod1.toString()
		assertTrue(!mod1.toString().contains("</html>"))
		StringBuilder mod2 = StringBuilderUtil.removeLastMatch(mod1, "</body>")
		println "mod2: " + mod2.toString()
		assertTrue(!mod2.toString().contains("</body>"))
	}
}

