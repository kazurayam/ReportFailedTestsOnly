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
		ReportsDirUtil rdu = new ReportsDirUtil(reportsDir)
		String testSuiteName = "TS1"
		Path dir = rdu.getLatestTestSuiteReportDirOf(testSuiteName)
		println dir.toString()
		assertNotNull(dir)
	}
}