package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.OrderItem;

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
public interface OrderItemService {
    boolean addOrderItems(List<OrderItem> orderitems) throws SQLException;

    List<OrderItem> findOrderitemsDetailByOid(String oid) throws SQLException;
}
