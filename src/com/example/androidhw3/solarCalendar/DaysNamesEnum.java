package com.example.androidhw3.solarCalendar;

public enum DaysNamesEnum {
	Monday("دو شنبه", "Monday"),

	Tuesday("سه شنبه", "Tuesday"),

	Wednesday("چهار شنبه", "Wednesday"),

	Thursday("پنج شنبه", "Thursday"),

	Friday("جمعه", "Friday"),

	Saturday("شنبه", "Saturday"),

	Sunday("یک شنبه", "Sunday");

	private String faName;
	private String enName;

	private DaysNamesEnum(String faName, String enName) {
		this.faName = faName;
		this.enName = enName;
	}

	public String getFaName() {
		return faName;
	}

	public String getEnName() {
		return enName;
	}
}
