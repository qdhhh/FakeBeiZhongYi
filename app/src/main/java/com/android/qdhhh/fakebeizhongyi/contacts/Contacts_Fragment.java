package com.android.qdhhh.fakebeizhongyi.contacts;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.contacts.bean.Contacts;
import com.android.qdhhh.fakebeizhongyi.view.RoundImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.FileRequest;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Contacts_Fragment extends Fragment {

    private View view;
    private View contacts_view_id;
    private ListView contact_ptrllv_id;

    private List<Contacts> friendList;

    private PinYinLVAdapter pinYinLVAdapter;

    private KJHttp kj;

    private View headView;

    private ContactsOnClickListener contactsOnClickListener;

    private LetterIndexView contacts_letter_id;

    private TextView show_letter_in_center;

    public Contacts_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        contact_ptrllv_id = (ListView) view.findViewById(R.id.contact_ptrllv_id);
        contacts_letter_id = (LetterIndexView) view.findViewById(R.id.contacts_letter_id);
        show_letter_in_center = (TextView) view.findViewById(R.id.show_letter_in_center);
        contacts_view_id = view.findViewById(R.id.contact_view_id);
        if (Build.VERSION.SDK_INT < 21) {
            contacts_view_id.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        kj = new KJHttp();

        friendList = new LinkedList<>();

        pinYinLVAdapter = new PinYinLVAdapter(getContext(), friendList);

        setListView();

        setLetterView();

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 设置侧边栏
     */
    private void setLetterView() {

        contacts_letter_id.setTextViewDialog(show_letter_in_center);
        contacts_letter_id.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = pinYinLVAdapter.getPositionForSection(currentChar.charAt(0));
                contact_ptrllv_id.setSelection(positionForSection);
            }
        });
        contact_ptrllv_id.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (friendList.size() == 0) {
                } else {

                    Log.i("HAHA","----"+ firstVisibleItem);

                    int sectionForPosition = pinYinLVAdapter.getSectionForPosition(firstVisibleItem);
                    contacts_letter_id.updateLetterIndexView(sectionForPosition);
                }
            }
        });
    }

    /**
     * listview的相关操作
     */
    private void setListView() {

        headView = View.inflate(getContext(), R.layout.headview_contacts, null);

        contact_ptrllv_id.addHeaderView(headView);

        setHeadView();

        contact_ptrllv_id.setAdapter(pinYinLVAdapter);

        getData();

    }

    /**
     * 头布局的相关操作
     */
    private void setHeadView() {

        contactsOnClickListener = new ContactsOnClickListener();

        headView.findViewById(R.id.contact_head_tab1).setOnClickListener(contactsOnClickListener);
        headView.findViewById(R.id.contact_head_tab2).setOnClickListener(contactsOnClickListener);
        headView.findViewById(R.id.contact_head_tab3).setOnClickListener(contactsOnClickListener);
        headView.findViewById(R.id.contact_head_tab4).setOnClickListener(contactsOnClickListener);

    }

    /**
     * 点击时间监听器
     */
    private final class ContactsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.contact_head_tab1: {
                    break;
                }
                case R.id.contact_head_tab2: {
                    break;
                }
                case R.id.contact_head_tab3: {
                    break;
                }
                case R.id.contact_head_tab4: {
                    break;
                }
                default: {
                    break;
                }
            }

        }
    }


    /**
     * 获取好友列表数据
     */
    private void getData() {

        HttpParams params = new HttpParams();

        params.put("userid", LocalInfo.userid);
        params.put("token", LocalInfo.token);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_GetFriendsList), params, false,
                new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {

                        try {
                            String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                            if ("200".equals(code)) {
                                setData(new JSONObject(t).getString("friendslist"));
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
     * 设置listview的数据
     */
    private void setData(String string) {

        friendList = JSON.parseArray(string, Contacts.class);

        formatData();
        pinYinLVAdapter.notifyDataSetChanged();
    }


    /**
     * 数据规范化
     */
    private void formatData() {

        for (int i = 0; i < friendList.size(); i++) {

            String convert = ChineseToPinyinHelper.getInstance().getPinyin(friendList.get(i).getUsername()).toUpperCase();
            friendList.get(i).setPinyin(convert);
            String substring = convert.substring(0, 1);
            if (substring.matches("[A-Z]")) {
                friendList.get(i).setFirstLetter(substring);
            } else {
                friendList.get(i).setFirstLetter("#");
            }
        }

        Collections.sort(friendList, new Comparator<Contacts>() {
            @Override
            public int compare(Contacts lhs, Contacts rhs) {
                if (lhs.getFirstLetter().contains("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().contains("#")) {
                    return -1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });

        Log.i("HAHA", friendList.toString());


    }

    public class PinYinLVAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public PinYinLVAdapter(Context context, List<Contacts> list) {

            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return friendList.size();
        }

        @Override
        public Object getItem(int position) {
            return friendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PinYinLVAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_contacts_list, null);
                holder =new  PinYinLVAdapter.ViewHolder();
                holder.item_contacts_name_tv_id = (TextView) convertView.findViewById(R.id.item_contacts_name_tv_id);
                holder.item_contacts_company_tv_id = (TextView) convertView.findViewById(R.id.item_contacts_company_tv_id);
                holder.item_contacts_pinyin_tv_id = (TextView) convertView.findViewById(R.id.item_contacts_pinyin_tv_id);
                holder.item_contacts_img_iv_id = (RoundImageView) convertView.findViewById(R.id.item_contacts_img_iv_id);
                convertView.setTag(holder);
            } else {
                holder = (PinYinLVAdapter.ViewHolder) convertView.getTag();
            }
            Contacts user = friendList.get(position);
            holder.item_contacts_name_tv_id.setText(user.getUsername());
            holder.item_contacts_company_tv_id.setText(user.getCompany());
            Picasso.with(getContext()).load(user.getIconimg()).into(holder.item_contacts_img_iv_id);

            //获得当前position是属于哪个分组
            int sectionForPosition = user.getFirstLetter().charAt(0);
            //获得该分组第一项的position
            int positionForSection = getPositionForSection(sectionForPosition);
            //查看当前position是不是当前item所在分组的第一个item
            //如果是，则显示showLetter，否则隐藏
            if (position == positionForSection) {
                holder.item_contacts_pinyin_tv_id.setVisibility(View.VISIBLE);
                holder.item_contacts_pinyin_tv_id.setText(user.getFirstLetter());
            } else {
                holder.item_contacts_pinyin_tv_id.setVisibility(View.GONE);
            }
            return convertView;
        }


        //传入一个分组值[A....Z],获得该分组的第一项的position
        public int getPositionForSection(int sectionIndex) {
            for (int i = 0; i < friendList.size(); i++) {
                if (friendList.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                    return i;
                }
            }
            return -1;
        }

        //传入一个position，获得该position所在的分组
        public int getSectionForPosition(int position) {
            return friendList.get(position).getFirstLetter().charAt(0);
        }

        class ViewHolder {
            RoundImageView item_contacts_img_iv_id;
            TextView item_contacts_name_tv_id, item_contacts_company_tv_id, item_contacts_pinyin_tv_id;
        }
    }
}
