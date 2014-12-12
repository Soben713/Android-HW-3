package com.example.androidhw3;

import com.orm.SugarContext;

import android.app.Application;

public class AndroidHW3App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
