package com.example.android.myhealth.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.android.myhealth.ui.sendbird.utils.PreferenceUtils;
import com.facebook.stetho.Stetho;
import com.sendbird.android.SendBird;

public class BaseApplication extends Application {

    public static final String VERSION = "3.0.40";
    private static final String APP_ID = "996BCC20-975F-4CE9-83E0-7FC58DF51B01"; // Health-Keep ID

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        PreferenceUtils.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}