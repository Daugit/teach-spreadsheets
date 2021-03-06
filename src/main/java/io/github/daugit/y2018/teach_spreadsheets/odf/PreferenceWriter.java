package io.github.daugit.y2018.teach_spreadsheets.odf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import io.github.daugit.y2018.teach_spreadsheets.courses.Choice;
import io.github.daugit.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.daugit.y2018.teach_spreadsheets.courses.CourseSheet;

public class PreferenceWriter {

	/**
	 * Assuming that the tables of coursePref starts at cells STARTCELL1 and
	 * STARTCELL2 for each sheet
	 */
	private final static String START_CELL_1 = "J4";
	/**
	 * Assuming that the tables of coursePref starts at cells STARTCELL1 and
	 * STARTCELL2 for each sheet
	 */
	private final static String START_CELL_2 = "X4";

	private OutputStream destination = null;
	private SpreadsheetDocument spreadsheetDocument = null;
	private CourseSheet courseSheet = null;

	public PreferenceWriter(SpreadsheetDocument spreadsheetDocument, OutputStream destination, CourseSheet courseSheet)
			throws Exception {
		this.destination = Objects.requireNonNull(destination);
		this.spreadsheetDocument = spreadsheetDocument;
		this.courseSheet = Objects.requireNonNull(courseSheet);
	}

	/**
	 * This method writes the courses preferences for one semester
	 * 
	 * @param coursesPref
	 *            is a list of {@link CoursePref} that will be fill in the
	 *            spreadsheet
	 * @param startCellPosition
	 *            the starting position of the array of {@link CoursePref}, of one
	 *            semester
	 * @param save
	 */
	private void writeSemesterCoursesPref(List<CoursePref> coursesPref, String startCellPosition, boolean save)
			throws Exception {

		// Design handling
		Border border = new Border(Color.BLACK, 1, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Table sheet = this.spreadsheetDocument.getSheetByName(courseSheet.getSheetMetadata().getYearOfStud());

		Cell currentCell = sheet.getCellByPosition(startCellPosition);

		int startCellColumnIndex = currentCell.getColumnIndex();
		int currentRow = currentCell.getRowIndex();

		// Writing coursePref by row
		for (CoursePref coursePref : coursesPref) {
			// CM Choice
			currentCell = sheet.getCellByPosition(startCellColumnIndex, currentRow);
			if (coursePref.getCmChoice() == Choice.NA) {
				currentCell.setBorders(CellBordersType.DIAGONALBLTR, border);
			} else if (coursePref.getCmChoice() == Choice.ABSENT) {
				currentCell.setStringValue("");
			} else {
				currentCell.setStringValue(coursePref.getCmChoice().toString());
			}

			// TD Choice
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 1, currentRow);
			if (coursePref.getTdChoice() == Choice.NA) {
				currentCell.setBorders(CellBordersType.DIAGONALBLTR, border);

			} else if (coursePref.getTdChoice() == Choice.ABSENT) {
				currentCell.setStringValue("");
			} else {
				currentCell.setStringValue(coursePref.getTdChoice().toString());
			}

			// TP Choice
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 2, currentRow);
			if (coursePref.getTpChoice() == Choice.NA) {
				currentCell.setBorders(CellBordersType.DIAGONALBLTR, border);

			} else if (coursePref.getTpChoice() == Choice.ABSENT) {
				currentCell.setStringValue("");
			} else {
				currentCell.setStringValue(coursePref.getTpChoice().toString());
			}

			// Groups number
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 3, currentRow);
			currentCell.setStringValue("CM : " + coursePref.getNbrGrpCm() + ", TD : " + coursePref.getNbrGrpTd()
					+ ", TP : " + coursePref.getNbrGrpTp());

			// Experience
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 4, currentRow);
			currentCell.setStringValue(String.valueOf(coursePref.getNbrExp()));

			currentRow++;

		}

		if (save)

		{
			this.spreadsheetDocument.save(this.destination);
		}

	}

	/**
	 * This method writes all the course preferences of a sheet for both semester.
	 */
	public void writeSheetCoursesPref(boolean save) throws Exception {

		writeSemesterCoursesPref(courseSheet.getCoursePrefS1(), START_CELL_1, save);
		writeSemesterCoursesPref(courseSheet.getCoursePrefS2(), START_CELL_2, save);

	}

	public void close() throws IOException {
		this.spreadsheetDocument.close();
		this.destination.close();
	}
}
