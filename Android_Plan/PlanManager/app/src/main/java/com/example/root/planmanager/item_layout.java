package com.example.root.planmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class item_layout extends AppCompatActivity {
    private static final String TAG = "item_layout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_layout);

        ImageView imageView = findViewById(R.id.img_head);
    }


}
