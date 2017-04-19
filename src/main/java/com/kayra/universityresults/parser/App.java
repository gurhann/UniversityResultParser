package com.kayra.universityresults.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.kayra.universityresults.parser.constant.RowType;
import com.kayra.universityresults.parser.model.City;
import com.kayra.universityresults.parser.model.Department;
import com.kayra.universityresults.parser.model.Faculty;
import com.kayra.universityresults.parser.model.University;
import com.kayra.universityresults.parser.service.ExcelParser;
import com.kayra.universityresults.parser.service.ExcelParserImpl;

public class App {

	private ExcelParser parser;
	private Sheet sheet;

	private App(ExcelParser parser, Sheet sheet) {
		this.parser = parser;
		this.sheet = sheet;
	}

	private void readDocument() {

		List<University> universityList = new ArrayList<University>();
		List<Faculty> facultyList = new ArrayList<Faculty>();
		List<Department> departmentList = new ArrayList<Department>();
		List<City> cityList = new ArrayList<City>();

		University currentUniversity = null;
		Faculty currentFaculty = null;

		for (int i = 2; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			RowType rowType = parser.getRowType(row);
			switch (rowType) {
			case UNIVERSITY:
				currentUniversity = parser.getUniversity(row);
				City city = parser.getCity(row);
				currentUniversity.setCity(city);
				currentUniversity.setPrivate(checkPrivateUniversity(i));
				universityList.add(currentUniversity);
				if (!cityList.contains(city)) {
					cityList.add(city);
				}
				break;
			case FACULTY:
				currentFaculty = parser.getFaculty(row);
				if (!facultyList.contains(currentFaculty)) {
					facultyList.add(currentFaculty);
				}
				break;
			case DEPARTMENT:
				Department department = parser.createDepartment(row);
				department.setFaculty(currentFaculty);
				department.setUniversity(currentUniversity);
				departmentList.add(department);
				break;
			default:
				throw new RuntimeException();
			}
		}
	}

	private boolean checkPrivateUniversity(int i) {
		int j = i + 1;
		while (j - i < 5) {
			if (RowType.DEPARTMENT == parser.getRowType(sheet.getRow(j))) {
				break;
			}
			j++;

		}
		return parser.checkPrivateUniversity(sheet.getRow(j));
	}

	public static void main(String[] args) {
		try {
			FileInputStream excelFile = new FileInputStream(new File(App.class.getClassLoader().getResource("Bolum-1_26102016.xlsx").getFile()));
			Workbook workbook = WorkbookFactory.create(excelFile);
			Sheet sheet = workbook.getSheetAt(0);
			App app = new App(new ExcelParserImpl(), sheet);
			app.readDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
