package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.qdhhh.fakebeizhongyi.R;

public class Activity_Feedback extends AppCompatActivity {

    private TextView me_setting_feedback_post;
    private EditText me_setting_feedback_et_id;
    private TextView me_setting_feedback_tv_id;
    private ImageView me_feedback_backicon_iv_id;

    private FeedBackListener feedBackListener;
    private FeedBackWatcher feedBackWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setStatus();

        initView();

    }

    private void initView() {

        feedBackListener = new FeedBackListener();

        feedBackWatcher = new FeedBackWatcher();

        me_setting_feedback_post = (TextView) findViewById(R.id.me_setting_feedback_post);
        me_setting_feedback_et_id = (EditText) findViewById(R.id.me_setting_feedback_et_id);
        me_setting_feedback_tv_id = (TextView) findViewById(R.id.me_setting_feedback_tv_id);
        me_feedback_backicon_iv_id = (ImageView) findViewById(R.id.me_feedback_backicon_iv_id);

        me_setting_feedback_post.setOnClickListener(feedBackListener);
        me_feedback_backicon_iv_id.setOnClickListener(feedBackListener);

        me_setting_feedback_et_id.addTextChangedListener(feedBackWatcher);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    private final class FeedBackWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            int count  = me_setting_feedback_et_id.getText().toString().length();

            StringBuffer sb = new StringBuffer();

            sb.append("还可输入").append(100-count+"").append("个字符");

            me_setting_feedback_tv_id.setText(sb.toString());

        }
    }

    private final class FeedBackListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.me_setting_feedback_post: {
                    onBackPressed();
                    break;
                }
                case R.id.me_setting_feedback_tv_id: {

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
