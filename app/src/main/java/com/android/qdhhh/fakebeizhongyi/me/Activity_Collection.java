package com.android.qdhhh.fakebeizhongyi.me;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.me.fragment.Fragment_Position;

import java.util.LinkedList;
import java.util.List;

public class Activity_Collection extends AppCompatActivity {


    private ImageView me_collection_backicon_iv_id;
    private TabLayout me_collection_tab_id;
    private ViewPager me_collection_vp_id;

    private Activity_Collection.CollectionViewPagerAdapter positionViewPagerAdapter;

    private List<Fragment> listData;

    private String[] tabNames ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        setStatus();
        initView();

        setViewPager();

    }

    private void setViewPager() {

        getFragments();

        positionViewPagerAdapter  = new Activity_Collection.CollectionViewPagerAdapter(getSupportFragmentManager());
        me_collection_vp_id.setAdapter(positionViewPagerAdapter);

        me_collection_tab_id.setupWithViewPager(me_collection_vp_id);
    }

    private void getFragments() {

        listData = new LinkedList<>();

        Fragment_Position fragment = null;

        Bundle bunder  = null;

        for(int i = 0 ;i< 3;i++){

            bunder = new Bundle();
            bunder.putInt("type",i);
            fragment  = new Fragment_Position();
            fragment.setArguments(bunder);
            listData.add(fragment);
        }

    }

    private void initView() {

        me_collection_backicon_iv_id = (ImageView) findViewById(R.id.me_collection_backicon_iv_id);
        me_collection_tab_id = (TabLayout) findViewById(R.id.me_collection_tab_id);
        me_collection_vp_id = (ViewPager) findViewById(R.id.me_collection_vp_id);

        tabNames = getResources().getStringArray(R.array.collection_tabname);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    private final class CollectionViewPagerAdapter extends FragmentStatePagerAdapter {

        public CollectionViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listData.get(position);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }


}