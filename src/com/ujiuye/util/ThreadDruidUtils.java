package com.ujiuye.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 并发访问处理获取可以安全获取数据库连接的工具类
 */
public class ThreadDruidUtils {
    // 本地线程变量
    private static final ThreadLocal<Connection> th = new ThreadLocal<>();
    /**
     * 获取Druid数据源
     * @return 数据库连接池对象
     */
    public static DataSource getDataSource(){
        // 读取资源文件
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("druid.properties");

        Properties p = new Properties();

        try {
            p.load(in); // 将读取到的数据存入 属性对象
            // 创建数据库连接池对象
            return DruidDataSourceFactory.createDataSource(p);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;
    }

    /**
     * 获取数据库连接从数据库连接池
     * @return 数据库连接
     */
    public  static Connection getConnection() throws SQLException {
        // 从本地线程中获取共享变量数据库连接
        Connection conn = th.get();


        if(conn == null){ // 如果获取不到
            // 从数据库获取连接存放本地变量中最为变量副本
            Connection connection = getDataSource().getConnection();
            th.set(connection);
            conn = th.get(); // 并取出返回给调用者
        }
        return conn;
    }

    /**
     * 释放连接
     */
    public static void  realse(Statement stat, ResultSet res){

        if(res != null){
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stat != null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 从本地线程中获取当前线程对应的连接
        Connection connection = th.get();
        if(connection != null){ // 如果不等于null
            try {
                th.remove(); // 移除线程空间中的值
                connection.close(); // 关闭连接

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
