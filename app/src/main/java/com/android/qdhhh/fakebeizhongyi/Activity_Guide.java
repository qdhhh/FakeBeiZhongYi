package com.android.qdhhh.fakebeizhongyi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Guide extends AppCompatActivity {

    private TextView guide_tv_id;

    private ViewPager guide_vp_id;

    private View[] views;

    private ImageView[] points;

    private int imageResourse[] = {R.drawable.yindaoye1, R.drawable.yindaoye2, R.drawable.yindaoye3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        setStatus();

        initView();

        setViewPager();

    }

    private void setViewPager() {

        setImageData();

        guide_vp_id.setAdapter(new GuideViewPagerAdapter());

        guide_vp_id.addOnPageChangeListener(new GuideOnPageChangeListener());

    }

    private void setImageData() {
        views = new View[3];

        for (int i = 0; i < imageResourse.length; i++) {
            views[i] = View.inflate(this, R.layout.guide_image_layout, null);

            ((ImageView) views[i].findViewById(R.id.guide_item_iv_id)).setImageResource(imageResourse[i]);

        }
        guide_tv_id = (TextView) views[2].findViewById(R.id.guide_tv_id);

        guide_tv_id.setVisibility(View.VISIBLE);

        guide_tv_id.setOnClickListener(new GuideOnClickListener());
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

    private void initView() {

        points = new ImageView[3];

        points[0] = (ImageView) findViewById(R.id.guide_point_first_id);
        points[1] = (ImageView) findViewById(R.id.guide_point_second_id);
        points[2] = (ImageView) findViewById(R.id.guide_point_third_id);

        points[0].setEnabled(false);

        guide_vp_id = (ViewPager) findViewById(R.id.guide_vp_id);
    }

    private final class GuideViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position]);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views[position]);
            return views[position];
        }
    }


    private final class GuideOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

            if (state == 2) {
                setCurDot(guide_vp_id.getCurrentItem());

                if (guide_vp_id.getCurrentItem() == 2) {

                    guide_tv_id.setVisibility(View.VISIBLE);
                } else {
                    guide_tv_id.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {

        for (int i = 0; i < 3; i++) {
            points[i].setEnabled(true);
        }
        points[positon].setEnabled(false);

    }

    private final class GuideOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.guide_tv_id) {
                String model = android.os.Build.BRAND;
                if (model.equals("HUAWEI") || model.equals("MEIZU")
                        || model.equals("Xiaomi") || model.equals("samsung")) {
                    Intent intent = new Intent(Activity_Guide.this,
                            Activity_Setting.class);
                    intent.putExtra("model", model);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Activity_Guide.this, Activity_Login.class);
                    startActivity(intent);
                    finish();

                }

            }
        }

    }


}
