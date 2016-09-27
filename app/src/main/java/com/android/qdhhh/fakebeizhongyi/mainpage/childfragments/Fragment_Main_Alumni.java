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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.mainpage.Activity_News_Detail;
import com.android.qdhhh.fakebeizhongyi.mainpage.bean.Main_News_Head_Bean;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.List;

public class Fragment_Main_Alumni extends Fragment {

    private View view = null;
    private SwipeRefreshLayout alumni_srl_id;
    private RecyclerView alumni_rv_id;

    private List<Main_News_Head_Bean> listData;

    private KJHttp kjhttp;

    private Alumni_RecyvlerView_Adapter alumni_RecyvlerView_Adapter;

    public Fragment_Main_Alumni() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        alumni_RecyvlerView_Adapter = new Alumni_RecyvlerView_Adapter();

        kjhttp = new KJHttp();

        requestData();

        super.onCreate(savedInstanceState);
    }

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
                alumni_srl_id.setRefreshing(false);
                super.onFinish();
            }
        });
    }

    private void setData(String string) {
        try {
            listData = JSONArray.parseArray(new JSONObject(string).getString("focusnews"),
                    Main_News_Head_Bean.class);

            alumni_RecyvlerView_Adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_alumni, null);

        alumni_srl_id = (SwipeRefreshLayout) view.findViewById(R.id.alumni_srl_id);
        alumni_rv_id = (RecyclerView) view.findViewById(R.id.alumni_rv_id);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        alumni_srl_id.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });

        alumni_rv_id.setAdapter(alumni_RecyvlerView_Adapter);
        alumni_rv_id.setLayoutManager(new GridLayoutManager(getContext(), 1));
        super.onActivityCreated(savedInstanceState);
    }

    private final class Alumni_RecyvlerView_Adapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = View.inflate(getContext(), R.layout.item_mainpage_hotnews, null);

            Alumni_RecyvlerView_Holder vh = new Alumni_RecyvlerView_Holder(view);

            return vh;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Picasso.with(getContext()).load(listData.get(position).getImgurl()).into(((Alumni_RecyvlerView_Holder) holder).sircle_iv_id);
            ((Alumni_RecyvlerView_Holder) holder).hot_news_tv_title_id.setText(listData.get(position).getNewstitle());
            ((Alumni_RecyvlerView_Holder) holder).hot_news_tv_time_id.setText(listData.get(position).getPublishtime());
        }


        @Override
        public int getItemCount() {
            if (listData == null) {
                return 0;
            }
            return listData.size();
        }
    }


    private final class Alumni_RecyvlerView_Holder extends RecyclerView.ViewHolder {

        ImageView sircle_iv_id;
        TextView hot_news_tv_title_id;
        TextView hot_news_tv_time_id;
        TextView hot_news_tv_body_id;

        public Alumni_RecyvlerView_Holder(View itemView) {
            super(itemView);

            sircle_iv_id = (ImageView) itemView.findViewById(R.id.sircle_iv_id);
            hot_news_tv_title_id = (TextView) itemView.findViewById(R.id.hot_news_tv_title_id);
            hot_news_tv_time_id = (TextView) itemView.findViewById(R.id.hot_news_tv_time_id);
            hot_news_tv_body_id = (TextView) itemView.findViewById(R.id.hot_news_tv_body_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Activity_News_Detail.class);

                    intent.putExtra("URL", listData.get(getAdapterPosition()));

                    startActivity(intent);
                }
            });
        }
    }
}
