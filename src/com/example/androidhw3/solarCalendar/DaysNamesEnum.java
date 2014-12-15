package com.example.androidhw3.solarCalendar;

public enum DaysNamesEnum {
	Monday("دوشنبه", "Monday"),

	Tuesday("سه‌شنبه", "Tuesday"),

	Wednesday("چهارشنبه", "Wednesday"),

	Thursday("پنج‌شنبه", "Thursday"),

	Friday("جمعه", "Friday"),

	Saturday("شنبه", "Saturday"),

	Sunday("یکشنبه", "Sunday");

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
