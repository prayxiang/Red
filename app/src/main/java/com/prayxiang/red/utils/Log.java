package com.prayxiang.red.utils;


import android.view.accessibility.AccessibilityEvent;

import com.prayxiang.red.service.Config;

public class Log {

    public static void i(String string) {

        if (Config.DEBUG) {
            android.util.Log.d(Config.DEBUG_TAG, String.valueOf(string));
        }

    }

    public static void d(AccessibilityEvent event) {

        if (Config.DEBUG) {
            int eventType = event.getEventType();
            String pkn = String.valueOf(event.getPackageName());

            Log.i("[pkn=]" + pkn);
            Log.i("[type]" + eventType);
            Log.i("[class=]" + event.getClassName().toString());
            Log.i(event.toString());
        }

    }
}
