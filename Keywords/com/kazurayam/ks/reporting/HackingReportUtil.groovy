package com.kazurayam.ks.reporting

import com.kms.katalon.core.logging.model.TestSuiteLogRecord
import com.kms.katalon.core.reporting.ReportUtil

public class HackingReportUtil {

	public static void writeHtmlReport(TestSuiteLogRecord suiteLogEntity, File logFolder) throws IOException, URISyntaxException {
		ReportUtil.writeHtmlReport(suiteLogEntity, logFolder)
	}
}
