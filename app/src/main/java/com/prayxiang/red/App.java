package com.prayxiang.red;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by xianggaofeng on 2018/2/11.
 */

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
