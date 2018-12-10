package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.Shoppingcar;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 12:02
 * Detail requirement:
 * Method:
 */
public interface ShoppingcarService {
    boolean isShoppingCarAvailableByUid(String uid) throws SQLException;

    boolean addShoppingCarByUid(String uid) throws SQLException;

    Shoppingcar findShoppingCarByUid(String uid) throws SQLException;
}
