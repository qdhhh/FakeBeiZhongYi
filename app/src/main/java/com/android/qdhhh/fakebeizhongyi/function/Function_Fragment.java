package com.android.qdhhh.fakebeizhongyi.function;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.function.activity.Activity_Activity;


public class Function_Fragment extends Fragment {


    private RecyclerView funvtion_rv_id;
    private int[] pic_Counts = {R.drawable.xiaoyoujuanzeng2x, R.drawable.xiaoyoufengcai2x, R.drawable.zhaoxiaoyou2x, R.drawable.zhaozuzhi2x, R.drawable.zhaogongzuo2x, R.drawable.zhaoziyuan2x, R.drawable.fabuzhiwei2x, R.drawable.tuijiangeihaoyou2x, R.drawable.tuijiangeihaoyou2x};
    private String[] function_Names;
    private View view;

    private View function_view_id;


    public Function_Fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        function_Names = getResources().getStringArray(R.array.function_Names);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_function, container, false);
        funvtion_rv_id = (RecyclerView) view.findViewById(R.id.funvtion_rv_id);
        function_view_id = (View) view.findViewById(R.id.function_view_id);
        if (Build.VERSION.SDK_INT < 21) {
            function_view_id.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        setRecyclerView();

        super.onActivityCreated(savedInstanceState);
    }

    private void setRecyclerView() {
        funvtion_rv_id.setAdapter(new FunctionAdapter());

        funvtion_rv_id.setLayoutManager(new GridLayoutManager(getContext(), 3));

    }


    private final class FunctionAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = View.inflate(getContext(), R.layout.item_function, null);

            FunctionViewHolder vh = new FunctionViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((FunctionViewHolder) holder).function_item_iv_id.setImageResource(pic_Counts[position]);
            ((FunctionViewHolder) holder).function_item_tv_id.setText(function_Names[position]);
        }

        @Override
        public int getItemCount() {
            return pic_Counts.length;
        }
    }

    protected final class FunctionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView function_item_iv_id;

        TextView function_item_tv_id;

        public FunctionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            function_item_iv_id = (ImageView) itemView.findViewById(R.id.function_item_iv_id);
            function_item_tv_id = (TextView) itemView.findViewById(R.id.function_item_tv_id);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(getContext(), function_Names[getAdapterPosition()] + "----", Toast.LENGTH_SHORT).show();
            switch (getAdapterPosition()) {
                case 0: {
                    break;
                }
                case 1: {
                    startActivity(new Intent(getContext(), Activity_Activity.class));
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
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


}
