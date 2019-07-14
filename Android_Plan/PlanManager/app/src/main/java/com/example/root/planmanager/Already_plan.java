package com.example.root.planmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.datasources.MySqlDS;
import com.datasources.PlanData;
import com.entity.PlanItem;

import java.util.List;

public class Already_plan extends AppCompatActivity {

    public static AppCompatActivity currActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.itemState = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_plan);
        currActivity = this;
        loadListViewData();
    }
    public static void loadListViewData(){
        List<PlanItem> planItemList = PlanData.getPlanItemsByState(false,3,4);
        ListView listView = Already_plan.currActivity.findViewById(R.id.list_alreadyplan);
        MainActivity.loadViewData(listView,planItemList,currActivity,R.id.list_alreadyplan);
    }

    @Override
    protected void onStop() {
        MainActivity.itemState = 0;
        super.onStop();
    }
}
