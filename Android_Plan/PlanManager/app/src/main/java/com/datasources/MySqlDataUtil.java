package com.datasources;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.lang.Object;

/***
 * MySql数据库操作类
 */
public class MySqlDataUtil {

    private static String url;
    private static String username;
    private static String password;
    private static Connection connection = null;

    public MySqlDataUtil() {
    }


    /***
     * 初始化方法
     * @param config_stream 数据库　propertis文件流　配置文件路径
     */
    public static void init(InputStream config_stream){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load(config_stream);
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }

        //==
        System.out.println(
                properties.getProperty("url") + "\n" +
                        properties.getProperty("username") + "\n" +
                        properties.getProperty("password")
        );
        //==
        try {
            connection = conn(properties);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    /***
     * 是否连接 连接: true  未连接: false
     * @return
     */
    public static boolean isConnClose(){
        return connection != null;
    }

    /**
     * 获取Connection连接
     * @return
     */
    public static Connection getConn(){
        return connection;
    }

    /**
     * 路径获取
     */
    public static void init(String jdbc＿config_path){
        try {
            init(new FileInputStream(jdbc＿config_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接方法
     * @param properties 配置文件对象
     * @return
     * @throws SQLException
     */
    public static Connection conn(Properties properties) throws SQLException {
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(url,username,password);
        return connection;
    }

    /**
     * 执行SQL查询方法
     * @param <E>
     * @return
     */
    public static <E> List<E> querySql(Class entityClass,String sql){
        List<E> t_list = new ArrayList<E>();
        try {
            Statement  statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            for( Map<String, Object> t_itemMap : converListMap(resultSet) ){
                t_list.add((E)setField(entityClass,t_itemMap));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return t_list;
    }



    /***
     * 关闭方法
     */
    public static void closeConn(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * ResultSet 转List<Map>
     * @param resultSet
     * @return
     */
    public static List<Map<String,Object>> converListMap(ResultSet resultSet) {
        if( resultSet == null ){
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        try{
            while( resultSet.next() ){
                ResultSetMetaData metaData = resultSet.getMetaData();
                Map<String,Object> t_map = new HashMap<>();
                int colCount =  metaData.getColumnCount();
                for( int i = 1; i <= colCount; i++ ){
                    if( metaData.getColumnType(i) == 93 && resultSet.getInt(i) == 0 ){
                        t_map.put(metaData.getColumnName(i),null);
                        continue;
                    }
                    t_map.put(metaData.getColumnName(i),resultSet.getObject(i));
                }
                list.add(t_map);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return list;
    }


    /**
     * 填充实体类
     * @param entityClass 任意实体类Class
     * @param t_entity Map
     * @return
     */
    public static <E> E setField(Class entityClass, Map<String,Object> t_entity){
        Object newObj = null;
        //Class entityClass = entity.getClass();
        try {
            newObj = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for ( int i = 0; i < fields.length; i++ ){
            String fieldName = fields[i].getName();
            Object value = t_entity.get(fieldName);
            if( value != null ){
                fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                Method setMethod = null;
                try {
                    Class<?> t_type = fields[i].getType();

                    setMethod = entityClass.getMethod("set"+fieldName,t_type);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    setMethod.invoke(newObj,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return (E)newObj;
    }


}
