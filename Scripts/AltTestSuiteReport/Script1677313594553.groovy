import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor

import com.kms.katalon.core.logging.TestSuiteXMLLogParser
import com.kms.katalon.core.logging.model.TestSuiteLogRecord
import com.kms.katalon.core.reporting.ReportUtil
import com.kazurayam.ks.reporting.ReportUtilHacked
import com.kazurayam.ks.reporting.ReportsDirUtil
import java.nio.file.Path
import java.nio.file.Paths

ReportsDirUtil rdu = new ReportsDirUtil(Paths.get("./Reports"))

// find the directory where the report of latest execution of Test Suite "TS1"
Path reportDir = rdu.getLatestTestSuiteReportDirOf("TS1")

IProgressMonitor monitor = new NullProgressMonitor()

TestSuiteXMLLogParser logParser = new TestSuiteXMLLogParser()

// parse the "execution0.log" file to consume as input of the report
TestSuiteLogRecord suiteLogEntity = logParser.readTestSuiteLogFromXMLFiles(reportDir.toString(), monitor)

File outFolder = new File("AltTestSuiteReport")

/*
 * compile a Test Suite report in the format slightly modified
 */
ReportUtilHacked.writeHtmlReport(suiteLogEntity, outFolder)
//ReportUtil.writeHtmlReport(suiteLogEntity, outFolder)
