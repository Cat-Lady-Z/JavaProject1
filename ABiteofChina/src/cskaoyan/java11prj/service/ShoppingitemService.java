package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.Shoppingitem;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 12:00
 * Detail requirement:
 * Method:
 */
public interface ShoppingitemService {
    boolean addShoppingitem(Shoppingitem shoppingitem) throws SQLException;

    List<Shoppingitem> findAllitemsBysid(int sid) throws SQLException;

    boolean deleteItemById(String itemid) throws SQLException;

    List<Shoppingitem> findAllitemsByPids(String[] pids, int uid) throws SQLException;

    boolean deleteItems(List<Shoppingitem> shoppingitems) throws SQLException;
}
