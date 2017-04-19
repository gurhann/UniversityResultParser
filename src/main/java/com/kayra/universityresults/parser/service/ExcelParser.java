package com.kayra.universityresults.parser.service;

import org.apache.poi.ss.usermodel.Row;

import com.kayra.universityresults.parser.constant.RowType;
import com.kayra.universityresults.parser.model.City;
import com.kayra.universityresults.parser.model.Department;
import com.kayra.universityresults.parser.model.Faculty;
import com.kayra.universityresults.parser.model.University;

public interface ExcelParser {

	public RowType getRowType(Row row);

	public City getCity(Row row);

	public University getUniversity(Row row);
	
	public Faculty getFaculty(Row row);
	
	public Department createDepartment(Row row);

	boolean checkPrivateUniversity(Row row);

	
}
