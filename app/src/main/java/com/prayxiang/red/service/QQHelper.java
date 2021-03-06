package com.prayxiang.red.service;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


import com.prayxiang.red.R;
import com.prayxiang.red.utils.Log;
import com.prayxiang.red.utils.PowerManagerWakeLock;

import java.util.List;

public class QQHelper implements IRedPacketHelper {
	private Context mContext;
	private static final String TALK_LIST_LAUNCHER_UI = "com.tencent.mm.ui.LauncherUI";
	private static final String LUCKY_MONEY_RECEIVE_UI = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI";
	private static final String LUCKY_MONEY_DETAIL_UI = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";

	private Handler mHandler;

	private IndicateHelper mIndicateHelper = new IndicateHelper();

	class What {

		public static final int ACTION_CLICK = 0x00000010;
		public static final int ACTION_PASTE = 0x00008000;
		public static final int ACTION_FOCUS = 0x00000001;
		public static final int TRY_OPEN_PACKET_UI = 0;
		public static final int TRY_OPEN_PACKET_UI_FAIL = 3;
		public static final int OPEN_PACKET = 1;
		public static final int CLOSE_PACKET = 2;
	}

	public QQHelper(Context context) {
		mContext = context;
		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				AccessibilityNodeInfo info = (AccessibilityNodeInfo) msg.obj;

				switch (msg.what) {
				case AccessibilityNodeInfo.ACTION_CLICK:
					info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					break;
				case AccessibilityNodeInfo.ACTION_FOCUS:

					info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
					break;
				case AccessibilityNodeInfo.ACTION_PASTE:

					info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
					break;
				case What.TRY_OPEN_PACKET_UI:

					break;

				case What.TRY_OPEN_PACKET_UI_FAIL:

					backRedHelperUI();
					break;
				default:
					break;
				}

			};
		};

	}

	@Override
	public String getTargetPacketName() {

		return Config.PACKAGE_WEICHAT;
	}

	@Override
	public void onReceive(AccessibilityEvent event) {
		int eventType = event.getEventType();
		String pkn = String.valueOf(event.getPackageName());

		Log.i("[pkn=]" + pkn);
		Log.i("[type]" + eventType);
		Log.i("[class=]" + event.getClassName().toString());
		Log.i(event.toString());
		switch (eventType) {
		case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
			provideNotificationStateChangedAction(event);
			break;
		case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
			provideWindowStateChangedAction(event);
			break;
		case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
			// provideWindowContentChangedAction(event);
			break;
		case AccessibilityEvent.TYPE_VIEW_SCROLLED:
			break;

		default:

			break;
		}

	}

	private void provideNotificationStateChangedAction(AccessibilityEvent event) {

		List<CharSequence> texts = event.getText();
		if (!texts.isEmpty()) {
			for (CharSequence t : texts) {
				String text = String.valueOf(t);
				if (text.contains("QQ���")) {
					if (openNotify(event) == 1) {
						mIndicateHelper.setCanRetrieve(true);
						mIndicateHelper.setRunning(true);
					}
					break;
				}
			}
		}
	}

	private void provideWindowStateChangedAction(AccessibilityEvent event) {

		if ((event.getClassName().toString()).equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
			AccessibilityNodeInfo rootNode = ((AccessibilityService) mContext).getRootInActiveWindow();
			openRedPacket(rootNode);
		}
		if ((event.getClassName().toString()).equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
			AccessibilityNodeInfo rootNode = ((AccessibilityService) mContext).getRootInActiveWindow();

			closeRedPacket(rootNode);
		}

//		if ((event.getClassName().toString()).equals("com.tencent.mm.ui.LauncherUI")) {
//			AccessibilityNodeInfo rootNode = ((AccessibilityService) mContext).getRootInActiveWindow();
//			recycle(rootNode, "΢�ź��");
//
//			Message msg = new Message();
//			msg.obj = rootNode;
//
//			if (mIndicateHelper.isCanRetrieve()) {
//
//				msg.what = What.TRY_OPEN_PACKET_UI_FAIL;
//				mHandler.sendMessageDelayed(msg, 5000);
//			} else {
//				msg.what = What.TRY_OPEN_PACKET_UI;
//				mHandler.sendMessageDelayed(msg, 5000);
//			}
//
//		}

	}

	private void provideViewScrolledAction(AccessibilityEvent event) {

	}

	private void provideWindowContentChangedAction(AccessibilityEvent event) {

		// if
		// ((event.getClassName().toString()).equals("android.widget.ListView"))
		// {
		// if (!IsCanGrab) {
		// AccessibilityNodeInfo rootNode = ((AccessibilityService)
		// mContext).getRootInActiveWindow();
		//
		// Message msg = new Message();
		// msg.obj = rootNode;
		// msg.what = What.TRY_OPEN_PACKET_UI_FAIL;
		// mHandler.sendMessageDelayed(msg, 100);
		// return;
		// }
		//
		// AccessibilityNodeInfo rootNode = ((AccessibilityService)
		// mContext).getRootInActiveWindow();
		// if (recycle(rootNode, "΢�ź��")) {
		// mTryOpenUI = true;
		// Message msg = new Message();
		// msg.obj = rootNode;
		// msg.what = What.TRY_OPEN_PACKET_UI;
		//
		// mHandler.sendMessageDelayed(msg, 5000);
		// // mHandler.sendEmptyMessageDelayed(What.TRY_OPEN_PACKET_UI,5000);
		// }
		//
		// }

		/*
		 * if
		 * (event.getClassName().toString().equals("android.widget.LinearLayout"
		 * )) { AccessibilityNodeInfo rootNode = ((AccessibilityService)
		 * mContext).getRootInActiveWindow(); recycle(rootNode, "΢�ź��"); }
		 */

	}

	private void closeRedPacket(AccessibilityNodeInfo rootNode) {

		List<AccessibilityNodeInfo> list = null;
		if (rootNode == null) {
			return;
		}
		list = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/f0");
		mIndicateHelper.setCanRetrieve(false);
		mIndicateHelper.setRunning(false);
		if (list.isEmpty()) {
			return;
		}
		// Log.i("", list + "");
		if (list != null) {
			if (list.isEmpty()) {
				return;
			}
			Message msg = new Message();
			msg.obj = list.get(0);
			msg.what = AccessibilityNodeInfo.ACTION_CLICK;
			mHandler.sendMessageDelayed(msg, 100);

		}

	}

	public boolean recycle(final AccessibilityNodeInfo nodeInfo, String string) {
		if (nodeInfo == null) {
			return false;
		}
		int nodeCount = nodeInfo.getChildCount();
		if (nodeCount == 0) {

			if (nodeInfo.getText() != null) {
				String targetString = nodeInfo.getText().toString();
				if (targetString.contains(string)) {
					// Log.i("", nodeInfo.toString() + "");
					// Log.i("", nodeInfo.getViewIdResourceName() + "");
					//
					// Log.i("", nodeInfo.getParent().toString());

					nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
					return true;
				}

			}
		} else {
			for (int i = nodeCount - 1; i >= 0; i--) {
				AccessibilityNodeInfo temp = nodeInfo.getChild(i);
				if (recycle(temp, string)) {
					return true;
				}
			}
		}

		return false;
	}

	private void openRedPacket(AccessibilityNodeInfo rootNode) {
		List<AccessibilityNodeInfo> list;
		list = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b_b");
		Log.i( list.toString());

		if (list != null) {
			if (list.isEmpty()) {
				return;
			}
			Message msg = new Message();
			msg.obj = list.get(0);
			msg.what = AccessibilityNodeInfo.ACTION_CLICK;
			mHandler.sendMessageDelayed(msg, 100);

		} else {

		}
	}

	public int openNotify(AccessibilityEvent event) {
		KeyguardManager mKeyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
		PowerManagerWakeLock.acquire(mContext);
		PowerManagerWakeLock.release();

		Notification notification = (Notification) event.getParcelableData();
		PendingIntent pendingIntent = notification.contentIntent;

		try {
			pendingIntent.send();
			playLuckySound();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	private void playLuckySound() {

		boolean playSound = true;

		if (playSound) {
			MediaPlayer player = MediaPlayer.create(mContext, R.raw.hongbao_arrived);
			player.start();
		}
	}


	private void backRedHelperUI() {
		mIndicateHelper.setCanRetrieve(true);
		mIndicateHelper.setRunning(false);
		Intent intent = new Intent("com.idroi.repackethelper.ACTION.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}

	private void performPasteAction(AccessibilityNodeInfo info) {
		List<AccessibilityNodeInfo> list;
		list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/yv");

		if (!list.isEmpty()) {
			Bundle arguments = new Bundle();
			ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("message", "���Զ��ظ�����ƭ��");

			clipboard.setPrimaryClip(clip);
			list.get(0).performAction(AccessibilityNodeInfo.ACTION_FOCUS);

			list.get(0).performAction(AccessibilityNodeInfo.ACTION_PASTE);

		}
		list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/z1");
		if (!list.isEmpty()) {
			list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
		}
	}
}
