package com.example.root.planmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.entity.PlanItem;

import java.util.List;

/**
 * Created by root on 19-3-31.
 */

public class PlanItemAdapter extends ArrayAdapter {
    public PlanItemAdapter( Context context, int resource,  List<PlanItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PlanItem planItem = (PlanItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_layout,null);
        TextView textView = view.findViewById(R.id.t_plan_state);
        Button button = view.findViewById(R.id.btn_ok);
        ImageView imageView = view.findViewById(R.id.img_head);
        textView.setText(planItem.getPlan_name());
        button.setText(planItem.getPlan_id()+"");
        switch ( planItem.getPlan_state() ){
            case 0:
                //未开始
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_start));
                break;
            case 1:
                //进行中
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_conduct));
                break;
            case 2:
                //暂停
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_stop));
                break;
            case 3:
                //失败
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_fail));
                break;
            case 4:
                //成功
                imageView.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_success));
                break;
        }


        return view;
    }
}
