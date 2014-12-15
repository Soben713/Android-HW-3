package com.example.androidhw3.solarCalendar;

public enum ShamsiMonthEnum {
	Farvardin("فروردین"),

	Ordibehesht("اردیبهشت"),

	Khordad("خرداد"),

	Tir("تیر"),

	Mordad("مرداد"),

	Shahrivar("شهریور"),

	Mehr("مهر"),

	Aban("آبان"),

	Azar("آذر"),

	Dey("دی"),

	Bahman("بهمن"),

	Esfand("اسفند");

	private String faName;

	private ShamsiMonthEnum(String faName) {
		this.faName = faName;
	}

	public String getFaName() {
		return faName;
	}
}
