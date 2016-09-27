package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

public class Activity_About extends AppCompatActivity {

    private ImageView about_iv_id;
    private TextView about_tv_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setStatus();

        initView();

        requestData();
    }

    private void requestData() {

        KJHttp kj = new KJHttp();

        kj.get(UrlAddress.getURLAddress(UrlAddress.Url_About), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");
                    if ("200".equals(code)) {
                        Picasso.with(Activity_About.this).load(new JSONObject(t).getString("iconurl"))
                                .into(about_iv_id);
                        about_tv_id.setText(new JSONObject(t).getString("description"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    private void initView() {
        about_iv_id = (ImageView) findViewById(R.id.about_iv_id);
        about_tv_id = (TextView) findViewById(R.id.about_tv_id);
        findViewById(R.id.me_about_backicon_iv_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }

}
