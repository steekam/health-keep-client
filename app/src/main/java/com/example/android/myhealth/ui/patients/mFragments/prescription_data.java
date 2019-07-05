package com.example.android.myhealth.ui.patients.mFragments;

public class prescription_data {

    private String mhour;
    private String mmedicine;
    private String mdosage;

    public prescription_data(String mhour, String mmedicine, String mdosage) {
        this.mhour  = mhour;
        this.mmedicine = mmedicine;
        this.mdosage = mdosage;
    }

    public String getmhour() {
        return mhour;
    }

    public String getmmedicine() {
        return mmedicine;
    }

    public String getmdosage() {
        return mdosage;
    }
}
