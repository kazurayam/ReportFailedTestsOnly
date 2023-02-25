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

Path reportDir = rdu.getLatestTestSuiteReportDirOf("TS1")

IProgressMonitor monitor = new NullProgressMonitor()

TestSuiteXMLLogParser logParser = new TestSuiteXMLLogParser()

TestSuiteLogRecord suiteLogEntity = logParser.readTestSuiteLogFromXMLFiles(reportDir.toString(), monitor)

File outFolder = new File("TestSuiteReport_hidable")

//ReportUtil.writeHtmlReport(suiteLogEntity, outFolder)
ReportUtilHacked.writeHtmlReport(suiteLogEntity, outFolder)
