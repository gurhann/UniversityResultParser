package com.kayra.universityresults.parser.constant;

public enum Scholarship {
	FULL("(Tam Burslu)"), FIFTY("(%50 Burslu)"), TWENTY_FIVE("(%75 Burslu)"), NO_SCHOLARSHIP("(Ücretli)");

	private String desc;

	private Scholarship(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
