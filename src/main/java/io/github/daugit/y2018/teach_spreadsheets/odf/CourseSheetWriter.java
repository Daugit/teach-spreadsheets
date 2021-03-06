package io.github.daugit.y2018.teach_spreadsheets.odf;

import java.io.OutputStream;
import java.util.List;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.daugit.y2018.teach_spreadsheets.courses.CourseSheet;

public class CourseSheetWriter {

	private final static Logger LOGGER = LoggerFactory.getLogger(CourseSheetWriter.class);

	public static void writeCourseSheets(SpreadsheetDocument spreadsheetDocument, OutputStream destination,
			List<CourseSheet> courseSheets, boolean save) throws Exception {

		LOGGER.info("Starting the writing of courses Sheets");
		for (CourseSheet courseSheet : courseSheets) {
			writeCourseSheet(spreadsheetDocument, destination, courseSheet, false);
		}
		if (save) {
			spreadsheetDocument.save(destination);
		}
		LOGGER.info("The writing of courses Sheets terminated successfully");

	}

	private static void writeCourseSheet(SpreadsheetDocument spreadsheetDocument, OutputStream destination,
			CourseSheet courseSheet, boolean save) throws Exception {
		CourseWriter writer = new CourseWriter(spreadsheetDocument, destination, courseSheet);
		writer.writeCoursesOfYear(save);

		PreferenceWriter prefWriter = new PreferenceWriter(spreadsheetDocument, destination, courseSheet);
		prefWriter.writeSheetCoursesPref(save);

		LOGGER.info("Course sheet " + courseSheet.getYearOfStud() + " has been writen");

	}
}
