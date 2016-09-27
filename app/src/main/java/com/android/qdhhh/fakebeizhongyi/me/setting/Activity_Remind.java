package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.BaseActivity.BaseActivity;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.me.Activity_MyInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.StringUtils;

public class Activity_Remind extends AppCompatActivity {

    private CheckBox me_setting_remind_receivepush_cb_id;
    private CheckBox me_setting_remind_chat_cb_id;
    private CheckBox me_setting_remind_sound_cb_id;
    private CheckBox me_setting_remind_shock_cb_id;
    private CheckBox me_setting_remind_no_cb_id;

    private RelativeLayout rl_start_time;
    private RelativeLayout rl_end_time;
    private TextView tv_start_time;
    private TextView tv_end_time;

    private ImageView me_setting_remind_backicon_iv_id;

    private RelativeLayout me_setting_group_setting_rl_id;

    private RemindCheckBoxListener remindCHeckBoxListener;

    private int ifreceive;
    private int voice;
    private int shake;

    private int disturbType;
    private String starttime;
    private String endtime;

    private int pushStatus;

    private TimePickerDialog timePicker;

    private boolean timeFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__remind);

        setStatus();

        initView();

        requestData();

        initTimePaker();

    }

    /**
     * 初始化时间选择器
     */
    private void initTimePaker() {
        timePicker = new TimePickerDialog(this, new RemindTimeSetListener(), 0, 0, true);
    }

    private final class RemindTimeSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            StringBuffer time = new StringBuffer();

            if (hourOfDay < 9) {
                time.append("0").append(hourOfDay).append(":").append(minute < 10 ? ("0" + minute) : minute);
            } else {
                time.append(hourOfDay).append(":").append(minute < 10 ? ("0" + minute) : minute);
            }
            if (timeFlag) {
                tv_start_time.setText(time);
            } else {
                tv_end_time.setText(time);
            }
            timePicker.dismiss();
            if (TextUtils.isEmpty(tv_start_time.getText().toString()) ||
                    TextUtils.isEmpty(tv_end_time.getText().toString())) {
            } else {
                updataDisturb();
            }
        }
    }


    /**
     * 请求数据
     */
    private void requestData() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_ChatStatus), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");
                    if ("200".equals(code)) {

                        ifreceive = new JSONObject(t).getInt("ifreceive");
                        voice = new JSONObject(t).getInt("voice");
                        shake = new JSONObject(t).getInt("shake");

                        setChatStatus();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_DisturbStatus), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {

                        disturbType = new JSONObject(t).getInt("type");
                        starttime = new JSONObject(t).getString("starttime");
                        endtime = new JSONObject(t).getString("endtime");

                        setDisturbStatus();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_PushStatus), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {

                        pushStatus = new JSONObject(t).getInt("status");

                        setPushStatus();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    /**
     * 设置推送的控件状态
     */
    private void setPushStatus() {

        if (pushStatus == 0) {
            me_setting_remind_receivepush_cb_id.setChecked(false);
        } else {
            me_setting_remind_receivepush_cb_id.setChecked(true);
        }
    }

    /**
     * 设置免打扰的控件状态
     */
    private void setDisturbStatus() {

        if (disturbType == 1) {
            me_setting_remind_no_cb_id.setChecked(true);
            rl_start_time.setVisibility(View.GONE);
            rl_end_time.setVisibility(View.GONE);
            tv_start_time.setText("");
            tv_end_time.setText("");
        } else {
            me_setting_remind_no_cb_id.setChecked(false);
            rl_start_time.setVisibility(View.VISIBLE);
            rl_end_time.setVisibility(View.VISIBLE);
            tv_start_time.setText(starttime);
            tv_end_time.setText(endtime);
        }
    }

    /**
     * 设置聊天的控件状态
     */
    private void setChatStatus() {

        me_setting_remind_chat_cb_id.setChecked(ifreceive == 0 ? false : true);
        me_setting_remind_sound_cb_id.setChecked(voice == 0 ? false : true);
        me_setting_remind_shock_cb_id.setChecked(shake == 0 ? false : true);

    }


    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    /**
     * 初始化控件
     */
    private void initView() {

        remindCHeckBoxListener = new RemindCheckBoxListener();

        me_setting_remind_receivepush_cb_id = (CheckBox) findViewById(R.id.me_setting_remind_receivepush_cb_id);
        me_setting_remind_chat_cb_id = (CheckBox) findViewById(R.id.me_setting_remind_chat_cb_id);
        me_setting_remind_sound_cb_id = (CheckBox) findViewById(R.id.me_setting_remind_sound_cb_id);
        me_setting_remind_shock_cb_id = (CheckBox) findViewById(R.id.me_setting_remind_shock_cb_id);
        me_setting_remind_no_cb_id = (CheckBox) findViewById(R.id.me_setting_remind_no_cb_id);

        me_setting_group_setting_rl_id = (RelativeLayout) findViewById(R.id.me_setting_group_setting_rl_id);

        me_setting_remind_backicon_iv_id = (ImageView) findViewById(R.id.me_setting_remind_backicon_iv_id);

        me_setting_group_setting_rl_id.setOnClickListener(remindCHeckBoxListener);
        me_setting_remind_receivepush_cb_id.setOnClickListener(remindCHeckBoxListener);
        me_setting_remind_chat_cb_id.setOnClickListener(remindCHeckBoxListener);
        me_setting_remind_sound_cb_id.setOnClickListener(remindCHeckBoxListener);
        me_setting_remind_shock_cb_id.setOnClickListener(remindCHeckBoxListener);
        me_setting_remind_no_cb_id.setOnClickListener(remindCHeckBoxListener);
        me_setting_remind_backicon_iv_id.setOnClickListener(remindCHeckBoxListener);

        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        rl_end_time = (RelativeLayout) findViewById(R.id.rl_end_time);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        rl_start_time.setOnClickListener(remindCHeckBoxListener);
        rl_end_time.setOnClickListener(remindCHeckBoxListener);

    }

    /**
     * 界面控件的点击监听
     */
    private final class RemindCheckBoxListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.me_setting_remind_backicon_iv_id: {
                    onBackPressed();
                    break;
                }
                case R.id.me_setting_remind_receivepush_cb_id: {
                    updataPushStatus();
                    break;
                }
                case R.id.me_setting_remind_chat_cb_id: {
                    if (!me_setting_remind_chat_cb_id.isChecked()) {
                        me_setting_remind_sound_cb_id.setChecked(false);
                        me_setting_remind_shock_cb_id.setChecked(false);
                    } else {
                        me_setting_remind_sound_cb_id.setChecked(true);
                        me_setting_remind_shock_cb_id.setChecked(true);
                    }
                    updataChatStatus();
                    break;
                }
                case R.id.me_setting_remind_sound_cb_id: {
                    if (me_setting_remind_sound_cb_id.isChecked()) {
                        me_setting_remind_chat_cb_id.setChecked(true);
                    } else if (!me_setting_remind_shock_cb_id.isChecked()) {
                        me_setting_remind_chat_cb_id.setChecked(false);
                    }
                    updataChatStatus();
                    break;
                }
                case R.id.me_setting_remind_shock_cb_id: {
                    if (me_setting_remind_shock_cb_id.isChecked()) {
                        me_setting_remind_chat_cb_id.setChecked(true);
                    } else if (!me_setting_remind_sound_cb_id.isChecked()) {
                        me_setting_remind_chat_cb_id.setChecked(false);
                    }
                    updataChatStatus();
                    break;
                }
                case R.id.me_setting_remind_no_cb_id: {
                    if (me_setting_remind_no_cb_id.isChecked()) {
                        rl_start_time.setVisibility(View.GONE);
                        rl_end_time.setVisibility(View.GONE);
                        tv_start_time.setText("");
                        tv_end_time.setText("");
                        updataDisturb();
                    } else {
                        rl_start_time.setVisibility(View.VISIBLE);
                        rl_end_time.setVisibility(View.VISIBLE);
                        tv_start_time.setText("");
                        tv_end_time.setText("");
                    }
                    break;
                }
                case R.id.rl_start_time: {
                    timeFlag = true;
                    timePicker.show();
                    break;
                }
                case R.id.rl_end_time: {
                    timeFlag = false;
                    timePicker.show();
                    break;
                }
                case R.id.me_setting_group_setting_rl_id: {

                    startActivity(new Intent(Activity_Remind.this, Activity_Setting_RemindGroup.class));

                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    /**
     * 更新聊天通知
     */
    private void updataChatStatus() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("shake", me_setting_remind_shock_cb_id.isChecked() ? 1 : 0);
        params.put("voice", me_setting_remind_sound_cb_id.isChecked() ? 1 : 0);
        params.put("ifreceive", me_setting_remind_chat_cb_id.isChecked() ? 1 : 0);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_ChatSetting), params, false, new HttpCallBack() {

            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        Toast.makeText(Activity_Remind.this, "聊天提醒修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Remind.this, "聊天提醒修改成功", Toast.LENGTH_SHORT).show();
                        me_setting_remind_receivepush_cb_id.setChecked(!me_setting_remind_receivepush_cb_id.isChecked());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_Remind.this, "聊天提醒修改失败", Toast.LENGTH_SHORT).show();
                me_setting_remind_receivepush_cb_id.setChecked(!me_setting_remind_receivepush_cb_id.isChecked());
                super.onFailure(errorNo, strMsg);
            }

        });

    }

    /**
     * 更新推送状态
     */
    private void updataPushStatus() {
        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("status", me_setting_remind_receivepush_cb_id.isChecked() ? 1 : 0);
        params.put("devicetype", 1);
        params.put("pushtoken", "");

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_PushSetting), params, false, new HttpCallBack() {

            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        Toast.makeText(Activity_Remind.this, "推送状态修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Remind.this, "推送状态修改失败", Toast.LENGTH_SHORT).show();
                        me_setting_remind_receivepush_cb_id.setChecked(!me_setting_remind_receivepush_cb_id.isChecked());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_Remind.this, "推送状态修改失败", Toast.LENGTH_SHORT).show();
                me_setting_remind_receivepush_cb_id.setChecked(!me_setting_remind_receivepush_cb_id.isChecked());
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    /**
     * 免打扰状态设置
     */
    private void updataDisturb() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("type", me_setting_remind_no_cb_id.isChecked() ? 1 : 2);
        params.put("starttime", tv_start_time.getText().toString());
        params.put("endtime", tv_end_time.getText().toString());

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_DisturbSetting), params, false, new HttpCallBack() {

            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        Toast.makeText(Activity_Remind.this, "免打扰状态修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Remind.this, "免打扰状态修改成功", Toast.LENGTH_SHORT).show();
                        me_setting_remind_receivepush_cb_id.setChecked(!me_setting_remind_receivepush_cb_id.isChecked());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_Remind.this, "免打扰状态修改成功", Toast.LENGTH_SHORT).show();
                me_setting_remind_receivepush_cb_id.setChecked(!me_setting_remind_receivepush_cb_id.isChecked());
                super.onFailure(errorNo, strMsg);
            }
        });
    }

}
