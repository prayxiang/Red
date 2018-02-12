package com.prayxiang.red.service;

import android.content.Intent;
import android.text.TextUtils;

import com.prayxiang.red.App;
import com.prayxiang.red.utils.Anchor;

/**
 * Created by xianggaofeng on 2018/2/11.
 */

public class Daemon {
    public static void start() {
        AppExecutors.get().getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.equals(Anchor.get().pkg, Config.PACKAGE_QQ) || TextUtils.equals(Anchor.get().pkg, Config.PACKAGE_WEICHAT)) {
                    Intent intent = new Intent("com.idroi.repackethelper.ACTION.VIEW");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.sContext.startActivity(intent);
                }
            }
        }, 5000);
    }
}
