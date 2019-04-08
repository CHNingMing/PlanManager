package com.example.root.planmanager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datasources.MySqlDS;
import com.entity.PlanItem;
import com.mysqlutil.MySqlDataUtil;

import java.util.List;

/**
 * Created by root on 19-3-31.
 * 自定义列表处理类
 */

public class PlanItemAdapter extends ArrayAdapter {
    private static final String TAG = "PlanItemAdapter";

    public PlanItemAdapter( Context context, int resource,  List<PlanItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final PlanItem planItem = (PlanItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_layout,null);
        TextView textView = view.findViewById(R.id.t_plan_state);
        ItemImageItemsOpt imageView = view.findViewById(R.id.img_head);
        ItemImageItemsOpt img_delplan = view.findViewById(R.id.img_delplan);
        img_delplan.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_del));
        item_layout_itemsOpt item_layout_itemsOpt_a = view.findViewById(R.id.list_itemopt);
        imageView.setPlan_id(planItem.getPlan_id());
        imageView.setPlan_state(planItem.getPlan_state());
        //item_layout_itemsOpt_a.setPlan_id(planItem.getPlan_id());
        textView.setText(planItem.getPlan_name());
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

        img_delplan.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {


                Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        /**
         * 1:进行中
         * 2:暂停
         */
        imageView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                ItemImageItemsOpt itemImageItemsOpt = view.findViewById(R.id.img_head);
                PlanItem t_planItem1 = new PlanItem();
                t_planItem1.setPlan_id(itemImageItemsOpt.getPlan_id());
                switch( itemImageItemsOpt.getPlan_state() ){
                    case 0:
                        //修改为进行中
                        t_planItem1.setPlan_state(1);
                        break;
                    case 1:
                        //修改为暂停
                        t_planItem1.setPlan_state(2);
                        break;
                    case 2:
                        //修改为进行中
                        t_planItem1.setPlan_state(1);
                        break;
                }
                MySqlDS.update(t_planItem1);
                Toast.makeText(getContext(),"操作成功",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ItemImageItemsOpt itemImageItemsOpt = view.findViewById(R.id.img_head);
                String msg = null;
                switch (itemImageItemsOpt.getPlan_state()){
                    case 0:
                        msg = "长按开始任务";
                        break;
                    case 1:
                        msg = "长按暂停任务";
                        break;
                    case 2:
                        msg = "长按继续任务";
                        break;
                }
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
