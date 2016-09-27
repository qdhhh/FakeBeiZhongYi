package com.android.qdhhh.fakebeizhongyi.me;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.me.bean.PersonInfo;
import com.android.qdhhh.fakebeizhongyi.me.setting.Activity_Me_Setting;
import com.android.qdhhh.fakebeizhongyi.view.RoundImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

public class Me_Fragment extends Fragment {

    private RecyclerView me_rv_id;
    private View view;
    private View headView;
    private ImageView iv_me_shezhi;
    private RoundImageView iv_me_touxiang;

    private boolean isSDK21orMore;

    private String[] me_Names;

    private int[] iconArray = {
            R.drawable.wodeziliao2x, R.drawable.wodexiangce2x, R.drawable.wodeshoucang2x, R.drawable.wodewenjian2x, R.drawable.wodexiaoxihezi2x, R.drawable.wodehuodong2x, R.drawable.wodejifen2x, R.drawable.wodezhiwei2x, R.drawable.wodeguanyuqiuzhu
    };

    private PersonInfo personInfo;

    public Me_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        isSDK21orMore = Build.VERSION.SDK_INT >= 21 ? true : false;

        me_Names = getResources().getStringArray(R.array.me_Names);
        super.onCreate(savedInstanceState);

    }

    private void requestData() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("otherid", LocalInfo.userid);
        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_GetPersonalInfo), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        personInfo = JSON.parseObject(new JSONObject(t).getString("personalinfo"), PersonInfo.class);

                        setInfo();
                    } else {
                        Toast.makeText(getContext(), "信息获取失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(getContext(), "信息获取失败", Toast.LENGTH_SHORT).show();

                super.onFailure(errorNo, strMsg);
            }
        });
    }

    /**
     * 设置头像
     */
    private void setInfo() {
        Picasso.with(getContext()).load(personInfo.getIconimg()).into(iv_me_touxiang);
    }

    @Override
    public void onResume() {
        requestData();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (isSDK21orMore) {
            headView = inflater.inflate(R.layout.me_rv_headview_immerse, container, false);

        } else {

            headView = inflater.inflate(R.layout.me_rv_headview, container, false);
        }

        view = inflater.inflate(R.layout.fragment_me, container, false);
        me_rv_id = (RecyclerView) view.findViewById(R.id.me_rv_id);
        iv_me_shezhi = (ImageView) headView.findViewById(R.id.iv_me_shezhi);
        iv_me_touxiang = (RoundImageView) headView.findViewById(R.id.iv_me_touxiang);
        iv_me_shezhi.setOnClickListener(new MeOnClickListener());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        setRecyclerView();

        super.onActivityCreated(savedInstanceState);
    }


    private final class MeOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_me_shezhi: {

                    startActivity(new Intent(getActivity(), Activity_Me_Setting.class));

                }

            }

        }
    }


    private void setRecyclerView() {

        me_rv_id.setAdapter(new MeAdapter());

        me_rv_id.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }

    private final class MeAdapter extends RecyclerView.Adapter {


        @Override
        public int getItemViewType(int position) {
            super.getItemViewType(position);
            if (position == 0) {
                return 1000;
            }
            return 1001;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == 1000) {

                View view = headView;

                HeadViewHolder vh = new HeadViewHolder(view);

                return vh;

            } else {
                View view = View.inflate(getContext(), R.layout.item_me, null);

                MeViewHolder vh = new MeViewHolder(view);

                return vh;
            }


        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof MeViewHolder) {
                ((MeViewHolder) holder).me_tv_id.setText(me_Names[(position - 1) % 9]);
                ((MeViewHolder) holder).me_iv_icon_id.setImageResource(iconArray[(position - 1) % 9]);

                Log.i("position", "------" + position);

            }

        }

        @Override
        public int getItemCount() {
            return (iconArray.length + 1);
        }
    }

    protected final class MeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView me_tv_id;
        ImageView me_iv_icon_id;

        public MeViewHolder(View itemView) {
            super(itemView);
            me_tv_id = (TextView) itemView.findViewById(R.id.me_tv_id);
            me_iv_icon_id = (ImageView) itemView.findViewById(R.id.me_iv_icon_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(getContext(), "--------" + me_Names[getAdapterPosition() - 1], Toast.LENGTH_SHORT).show();

            switch (getAdapterPosition() - 1) {
                case 0: {

                    Intent intent = new Intent(getActivity(), Activity_MyInfo.class);

                    intent.putExtra("personinfo", personInfo);

                    startActivity(intent);
                    break;
                }
                case 1: {
                    startActivity(new Intent(getActivity(), Activity_Document.class));
                    break;
                }
                case 2: {
                    startActivity(new Intent(getActivity(), Activity_Position.class));
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    startActivity(new Intent(getActivity(), Activity_Collection.class));
                    break;
                }
                case 5: {
                    break;
                }
                case 6: {
                    break;
                }
                case 7: {
                    break;
                }
                case 8: {
                    break;
                }
                default: {
                    break;
                }
            }


        }
    }

    protected final class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
