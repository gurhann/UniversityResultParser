package com.kayra.universityresults.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.kayra.universityresults.parser.constant.InfoType;
import com.kayra.universityresults.parser.constant.RowType;
import com.kayra.universityresults.parser.model.City;
import com.kayra.universityresults.parser.model.Department;
import com.kayra.universityresults.parser.model.Faculty;
import com.kayra.universityresults.parser.model.University;
import com.kayra.universityresults.parser.persistence.MongoDriver;
import com.kayra.universityresults.parser.service.ExcelParser;
import com.kayra.universityresults.parser.service.ExcelParserImpl;

public class App {

	private ExcelParser parser;
	private Sheet sheet;

	private List<University> universityList = new ArrayList<University>();
	private List<Faculty> facultyList = new ArrayList<Faculty>();
	private List<Department> departmentList = new ArrayList<Department>();
	private List<City> cityList = new ArrayList<City>();
	private List<String> scoreTypesList = new ArrayList<String>();

	private App(ExcelParser parser, Sheet sheet) {
		this.parser = parser;
		this.sheet = sheet;
	}

	private void readDocument() {

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
				if (!scoreTypesList.contains(department.getScoreType())) {
					scoreTypesList.add(department.getScoreType());
				}
				break;
			default:
				throw new RuntimeException();
			}
		}

	}

	private void insertToDB() {
		MongoDriver instance = MongoDriver.getInstance();

		departmentList.stream().forEach((department) -> {
			instance.insertDepartment(department);
		});

		universityList.stream().forEach((university) -> {
			instance.insertExtInfo(university, InfoType.UNIVERSITY);
		});

		cityList.stream().forEach((city) -> {
			instance.insertExtInfo(city.getName(), InfoType.CITY);
		});

		facultyList.stream().forEach((faculty) -> {
			instance.insertExtInfo(faculty.getName(), InfoType.FACULTY);
		});

		scoreTypesList.stream().forEach((scoreType) -> {
			instance.insertExtInfo(scoreType, InfoType.SCORE_TYPE);
		});

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
			app.insertToDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
