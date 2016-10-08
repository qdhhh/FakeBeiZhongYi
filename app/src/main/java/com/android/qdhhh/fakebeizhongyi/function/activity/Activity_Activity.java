package com.android.qdhhh.fakebeizhongyi.function.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.function.fragment.Fragment_Activity_Common;
import com.android.qdhhh.fakebeizhongyi.function.fragment.Fragment_Activity_Total;

import java.util.LinkedList;
import java.util.List;

public class Activity_Activity extends AppCompatActivity {

    private TabLayout activity_tablayout_id;

    private ActivityOnClickListener activityOnClickListener;

    private ViewPager activity_vp_id;

    private String[] tabNames;

    private List<Fragment> listFragments;

    private ActivityViewPagerAdapter activityViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        setStatus();

        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {

        tabNames = getResources().getStringArray(R.array.activity_tab_names);

        activityOnClickListener = new ActivityOnClickListener();

        findViewById(R.id.activity_backicon_iv_id).setOnClickListener(activityOnClickListener);

        activity_tablayout_id = (TabLayout) findViewById(R.id.activity_tablayout_id);

        activity_vp_id = (ViewPager) findViewById(R.id.activity_vp_id);

        setViewPager();

        setTabLayout();

    }

    /**
     * 设置tablayout
     */
    private void setTabLayout() {

        activity_tablayout_id.setupWithViewPager(activity_vp_id);

    }


    /**
     * 设置viewpager
     */
    private void setViewPager() {

        getFragmentsData();

        activityViewPagerAdapter = new ActivityViewPagerAdapter(getSupportFragmentManager());

        activity_vp_id.setAdapter(activityViewPagerAdapter);
    }

    /**
     * 获取数据
     */
    private void getFragmentsData() {

        listFragments = new LinkedList<>();

        Fragment_Activity_Total fragment_activity_total = new Fragment_Activity_Total();

        listFragments.add(fragment_activity_total);

        Fragment_Activity_Common fragment_activity_common = null;

        for (int i = 1; i < tabNames.length; i++) {

            fragment_activity_common = new Fragment_Activity_Common();

            Bundle bundle = new Bundle();

            bundle.putString("tabName", tabNames[i]);

            fragment_activity_common.setArguments(bundle);

            listFragments.add(fragment_activity_common);
        }

    }


    /**
     * viewpager的适配器
     */
    private final class ActivityViewPagerAdapter extends FragmentStatePagerAdapter {

        public ActivityViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }


    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }

    private final class ActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activity_backicon_iv_id: {
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


}
