package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.qdhhh.fakebeizhongyi.R;

public class Activity_Privacy extends AppCompatActivity {

    private CheckBox me_setting_privacy_need;
    private CheckBox me_setting_privacy_visiable;

    private RelativeLayout me_setting_privacy_pic;

    private PrivacyOnCheckedChangedListemer privacyOnCheckedChangedListemer;
    private PrivacyOnClickListener privacyOnClickListener;

    private ImageView me_privacy_backicon_iv_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        setStatus();
        initView();

    }

    private void initView() {

        privacyOnCheckedChangedListemer = new PrivacyOnCheckedChangedListemer();

        privacyOnClickListener = new PrivacyOnClickListener();

        me_setting_privacy_need = (CheckBox) findViewById(R.id.me_setting_privacy_need);
        me_setting_privacy_visiable = (CheckBox) findViewById(R.id.me_setting_privacy_visiable);
        me_setting_privacy_pic = (RelativeLayout) findViewById(R.id.me_setting_privacy_pic);
        me_privacy_backicon_iv_id = (ImageView) findViewById(R.id.me_privacy_backicon_iv_id);


        me_setting_privacy_need.setOnCheckedChangeListener(privacyOnCheckedChangedListemer);
        me_setting_privacy_visiable.setOnCheckedChangeListener(privacyOnCheckedChangedListemer);

        me_setting_privacy_pic.setOnClickListener(privacyOnClickListener);
        me_privacy_backicon_iv_id.setOnClickListener(privacyOnClickListener);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }

    private final class PrivacyOnCheckedChangedListemer implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }

    private final class PrivacyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.me_privacy_backicon_iv_id: {
                    onBackPressed();
                    break;
                }
                default: {
                    break;
                }
            }

        }
    }
}
