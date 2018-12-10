package cskaoyan.java11prj.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/07
 * Time: 下午 7:55
 * Detail requirement:
 * Method:
 */
public class C3P0Utils {
    static ComboPooledDataSource cpds;
    static {
        cpds = new ComboPooledDataSource("mysql");
    }

    public static ComboPooledDataSource getCpds(){
        return cpds;
    }

    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    //这样设计有什么意义？能调用关闭资源？
    public static void releaseDBResource(ResultSet rs, Statement st, Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (st != null ){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}