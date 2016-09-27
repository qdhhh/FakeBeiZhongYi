package com.android.qdhhh.fakebeizhongyi.mainpage.childfragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.qdhhh.fakebeizhongyi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_OneOfUs extends Fragment {

    private View view;

    private WebView mainpage_oneofus_wv_id;


    public Fragment_OneOfUs() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_one_of_us, container, false);

        mainpage_oneofus_wv_id = (WebView) view.findViewById(R.id.mainpage_oneofus_wv_id);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        WebSettings settings = mainpage_oneofus_wv_id.getSettings();
        settings.setJavaScriptEnabled(true);
        mainpage_oneofus_wv_id.loadUrl("http://www.baidu.com");

        mainpage_oneofus_wv_id.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        super.onActivityCreated(savedInstanceState);
    }
}
