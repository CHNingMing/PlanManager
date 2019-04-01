package com.example.root.planmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.datasources.MySqlDS;
import com.datasources.PlanData;
import com.entity.PlanItem;

import java.util.List;

public class Already_plan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_plan);
        List<PlanItem> planItemList = PlanData.getPlanItemsByState(false,3,4);
        ListView listView = findViewById(R.id.list_alreadyplan);
        PlanItemAdapter planItemAdapter = new PlanItemAdapter(this,R.layout.activity_item_layout,planItemList);
        listView.setAdapter(planItemAdapter);

    }
}
