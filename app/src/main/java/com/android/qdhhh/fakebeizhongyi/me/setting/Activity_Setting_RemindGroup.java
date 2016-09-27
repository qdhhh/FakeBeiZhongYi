package com.android.qdhhh.fakebeizhongyi.me.setting;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.me.bean.GroupRemindBean;
import com.android.qdhhh.fakebeizhongyi.me.bean.SimpleRemainBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.LinkedList;
import java.util.List;

public class Activity_Setting_RemindGroup extends AppCompatActivity {

    private RecyclerView groupremind_setting_rv_id;
    private CheckBox groupremind_setting_cb_id;
    private ImageView me_setting_groupremind_backicon_iv_id;

    private GroupSettingOnClickListener groupSettingOnClickListener;

    private List<GroupRemindBean> listData;
    private GroupSettingRevyclerViewAdapter groupSettingRevyclerViewAdapter;

    private boolean mainCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_remind_group);
        setStatus();

        initView();

        setRecyclerView();
    }


    /**
     * recyclerview的相关操作
     */
    private void setRecyclerView() {

        getData();

        groupSettingRevyclerViewAdapter = new GroupSettingRevyclerViewAdapter();

        groupremind_setting_rv_id.setLayoutManager(new GridLayoutManager(this, 1));

        groupremind_setting_rv_id.setAdapter(groupSettingRevyclerViewAdapter);

    }

    /**
     * 请求数据
     */
    private void getData() {

        listData = new LinkedList<>();

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_GroupStatus), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        listData = JSON.parseArray(new JSONObject(t).
                                getString("grpsettings"), GroupRemindBean.class);
                        groupSettingRevyclerViewAdapter.notifyDataSetChanged();

                        setMainCHeckBox();
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
        });
    }

    private void setMainCHeckBox() {

        mainCheckBox = true;

        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getIfreceive() == 0) {
                mainCheckBox = false;
                break;
            }
        }
        groupremind_setting_cb_id.setChecked(mainCheckBox);
    }

    /**
     * 初始化控件
     */
    private void initView() {

        groupSettingOnClickListener = new GroupSettingOnClickListener();

        groupremind_setting_rv_id = (RecyclerView) findViewById(R.id.groupremind_setting_rv_id);
        groupremind_setting_cb_id = (CheckBox) findViewById(R.id.groupremind_setting_cb_id);
        me_setting_groupremind_backicon_iv_id = (ImageView) findViewById(R.id.me_setting_groupremind_backicon_iv_id);

        me_setting_groupremind_backicon_iv_id.setOnClickListener(groupSettingOnClickListener);
        groupremind_setting_cb_id.setOnClickListener(groupSettingOnClickListener);


    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    /**
     * 控件的点击监听器
     */
    private final class GroupSettingOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.me_setting_groupremind_backicon_iv_id: {
                    upDataGroupData();
                    onBackPressed();
                    break;
                }
                case R.id.groupremind_setting_cb_id: {
                    boolean b = groupremind_setting_cb_id.isChecked();

                    for (int i = 0; i < listData.size(); i++) {
                        listData.get(i).setIfreceive(b ? 1 : 0);
                    }
                    groupSettingRevyclerViewAdapter.notifyDataSetChanged();
                    break;
                }
            }


        }
    }

    /**
     * recyclerview的适配器
     */
    private final class GroupSettingRevyclerViewAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            GroupSettingViewHolder groupSettingViewHolder = new GroupSettingViewHolder(View.inflate

                    (Activity_Setting_RemindGroup.this, R.layout.item_remind_group, null));

            return groupSettingViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((GroupSettingViewHolder) holder).item_group_remind_tv_id.setText(listData.get(position).getGroupname());

            ((GroupSettingViewHolder) holder).item_group_remind_cb_id.setChecked(listData.get(position).getIfreceive() == 1 ? true : false);

        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }


    /**
     * revyvlerview的viewholder
     */
    private final class GroupSettingViewHolder extends RecyclerView.ViewHolder {

        TextView item_group_remind_tv_id;
        CheckBox item_group_remind_cb_id;

        View.OnClickListener itemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean b = ((CheckBox) v).isChecked();

                listData.get(getAdapterPosition()).setIfreceive(b ? 1 : 0);
                setMainCHeckBox();
                groupSettingRevyclerViewAdapter.notifyDataSetChanged();
            }
        };

        public GroupSettingViewHolder(View itemView) {

            super(itemView);

            item_group_remind_cb_id = (CheckBox) itemView.findViewById(R.id.item_group_remind_cb_id);
            item_group_remind_tv_id = (TextView) itemView.findViewById(R.id.item_group_remind_tv_id);

            item_group_remind_cb_id.setOnClickListener(itemOnClickListener);

        }
    }

    private void upDataGroupData() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        List<SimpleRemainBean> list = new LinkedList<>();

        for (int i = 0; i < listData.size(); i++) {
            list.add(new SimpleRemainBean(listData.get(i).getGroupid(), listData.get(i).getIfreceive()));
        }

        String array = JSON.toJSONString(list);

        try {
            JSONObject obj = new JSONObject();

            obj.put("token", LocalInfo.token);
            obj.put("userid", LocalInfo.userid);
            obj.put("grpsettings", array);

            params.putJsonParams(obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_GroupSetting), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        Toast.makeText(Activity_Setting_RemindGroup.this, "更新群组信息成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Setting_RemindGroup.this, "更新群组信息失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_Setting_RemindGroup.this, "更新群组信息失败", Toast.LENGTH_SHORT).show();
                super.onFailure(errorNo, strMsg);
            }
        });
    }
}
