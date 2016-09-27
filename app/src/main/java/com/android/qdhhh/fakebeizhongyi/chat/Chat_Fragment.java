package com.android.qdhhh.fakebeizhongyi.chat;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.qdhhh.fakebeizhongyi.R;

public class Chat_Fragment extends Fragment {

    private View view;
    private View chat_view_id;


    public Chat_Fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        chat_view_id = view.findViewById(R.id.chat_view_id);

        if (Build.VERSION.SDK_INT < 21) {
            chat_view_id.setVisibility(View.GONE);
        }


        return view;

    }

}
