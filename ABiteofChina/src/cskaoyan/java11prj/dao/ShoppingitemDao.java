package cskaoyan.java11prj.dao;

import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.domain.Shoppingitem;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 10:53
 * Detail requirement:
 * Method: 增删改查
 */
public interface ShoppingitemDao {
    List<Shoppingitem> findAllShoppingitemBySid(int sid) throws SQLException;
    Shoppingitem findShoppingitemByItemId(int itemid) throws SQLException;
    List<Shoppingitem> findShoppingitemsByPid(String pid) throws SQLException;

    boolean addShoppingitem(Shoppingitem shoppingitem) throws SQLException;
    boolean deleteShoppingitem(int itemid) throws SQLException;
    boolean reduceShoppingitemSnum(int itemid,int snum) throws SQLException;

    List<Shoppingitem> findAllitemsByPids(String[] pids, int uid) throws SQLException;
}
