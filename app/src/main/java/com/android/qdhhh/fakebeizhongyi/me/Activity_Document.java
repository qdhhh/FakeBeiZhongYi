package com.android.qdhhh.fakebeizhongyi.me;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.qdhhh.fakebeizhongyi.R;

public class Activity_Document extends AppCompatActivity {

    private SwipeRefreshLayout me_document_srl_id;
    private RecyclerView me_document_rv_id;

    private ImageView me_document_backicon_iv_id;

    private DocumentOnCLickListener documentOnCLickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        setStatus();

        initView();

    }

    private void initView() {

        documentOnCLickListener = new DocumentOnCLickListener();

        me_document_srl_id = (SwipeRefreshLayout)findViewById(R.id.me_document_srl_id);
        me_document_rv_id = (RecyclerView) findViewById(R.id.me_document_rv_id);
        me_document_backicon_iv_id = (ImageView) findViewById(R.id.me_document_backicon_iv_id);

        me_document_backicon_iv_id.setOnClickListener(documentOnCLickListener);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }


    private final class DocumentOnCLickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.me_document_backicon_iv_id:{
                    break;
                }
                default:{
                    break;
                }
            }

        }
    }

}
