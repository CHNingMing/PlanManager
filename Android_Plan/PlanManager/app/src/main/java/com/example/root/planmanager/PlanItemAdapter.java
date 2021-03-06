package com.example.root.planmanager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.Touch;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.datasources.MySqlDS;
import com.datasources.PlanData;
import com.entity.PlanDateItem;
import com.entity.PlanItem;
import com.mysqlutil.MySqlDataUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by root on 19-3-31.
 * 自定义列表处理类
 */

public class PlanItemAdapter extends ArrayAdapter {
    private static final String TAG = "PlanItemAdapter";
    private static View currentView;
    public PlanItemAdapter( Context context, int resource,  List<PlanItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final PlanItem planItem = (PlanItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_layout,null);
        currentView = view;
        TextView textView = view.findViewById(R.id.t_plan_state);
        //任务状态
        ItemImageItemsOpt imageView = view.findViewById(R.id.img_head);
        //删除图标
        ItemImageItemsOpt img_delplan = view.findViewById(R.id.img_delplan);
        //成功图标
        ItemImageItemsOpt img_success = view.findViewById(R.id.img_success);
        //失败图标
        ItemImageItemsOpt img_fail = view.findViewById(R.id.img_fail);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);


        imageView.setPlan_id(planItem.getPlan_id());
        imageView.setPlan_state(planItem.getPlan_state());
        img_delplan.setPlan_id(planItem.getPlan_id());
        img_success.setPlan_id(planItem.getPlan_id());
        img_fail.setPlan_id(planItem.getPlan_id());

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
        textView.setText(planItem.getPlan_name());

        switch (MainActivity.itemState){
            case 0:
            /* ========================== */
                    // 任务列表显示时
                    img_delplan.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_del));
                    img_success.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_success));
                    img_fail.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_fail));

                    item_layout_itemsOpt item_layout_itemsOpt_a = view.findViewById(R.id.list_itemopt);


                    //item_layout_itemsOpt_a.setPlan_id(planItem.getPlan_id());

                    //删除任务
                    img_delplan.setOnLongClickListener(new View.OnLongClickListener(){

                        @Override
                        public boolean onLongClick(View view) {
                            ItemImageItemsOpt itemImageItemsOpt = view.findViewById(R.id.img_delplan);
                            PlanItem planItem1 = new PlanItem();
                            planItem.setPlan_id(itemImageItemsOpt.getPlan_id());
                            planItem.setDel_flag(true);
                            MySqlDS.update(planItem);
                            MySqlDS.printMsg("删除成功！",getContext());
                            reloadList();
                            //Toast.makeText(getContext(), "删除成功！"+planItem.getPlan_id(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    //任务失败
                    img_fail.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            ItemImageItemsOpt itemImageItemsOpt = view.findViewById(R.id.img_fail);
                            PlanItem planItem1 = new PlanItem();
                            planItem.setPlan_id(itemImageItemsOpt.getPlan_id());
                            planItem.setPlan_state(3);
                            MySqlDS.update(planItem);
                            MySqlDS.printMsg("操作成功！，继续努力。",getContext());
                            reloadList();
                            return true;
                        }
                    });
                    //任务成功
                    img_success.setOnLongClickListener(new View.OnLongClickListener(){

                        @Override
                        public boolean onLongClick(View view) {
                            ItemImageItemsOpt itemImageItemsOpt = view.findViewById(R.id.img_success);
                            PlanItem planItem1 = new PlanItem();
                            planItem.setPlan_id(itemImageItemsOpt.getPlan_id());
                            planItem.setPlan_state(4);
                            MySqlDS.update(planItem);
                            MySqlDS.printMsg("操作成功！坚持...。",getContext());
                            reloadList();
                            return true;
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
                            PlanDateItem planDateItem = new PlanDateItem();
                            planDateItem.setPlanId(itemImageItemsOpt.getPlan_id());
                            t_planItem1.setPlan_id(itemImageItemsOpt.getPlan_id());
                            switch( itemImageItemsOpt.getPlan_state() ){
                                case 0:
                                    //修改为进行中
                                    t_planItem1.setPlan_state(1);
                                    planDateItem.setBeginDate(new Timestamp(System.currentTimeMillis()));
                                    MySqlDS.insert(planDateItem);
                                    break;
                                case 1:
                                    //修改为暂停
                                    t_planItem1.setPlan_state(2);
                                    planDateItem.setEndDate(new Timestamp(System.currentTimeMillis()));
                                    MySqlDS.update(planDateItem,"plan_id = "+itemImageItemsOpt.getPlan_id()+" and end_date is null");
                                    break;
                                case 2:
                                    //修改为进行中
                                    planDateItem.setBeginDate(new Timestamp(System.currentTimeMillis()));
                                    t_planItem1.setPlan_state(1);
                                    MySqlDS.insert(planDateItem);
                                    break;
                            }
                            MySqlDS.update(t_planItem1);
                            //移除所选项
                            //Toast.makeText(getContext(),"操作成功",Toast.LENGTH_SHORT).show();
                            MySqlDS.printMsg("操作成功",getContext());
                            reloadList();
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
                    img_delplan.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            MySqlDS.printMsg("长按删除任务",getContext());
                        }
                    });
                    img_success.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            MySqlDS.printMsg("长按完成任务",getContext());
                        }
                    });
                    img_fail.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            MySqlDS.printMsg("长按以失败结束任务",getContext());
                        }
                    });

            /* =============END============= */
            break;
            case 1:
            /* ========================== */
            //已处理列表显示时
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            layoutParams.width = 800;
            textView.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParamsBar = progressBar.getLayoutParams();
            layoutParamsBar.width = 800;
            progressBar.setLayoutParams(layoutParamsBar);
            img_delplan.setImageDrawable(view.getResources().getDrawable(R.drawable.plan_recovery));
            img_delplan.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    MySqlDS.printMsg("长按恢复删除的任务",getContext());
                }
            });
            img_delplan.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    ItemImageItemsOpt itemImageItemsOpt = view.findViewById(R.id.img_delplan);
                    PlanItem t_planItem = new PlanItem();
                    t_planItem.setPlan_id(itemImageItemsOpt.getPlan_id());
                    t_planItem.setPlan_state(0);
                    MySqlDS.update(t_planItem);
                    MySqlDS.printMsg("恢复成功！",getContext());
                    reloadList();
                    return false;
                }
            });

            /* ============END========== */
            break;
        }



        return view;
    }

    /**
     * 重新加载
     */
    private void reloadList(){
        switch (MainActivity.itemState){
            case 0:
                ListView listView = MainActivity.currActivity.findViewById(R.id.list_planitem);
                List<PlanItem> planItemList = PlanData.getPlanItemsByState(true,0,1,2);
                List<String> strs = new ArrayList<>();
                for( PlanItem planItem : planItemList ){
                    strs.add(planItem.getPlan_name());
                }
                PlanItemAdapter planItemAdapter = new PlanItemAdapter(getContext(),R.layout.activity_item_layout,planItemList);
                listView.setAdapter(planItemAdapter);
                break;
            case 1:
                Already_plan.loadListViewData();
/*                ListView listAlreadyListView = Already_plan.currActivity.findViewById(R.id.img_delplan);
                List<PlanItem> listAlreadyList = PlanData.getPlanItemsByState(true,0,1,2);
                List<String> strsAlready = new ArrayList<>();
                for( PlanItem planItem : listAlreadyList ){
                    strsAlready.add(planItem.getPlan_name());
                }
                PlanItemAdapter planItemAlreadyAdapter = new PlanItemAdapter(getContext(),R.layout.activity_item_layout,listAlreadyList);
                listAlreadyListView.setAdapter(planItemAlreadyAdapter);*/
                break;
        }


    }

}
