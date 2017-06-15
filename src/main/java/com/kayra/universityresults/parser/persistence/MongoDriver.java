package com.kayra.universityresults.parser.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;

import com.kayra.universityresults.parser.constant.InfoType;
import com.kayra.universityresults.parser.model.Department;
import com.kayra.universityresults.parser.model.University;
import com.kayra.universityresults.parser.util.StringUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDriver {
	private static MongoDriver instance;
	private MongoDatabase db;

	@SuppressWarnings("resource")
	private MongoDriver() {
		Properties prop = getProperties();
		db = new MongoClient(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port"))).getDatabase(prop.getProperty("databaseName"));
		db.getCollection("departments").drop();
		db.getCollection("ext_infos").drop();
	}

	public static MongoDriver getInstance() {
		if (instance == null) {
			instance = new MongoDriver();
		}
		return instance;
	}

	private Properties getProperties() {
		Properties prop = new Properties();
		InputStream input = MongoDriver.class.getClassLoader().getResourceAsStream("mongo.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public void insertDepartment(Department department) {
		MongoCollection<Document> departmentCollection = db.getCollection("departments");
		try {
			University university = department.getUniversity();
			Document universityDoc = new Document("name", university.getName()).append("city", university.getCity().getName()).append("isPrivate", university.isPrivate());
			String facultyName = department.getFaculty() != null ? department.getFaculty().getName() : null;
			//@formatter:off
			Document departmentDoc = new Document("name", department.getName())
					.append("university", universityDoc)
					.append("faculty_name", facultyName)
					.append("quota", department.getQuota())
					.append("settled", department.getSettled())
					.append("score_type", department.getScoreType())
					.append("min_point", department.getMinPoint())
					.append("max_point", department.getMaxPoint())
					.append("success_sequence", department.getSuccesSequence())
					.append("top_students_of_school_min_point", department.getTopStudentOfSchoolMinPoint())
					.append("top_students_of_school_max_point", department.getTopStudentOfSchoolMaxPoint())
					.append("is_night", department.isNight())
					.append("is_english", department.isEnglish())
					.append("is_mtok", department.isMTOK())
					.append("scholarship", StringUtil.toString(department.getScholarship()))
					.append("is_for_kktc", department.isForKktc());
			//@formatter:on
			departmentCollection.insertOne(departmentDoc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception when adding department:" + department);

		}
	}

	public void insertExtInfo(Object obj, InfoType infoType) {
		MongoCollection<Document> extInfoCollection = db.getCollection("ext_infos");
		Document doc = new Document();
		try {
			switch (infoType) {
			case UNIVERSITY:
				University university = (University) obj;
				Document universityDoc = new Document("name", university.getName()).append("city", university.getCity().getName()).append("isPrivate", university.isPrivate());
				doc.append("type", "university").append("value", universityDoc);
				break;
			case CITY:
				doc.append("type", "city").append("value", (String) obj);
				break;
			case FACULTY:
				doc.append("type", "faculty").append("value", (String) obj);
				break;
			case SCORE_TYPE:
				doc.append("type", "scoreType").append("value", (String) obj);
				break;
			default:
				break;
			}
			extInfoCollection.insertOne(doc);
		} catch (Exception e) {
			e.printStackTrace();
			String exceptionVal = infoType == InfoType.UNIVERSITY ? ((University) obj).toString() : (String) obj;
			throw new RuntimeException("Exception when adding extInfo" + exceptionVal);
		}
	}
}
