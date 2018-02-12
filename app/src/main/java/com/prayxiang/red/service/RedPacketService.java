package com.prayxiang.red.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;


import com.prayxiang.red.MainActivity;
import com.prayxiang.red.R;
import com.prayxiang.red.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class RedPacketService extends AccessibilityService {

    private static final Intent sIntent = new Intent(MainActivity.ACTION_RED_PACKET_HELPER);

    private List<IRedPacketHelper> mPacketHelpers = new ArrayList<IRedPacketHelper>();
    private NotificationManager mNotificationManager;

    @Override
    protected void onServiceConnected() {

        mPacketHelpers.add(new WeiChatHelper(this));
        mPacketHelpers.add(new QQHelper(this));

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (mNotificationManager != null) {
            mNotificationManager.cancel(1);
        }
        Notification notification = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("我的红包助手")
                .setContentText("我的红包助手运行中").setContentIntent(makeMoodIntent()).build();
        startForeground(1, notification);

    }

    private PendingIntent makeMoodIntent() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, sIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return contentIntent;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(event);
        String pkn = String.valueOf(event.getPackageName());
        for (IRedPacketHelper helper : mPacketHelpers) {
            if (helper.getTargetPacketName().equals(pkn)) {
                helper.onReceive(event);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopForeground(true);
        return super.onUnbind(intent);
    }

}
