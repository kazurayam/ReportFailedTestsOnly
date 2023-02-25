package com.kazurayam.ks.reporting

import static org.junit.Assert.*

import java.nio.file.Path

import org.junit.Test

public class ReportsDirUtilTest {

	@Test
	public void test_getLatestTestSuiteReportDirOf() {
		String testSuiteName = "TS1"
		ReportsDirUtil util = new ReportsDirUtil()
		Path dir = util.getLatestTestSuiteReportDirOf(testSuiteName)
		assertNotNull(dir)
	}
}