package com.android.qdhhh.fakebeizhongyi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Setting extends AppCompatActivity {

    private ImageView iv;
    private TextView tv;
    private int type = 1;

    private SettingOnClickListener settingOnClickListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setStatus();

        settingOnClickListener = new SettingOnClickListener();


        iv = (ImageView) findViewById(R.id.iv_setting);
        tv = (TextView) findViewById(R.id.tv_setting);
        if (getIntent().getStringExtra("model").equals("HUAWEI")) {
            iv.setImageResource(R.drawable.ziqi_huawei);
        } else if (getIntent().getStringExtra("model").equals("Xiaomi")) {
            iv.setImageResource(R.drawable.ziqi_xiaomi);
        } else if (getIntent().getStringExtra("model").equals("MEIZU")) {
            iv.setImageResource(R.drawable.ziqi_meizu);
        } else if (getIntent().getStringExtra("model").equals("samsung")) {
            iv.setImageResource(R.drawable.ziqi_sanxing);
        }
        iv.setOnClickListener(settingOnClickListener);
        tv.setOnClickListener(settingOnClickListener);

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

    private final class SettingOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.tv_setting:
                    intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                    type = 2;
                    break;
                case R.id.iv_setting:
                    intent = new Intent(Activity_Setting.this, Activity_Login.class);
                    startActivity(intent);
                    finish();
                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type == 2) {
            startActivity(new Intent(Activity_Setting.this, Activity_Login.class));
            finish();
        }
    }

    @Override
    protected void onRestart() {
        type = 2;
        super.onRestart();
    }
}
