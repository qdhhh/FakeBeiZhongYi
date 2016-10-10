package com.android.qdhhh.fakebeizhongyi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.commom.Constants;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.utils.AndroidUtils;
import com.android.qdhhh.fakebeizhongyi.utils.MD5Utils;
import com.yonyou.sns.im.config.YYIMPreferenceConfig;
import com.yonyou.sns.im.core.YYIMCallBack;
import com.yonyou.sns.im.core.YYIMChatManager;
import com.yonyou.sns.im.core.YYIMSessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.Locale;

public class Activity_Login extends AppCompatActivity {

    private TextView switch_language_chi_tv_id;
    private TextView switch_language_eng_tv_id;

    private TextView btn_login;

    private TextView creatcard_tv_id;
    private TextView forgetpwd_tv_id;

    private EditText et_number;
    private EditText et_password;

    private LoginClickListener loginClickListener;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private boolean language;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        setStatus();

        initViews();

        switchLanguage();

        initProgressDialog();


    }

    private void switchLanguage() {

        language = sharedPreferences.getBoolean("language", false);

        editor = sharedPreferences.edit();

        switch_language_chi_tv_id = (TextView) findViewById(R.id.switch_language_chi_tv_id);
        switch_language_eng_tv_id = (TextView) findViewById(R.id.switch_language_eng_tv_id);

        if (language) {
            switch_language_eng_tv_id.setTextColor(getResources().getColor(R.color.colorDefault));
            switch_language_chi_tv_id.setTextColor(Color.GRAY);
            switch_language_eng_tv_id.setEnabled(true);
            switch_language_chi_tv_id.setEnabled(false);

        } else {
            switch_language_chi_tv_id.setTextColor(getResources().getColor(R.color.colorDefault));
            switch_language_eng_tv_id.setTextColor(Color.GRAY);
            switch_language_eng_tv_id.setEnabled(false);
            switch_language_chi_tv_id.setEnabled(true);
        }

        SwitchLanguageListener listener = new SwitchLanguageListener();

        switch_language_chi_tv_id.setOnClickListener(listener);
        switch_language_eng_tv_id.setOnClickListener(listener);


    }

    private final class SwitchLanguageListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {

            switch (arg0.getId()) {
                case R.id.switch_language_chi_tv_id: {

                    editor.putBoolean("language", true);
                    editor.commit();

                    switch_language_eng_tv_id
                            .setTextColor(getResources().getColor(R.color.colorDefault));
                    switch_language_chi_tv_id.setTextColor(Color.GRAY);
                    switch_language_chi_tv_id.setEnabled(false);

                    switch_language_eng_tv_id.setEnabled(true);
                    setDefaultLanguage();

                    break;
                }
                case R.id.switch_language_eng_tv_id: {

                    editor.putBoolean("language", false);
                    editor.commit();

                    switch_language_chi_tv_id
                            .setTextColor(getResources().getColor(R.color.colorDefault));
                    switch_language_eng_tv_id.setTextColor(Color.GRAY);
                    switch_language_eng_tv_id.setEnabled(false);

                    switch_language_chi_tv_id.setEnabled(true);

                    setDefaultLanguage();
                    break;
                }
                default: {
                    break;
                }

            }

            startActivity(new Intent(Activity_Login.this, Activity_Login.class));

            finish();
        }
    }

    /**
     * 设置当前语言为用户设置的语言类型（英语）（汉语），默认汉语
     */
    protected void setDefaultLanguage() {

        sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE);

        language = sharedPreferences.getBoolean("language", true);

        if (language) {

            setCurrentLanguage(Locale.SIMPLIFIED_CHINESE);

        } else {

            setCurrentLanguage(Locale.ENGLISH);

        }

    }

    protected void setCurrentLanguage(Locale locale) {

        Resources resources = getResources();// 获得res资源对象

        Configuration config = resources.getConfiguration();// 获得设置对象

        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。

        config.locale = locale;

        resources.updateConfiguration(config, dm);

    }


    private void initViews() {

        sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE);

        loginClickListener = new LoginClickListener();

        switch_language_chi_tv_id = (TextView) findViewById(R.id.switch_language_chi_tv_id);
        switch_language_eng_tv_id = (TextView) findViewById(R.id.switch_language_eng_tv_id);
        btn_login = (TextView) findViewById(R.id.btn_login);
        creatcard_tv_id = (TextView) findViewById(R.id.creatcard_tv_id);
        forgetpwd_tv_id = (TextView) findViewById(R.id.forgetpwd_tv_id);
        et_number = (EditText) findViewById(R.id.et_number);
        et_password = (EditText) findViewById(R.id.et_password);


        switch_language_chi_tv_id.setOnClickListener(loginClickListener);
        switch_language_eng_tv_id.setOnClickListener(loginClickListener);
        btn_login.setOnClickListener(loginClickListener);
        creatcard_tv_id.setOnClickListener(loginClickListener);
        forgetpwd_tv_id.setOnClickListener(loginClickListener);

        creatcard_tv_id.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

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

    private void initProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登录中...");

    }

    private final class LoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_login: {

                    requestLogin();

                    break;
                }
                case R.id.creatcard_tv_id: {
                    startActivity(new Intent(Activity_Login.this, Activity_CreatCard.class));
                    break;
                }
                case R.id.forgetpwd_tv_id: {
                    startActivity(new Intent(Activity_Login.this, Activity_FindBackPwd.class));
                    break;
                }
                default: {
                    break;
                }
            }


        }
    }


    private void requestLogin() {
        if (!AndroidUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "当前网络不可用！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(et_number.getText().toString())
                || TextUtils.isEmpty(et_password.getText().toString())) {
            Toast.makeText(this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (et_number.getText().toString().startsWith(" ")
                || et_number.getText().toString().endsWith(" ")) {
            Toast.makeText(this, "您输入的账号格式错误！", Toast.LENGTH_SHORT).show();
            return;
        }

        sendRequest();
    }

    private void sendRequest() {

        progressDialog.show();

        KJHttp kjHttp = new KJHttp();

        HttpParams params = new HttpParams();
        params.put("account", et_number.getText().toString());
        params.put("password", MD5Utils.get_MD5(et_password.getText().toString()));


        kjHttp.post(UrlAddress.getURLAddress(UrlAddress.Url_Login), params, new HttpCallBack() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                Toast.makeText(Activity_Login.this, "登录失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                try {
                    int i = new JSONObject(t).getInt("response");
                    String msg = new JSONObject(t).getString("failmsg");
                    if (i == 1) {
                        setLocalInfo(t);
                        Toast.makeText(Activity_Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        loginYYIM(LocalInfo.userid,
                                et_number.getText().toString(),
                                LocalInfo.username,
                                et_password.getText().toString());
                    } else {
                        Toast.makeText(Activity_Login.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                super.onFinish();
            }
        });
    }

    /**
     * 登录用友IM
     *
     * @param userID
     * @param userName
     * @param nickName
     * @param passWord
     */
    private void loginYYIM(String userID, String userName, String nickName,
                           String passWord) {

        YYIMPreferenceConfig.getInstance().setString(
                Constants.FRONT_LAST_LOGIN_USERID, userID);
        YYIMPreferenceConfig.getInstance().setString(
                Constants.FRONT_LAST_LOGIN_ACCOUNT, userName);
        YYIMPreferenceConfig.getInstance().setString(
                Constants.FRONT_LAST_LOGIN_PASSWORD, passWord);
        YYIMPreferenceConfig.getInstance().setString(
                Constants.FRONT_LAST_LOGIN_NICKNAME, nickName);
        YYIMSessionManager.getInstance().clearSession();
        YYIMChatManager.getInstance().getYmSettings()
                .setCustomAppkey(Constants.appid);
        YYIMChatManager.getInstance().getYmSettings()
                .setCustomEtpkey(Constants.etpid);

        Log.i("IM","------------");

        // 登录
        YYIMChatManager.getInstance().login(userID, new YYIMCallBack() {

            @Override
            public void onSuccess(Object object) {
                Log.i("IM","IM登录成功");
                startActivity(new Intent(Activity_Login.this, MainActivity.class));
                finish();
            }

            @Override
            public void onProgress(int errno, String errmsg) {
                Log.i("IM","IM登录中------------");
            }

            @Override
            public void onError(int errno, String errmsg) {
                Log.i("IM","IM登录失败");
            }
        });
    }


    private void setLocalInfo(String t) {

        try {
            JSONObject obj = new JSONObject(t).getJSONObject("personinfo");
            LocalInfo.userid = obj.getString("userid");
            LocalInfo.imid = obj.getString("imid");
            LocalInfo.token = obj.getString("token");
            LocalInfo.username = obj.getString("username");
            LocalInfo.iconimg = obj.getString("iconimg");
            LocalInfo.IsPublishHd = obj.getString("IsPublishHd");
            LocalInfo.isfirsttime = obj.getInt("isfirsttime");
            LocalInfo.editable = obj.getInt("editable");
            LocalInfo.registerStatus = obj.getInt("registerStatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
