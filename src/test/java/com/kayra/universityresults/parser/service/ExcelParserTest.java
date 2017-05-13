package com.kayra.universityresults.parser.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kayra.universityresults.parser.constant.RowType;
import com.kayra.universityresults.parser.model.Department;

public class ExcelParserTest {

	public static Sheet sheet;
	public static ExcelParser parser;

	@BeforeClass
	public static void init() throws IOException, InvalidFormatException {
		FileInputStream excelFile = new FileInputStream(new File(ExcelParserTest.class.getClassLoader().getResource("sample.xlsx").getFile()));
		Workbook workbook = WorkbookFactory.create(excelFile);
		sheet = workbook.getSheetAt(0);
		parser = new ExcelParserImpl();
	}

	@Test
	public void universityRowTypeTest() {
		assertEquals(RowType.UNIVERSITY, parser.getRowType(sheet.getRow(0)));
	}

	@Test
	public void epartmentRowTypeTest() {
		assertEquals(RowType.DEPARTMENT, parser.getRowType(sheet.getRow(1)));
		assertEquals(RowType.DEPARTMENT, parser.getRowType(sheet.getRow(2)));
	}

	@Test
	public void facultyRowTypeTest() {
		assertEquals(RowType.FACULTY, parser.getRowType(sheet.getRow(3)));
	}

	@Test
	public void uniNameAndCityTest() {
		assertEquals("BOLU", parser.getCity(sheet.getRow(0)).getName());
		assertEquals("ABANT İZZET BAYSAL ÜNİVERSİTESİ", parser.getUniversity(sheet.getRow(0)).getName());
	}

	@Test
	public void facultynameTest() {
		assertEquals("Eğitim Fakültesi", parser.getFaculty(sheet.getRow(3)).getName());
	}

	@Test
	public void standartDepartmentTest() {
		Department department = parser.createDepartment(sheet.getRow(1));
		Department createdDep = new Department();

		createdDep.setId(100110433);
		createdDep.setName("Diş Hekimliği Fakültesi");
		createdDep.setQuota((short) 93);
		createdDep.setSettled((short) 93);
		createdDep.setScoreType("MF-3");
		createdDep.setMinPoint(454.61758);
		createdDep.setMaxPoint(464.33131);
		createdDep.setSuccesSequence(19100);
		createdDep.setTopStudentOfSchoolMinPoint(452.80088);
		createdDep.setTopStudentOfSchoolMaxPoint(454.09851);

		assertEquals(createdDep, department);
	}

	@Test
	public void englishDepartmentTest() {
		Department department = parser.createDepartment(sheet.getRow(5));
		Department createdDep = new Department();

		createdDep.setId(100110027);
		createdDep.setName("Fen Bilgisi Öğretmenliği (İngilizce)");
		createdDep.setQuota((short) 63);
		createdDep.setSettled((short) 62);
		createdDep.setScoreType("MF-2");
		createdDep.setMinPoint(259.16387);
		createdDep.setMaxPoint(342.07550);
		createdDep.setSuccesSequence(200000);
		createdDep.setEnglish(true);
		createdDep.setTopStudentOfSchoolMinPoint(-1);
		createdDep.setTopStudentOfSchoolMaxPoint(-1);
		
		assertEquals(createdDep, department);

	}

	@Test
	public void mtokAndNightDepartmentTest() {
		Department department = parser.createDepartment(sheet.getRow(11));
		Department createdDep = new Department();

		createdDep.setId(100430479);
		createdDep.setName("Elektrik-Elektronik Mühendisliği (M.T.O.K.) (İÖ)");
		createdDep.setQuota((short) 13);
		createdDep.setSettled((short) 13);
		createdDep.setScoreType("MF-4");
		createdDep.setMinPoint(247.76146);
		createdDep.setMaxPoint(258.69823);
		createdDep.setSuccesSequence(222000);
		createdDep.setNight(true);
		createdDep.setMTOK(true);
		createdDep.setTopStudentOfSchoolMinPoint(-1);
		createdDep.setTopStudentOfSchoolMaxPoint(-1);
		assertEquals(createdDep, department);
	}

	@Test
	public void privateDepartmentTest() {

		assertEquals(false, parser.checkPrivateUniversity(sheet.getRow(13)));
		assertEquals(true, parser.checkPrivateUniversity(sheet.getRow(16)));
		assertEquals(true, parser.checkPrivateUniversity(sheet.getRow(17)));
		assertEquals(true, parser.checkPrivateUniversity(sheet.getRow(18)));
		assertEquals(true, parser.checkPrivateUniversity(sheet.getRow(19)));
	}

	@Test
	public void departmentForKktcTest() {
		Department department = parser.createDepartment(sheet.getRow(2));
		Department createdDep = new Department();

		createdDep.setId(100110609);
		createdDep.setName("Diş Hekimliği Fakültesi (KKTC Uyruklu)");
		createdDep.setQuota((short) 1);
		createdDep.setScoreType("MF-3");
		createdDep.setMaxPoint(-1.0);
		createdDep.setMinPoint(-1.0);
		createdDep.setTopStudentOfSchoolMinPoint(-1);
		createdDep.setTopStudentOfSchoolMaxPoint(-1);
		createdDep.setForKktc(true);
		assertEquals(createdDep, department);
	}
}
