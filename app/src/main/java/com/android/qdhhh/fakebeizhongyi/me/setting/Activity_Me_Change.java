package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.Activity_Login;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.utils.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

public class Activity_Me_Change extends AppCompatActivity {

    private MeChangeListener meChangeListener;

    private ImageView me_change_backicon_iv_id;
    private RelativeLayout me_setting_change_name_rl_id;
    private RelativeLayout me_setting_change_pwd_rl_id;
    private TextView me_setting_change_name_tv_id;

    private AlertDialog changeNameDialog;

    private EditText me_change_name_et_id;

    private AlertDialog changePWDDialog;

    private EditText me_change_pwd_current_et_id;
    private EditText me_change_pwd_new_et_id;
    private EditText me_change_pwd_ensure_et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_change);
        setStatus();

        initView();

        initChangeNameDialog();

        initChangePWDDialog();


    }

    private void initChangePWDDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(this, R.layout.dialog_change_pwd, null);

        me_change_pwd_current_et_id = (EditText) view.findViewById(R.id.me_change_pwd_current_et_id);
        me_change_pwd_new_et_id = (EditText) view.findViewById(R.id.me_change_pwd_new_et_id);
        me_change_pwd_ensure_et_id = (EditText) view.findViewById(R.id.me_change_pwd_ensure_et_id);

        builder.setTitle("修改密码").setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changePWDDialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (TextUtils.isEmpty(me_change_pwd_new_et_id.getText().toString())
                        || TextUtils.isEmpty(me_change_pwd_ensure_et_id.getText().toString())) {
                    Toast.makeText(Activity_Me_Change.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (me_change_pwd_new_et_id.getText().toString().
                        equals(me_change_pwd_ensure_et_id.getText().toString())) {
                    changePWD();
                    changePWDDialog.dismiss();
                } else {
                    Toast.makeText(Activity_Me_Change.this, "两次密码不相等", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changePWDDialog = builder.create();
    }

    private void initChangeNameDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(this, R.layout.dialog_change_name, null);

        me_change_name_et_id = (EditText) view.findViewById(R.id.me_change_name_et_id);

        builder.setTitle("修改用户名").setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeNameDialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (TextUtils.isEmpty(me_change_name_et_id.getText().toString())) {
                    Toast.makeText(Activity_Me_Change.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    changeNameDialog.dismiss();
                    changeName();
                    me_setting_change_name_tv_id.setText(me_change_name_et_id.getText().toString());
                }
            }
        });

        changeNameDialog = builder.create();

    }

    /**
     * 请求修改用户名
     */
    private void changeName() {

        KJHttp kj = new KJHttp();
        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("accountame", me_change_name_et_id.getText().toString());

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_ModifyUserName), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");
                    int num = new JSONObject(t).getInt("response");
                    if ("200".equals(code) && num == 1) {
                        me_setting_change_name_tv_id.setText(me_change_name_et_id.getText().toString());
                    }
                    Toast.makeText(Activity_Me_Change.this, new JSONObject(t).getString("failmsg"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_Me_Change.this, "用户名修改失败", Toast.LENGTH_SHORT).show();

                super.onFailure(errorNo, strMsg);
            }
        });
    }

    /**
     * 请求密码
     */
    private void changePWD() {

        KJHttp kj = new KJHttp();
        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("newpasswd", MD5Utils.get_MD5(me_change_pwd_new_et_id.getText().toString()));
        params.put("oldpasswd", MD5Utils.get_MD5(me_change_pwd_current_et_id.getText().toString()));

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_ModifyPasswd), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");
                    int num = new JSONObject(t).getInt("response");
                    if ("200".equals(code) && num == 1) {
                        Toast.makeText(Activity_Me_Change.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activity_Me_Change.this, Activity_Login.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(Activity_Me_Change.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_Me_Change.this, "密码修改失败", Toast.LENGTH_SHORT).show();

                super.onFailure(errorNo, strMsg);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {

        meChangeListener = new MeChangeListener();

        me_change_backicon_iv_id = (ImageView) findViewById(R.id.me_change_backicon_iv_id);
        me_setting_change_name_rl_id = (RelativeLayout) findViewById(R.id.me_setting_change_name_rl_id);
        me_setting_change_pwd_rl_id = (RelativeLayout) findViewById(R.id.me_setting_change_pwd_rl_id);
        me_setting_change_name_tv_id = (TextView) findViewById(R.id.me_setting_change_name_tv_id);

        me_setting_change_name_rl_id.setOnClickListener(meChangeListener);
        me_setting_change_pwd_rl_id.setOnClickListener(meChangeListener);
        me_change_backicon_iv_id.setOnClickListener(meChangeListener);

        me_setting_change_name_tv_id.setText("");

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    private final class MeChangeListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.me_setting_change_name_rl_id: {
                    changeNameDialog.show();
                    break;
                }
                case R.id.me_setting_change_pwd_rl_id: {
                    changePWDDialog.show();
                    break;
                }
                case R.id.me_change_backicon_iv_id: {
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
