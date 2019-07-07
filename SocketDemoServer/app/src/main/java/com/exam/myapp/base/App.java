package com.exam.myapp.base;

import android.app.Application;
import android.content.Context;

import com.exam.myapp.R;
import com.wuxiaolong.androidutils.library.LogUtil;

public class App extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();
        LogUtil.setTag(getString(R.string.app_name));
        LogUtil.i("App - onCreated!");
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
