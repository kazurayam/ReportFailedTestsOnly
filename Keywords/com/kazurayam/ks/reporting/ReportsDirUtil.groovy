package com.kazurayam.ks.reporting

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor

public class ReportsDirUtil {

	private Path reportsDir

	public ReportsDirUtil(Path reportsDir) {
		assert Objects.requireNonNull(reportsDir)
		assert Files.exists(reportsDir)
		this.reportsDir = reportsDir
	}

	public Path getLatestTestSuiteReportDirOf(String testSuiteName) {
		Objects.requireNonNull(testSuiteName)
		ReportsDirVisitor visitor = new ReportsDirVisitor(testSuiteName)
		Files.walkFileTree(reportsDir, visitor)
		return visitor.getLatestTestSuiteReportDir()
	}


	/*
	 * 
	 */
	class ReportsDirVisitor extends SimpleFileVisitor<Path> {
		private String testSuiteName
		private List<Path> dirs

		public ReportsDirVisitor(String tsn) {
			Objects.requireNonNull(tsn)
			this.testSuiteName = tsn
			this.dirs = new ArrayList<Path>()
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
			if (e == null) {
				if (dir.getParent().getFileName().toString() == testSuiteName) {
					dirs.add(dir)
				}
				return FileVisitResult.CONTINUE
			} else {
				// directory iteration failed
				throw e;
			}
		}
		
		public Path getLatestTestSuiteReportDir() {
			if (dirs.size() > 0) {
				return dirs.get(dirs.size() - 1)
			} else {
				return null
			}
		}
	}
}
