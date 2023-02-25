package com.kazurayam.ks.reporting

import com.kms.katalon.core.logging.model.TestSuiteLogRecord
import com.kms.katalon.core.reporting.ReportUtil

import org.apache.commons.io.FileUtils
import com.kms.katalon.core.constants.StringConstants
import com.kms.katalon.core.reporting.JsSuiteModel
import org.apache.commons.io.IOUtils
import java.util.jar.JarFile
import java.util.jar.JarEntry
import org.apache.commons.lang.StringUtils

import com.kms.katalon.core.reporting.template.ResourceLoader
import org.apache.commons.io.output.StringBuilderWriter

public class ReportUtilHacked {

	/*
	 public static void writeHtmlReport(TestSuiteLogRecord suiteLogEntity, File logFolder) throws IOException, URISyntaxException {
	 ReportUtil.writeHtmlReport(suiteLogEntity, logFolder)
	 }
	 */

	public static void writeHtmlReport(TestSuiteLogRecord suiteLogEntity, File logFolder) throws IOException, URISyntaxException {
		StringBuilder htmlSb = prepareHtmlContent(suiteLogEntity);

		// Write main HTML Report
		FileUtils.writeStringToFile(new File(logFolder, logFolder.getName() + ".html"), htmlSb.toString(), StringConstants.DF_CHARSET);
	}

	private static StringBuilder prepareHtmlContent(TestSuiteLogRecord suiteLogEntity) throws IOException, URISyntaxException {
		List<String> strings = new ArrayList<String>();

		JsSuiteModel jsSuiteModel = new JsSuiteModel(suiteLogEntity, strings);
		StringBuilder sbModel = jsSuiteModel.toArrayString();

		StringBuilder htmlSb = new StringBuilder();

		readFileToStringBuilder(ResourceLoader.HTML_TEMPLATE_FILE, htmlSb);
		htmlSb.append("<!-- 0 ################################################################### -->\n");

		htmlSb.append(generateVars(strings, suiteLogEntity, sbModel));
		htmlSb.append("<!-- 1 ################################################################### -->\n");

		htmlSb.append('''
<style media="all" type="text/css">
div.hidden {
    display: none;
}
    </style>
    <script type="text/javascript">
$(function() {
    $("button#CTRL_PASSED_TESTS").on('click', function() {
        console.log("the button was clicked ----------------")
        var flag = false;
        if ($(this).text() == 'Show the PASSED') {
            flag = true;
        } else {
            flag = false;
        }
        $('div.test').each(function(){
            console.log("id=" + $(this).attr('id'));
            console.log($('div.element-header > span:first', this).hasClass('passed'))
            if ($('div.element-header > span:first', this).hasClass('passed')) {
                if (flag) {
                    $(this).removeClass('hidden');
                } else {
                    $(this).addClass('hidden');
                }
            }
        });
        if (flag) {
            $(this).html('Hide the PASSED')
        } else {
            $(this).html('Show the PASSED')
        }
     })
});
    </script>\n
''');

		htmlSb.append("<!-- 2 ################################################################### -->\n");

		readFileToStringBuilder(ResourceLoader.HTML_TEMPLATE_CONTENT, htmlSb);
		htmlSb.append("<!-- 3 ################################################################### -->\n");

		StringBuilder sb1 = StringBuilderUtil.removeLastMatch(htmlSb, "</html>")
		StringBuilder sb2 = StringBuilderUtil.removeLastMatch(sb1, "</body>")
		sb2.append('''
<p><button id="CTRL_PASSED_TESTS">Hide the PASSED</button></p>\n
''')
		sb2.append("<!-- 4 ################################################################### -->\n");
		sb2.append("</body>\n")
		sb2.append("</html>\n")
		return sb2;
	}








	/**
	 * Unfortunately ReportUtil#readFileToStringBuilder() method is private.
	 * Therefore I had to copy it here 
	 */
	private static void readFileToStringBuilder(String fileName, StringBuilder sb) throws IOException, URISyntaxException {
		String path = ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		path = URLDecoder.decode(path, "utf-8");
		File jarFile = new File(path);
		if (jarFile.isFile()) {
			JarFile jar = new JarFile(jarFile);
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				String name = jarEntry.getName();
				if (name.endsWith(fileName)) {
					StringBuilderWriter sbWriter = new StringBuilderWriter(new StringBuilder());
					IOUtils.copy(jar.getInputStream(jarEntry), sbWriter);
					sbWriter.flush();
					sbWriter.close();
					sb.append(sbWriter.getBuilder());
					break;
				}
			}
			jar.close();
		} else {
			// Run with IDE
			// sb.append(FileUtils.readFileToString(new
			// File(ResourceLoader.class.getResource(fileName).getContent()
			// )));
			InputStream is = (InputStream) ResourceLoader.class.getResource(fileName).getContent();
			sb.append(IOUtils.toString(is, "UTF-8"));
		}
	}

	/**
	 * Unfortunately ReportUtil#generateVars() method is private.
	 * Therefore I had to to copy it here
	 */
	private static StringBuilder generateVars(List<String> strings, TestSuiteLogRecord suiteLogEntity, StringBuilder model) throws IOException {
		StringBuilder sb = new StringBuilder();
		List<String> lines = IOUtils.readLines(ResourceLoader.class.getResourceAsStream(ResourceLoader.HTML_TEMPLATE_VARS));
		for (String line : lines) {
			if (line.equals(ResourceLoader.HTML_TEMPLATE_SUITE_MODEL_TOKEN)) {
				sb.append(model);
			} else if (line.equals(ResourceLoader.HTML_TEMPLATE_STRINGS_CONSTANT_TOKEN)) {
				sb.append(StringUtils.join(strings, (",")));
			} else if (line.equals(ResourceLoader.HTML_TEMPLATE_EXEC_ENV_TOKEN)) {
				StringBuilder envInfoSb = new StringBuilder();
				envInfoSb.append("{");
				envInfoSb.append(String.format("\"host\" : \"%s\", ", suiteLogEntity.getHostName()));
				envInfoSb.append(String.format("\"os\" : \"%s\", ", suiteLogEntity.getOs()));
				envInfoSb.append(String.format("\"" + StringConstants.APP_VERSION + "\" : \"%s\", ", suiteLogEntity.getAppVersion()));
				if (suiteLogEntity.getBrowser() != null && !suiteLogEntity.getBrowser().equals("")) {
					if (suiteLogEntity.getRunData().containsKey("browser")) {
						envInfoSb.append(String.format("\"browser\" : \"%s\",", suiteLogEntity.getRunData().get("browser")));
					} else {
						envInfoSb.append(String.format("\"browser\" : \"%s\",", suiteLogEntity.getBrowser()));
					}
				}
				if (suiteLogEntity.getDeviceName() != null && !suiteLogEntity.getDeviceName().equals("")) {
					envInfoSb.append(String.format("\"deviceName\" : \"%s\",", suiteLogEntity.getDeviceName()));
				}
				if (suiteLogEntity.getDeviceName() != null && !suiteLogEntity.getDeviceName().equals("")) {
					envInfoSb.append(String.format("\"devicePlatform\" : \"%s\",", suiteLogEntity.getDevicePlatform()));
				}
				envInfoSb.append("\"\" : \"\"");

				envInfoSb.append("}");
				sb.append(envInfoSb);
			} else {
				sb.append(line);
				sb.append("\n");
			}
		}
		return sb;
	}
}
