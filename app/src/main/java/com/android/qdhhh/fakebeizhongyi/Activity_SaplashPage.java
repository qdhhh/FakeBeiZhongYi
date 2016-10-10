package com.android.qdhhh.fakebeizhongyi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.qdhhh.fakebeizhongyi.im.CusTokenProvider;
import com.android.qdhhh.fakebeizhongyi.im.CusYYMessageNotifyListener;
import com.yonyou.sns.im.core.YYIMChat;
import com.yonyou.sns.im.core.YYIMChatManager;
import com.yonyou.sns.im.core.YYIMProviderHandler;

public class Activity_SaplashPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__saplash_page);
        setStatus();
        sharedPreferences = getSharedPreferences("isFirstTime", Context.MODE_PRIVATE);

        boolean b = sharedPreferences.getBoolean("isFirstTime", false);

        if (!b) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Activity_SaplashPage.this, Activity_Guide.class));

                    finish();
                }
            }, 3000);


        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Activity_SaplashPage.this, Activity_Login.class));

                    finish();
                }
            }, 3000);
        }


    }

    private void setStatus() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
