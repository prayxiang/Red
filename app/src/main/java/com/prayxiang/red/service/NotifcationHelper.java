package com.prayxiang.red.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.prayxiang.red.R;


public class NotifcationHelper {
	private NotifcationHelper mNotifcationHelper;
	private static NotificationManager mNotificationManager;

	private static final Intent sSettingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
	public static final String ACTION_RED_PACKET_HELPER = "com.idroi.redpackethelper.MainActivity";

	private NotifcationHelper() {

	}

	static void showDefaultNotification(Context context) {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, sSettingsIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Notification notif = new Notification.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("�ף������������").setContentText("��������").setAutoCancel(true).setContentIntent(contentIntent)
				.build();
		notif.defaults = Notification.DEFAULT_ALL;
		manager.notify(1, notif);
	}

}
