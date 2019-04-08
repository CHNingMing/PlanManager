package com.datasources;

import android.content.res.AssetManager;
import android.util.Log;


import com.entity.PlanItem;
import com.mysqlutil.MySqlDataUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * MySqlDataSource
 * Created by root on 19-3-26.
 * Android MySql 数据库操作
 */

public class MySqlDS {

    private static final String TAG = "MySqlDataSource";
    private static  Connection connection = null;

    private static Map<String,Object> t_objs = new HashMap<>();
    /**
     * 初始化连接方法
     * @param assetManager
     */
/*    public static void init(AssetManager assetManager){
        try {
            InputStream jdbcconfig = assetManager.open("jdbc.properties");
            //创建连接对象
            Conn conn = new Conn(assetManager);
            Thread t1 = new Thread(conn);
            //开始连接
            t1.start();
            //获取连接
            connection = conn.getConn();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 执行普通SQL
     * @return
     */
/*    public static <V> List<V> querySql(String sql,Class entityClass){
        Conn conn = new Conn(sql);
        Thread t1 = new Thread(conn);
        t1.start();
        ResultSet resultSet = conn.getResultSet();
        List<V> planItemList = new ArrayList<>();
        List<Map<String, Object>> maps = MySqlDataUtil.converListMap(resultSet);
        if( maps == null ){
            return null;
        }
        for( Map<String, Object> i_map : maps ){
            V planItem = MySqlDataUtil.setField(PlanItem.class,i_map);
            planItemList.add(planItem);
        }
        System.out.println(planItemList);
        return planItemList;
    }*/

    /**
     * 初始化连接方法
     * @param assetManager
     */
    public static void init(AssetManager assetManager){
        try {
            final InputStream jdbcconfig = assetManager.open("jdbc.properties");
            Thread thread =  new Thread(){
                @Override
                public void run() {
                    MySqlDataUtil.init(jdbcconfig);
                    connection = MySqlDataUtil.getConn();
                    Log.i(TAG,"===================================!");
                    Log.i(TAG,"连接成功!");
                }
            };
            thread.start();
            thread.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 执行普通SQL
     * @return
     */
    public static <V> List<V> querySql(final String sql, final Class entityClass){
        Thread t =  new Thread(){
            @Override
            public void run() {
                List<V> list = MySqlDataUtil.querySql(entityClass,sql);
                t_objs.put("querySql",list);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<V> list =  (List<V>)t_objs.get("querySql");
        return list;
    }

    /**
     * 删除数据
     * @param newObj
     * @return
     */
    public static boolean delete(final Object newObj){
        Thread t = new Thread(){
            @Override
            public void run() {
                t_objs.put("delObj",MySqlDataUtil.update(newObj));
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if( t_objs.get("delObj") != null ){
            return true;
        }
        return false;

    }

    /***
     * 更新数据
     * 保证实体主键描述属性不为null,非空项不为null
     * @param newObj 更新数据对象
     * @return
     */
    public static <T> T update(final Object newObj){
        Thread t = new Thread(){
            @Override
            public void run() {
                t_objs.put("updateObj",MySqlDataUtil.update(newObj));
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object t_obj = t_objs.get("updateObj");
        if( t_obj != null ){
            return (T)t_obj;
        }else{
            return null;
        }
    }






    /**
     * 是否连接
     * @return true: 连接,false: 关闭
     */
    public static boolean isConnClose(){
        return connection != null;
    }




}



/**
 * 连接类
 */
class Conn implements Runnable {
    private static final String TAG = "getConn";
    //连接数据库
    private static Connection connection = null;
    private InputStream jdbcconfig;
    //执行SQL
    private String exec_sql = null;
    private static ResultSet resultSet;
    //记录获取连接次数
    int conn_count = 0;

    //记录执行次数
    int start_count = 0;


    /***
     * 获取jdbc配置文件流
     * @param assetManager 配置文件流
     */
    public Conn(AssetManager assetManager) {
        try {
            jdbcconfig = assetManager.open("jdbc.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行Sql语句
     * @param exec_sql
     */
    public Conn (String exec_sql){
        this.exec_sql = exec_sql;
    }


    @Override
    public void run() {
        Log.i(TAG,"执行次数:"+start_count++);

        //未连接时先连接
        if( !MySqlDataUtil.isConnClose() ){
            MySqlDataUtil.init(jdbcconfig);
            if( MySqlDataUtil.isConnClose() ){
                connection = MySqlDataUtil.getConn();
                Log.i(TAG,"===================================!");
                Log.i(TAG,"连接成功!");
            }else{
                Log.e(TAG,"===================================!");
                Log.e(TAG,"连接失败!");
                return;
            }
        }
        //如果有sql语句,执行
        if( exec_sql != null ){
            Log.i(TAG,"执行SQL:"+exec_sql);
            try {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(exec_sql);
                if( resultSet != null ){
                    Log.i(TAG,"执行成功!");
                }
                exec_sql = null;
            } catch (SQLException e) {
                e.printStackTrace();
                exec_sql = null;
                return;
            }
        }
    }

    public String getExec_sql() {
        return exec_sql;
    }

    public void setExec_sql(String exec_sql) {
        this.exec_sql = exec_sql;
    }

    public ResultSet getResultSet() {
        return getObj(resultSet);
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * 获取资源
     * @param obj
     * @param <V>
     * @return
     */
    public <V> V getObj(V obj){
        int i = 0;
        while( obj == null && i < 30 ){
            Log.i(TAG,"没有检测到"+obj);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if( obj == null ){
            Log.e(TAG,"获取"+obj+"失败!");
            return null;
        }
        return obj;
    }



    /**
     * 获取连接
     * @return
     */
    public Connection getConn() throws Exception {
        int i = 0;
        while( connection == null && i < 80 ){
            try {
                //睡一会在检查
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        if( connection == null ){
            throw new Exception("连接失败!");
        }
        return connection;
    }

    /**
     * 关闭连接
     */
    public void closeConn(){
        if( connection == null ){
            return ;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



