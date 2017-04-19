package com.kayra.universityresults.parser.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.kayra.universityresults.parser.constant.AppConstant;
import com.kayra.universityresults.parser.constant.DepartmentColumn;
import com.kayra.universityresults.parser.constant.RowType;
import com.kayra.universityresults.parser.constant.Scholarship;
import com.kayra.universityresults.parser.model.City;
import com.kayra.universityresults.parser.model.Department;
import com.kayra.universityresults.parser.model.Faculty;
import com.kayra.universityresults.parser.model.University;

public class ExcelParserImpl implements ExcelParser {

	public RowType getRowType(Row row) {
		String val = row.getCell(0).getStringCellValue();
		if (StringUtils.isBlank(val)) {
			return RowType.FACULTY;
		}

		if (StringUtils.isNumeric(val)) {
			return RowType.DEPARTMENT;
		}

		return RowType.UNIVERSITY;
	}

	public City getCity(Row row) {
		String val = row.getCell(0).getStringCellValue().trim();
		String cityName = "";
		if (val.contains("(")) {
			cityName = val.substring(val.indexOf("(") + 1, val.indexOf(")"));
		} else {
			cityName = val.split(" ")[0];
		}
		return new City(cityName);
	}

	public University getUniversity(Row row) {
		String val = row.getCell(0).getStringCellValue().trim();
		String uniName = "";
		if (val.contains("(")) {
			uniName = val.substring(0, val.indexOf("(") - 1);
		} else {
			uniName = val;
		}
		return new University(uniName);
	}

	public Department createDepartment(Row row) {
		Department department = new Department();
		department.setId(NumberUtils.toInt(row.getCell(DepartmentColumn.ID.ordinal()).getStringCellValue(), AppConstant.NULL_NUMBER_COLUMN));
		department.setName(row.getCell(DepartmentColumn.NAME.ordinal()).getStringCellValue());
		department.setQuota((short) getNumericIntVal(row.getCell(DepartmentColumn.QUOTA.ordinal()), AppConstant.ZERO_NUMBER_COLUMN));
		department.setSettled((short) getNumericIntVal(row.getCell(DepartmentColumn.SETTLED.ordinal()), AppConstant.ZERO_NUMBER_COLUMN));
		department.setScoreType(row.getCell(DepartmentColumn.SCORE_TYPE.ordinal()).getStringCellValue());
		department.setMinPoint(getNumericDoubleVal(row.getCell(DepartmentColumn.MIN_POINT.ordinal()), AppConstant.NULL_NUMBER_COLUMN));
		department.setSuccesSequence(getNumericIntVal(row.getCell(DepartmentColumn.SUCCESS_SEQUENCE.ordinal()), AppConstant.NULL_NUMBER_COLUMN));
		department.setMaxPoint(getNumericDoubleVal(row.getCell(DepartmentColumn.MAX_POINT.ordinal()), AppConstant.NULL_NUMBER_COLUMN));
		department.setTopStudentOfSchoolMinPoint(getNumericDoubleVal(row.getCell(DepartmentColumn.TOP_STUDENT_OF_SCHOOL_MIN_POINT.ordinal()), AppConstant.NULL_NUMBER_COLUMN));
		department.setTopStudentOfSchoolMaxPoint(getNumericDoubleVal(row.getCell(DepartmentColumn.TOP_STUDENT_OF_SCHOOL_MAX_POINT.ordinal()), AppConstant.NULL_NUMBER_COLUMN));
		parseDepartmentName(department);
		checkEnglishDepartmentTest(department);
		checkKktcDepartment(department);
		checkMtok(department);
		checkNight(department);
		return department;
	}

	public boolean checkPrivateUniversity(Row row) {
		String val = row.getCell(1).getStringCellValue().trim();
		if (val.contains(Scholarship.FIFTY.getDesc()) || val.contains(Scholarship.FULL.getDesc()) || val.contains(Scholarship.NO_SCHOLARSHIP.getDesc())
				|| val.contains(Scholarship.TWENTY_FIVE.getDesc())) {
			return true;
		}
		return false;
	}

	public Faculty getFaculty(Row row) {
		String val = row.getCell(1).getStringCellValue().trim();
		return new Faculty(val);
	}

	private int getNumericIntVal(Cell cell, int defaultVal) {
		if (cell == null) {
			return defaultVal;
		}
		return (int) cell.getNumericCellValue();
	}

	private double getNumericDoubleVal(Cell cell, int defaultVal) {
		if (cell == null) {
			return defaultVal;
		}

		return NumberUtils.toDouble(cell.getStringCellValue().replaceAll(",", "."), defaultVal);
	}

	private void checkEnglishDepartmentTest(Department department) {
		if (department.getName().contains("(İngilizce)")) {
			department.setEnglish(true);
		}
	}

	private void parseDepartmentName(Department department) {
		String name = department.getName();
		if (name.contains(AppConstant.ENGLISH_DEPARTMENT)) {
			department.setEnglish(true);
		}
	}

	private void checkKktcDepartment(Department department) {
		if (department.getName().contains("(KKTC Uyruklu)")) {
			department.setForKktc(true);
		}
	}

	private void checkMtok(Department department) {
		if (department.getName().contains("(M.T.O.K.)")) {
			department.setMTOK(true);
		}
	}

	private void checkNight(Department department) {
		if (department.getName().contains("(İ.Ö)")) {
			department.setNight(true);
		}
	}
}
