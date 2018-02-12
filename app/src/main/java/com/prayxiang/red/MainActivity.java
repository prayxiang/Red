package com.prayxiang.red;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.prayxiang.red.service.Config;


public class MainActivity extends Activity {
    private static final Intent sSettingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    public static final String ACTION_RED_PACKET_HELPER = "com.idroi.redpackethelper.MainActivity";

    private SharedPreferences mPreferences;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (!checkRedPacketService(this)) {
            showDefaultNotification(getApplicationContext());

        }

//		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent("com.freeme.yellowpage.ACTION.VIEW");
//				startActivity(intent);
//			}
//		});
//
//		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this, FragmentPreferences.class);
//				startActivity(intent);
//			}
//		});

    }

    private void showDefaultNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, sSettingsIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notif = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("我的红包助手")
                .setContentText("我的红包助手运行中").setContentIntent(contentIntent).build();

        notif.defaults = Notification.DEFAULT_ALL;
        manager.notify(1, notif);
    }

    private boolean checkRedPacketService(Context context) {
        return Config.isAccessibilitySettingsOn(context);
    }


}
