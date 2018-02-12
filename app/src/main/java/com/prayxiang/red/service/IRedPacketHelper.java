package com.prayxiang.red.service;

import android.view.accessibility.AccessibilityEvent;

public interface IRedPacketHelper {

    String getTargetPacketName();

    void onReceive(AccessibilityEvent event);
}
