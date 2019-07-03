package com.example.android.myhealth.ui.patients.mFragments;

//TODO: to be removed from here
public class appointments_data {

	private String mdoctor;
	private String mdate;
	private String mtime;

	public appointments_data(String mdoctor, String mdate, String mtime) {
		this.mdoctor = mdoctor;
		this.mdate = mdate;
		this.mtime = mtime;
	}

	public String getmdoctor() {
		return mdoctor;
	}

	public String getmdate() {
		return mdate;
	}

	public String getmtime() {
		return mtime;
	}
}
