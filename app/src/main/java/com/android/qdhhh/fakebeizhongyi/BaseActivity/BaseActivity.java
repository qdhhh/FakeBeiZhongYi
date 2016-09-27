package com.android.qdhhh.fakebeizhongyi.BaseActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.android.qdhhh.fakebeizhongyi.R;

/**
 * Created by qdhhh on 2016/9/20.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setStatus();

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


}
