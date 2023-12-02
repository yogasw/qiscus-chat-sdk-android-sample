package com.qiscus.mychatui;


import android.util.Log;
import androidx.multidex.MultiDexApplication;

import com.qiscus.jupuk.Jupuk;
import com.qiscus.mychatui.util.PushNotificationUtil;
import com.qiscus.nirmana.Nirmana;
import com.qiscus.sdk.chat.core.QiscusCore;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.one.EmojiOneProvider;

/**
 * Created on : January 30, 2018
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;

    private AppComponent component;

    public static MyApplication getInstance() {
        return instance;
    }

    private static void initEmoji() {
        EmojiManager.install(new EmojiOneProvider());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = new AppComponent(this);
        Nirmana.init(this);

        String appId = System.getenv("APP_ID");
        String baseUrl = System.getenv("BASE_URL");
        if (appId == null) {
            appId = "sdksample";
        }
        if (baseUrl == null) {
            baseUrl = "https://api.qiscus.com";
        }

        Log.d("Qiscus", "Using app id: " + appId);
        Log.d("Qiscus", "Using base url: " + baseUrl);

        QiscusCore.setupWithCustomServer(this, appId, baseUrl, null, null);
        QiscusCore.getChatConfig()
                .enableDebugMode(true)
                .setNotificationListener(PushNotificationUtil::showNotification)
                .setEnableFcmPushNotification(true);
        initEmoji();
        Jupuk.init(this);
    }

    public AppComponent getComponent() {
        return component;
    }
}


