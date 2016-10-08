package com.android.qdhhh.fakebeizhongyi.function.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.function.bean.Activity_Bean;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Activity_Common extends Fragment {


    private String tabName;

    private View view;

    private PullToRefreshListView activity_ptrlv_id;

    private int pageCount;

    private List<Activity_Bean> listData;

    private ActivityListViewAdapter activityListViewAdapter;

    private KJHttp kj;

    public Fragment_Activity_Common() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        kj = new KJHttp();

        tabName = getArguments().getString("tabName");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_activity, container, false);

        activity_ptrlv_id = (PullToRefreshListView) view.findViewById(R.id.activity_ptrlv_id);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        activityListViewAdapter = new ActivityListViewAdapter();

        setListView();

        getData();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 获取数据
     */
    private void getData() {

        HttpParams params = new HttpParams();

        params.put("Condition", tabName);
        params.put("page", 1);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_KeyWordsSearchAt),
                params, false, new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        try {
                            String str = new JSONObject(t).optJSONObject("result").getString("error_code");

                            if ("200".equals(str)) {

                                setData(new JSONObject(t).getString("objdata"));
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

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                }

        );
    }

    /**
     * 更新数据
     */
    private void setData(String t) {

        listData = JSON.parseArray(t, Activity_Bean.class);

        activityListViewAdapter.notifyDataSetChanged();

    }


    /**
     * 设置listview
     */
    private void setListView() {

        listData = new LinkedList<>();

        activity_ptrlv_id.setAdapter(activityListViewAdapter);

        activity_ptrlv_id.setOnItemClickListener(null);

    }

    /**
     * listview的适配器
     */
    private final class ActivityListViewAdapter extends BaseAdapter {

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
            ActivityViewHolder activityViewHolder = null;

            if (convertView == null) {

                convertView = View.inflate(getContext(), R.layout.item_mainpage_hotnews, null);

                activityViewHolder = new ActivityViewHolder(convertView);

                convertView.setTag(activityViewHolder);

            } else {

                activityViewHolder = (ActivityViewHolder) convertView.getTag();

            }
            Picasso.with(getContext()).load(listData.get(position).getUserIcon()).into(activityViewHolder.sircle_iv_id);
            activityViewHolder.hot_news_tv_title_id.setText(listData.get(position).getUserName());
            activityViewHolder.hot_news_tv_time_id.setText(listData.get(position).getUserName());

            return convertView;
        }
    }


    /**
     * listview的viewholder实体类
     */
    private class ActivityViewHolder {
        ImageView sircle_iv_id;
        TextView hot_news_tv_title_id;
        TextView hot_news_tv_time_id;
        TextView hot_news_tv_body_id;

        public ActivityViewHolder(View itemView) {

            sircle_iv_id = (ImageView) itemView.findViewById(R.id.sircle_iv_id);
            hot_news_tv_title_id = (TextView) itemView.findViewById(R.id.hot_news_tv_title_id);
            hot_news_tv_time_id = (TextView) itemView.findViewById(R.id.hot_news_tv_time_id);
            hot_news_tv_body_id = (TextView) itemView.findViewById(R.id.hot_news_tv_body_id);

        }
    }

}
