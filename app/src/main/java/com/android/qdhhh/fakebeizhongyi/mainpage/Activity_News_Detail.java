package com.android.qdhhh.fakebeizhongyi.mainpage;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.mainpage.bean.Main_News_Head_Bean;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

public class Activity_News_Detail extends AppCompatActivity {

    private ImageView news_detail_iv_collection_id;

    private NewsDetailClickListener newsDetailClickListener;

    private Main_News_Head_Bean main_News_Head_Bean;

    private WebView news_detail_wv_id;

    private boolean collectionStates;

    private AlertDialog me_news_comment_dialog;

    private View comment_view;

    private EditText me_news_comment_et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__news__detail);

        main_News_Head_Bean = (Main_News_Head_Bean) getIntent().getSerializableExtra("URL");

        initView();

        setWebView();

        checkIsCollected();

        initDialog();

    }

    private void initDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        comment_view = View.inflate(this, R.layout.dialog_news_comment, null);

        me_news_comment_et_id = (EditText) comment_view.findViewById(R.id.me_news_comment_et_id);

        builder.setTitle("评论").setView(comment_view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                me_news_comment_dialog.dismiss();
            }
        });

        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                me_news_comment_dialog.dismiss();
                postOnComment();
            }
        });

        me_news_comment_dialog = builder.create();
    }

    private void postOnComment() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("newsid", main_News_Head_Bean.getNewsid());
        params.put("commentcontent", me_news_comment_et_id.getText().toString());

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_AddNewsComment), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String result_Code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("201".equals(result_Code)) {
                        Toast.makeText(Activity_News_Detail.this, "等待审核", Toast.LENGTH_SHORT).show();
                    }
                    if ("200".equals(result_Code)) {
                        Toast.makeText(Activity_News_Detail.this, "评论成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_News_Detail.this, "评论失败", Toast.LENGTH_SHORT).show();
                super.onFailure(errorNo, strMsg);
            }
        });


    }


    /**
     * 检查是否是已收藏状态
     */
    private void checkIsCollected() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("newsid", main_News_Head_Bean.getNewsid());

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_CheckSaving), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String error_Code = new JSONObject(t)
                            .optJSONObject("result").getString("error_code");

                    if ("200".equals(error_Code)) {

                        int code = new JSONObject(t).getInt("issave");

                        if (code == 1) {
                            collectionStates = true;
                            news_detail_iv_collection_id.setImageResource(R.drawable.weishoucang_s2x);
                        }
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

    private void setWebView() {

        news_detail_wv_id.loadUrl(main_News_Head_Bean.getDetailurl());

        WebSettings settings = news_detail_wv_id.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        news_detail_wv_id.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

    }

    private void initView() {

        newsDetailClickListener = new NewsDetailClickListener();

        news_detail_iv_collection_id = (ImageView) findViewById(R.id.news_detail_iv_collection_id);
        news_detail_wv_id = (WebView) findViewById(R.id.news_detail_wv_id);

        ((LinearLayout) findViewById(R.id.news_detail_ll_back)).setOnClickListener(newsDetailClickListener);
        ((LinearLayout) findViewById(R.id.news_detail_ll_collection)).setOnClickListener(newsDetailClickListener);
        ((LinearLayout) findViewById(R.id.news_detail_ll_comment)).setOnClickListener(newsDetailClickListener);
        ((LinearLayout) findViewById(R.id.news_detail_ll_more)).setOnClickListener(newsDetailClickListener);

    }


    /**
     * 控件的点击事件
     */
    private final class NewsDetailClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.news_detail_ll_back: {
                    onBackPressed();
                    break;
                }
                case R.id.news_detail_ll_collection: {
                    changeCollectionStates();
                    break;
                }
                case R.id.news_detail_ll_comment: {
                    me_news_comment_dialog.show();
                    break;
                }
                case R.id.news_detail_ll_more: {
                    Toast.makeText(Activity_News_Detail.this, "敬请期待", Toast.LENGTH_SHORT).show();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


    /**
     * 改变收藏状态
     */
    private void changeCollectionStates() {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();
        /**
         * 请求时传入0代表新闻，1代表图片
         */
        params.put("token", LocalInfo.token);
        params.put("userid", LocalInfo.userid);
        params.put("newsid", main_News_Head_Bean.getNewsid());
        params.put("type", 0);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_FavouriteNews), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    int code = new JSONObject(t).getInt("response");

                    if (code == 1) {
                        collectionStates = !collectionStates;

                        Toast.makeText(Activity_News_Detail.this, collectionStates ? "已收藏" : "取消收藏", Toast.LENGTH_SHORT).show();
                        if (collectionStates) {
                            news_detail_iv_collection_id.setImageResource(R.drawable.weishoucang_s2x);
                        } else {
                            news_detail_iv_collection_id.setImageResource(R.drawable.weishoucang_2x);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {

                Toast.makeText(Activity_News_Detail.this, "数据请求失败", Toast.LENGTH_SHORT).show();

                super.onFailure(errorNo, strMsg);
            }
        });
    }


}
