package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:27
 * Detail requirement:
 * Method:
 */
public interface OrderService {

    List<Order> findOrdersByUid(int uid) throws SQLException;

    boolean addOrder(Order order) throws SQLException;

    boolean changeOrderStateByOid(String oid, String state) throws SQLException;

    boolean cancelOrder(String oid, String state) throws SQLException;
}
