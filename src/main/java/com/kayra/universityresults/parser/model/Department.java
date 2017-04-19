package com.kayra.universityresults.parser.model;

import com.kayra.universityresults.parser.constant.Scholarship;

public class Department {

	private int id;
	private University university;
	private Faculty faculty;
	private String name;
	private short quota;
	private short settled;
	private String scoreType;
	private double minPoint;
	private double maxPoint;
	private int succesSequence;
	private double topStudentOfSchoolMinPoint;
	private double topStudentOfSchoolMaxPoint;
	private boolean isNight = false;
	private boolean isEnglish = false;
	private boolean isMTOK = false;
	private Scholarship scholarship;
	private boolean isForKktc = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getQuota() {
		return quota;
	}

	public void setQuota(short quota) {
		this.quota = quota;
	}

	public short getSettled() {
		return settled;
	}

	public void setSettled(short settled) {
		this.settled = settled;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public double getMinPoint() {
		return minPoint;
	}

	public void setMinPoint(double minPoint) {
		this.minPoint = minPoint;
	}

	public double getMaxPoint() {
		return maxPoint;
	}

	public void setMaxPoint(double maxPoint) {
		this.maxPoint = maxPoint;
	}

	public int getSuccesSequence() {
		return succesSequence;
	}

	public void setSuccesSequence(int succesSequence) {
		this.succesSequence = succesSequence;
	}

	public double getTopStudentOfSchoolMinPoint() {
		return topStudentOfSchoolMinPoint;
	}

	public void setTopStudentOfSchoolMinPoint(double topStudentOfSchoolMinPoint) {
		this.topStudentOfSchoolMinPoint = topStudentOfSchoolMinPoint;
	}

	public double getTopStudentOfSchoolMaxPoint() {
		return topStudentOfSchoolMaxPoint;
	}

	public void setTopStudentOfSchoolMaxPoint(double topStudentOfSchoolMaxPoint) {
		this.topStudentOfSchoolMaxPoint = topStudentOfSchoolMaxPoint;
	}

	public boolean isNight() {
		return isNight;
	}

	public void setNight(boolean isNight) {
		this.isNight = isNight;
	}

	public boolean isEnglish() {
		return isEnglish;
	}

	public void setEnglish(boolean isEnglish) {
		this.isEnglish = isEnglish;
	}

	public boolean isMTOK() {
		return isMTOK;
	}

	public void setMTOK(boolean isMTOK) {
		this.isMTOK = isMTOK;
	}

	public Scholarship getScholarship() {
		return scholarship;
	}

	public void setScholarship(Scholarship scholarship) {
		this.scholarship = scholarship;
	}

	public boolean isForKktc() {
		return isForKktc;
	}

	public void setForKktc(boolean isForKktc) {
		this.isForKktc = isForKktc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + id;
		result = prime * result + (isEnglish ? 1231 : 1237);
		result = prime * result + (isForKktc ? 1231 : 1237);
		result = prime * result + (isMTOK ? 1231 : 1237);
		result = prime * result + (isNight ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(maxPoint);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minPoint);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + quota;
		result = prime * result + ((scholarship == null) ? 0 : scholarship.hashCode());
		result = prime * result + ((scoreType == null) ? 0 : scoreType.hashCode());
		result = prime * result + settled;
		result = prime * result + succesSequence;
		temp = Double.doubleToLongBits(topStudentOfSchoolMaxPoint);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(topStudentOfSchoolMinPoint);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((university == null) ? 0 : university.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (faculty == null) {
			if (other.faculty != null)
				return false;
		} else if (!faculty.equals(other.faculty))
			return false;
		if (id != other.id)
			return false;
		if (isEnglish != other.isEnglish)
			return false;
		if (isForKktc != other.isForKktc)
			return false;
		if (isMTOK != other.isMTOK)
			return false;
		if (isNight != other.isNight)
			return false;
		if (Double.doubleToLongBits(maxPoint) != Double.doubleToLongBits(other.maxPoint))
			return false;
		if (Double.doubleToLongBits(minPoint) != Double.doubleToLongBits(other.minPoint))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quota != other.quota)
			return false;
		if (scholarship != other.scholarship)
			return false;
		if (scoreType == null) {
			if (other.scoreType != null)
				return false;
		} else if (!scoreType.equals(other.scoreType))
			return false;
		if (settled != other.settled)
			return false;
		if (succesSequence != other.succesSequence)
			return false;
		if (Double.doubleToLongBits(topStudentOfSchoolMaxPoint) != Double.doubleToLongBits(other.topStudentOfSchoolMaxPoint))
			return false;
		if (Double.doubleToLongBits(topStudentOfSchoolMinPoint) != Double.doubleToLongBits(other.topStudentOfSchoolMinPoint))
			return false;
		if (university == null) {
			if (other.university != null)
				return false;
		} else if (!university.equals(other.university))
			return false;
		return true;
	}

}
