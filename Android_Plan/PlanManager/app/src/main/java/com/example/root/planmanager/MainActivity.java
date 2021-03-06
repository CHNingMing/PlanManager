package com.example.root.planmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.datasources.MySqlDS;
import com.datasources.PlanData;
import com.entity.PlanItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public static AppCompatActivity currActivity;

    public static Integer itemState = 0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //连接数据库
        MySqlDS.init(getAssets());
        List<PlanItem> planItemList = PlanData.getPlanItemsByState(true,0,1,2);
        //初始化界面
        setContentView(R.layout.activity_main);
        //设置事件
        setEvent();
        currActivity = this;
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView listView = findViewById(R.id.list_planitem);
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item_layout_itemsOpt item_layout_itemsOpt_a = view.findViewById(R.id.list_itemopt);
                Log.d(TAG,"修改任务");

            }
        });
        loadViewData(listView,planItemList);

        //getResources().getStringArray(R.array.names)

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,strs);

    }

    public static void loadViewData(ListView listView,List<PlanItem> planItems,AppCompatActivity p_currActivity,int p_resource){
        List<String> strs = new ArrayList<>();
        for( PlanItem planItem : planItems ){
            strs.add(planItem.getPlan_name());
        }
        AppCompatActivity appCompatActivity = p_currActivity != null ? p_currActivity : currActivity;
        int resource = p_resource == -1 ? R.layout.activity_item_layout : p_resource;
        PlanItemAdapter planItemAdapter = new PlanItemAdapter(currActivity,resource,planItems);
        listView.setAdapter(planItemAdapter);
    }
    public static void loadViewData(ListView listView,List<PlanItem> planItems){
        loadViewData(listView,planItems,null,-1);
    }

    /***
     * 设置事件
     */
    private void setEvent(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button t_searchplan = findViewById(R.id.btn_search);
        t_searchplan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if( MySqlDS.isConnClose() ){
                    Toast.makeText(MainActivity.this, "连接成功!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Button btn_reload = findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                List<PlanItem> planItemList = PlanData.getPlanItemsByState(true,0,1,2);
                ListView listView = findViewById(R.id.list_planitem);
                loadViewData(listView,planItemList);


            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch ( id ){
            case R.id.m_plan_create:
                Intent intent = new Intent(MainActivity.this,CreatePlan.class);
                startActivity(intent);
                break;
            case R.id.m_plan_alreadyplan:
                startActivity(new Intent(MainActivity.this,Already_plan.class));
                break;
            case R.id.m_plan_alreadydel:
                //删除项列表

                break;



        }
//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
