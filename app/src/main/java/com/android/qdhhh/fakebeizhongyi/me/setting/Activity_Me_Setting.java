package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.Activity_Login;
import com.android.qdhhh.fakebeizhongyi.R;
import com.yonyou.sns.im.core.YYIMChatManager;

public class Activity_Me_Setting extends AppCompatActivity {

    private MeSettingOnClickListener meSettingOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me__setting);

        setStatus();

        initView();

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }

    private void initView() {

        meSettingOnClickListener = new MeSettingOnClickListener();

        findViewById(R.id.me_setting_item_remind).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_item_change).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_item_privacy).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_item_cache).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_item_feedback).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_item_score).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_item_about).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.btn_logout).setOnClickListener(meSettingOnClickListener);
        findViewById(R.id.me_setting_backicon_iv_id).setOnClickListener(meSettingOnClickListener);

    }

    private final class MeSettingOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.me_setting_item_remind: {

                    startActivity(new Intent(Activity_Me_Setting.this, Activity_Remind.class));

                    break;
                }
                case R.id.me_setting_item_change: {

                    startActivity(new Intent(Activity_Me_Setting.this, Activity_Me_Change.class));

                    break;
                }
                case R.id.me_setting_item_privacy: {

                    startActivity(new Intent(Activity_Me_Setting.this, Activity_Privacy.class));

                    break;
                }
                case R.id.me_setting_item_cache: {
                    cleanCache();
                    break;
                }
                case R.id.me_setting_item_feedback: {
                    startActivity(new Intent(Activity_Me_Setting.this, Activity_Feedback.class));
                    break;
                }
                case R.id.me_setting_item_score: {

                    goToScore();

                    break;
                }
                case R.id.me_setting_item_about: {

                    startActivity(new Intent(Activity_Me_Setting.this, Activity_About.class));

                    break;
                }
                case R.id.btn_logout: {

                    startActivity(new Intent(Activity_Me_Setting.this, Activity_Login.class));
                    YYIMChatManager.getInstance().logout();

                    finishAffinity();

                    break;
                }
                case R.id.me_setting_backicon_iv_id: {

                    onBackPressed();

                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private void goToScore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" +
                getPackageName()));
        startActivity(intent);
    }

    private void cleanCache() {
        try {
            String cacheSize = DataCleanManger
                    .getTotalCacheSize(this);

            DataCleanManger.clearAllCache(this);

            Toast.makeText(this, "已为您删除缓存数据" + cacheSize,
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, "删除异常，请稍后重试", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
