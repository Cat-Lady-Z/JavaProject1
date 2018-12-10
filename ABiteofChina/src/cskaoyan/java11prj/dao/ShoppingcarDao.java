package cskaoyan.java11prj.dao;

import cskaoyan.java11prj.domain.Shoppingcar;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 10:37
 * Detail requirement:
 * Method:
 */
public interface ShoppingcarDao {
    Shoppingcar findShoppingCarByUid(int uid) throws SQLException;
    boolean addShoppingCar(int uid) throws SQLException;
}
