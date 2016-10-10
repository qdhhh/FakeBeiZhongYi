package com.android.qdhhh.fakebeizhongyi;

import android.app.Application;

import com.android.qdhhh.fakebeizhongyi.im.CusTokenProvider;
import com.android.qdhhh.fakebeizhongyi.im.CusYYMessageNotifyListener;
import com.yonyou.sns.im.core.YYIMChat;
import com.yonyou.sns.im.core.YYIMChatManager;
import com.yonyou.sns.im.core.YYIMProviderHandler;

/**
 * Created by qdhhh on 2016/10/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initIM();
    }
    private void initIM() {
        // 用友IM SDK初始化
        YYIMChat.getInstance().init(this);

        // 注册tokenProvider
        YYIMProviderHandler.getInstance().registerTokenProvider(
                new CusTokenProvider());

        // 设置notification消息点击时，跳转的intent为自定义的intent和样式
        YYIMChatManager.getInstance().getYmSettings()
                .setMessageNotifyListener(new CusYYMessageNotifyListener());

        YYIMChatManager.getInstance().getYmSettings()
                .setAutoAcceptRosterInvite(false);
    }
}
