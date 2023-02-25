import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor

import com.kms.katalon.core.logging.TestSuiteXMLLogParser
import com.kms.katalon.core.logging.model.TestSuiteLogRecord
import com.kms.katalon.core.reporting.ReportUtil
import com.kazurayam.ks.reporting.ReportUtilHacked

File reportDir = new File("Reports/20230222_080435/TS1/20230222_080435")

IProgressMonitor monitor = new NullProgressMonitor()

TestSuiteXMLLogParser logParser = new TestSuiteXMLLogParser()

TestSuiteLogRecord suiteLogEntity = logParser.readTestSuiteLogFromXMLFiles(reportDir.toString(), monitor)

File outFolder = new File("compileTestSuiteReport_output")

//ReportUtil.writeHtmlReport(suiteLogEntity, outFolder)
ReportUtilHacked.writeHtmlReport(suiteLogEntity, outFolder)
