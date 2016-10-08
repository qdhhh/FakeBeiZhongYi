package com.android.qdhhh.fakebeizhongyi.mainpage.childfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.mainpage.Activity_News_Detail;
import com.android.qdhhh.fakebeizhongyi.mainpage.bean.Main_News_Head_Bean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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

    private PullToRefreshListView main_mainpage_hotnews_ptrlv_id;

    private MainpageOnRefreshListener mainpageOnRefreshListener;

    private MainPageHotNewsAdapter mainPageHotNewsAdapter;

    private ListView listView;

    private View view;

    private int picCount;

    private View headView;

    private Banner mainpage_hotnews_vp_id;

    private KJHttp kjhttp;

    private List<String> headTitle;

    private List<String> headURL;

    private List<Main_News_Head_Bean> mainNews_Head;

    private List<Main_News_Head_Bean> mainNews_List;

    private int pageCount = 1;


    public Fragment_HotNews() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        kjhttp = new KJHttp();
        super.onCreate(savedInstanceState);
    }

    /**
     * 网络请求下载新的新闻数据
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
                        pageCount = 2;
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
                if (main_mainpage_hotnews_ptrlv_id.isRefreshing()) {
                    main_mainpage_hotnews_ptrlv_id.onRefreshComplete();
                }
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

            setHeadData();

            mainPageHotNewsAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__hotnews, null);
        main_mainpage_hotnews_ptrlv_id = (PullToRefreshListView) view.findViewById(R.id.main_mainpage_hotnews_ptrlv_id);
        listView = main_mainpage_hotnews_ptrlv_id.getRefreshableView();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        mainNews_Head = new LinkedList<>();

        mainNews_List = new LinkedList<>();

        mainpageOnRefreshListener = new MainpageOnRefreshListener();

        mainPageHotNewsAdapter = new MainPageHotNewsAdapter();

        setHeadView();

        listView.addHeaderView(headView);

        setListView();

        getNewsData();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 设置头控件
     */
    private void setHeadView() {

        headView = View.inflate(getActivity(), R.layout.mainpage_hotnews_headview, null);

        mainpage_hotnews_vp_id = (Banner) headView.findViewById(R.id.mainpage_hotnews_vp_id);

        mainpage_hotnews_vp_id.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

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
        mainpage_hotnews_vp_id.setImages(headURL);

        mainpage_hotnews_vp_id.setBannerTitleList(headTitle);

        mainpage_hotnews_vp_id.setOnBannerClickListener(new HeadBannerClickListener());

    }

    /**
     * 设置listView
     */
    private void setListView() {

        main_mainpage_hotnews_ptrlv_id.setAdapter(mainPageHotNewsAdapter);

        main_mainpage_hotnews_ptrlv_id.setOnItemClickListener(new MainpageNewsItemClickListener());

        main_mainpage_hotnews_ptrlv_id.setOnRefreshListener(mainpageOnRefreshListener);

        main_mainpage_hotnews_ptrlv_id.setMode(PullToRefreshBase.Mode.BOTH);
    }


    /**
     * 刷新监听
     */
    private final class MainpageOnRefreshListener implements PullToRefreshBase.OnRefreshListener2 {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            getNewsData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            addNewsData();
        }
    }


    /**
     * 上拉加载更多
     */
    private void addNewsData() {

        HttpParams params = new HttpParams();
        params.put("token", LocalInfo.token);
        params.put("page", pageCount);
        params.put("userid", LocalInfo.userid);
        params.put("categoryid", 2);

        kjhttp.post(UrlAddress.getURLAddress(UrlAddress.Url_GetHeadlineNews), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).getJSONObject("result").getString("error_code");
                    if ("200".equals(code)) {
                        pageCount++;
                        addNewsData(t);
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
                main_mainpage_hotnews_ptrlv_id.onRefreshComplete();
                super.onFinish();
            }
        });
    }

    /**
     * 加载更多数据
     *
     * @param json
     */
    private void addNewsData(String json) {

        List listTemp = new LinkedList();

        try {

            listTemp = JSONArray.parseArray(new JSONObject(json).getString("listnews"),
                    Main_News_Head_Bean.class);

            mainNews_List.addAll(listTemp);

            mainPageHotNewsAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 条目的点击监听器
     */
    private final class MainpageNewsItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), Activity_News_Detail.class);

            intent.putExtra("URL", mainNews_List.get(position));

            startActivity(intent);

        }
    }


    /**
     * pulltorefreshlistview的适配器
     */
    private final class MainPageHotNewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mainNews_List.size();
        }

        @Override
        public Object getItem(int position) {
            return mainNews_List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MainPageHotNewsViewHolder mainPageHotNewsViewHolder = null;

            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_mainpage_hotnews, null);

                mainPageHotNewsViewHolder = new MainPageHotNewsViewHolder(convertView);

                convertView.setTag(mainPageHotNewsViewHolder);
            } else {
                mainPageHotNewsViewHolder = (MainPageHotNewsViewHolder) convertView.getTag();
            }

            Picasso.with(getContext()).load(mainNews_List.get(position).getImgurl()).into(mainPageHotNewsViewHolder.sircle_iv_id);
            mainPageHotNewsViewHolder.hot_news_tv_title_id.setText(mainNews_List.get(position).getNewstitle());
            mainPageHotNewsViewHolder.hot_news_tv_time_id.setText(mainNews_List.get(position).getPublishtime());

            return convertView;
        }
    }


    /**
     * 条目的holder类
     */
    private final class MainPageHotNewsViewHolder {

        ImageView sircle_iv_id;
        TextView hot_news_tv_title_id;
        TextView hot_news_tv_time_id;
        TextView hot_news_tv_body_id;

        public MainPageHotNewsViewHolder(View itemView) {

            sircle_iv_id = (ImageView) itemView.findViewById(R.id.sircle_iv_id);
            hot_news_tv_title_id = (TextView) itemView.findViewById(R.id.hot_news_tv_title_id);
            hot_news_tv_time_id = (TextView) itemView.findViewById(R.id.hot_news_tv_time_id);
            hot_news_tv_body_id = (TextView) itemView.findViewById(R.id.hot_news_tv_body_id);

        }
    }

}
