package com.kazurayam.ks.reporting

import static org.junit.Assert.*

import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Before
import org.junit.Test

public class ReportsDirUtilTest {

	Path reportsDir

	@Before
	public void setup() {
		reportsDir = Paths.get("./Reports")
	}

	@Test
	public void test_getLatestTestSuiteReportDirOf() {
		ReportsDirUtil util = new ReportsDirUtil(reportsDir)
		String testSuiteName = "TS1"
		Path dir = util.getLatestTestSuiteReportDirOf(testSuiteName)
		println dir.toString()
		assertNotNull(dir)
	}
}