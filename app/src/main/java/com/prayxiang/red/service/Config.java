package com.prayxiang.red.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;

public class Config {

	private boolean switchRedHelper;
	private boolean lockScreenHelper;
	private boolean autoBackRoom;
	private boolean soundHint;
	private boolean screenLight;

	public static final boolean DEBUG = true;
	public static final String DEBUG_TAG = "test";

	public static final String PACKAGE_WEICHAT = "com.tencent.mm";

	public static final String PACKAGE_QQ = "com.tencent.mobileqq";

	private volatile Config mConfig;

	private Config() {

	}

	class Key {
		static final String RED_HELPER_KEY = "red_helper_key";
		static final String SCREEN_LIGHT_KEY = "screen_light_key";
		static final String SCREEN_LOCK_KEY = "screen_lock_key";
		static final String AUTO_BACK_KEY = "auto_back_key";
		static final String SOUND_HINT_KEY = "sound_hint_key";

	}

	public void getInstance(Context context) {
		if (mConfig == null) {
			synchronized (Config.class) {
				if (mConfig == null) {
					mConfig = new Config();
					init(context);
					
				}
			}
		}
	}

	private void init(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		setAutoBackRoom(preferences.getBoolean(Key.AUTO_BACK_KEY, true));
		setLockScreenHelper(preferences.getBoolean(Key.SCREEN_LOCK_KEY, true));
		setScreenLight(preferences.getBoolean(Key.SCREEN_LIGHT_KEY, true));
		setSoundHint(preferences.getBoolean(Key.SOUND_HINT_KEY, true));
		setSwitchRedHelper(preferences.getBoolean(Key.RED_HELPER_KEY, true));

	}

	/**
	 * wechat version 6.3.9
	 */
	private static final int WECHAT_VERSION_700 = 700;
	private static final String WECHAT_LUCKYMONEY_OPEN_V700 = "com.tencent.mm:id/b2c";
	/**
	 * wechat version 6.3.16
	 */
	private static final int WECHAT_VERSION_740 = 740;
	private static final String WECHAT_LUCKYMONEY_OPEN_V740 = "com.tencent.mm:id/b43";
	/**
	 * wechat version 6.3.15
	 */
	private static final int WECHAT_VERSION_760 = 760;
	private static final String WECHAT_LUCKYMONEY_OPEN_V760 = "com.tencent.mm:id/b3h";

	private static final int WECHAT_VERSION_800 = 800;
	private static final String WECHAT_LUCKYMONEY_OPEN_V800 = "com.tencent.mm:id/b9m";

	public static void startService(Context context) {
		context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
	}

	// To check if service is enabled
	public static boolean isAccessibilitySettingsOn(Context context) {
		int accessibilityEnabled = 0;
		final String service = context.getPackageName() + "/" + RedPacketService.class.getName();
		boolean accessibilityFound = false;
		try {
			accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(),
					android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
		} catch (SettingNotFoundException e) {
		}
		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
		if (accessibilityEnabled == 1) {
			String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

			if (settingValue != null) {
				TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
				splitter.setString(settingValue);
				while (splitter.hasNext()) {
					String accessabilityService = splitter.next();
					if (accessabilityService.equalsIgnoreCase(service)) {
						return true;
					}
				}
			}
		} else {
			// Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
		}

		return accessibilityFound;
	}

	public boolean isSwitchRedHelper() {
		return switchRedHelper;
	}

	public void setSwitchRedHelper(boolean switchRedHelper) {
		this.switchRedHelper = switchRedHelper;
	}

	public boolean isLockScreenHelper() {
		return lockScreenHelper;
	}

	public void setLockScreenHelper(boolean lockScreenHelper) {
		this.lockScreenHelper = lockScreenHelper;
	}

	public boolean isAutoBackRoom() {
		return autoBackRoom;
	}

	public void setAutoBackRoom(boolean autoBackRoom) {
		this.autoBackRoom = autoBackRoom;
	}

	public boolean isSoundHint() {
		return soundHint;
	}

	public void setSoundHint(boolean soundHint) {
		this.soundHint = soundHint;
	}

	public boolean isScreenLight() {
		return screenLight;
	}

	public void setScreenLight(boolean screenLight) {
		this.screenLight = screenLight;
	}

}
