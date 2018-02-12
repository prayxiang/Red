package com.prayxiang.red.utils;

import android.view.accessibility.AccessibilityEvent;

/**
 * Created by xianggaofeng on 2018/2/11.
 */

public class Anchor {
    static Anchor anchor = new Anchor();

    public static Anchor get() {
        return anchor;
    }

    public int eventType;
    public String className;
    public String pkg;
    public AccessibilityEvent event;


    public void track(AccessibilityEvent event) {
        eventType = event.getEventType();
        className = event.getClassName().toString();
        pkg = event.getPackageName().toString();
        this.event = event;
    }

}
