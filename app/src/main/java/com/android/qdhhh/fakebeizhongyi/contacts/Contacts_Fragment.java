package com.android.qdhhh.fakebeizhongyi.contacts;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.qdhhh.fakebeizhongyi.R;

public class Contacts_Fragment extends Fragment {

    private View view;
    private View contacts_view_id;


    public Contacts_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contacts, container, false);

        contacts_view_id = view.findViewById(R.id.contact_view_id);

        if (Build.VERSION.SDK_INT < 21) {
            contacts_view_id.setVisibility(View.GONE);
        }

        return view;

    }

}
