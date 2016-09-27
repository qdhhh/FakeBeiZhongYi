package com.android.qdhhh.fakebeizhongyi;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.qdhhh.fakebeizhongyi.chat.Chat_Fragment;
import com.android.qdhhh.fakebeizhongyi.contacts.Contacts_Fragment;
import com.android.qdhhh.fakebeizhongyi.function.Function_Fragment;
import com.android.qdhhh.fakebeizhongyi.mainpage.MainPage_Fragment;
import com.android.qdhhh.fakebeizhongyi.me.Me_Fragment;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView main_iv_chat;
    private ImageView main_iv_contacts;
    private ImageView main_iv_function;
    private ImageView main_iv_me;
    private ImageView main_iv_mainpage;

    private FrameLayout main_fl_id;

    private List<Fragment> listData;

    private BottomTabClickListener bottomTabClickListener;

    private FragmentTransaction transition;

    private FragmentManager manger;

    private Fragment currentFragment;

    private Fragment toFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMainStatus();

        setStatus(false);

        setContentView(R.layout.activity_main);

        initViews();

        initeFragmentsData();
    }

    private void setMainStatus() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
    }

    private void initeFragmentsData() {

        manger = getSupportFragmentManager();

        listData = new LinkedList<>();

        listData.add(new Chat_Fragment());
        listData.add(new Contacts_Fragment());
        listData.add(new MainPage_Fragment());
        listData.add(new Function_Fragment());
        listData.add(new Me_Fragment());

        transition = manger.beginTransaction();

        transition.add(R.id.main_fl_id, listData.get(2));

        currentFragment = listData.get(2);

        transition.commit();
    }


    private void setSelect(int num) {

        transition = manger.beginTransaction();

        toFragment = listData.get(num);

        if (!toFragment.isAdded()) {
            transition.hide(currentFragment).add(R.id.main_fl_id, toFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transition.hide(currentFragment).show(toFragment).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = toFragment;
    }

    private void initViews() {

        bottomTabClickListener = new BottomTabClickListener();

        main_fl_id = (FrameLayout) findViewById(R.id.main_fl_id);

        main_iv_chat = (ImageView) findViewById(R.id.main_iv_chat);
        main_iv_chat.setOnClickListener(bottomTabClickListener);
        main_iv_contacts = (ImageView) findViewById(R.id.main_iv_contacts);
        main_iv_contacts.setOnClickListener(bottomTabClickListener);
        main_iv_function = (ImageView) findViewById(R.id.main_iv_function);
        main_iv_function.setOnClickListener(bottomTabClickListener);
        main_iv_me = (ImageView) findViewById(R.id.main_iv_me);
        main_iv_me.setOnClickListener(bottomTabClickListener);
        main_iv_mainpage = (ImageView) findViewById(R.id.main_iv_mainpage);
        main_iv_mainpage.setOnClickListener(bottomTabClickListener);

        main_iv_mainpage.setEnabled(false);
    }


    private final class BottomTabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.main_iv_chat: {
                    setStatus(false);
                    setSelect(0);
                    setBottomTab(v);

                    break;
                }
                case R.id.main_iv_contacts: {
                    setStatus(false);
                    setSelect(1);
                    setBottomTab(v);
                    break;
                }
                case R.id.main_iv_mainpage: {
                    setStatus(false);
                    setSelect(2);
                    setBottomTab(v);
                    break;
                }
                case R.id.main_iv_function: {
                    setStatus(false);
                    setSelect(3);
                    setBottomTab(v);
                    break;
                }
                case R.id.main_iv_me: {
                    setStatus(true);
                    setSelect(4);
                    setBottomTab(v);
                    break;
                }
            }

        }
    }

    private void setBottomTab(View view) {

        main_iv_chat.setEnabled(true);
        main_iv_contacts.setEnabled(true);
        main_iv_mainpage.setEnabled(true);
        main_iv_function.setEnabled(true);
        main_iv_me.setEnabled(true);

        view.setEnabled(false);
    }

    private void setStatus(boolean b) {

        if (b) {
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }

        } else {

            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));

            }

        }
    }
}
