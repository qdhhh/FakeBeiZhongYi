package com.android.qdhhh.fakebeizhongyi.mainpage.childfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.LinkedList;
import java.util.List;

public class Fragment_Main_Alumni extends Fragment {

    private View view = null;

    private PullToRefreshListView alumni_ptrlv_id;

    private int pageCount = 1;

    private List<Main_News_Head_Bean> listData;

    private KJHttp kjhttp;

    private Alumni_ListView_Adapter alumni_ListView_Adapter;

    public Fragment_Main_Alumni() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        kjhttp = new KJHttp();

        super.onCreate(savedInstanceState);
    }


    /**
     * 获取新数据
     */
    private void requestData() {

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("page", 1);
        params.put("userid", LocalInfo.userid);
        params.put("categoryid", 2);


        kjhttp.post(UrlAddress.getURLAddress(UrlAddress.Url_GetGeneralNews), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                try {
                    String code = new JSONObject(t).getJSONObject("result").getString("error_code");
                    if ("200".equals(code)) {
                        pageCount = 2;
                        setData(t);
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
                if (alumni_ptrlv_id.isRefreshing()) {
                    alumni_ptrlv_id.onRefreshComplete();
                }
                super.onFinish();
            }
        });
    }


    /**
     * 设置数据
     *
     * @param string
     */
    private void setData(String string) {
        try {
            listData = JSONArray.parseArray(new JSONObject(string).getString("focusnews"),
                    Main_News_Head_Bean.class);

            alumni_ListView_Adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_alumni, null);

        alumni_ptrlv_id = (PullToRefreshListView) view.findViewById(R.id.alumni_ptrlv_id);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        listData = new LinkedList<>();

        alumni_ListView_Adapter = new Alumni_ListView_Adapter();

        alumni_ptrlv_id.setAdapter(alumni_ListView_Adapter);

        alumni_ptrlv_id.setOnItemClickListener(new AlumniOnItemClickListener());

        alumni_ptrlv_id.setMode(PullToRefreshBase.Mode.BOTH);

        alumni_ptrlv_id.setOnRefreshListener(new AlumniRefreshListener());

        requestData();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 刷新监听器
     */
    private final class AlumniRefreshListener implements PullToRefreshBase.OnRefreshListener2 {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            requestData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            addNewsData();
        }
    }

    /**
     * 加载更多
     */
    private void addNewsData() {
        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("page", pageCount);
        params.put("userid", LocalInfo.userid);
        params.put("categoryid", 2);


        kjhttp.post(UrlAddress.getURLAddress(UrlAddress.Url_GetGeneralNews), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                try {
                    String code = new JSONObject(t).getJSONObject("result").getString("error_code");
                    if ("200".equals(code)) {
                        pageCount++;
                        addData(t);
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
                if (alumni_ptrlv_id.isRefreshing()) {
                    alumni_ptrlv_id.onRefreshComplete();
                }
                super.onFinish();
            }
        });
    }

    /**
     * 添加新数据
     *
     * @param t
     */
    private void addData(String t) {

        List<Main_News_Head_Bean> list = new LinkedList();

        try {
            list = JSONArray.parseArray(new JSONObject(t).getString("focusnews"),
                    Main_News_Head_Bean.class);

            listData.addAll(list);

            alumni_ListView_Adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 条目点击监听器
     */
    private final class AlumniOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), Activity_News_Detail.class);

            intent.putExtra("URL", listData.get(position));

            startActivity(intent);

        }
    }


    /**
     * 刷新ListView适配器
     */
    private final class Alumni_ListView_Adapter extends BaseAdapter {


        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Alumni_ListView_Holder alumni_listView_holder = null;

            if (convertView == null) {

                convertView = View.inflate(getContext(), R.layout.item_mainpage_hotnews, null);

                alumni_listView_holder = new Alumni_ListView_Holder(convertView);

                convertView.setTag(alumni_listView_holder);

            } else {

                alumni_listView_holder = (Alumni_ListView_Holder) convertView.getTag();

            }
            Picasso.with(getContext()).load(listData.get(position).getImgurl()).into(alumni_listView_holder.sircle_iv_id);
            alumni_listView_holder.hot_news_tv_title_id.setText(listData.get(position).getNewstitle());
            alumni_listView_holder.hot_news_tv_time_id.setText(listData.get(position).getPublishtime());

            return convertView;
        }
    }


    /**
     * viewholder实体类
     */
    private final class Alumni_ListView_Holder {

        ImageView sircle_iv_id;
        TextView hot_news_tv_title_id;
        TextView hot_news_tv_time_id;
        TextView hot_news_tv_body_id;

        public Alumni_ListView_Holder(View itemView) {

            sircle_iv_id = (ImageView) itemView.findViewById(R.id.sircle_iv_id);
            hot_news_tv_title_id = (TextView) itemView.findViewById(R.id.hot_news_tv_title_id);
            hot_news_tv_time_id = (TextView) itemView.findViewById(R.id.hot_news_tv_time_id);
            hot_news_tv_body_id = (TextView) itemView.findViewById(R.id.hot_news_tv_body_id);

        }
    }
}
