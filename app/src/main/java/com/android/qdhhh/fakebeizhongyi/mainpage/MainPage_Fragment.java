package com.android.qdhhh.fakebeizhongyi.mainpage;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.mainpage.childfragments.Fragment_HotNews;
import com.android.qdhhh.fakebeizhongyi.mainpage.childfragments.Fragment_Main_Alumni;
import com.android.qdhhh.fakebeizhongyi.mainpage.childfragments.Fragment_OneOfUs;

import java.util.LinkedList;
import java.util.List;


public class MainPage_Fragment extends Fragment {

    private TabLayout main_page_tl_id;
    private ViewPager main_page_vp_id;

    private String[] TabNames;

    private List<Fragment> listData;

    private View view;

    private View mianpage_view_id;

    public MainPage_Fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        TabNames = getResources().getStringArray(R.array.TabName);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        mianpage_view_id = (View) view.findViewById(R.id.mainpage_view_id);

        if (Build.VERSION.SDK_INT < 21) {
            mianpage_view_id.setVisibility(View.GONE);
        }

        main_page_tl_id = (TabLayout) view.findViewById(R.id.main_page_tl_id);
        main_page_vp_id = (ViewPager) view.findViewById(R.id.main_page_vp_id);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setTabLayout();

        setViewPager();


    }

    private void setViewPager() {

        listData = new LinkedList<>();

        getData();

        main_page_vp_id.setOffscreenPageLimit(3);

        main_page_vp_id.setAdapter(new MainPageViewPagerAdapter(getChildFragmentManager()));

    }


    private void setTabLayout() {

        main_page_tl_id.setupWithViewPager(main_page_vp_id);

    }

    private void getData() {

        listData.add(new Fragment_HotNews());
        listData.add(new Fragment_Main_Alumni());
        listData.add(new Fragment_OneOfUs());
        listData.add(new Fragment_OneOfUs());


    }

    private final class MainPageViewPagerAdapter extends FragmentStatePagerAdapter {


        public MainPageViewPagerAdapter(FragmentManager fm) {
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
        public Object instantiateItem(ViewGroup container, int position) {

            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TabNames[position];
        }
    }


}
