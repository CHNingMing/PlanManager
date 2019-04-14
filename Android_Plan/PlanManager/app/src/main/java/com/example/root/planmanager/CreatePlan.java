package com.example.root.planmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datasources.MySqlDS;
import com.entity.PlanItem;

import java.sql.Timestamp;
import java.util.Date;

public class CreatePlan extends AppCompatActivity {

    private static final String TAG = "CreatePlan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        EditText editText = findViewById(R.id.e_planinfo);
        Log.i(TAG,editText.getHeight()+"");
        editText.setHeight(editText.getHeight()-20);
        Button button = findViewById(R.id.btn_createplan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存任务操作
                //标题
                TextView txtplanname = findViewById(R.id.e_plan_name);
                //详细
                TextView txtplaninfo = findViewById(R.id.e_planinfo);
                PlanItem planItem = new PlanItem();
                planItem.setPlan_state(0);
                planItem.setCreate_date(new Timestamp(System.currentTimeMillis()));
                planItem.setPlan_info(txtplaninfo.getText().toString());
                planItem.setPlan_name(txtplanname.getText().toString());
                Object rsObj = MySqlDS.insert(planItem);
                if( rsObj != null ){
                    Toast.makeText(CreatePlan.this,"保存成功!",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(CreatePlan.this,"==保存失败!==",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
