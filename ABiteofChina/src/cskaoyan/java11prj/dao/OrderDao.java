package cskaoyan.java11prj.dao;

import cskaoyan.java11prj.domain.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:21
 * Detail requirement:
 * Method:
 */
public interface OrderDao {
    List<Order> findOrdersByUid(int uid) throws SQLException;

    boolean addOrder(Order order) throws SQLException;

    boolean changeOrderStateByOid(String oid, int state) throws SQLException;

    Order findOrdersByOid(String oid) throws SQLException;
}
