package com.example.root.planmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                
                Toast.makeText(CreatePlan.this,"保存成功!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
