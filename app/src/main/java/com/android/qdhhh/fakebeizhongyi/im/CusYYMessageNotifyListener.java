package com.android.qdhhh.fakebeizhongyi.im;

import android.content.Intent;

import com.yonyou.sns.im.entity.YYMessage;
import com.yonyou.sns.im.util.message.YYMessageNotifyListener;

/**
 * Created by qdhhh on 2016/10/8.
 */

public class CusYYMessageNotifyListener implements YYMessageNotifyListener {
    @Override
    public String getNotificationMessage(YYMessage yyMessage) {
        return null;
    }

    @Override
    public String getNotificationTitle(YYMessage yyMessage) {
        return null;
    }

    @Override
    public Intent getNotificationResponse(YYMessage yyMessage) {
        return null;
    }

    @Override
    public String getNotificationTotal(YYMessage yyMessage, int i, int i1) {
        return null;
    }

    @Override
    public int getNotificationIcon() {
        return 0;
    }

    @Override
    public int getNotificationIconLevel() {
        return 0;
    }
}
