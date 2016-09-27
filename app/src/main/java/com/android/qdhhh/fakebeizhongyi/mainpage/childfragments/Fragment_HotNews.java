package com.android.qdhhh.fakebeizhongyi.mainpage.childfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.mainpage.Activity_News_Detail;
import com.android.qdhhh.fakebeizhongyi.mainpage.bean.Main_News_Head_Bean;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.LinkedList;
import java.util.List;

public class Fragment_HotNews extends Fragment {

    private RecyclerView main_mainpage_hotnews_rv_id;
    private SwipeRefreshLayout main_mainpage_hotnews_srl_id;
    private View view;

    private int picCount;

    private List<ImageView> listImage;

    private View headView;

    private Banner mainpage_hotnews_vp_id;

    private KJHttp kjhttp;

    private List<String> headTitle;

    private List<String> headURL;

    private List<Main_News_Head_Bean> mainNews_Head;

    private List<Main_News_Head_Bean> mainNews_List;


    public Fragment_HotNews() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        kjhttp = new KJHttp();

        getNewsData();

        super.onCreate(savedInstanceState);
    }

    /**
     * 网络请求下载新闻数据
     */
    private void getNewsData() {
        HttpParams params = new HttpParams();
        params.put("token", LocalInfo.token);
        params.put("page", 1);
        params.put("userid", LocalInfo.userid);
        params.put("categoryid", 2);


        kjhttp.post(UrlAddress.getURLAddress(UrlAddress.Url_GetHeadlineNews), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).getJSONObject("result").getString("error_code");
                    if ("200".equals(code)) {
                        setNewsData(t);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(getActivity(), "获取数据失败。。。", Toast.LENGTH_SHORT).show();
                super.onFailure(errorNo, strMsg);
            }

            @Override
            public void onFinish() {
                main_mainpage_hotnews_srl_id.setRefreshing(false);
                super.onFinish();
            }
        });
    }


    /**
     * 设置数据源
     *
     * @param json
     */
    private void setNewsData(String json) {

        mainNews_Head = new LinkedList<>();

        mainNews_List = new LinkedList<>();

        try {
            mainNews_Head = JSONArray.parseArray(new JSONObject(json).getString("focusnews"),
                    Main_News_Head_Bean.class);

            picCount = mainNews_Head.size();

            mainNews_List = JSONArray.parseArray(new JSONObject(json).getString("listnews"),
                    Main_News_Head_Bean.class);

            setHeadView();

            setRecyclerView();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__hotnews, null);
        main_mainpage_hotnews_rv_id = (RecyclerView) view.findViewById(R.id.main_mainpage_hotnews_rv_id);
        main_mainpage_hotnews_srl_id = (SwipeRefreshLayout) view.findViewById(R.id.main_mainpage_hotnews_srl_id);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        main_mainpage_hotnews_srl_id.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsData();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 设置头控件
     */
    private void setHeadView() {

        headView = View.inflate(getActivity(), R.layout.mainpage_hotnews_headview, null);

        mainpage_hotnews_vp_id = (Banner) headView.findViewById(R.id.mainpage_hotnews_vp_id);

        mainpage_hotnews_vp_id.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        setHeadData();

        mainpage_hotnews_vp_id.setImages(headURL);

        mainpage_hotnews_vp_id.setBannerTitleList(headTitle);

        mainpage_hotnews_vp_id.setOnBannerClickListener(new HeadBannerClickListener());

    }

    /**
     * 图片轮播器的点击事件监听器
     */
    private final class HeadBannerClickListener implements OnBannerClickListener {

        @Override
        public void OnBannerClick(int position) {

            Intent intent = new Intent(getActivity(), Activity_News_Detail.class);

            intent.putExtra("URL", mainNews_Head.get(position - 1));

            startActivity(intent);

        }
    }

    private void setHeadData() {

        headTitle = new LinkedList<>();

        headURL = new LinkedList<>();

        for (int i = 0; i < mainNews_Head.size(); i++) {

            headTitle.add(mainNews_Head.get(i).getNewstitle());

            headURL.add(mainNews_Head.get(i).getImgurl());
        }
    }


    /**
     * 设置recyclerView
     */
    private void setRecyclerView() {

        main_mainpage_hotnews_rv_id.setAdapter(new MainPageHotNewsAdapter());

        main_mainpage_hotnews_rv_id.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    /**
     * viewpager的适配器
     */
    private final class MainPageHotNewsAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {

            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = null;

            RecyclerView.ViewHolder vh = null;

            if (viewType == 0) {

                vh = new MainPageHeadViewViewHolder(headView);

            } else {

                view = View.inflate(getContext(), R.layout.item_mainpage_hotnews, null);

                vh = new MainPageHotNewsViewHolder(view);
            }


            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MainPageHeadViewViewHolder) {
            } else {
                Picasso.with(getContext()).load(mainNews_List.get(position - 1).getImgurl()).into(((MainPageHotNewsViewHolder) holder).sircle_iv_id);
                ((MainPageHotNewsViewHolder) holder).hot_news_tv_title_id.setText(mainNews_List.get(position - 1).getNewstitle());
                ((MainPageHotNewsViewHolder) holder).hot_news_tv_time_id.setText(mainNews_List.get(position - 1).getPublishtime());
            }
        }

        @Override
        public int getItemCount() {
            return mainNews_List.size() + 1;
        }
    }


    /**
     * 一般条目的holder类
     */
    private final class MainPageHotNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView sircle_iv_id;
        TextView hot_news_tv_title_id;
        TextView hot_news_tv_time_id;
        TextView hot_news_tv_body_id;

        public MainPageHotNewsViewHolder(View itemView) {
            super(itemView);

            sircle_iv_id = (ImageView) itemView.findViewById(R.id.sircle_iv_id);
            hot_news_tv_title_id = (TextView) itemView.findViewById(R.id.hot_news_tv_title_id);
            hot_news_tv_time_id = (TextView) itemView.findViewById(R.id.hot_news_tv_time_id);
            hot_news_tv_body_id = (TextView) itemView.findViewById(R.id.hot_news_tv_body_id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Activity_News_Detail.class);

                    intent.putExtra("URL", mainNews_List.get(getAdapterPosition() - 1));

                    startActivity(intent);
                }
            });

        }
    }


    /**
     * 头view 的holder类
     */
    private final class MainPageHeadViewViewHolder extends RecyclerView.ViewHolder {


        public MainPageHeadViewViewHolder(View itemView) {
            super(itemView);

        }

    }
}
