package com.android.qdhhh.fakebeizhongyi;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.Calendar;

public class Activity_FindBackPwd extends AppCompatActivity {

    private EditText findback_pwd_et_number;
    private EditText findback_pwd_et_realname;
    private EditText findback_pwd_et_idcard;
    private EditText findback_pwd_et_class;
    private EditText findback_pwd_et_mates;

    private TextView findback_pwd_bt_post;
    private TextView findback_pwd_tv_intime;
    private TextView findback_pwd_tv_outtime;

    private LinearLayout findback_pwd_iv_intime;
    private LinearLayout findback_pwd_iv_outtime;

    private ImageView findback_pwd_backicon_iv_id;

    private DatePickerDialog datePickerDialoge;

    private int flag;

    private Calendar calendar;

    private FindBackPWDOnClickListener findBackPWDOnClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_back_pwd);

        setStatus();

        initView();


    }

    private void initView() {

        calendar = Calendar.getInstance();

        findBackPWDOnClickListener = new FindBackPWDOnClickListener();

        findback_pwd_et_number = (EditText) findViewById(R.id.findback_pwd_et_number);
        findback_pwd_et_realname = (EditText) findViewById(R.id.findback_pwd_et_realname);
        findback_pwd_et_idcard = (EditText) findViewById(R.id.findback_pwd_et_idcard);
        findback_pwd_et_class = (EditText) findViewById(R.id.findback_pwd_et_class);
        findback_pwd_et_mates = (EditText) findViewById(R.id.findback_pwd_et_mates);

        findback_pwd_bt_post = (TextView) findViewById(R.id.findback_pwd_bt_post);
        findback_pwd_tv_intime = (TextView) findViewById(R.id.findback_pwd_tv_intime);
        findback_pwd_tv_outtime = (TextView) findViewById(R.id.findback_pwd_tv_outtime);

        findback_pwd_iv_intime = (LinearLayout) findViewById(R.id.findback_pwd_iv_intime);
        findback_pwd_iv_outtime = (LinearLayout) findViewById(R.id.findback_pwd_iv_outtime);

        findback_pwd_backicon_iv_id = (ImageView) findViewById(R.id.findback_pwd_backicon_iv_id);

        findback_pwd_backicon_iv_id.setOnClickListener(findBackPWDOnClickListener);
        findback_pwd_iv_intime.setOnClickListener(findBackPWDOnClickListener);
        findback_pwd_iv_outtime.setOnClickListener(findBackPWDOnClickListener);
        findback_pwd_bt_post.setOnClickListener(findBackPWDOnClickListener);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    private final class FindBackPWDOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.findback_pwd_iv_intime: {

                    flag = 1;

                    getTime();

                    break;

                }
                case R.id.findback_pwd_iv_outtime: {

                    flag = 2;

                    getTime();

                    break;

                }
                case R.id.findback_pwd_bt_post: {

                    postOn();

                    break;

                }
                case R.id.findback_pwd_backicon_iv_id: {

                    Activity_FindBackPwd.this.onBackPressed();

                    break;

                }
                default: {
                    break;
                }

            }
        }
    }

    private void postOn() {

        if (TextUtils.isEmpty(findback_pwd_et_number.getText().toString())) {

            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(findback_pwd_et_realname.getText().toString())) {

            Toast.makeText(this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(findback_pwd_et_idcard.getText().toString())) {

            Toast.makeText(this, "身份证不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(findback_pwd_et_class.getText().toString())) {

            Toast.makeText(this, "班级不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(findback_pwd_tv_intime.getText().toString())) {

            Toast.makeText(this, "入校时间不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(findback_pwd_tv_outtime.getText().toString())) {

            Toast.makeText(this, "毕业时间不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        if (findback_pwd_et_mates.getText().toString().trim().contains(";")
                && findback_pwd_et_mates.getText().toString().trim().split(";").length < 3) {
            Toast.makeText(this, "请至少输入3位同班同学，以英文;隔开！", Toast.LENGTH_SHORT).show();
            return;
        }

        request();
    }


    private void request() {

        KJHttp kjhttp = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("username", findback_pwd_et_number.getText().toString());
        params.put("realname", findback_pwd_et_realname.getText().toString());
        params.put("starttime", findback_pwd_tv_intime.getText().toString());
        params.put("endtime", findback_pwd_tv_outtime.getText().toString());
        params.put("classname", findback_pwd_et_class.getText().toString());
        params.put("classmates", findback_pwd_et_mates.getText().toString());
        params.put("idcard", findback_pwd_et_idcard.getText().toString());

        kjhttp.post("", params, new HttpCallBack() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }
        });
    }

    private void getTime() {
        if (datePickerDialoge == null) {

            datePickerDialoge = new DatePickerDialog(this, new FindBackPWDOnDateSetListener(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                    .get(Calendar.DAY_OF_MONTH));

        } else {

            datePickerDialoge.show();

        }


    }


    private final class FindBackPWDOnDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {

            monthOfYear = monthOfYear + 1;

            if (flag == 1) {
                if (monthOfYear < 10) {
                    findback_pwd_tv_intime.setText(year + "-0"
                            + monthOfYear);
                } else {
                    findback_pwd_tv_intime.setText(year + "-"
                            + monthOfYear);
                }
            } else if (flag == 2) {
                if (monthOfYear < 10) {
                    findback_pwd_tv_outtime.setText(year + "-0"
                            + monthOfYear);
                } else {
                    findback_pwd_tv_outtime.setText(year + "-"
                            + monthOfYear);
                }
            }
            flag = -1;
        }
    }


}
