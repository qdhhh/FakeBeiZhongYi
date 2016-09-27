package com.android.qdhhh.fakebeizhongyi;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.R;
import com.wx.wheelview.widget.WheelViewDialog;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.LinkedList;
import java.util.List;

public class Activity_CreatCard extends AppCompatActivity {

    private EditText regist_name_et_id;
    private EditText regist_phone_et_id;
    private EditText regist_email_et_id;
    private EditText regist_pwd_et_id;
    private EditText regist_college_et_id;
    private EditText regist_major_et_id;
    private EditText regist_class_et_id;
    private EditText regist_number_et_id;
    private TextView regist_intime_tv_id;

    private TextView regist_gender_tv_id;
    private TextView regist_outtime_tv_id;
    private TextView regist_degree_tv_id;
    private TextView regist_post_on;

    private ImageView regist_backicon_iv_id;
    private ImageView regist_college_select_iv_id;

    private RegistOnClickListener registOnClickListener;

    private AlertDialog sexChoice;
    private AlertDialog degreeChoice;

    private int flag;


    private String[] genderArray;
    private String[] degreeArray;

    private List<String> yearList;

    private List<String> collegeList;

    private WheelViewDialog timeViewDialog;

    private WheelViewDialog collegeViewDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_card);
        setStatus();

        initView();

        initDialog();

        requestForCollegeData();

    }

    private void requestForCollegeData() {

        KJHttp kjhttp = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("", "");

        kjhttp.post("", params, new HttpCallBack() {

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    private void initView() {

        genderArray = getResources().getStringArray(R.array.genderArray);

        degreeArray = getResources().getStringArray(R.array.degreeArray);

        getTimeData();

        registOnClickListener = new RegistOnClickListener();

        regist_name_et_id = (EditText) findViewById(R.id.regist_name_et_id);
        regist_phone_et_id = (EditText) findViewById(R.id.regist_phone_et_id);
        regist_email_et_id = (EditText) findViewById(R.id.regist_email_et_id);
        regist_pwd_et_id = (EditText) findViewById(R.id.regist_pwd_et_id);
        regist_major_et_id = (EditText) findViewById(R.id.regist_major_et_id);
        regist_class_et_id = (EditText) findViewById(R.id.regist_class_et_id);
        regist_number_et_id = (EditText) findViewById(R.id.regist_number_et_id);
        regist_college_et_id = (EditText) findViewById(R.id.regist_college_et_id);


        regist_gender_tv_id = (TextView) findViewById(R.id.regist_gender_tv_id);
        regist_intime_tv_id = (TextView) findViewById(R.id.regist_intime_tv_id);
        regist_outtime_tv_id = (TextView) findViewById(R.id.regist_outtime_tv_id);
        regist_degree_tv_id = (TextView) findViewById(R.id.regist_degree_tv_id);
        regist_post_on = (TextView) findViewById(R.id.regist_post_on);

        regist_backicon_iv_id = (ImageView) findViewById(R.id.regist_backicon_iv_id);
        regist_college_select_iv_id = (ImageView) findViewById(R.id.regist_college_select_iv_id);

        regist_gender_tv_id.setOnClickListener(registOnClickListener);
        regist_intime_tv_id.setOnClickListener(registOnClickListener);
        regist_outtime_tv_id.setOnClickListener(registOnClickListener);
        regist_degree_tv_id.setOnClickListener(registOnClickListener);
        regist_post_on.setOnClickListener(registOnClickListener);

        regist_backicon_iv_id.setOnClickListener(registOnClickListener);
        regist_college_select_iv_id.setOnClickListener(registOnClickListener);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }

    private final class RegistOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.regist_gender_tv_id: {

                    sexChoice.show();

                    break;
                }
                case R.id.regist_backicon_iv_id: {

                    onBackPressed();

                    break;
                }
                case R.id.regist_college_select_iv_id: {

                    if (collegeList == null) {
                        Toast.makeText(Activity_CreatCard.this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {

                        if (collegeViewDialog == null) {
                            initCollegeDialog();
                        }
                        collegeViewDialog.show();
                    }
                    break;
                }
                case R.id.regist_intime_tv_id: {
                    flag = 1;
                    timeViewDialog.show();
                    break;
                }
                case R.id.regist_outtime_tv_id: {
                    flag = 2;
                    timeViewDialog.show();
                    break;
                }
                case R.id.regist_degree_tv_id: {

                    degreeChoice.show();

                    break;
                }
                case R.id.regist_post_on: {

                    postOnData();

                    break;
                }
            }

        }
    }

    private void postOnData() {


    }

    private void initDialog() {
        initGenderDialog();

        initDegreeDialog();

        initTimeDialog();

    }

    private void initTimeDialog() {

        timeViewDialog = new WheelViewDialog(this);

        timeViewDialog.setItems(yearList);

        timeViewDialog.setSelection(2016 - 1990);

        timeViewDialog.setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
            @Override
            public void onItemClick(int i, String s) {

                if (flag == 1) {
                    regist_intime_tv_id.setText(s);
                } else {
                    regist_outtime_tv_id.setText(s);
                }

                timeViewDialog.dismiss();
            }
        });

    }

    private void initCollegeDialog() {

        collegeViewDialog = new WheelViewDialog(this);

        collegeViewDialog.setItems(collegeList);

        collegeViewDialog.setSelection(0);

        collegeViewDialog.setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
            @Override
            public void onItemClick(int i, String s) {

                regist_college_et_id.setText(s);

                collegeViewDialog.dismiss();
            }
        });

    }

    private void getTimeData() {

        yearList = new LinkedList<>();

        for (int i = 1900; i <= 2020; i++) {
            yearList.add(i + "");
        }

    }


    /**
     * 性别选择器
     */
    private void initGenderDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setSingleChoiceItems(genderArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                regist_gender_tv_id.setText(genderArray[which]);

                sexChoice.dismiss();
            }
        });
        sexChoice = builder.create();
    }

    /**
     * 学历选择器
     */
    private void initDegreeDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setSingleChoiceItems(degreeArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                regist_degree_tv_id.setText(degreeArray[which]);

                degreeChoice.dismiss();
            }
        });
        degreeChoice = builder.create();
    }

}
