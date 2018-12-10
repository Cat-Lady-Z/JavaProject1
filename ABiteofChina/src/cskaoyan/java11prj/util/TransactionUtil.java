package cskaoyan.java11prj.util;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/18
 * Time: 下午 10:28
 * Detail requirement:
 * Method:
 */
public class TransactionUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    public static Connection get(){
        return threadLocal.get();
    }

    public static void beginTransaction() throws SQLException {
        Connection connection = C3P0Utils.getConnection();
        connection.setAutoCommit(false);
        threadLocal.set(connection);
    }

    public static void commitTransaction(){
        DbUtils.commitAndCloseQuietly(get());
    }

    public static void rollBackAndRelease(){
        DbUtils.rollbackAndCloseQuietly(threadLocal.get());
    }
}