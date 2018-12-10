package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.ShoppingcarDaoImpl;
import cskaoyan.java11prj.domain.Shoppingcar;
import cskaoyan.java11prj.service.ShoppingcarService;

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
public class ShoppingcarServiceImpl implements ShoppingcarService {
    ShoppingcarDaoImpl shoppingcarDao = new ShoppingcarDaoImpl();

    @Override
    public boolean isShoppingCarAvailableByUid(String uid) throws SQLException,NumberFormatException {
        boolean result = false;
        if (uid != null && !"".equals(uid)){
            int uidInt = Integer.parseInt(uid);
            if (uidInt > 0){
                Shoppingcar car = shoppingcarDao.findShoppingCarByUid(uidInt);
                if (car != null)
                    result = true;
            }
        }

        return result;
    }

    @Override
    public boolean addShoppingCarByUid(String uid) throws SQLException,NumberFormatException {
        if (uid != null && !"".equals(uid)){
            int uidInt = Integer.parseInt(uid);
            if (uidInt > 0){
                return shoppingcarDao.addShoppingCar(uidInt);
            }
        }

        return false;
    }

    @Override
    public Shoppingcar findShoppingCarByUid(String uid) throws SQLException,NumberFormatException {
        if (uid != null && !"".equals(uid)){
            int uidInt = Integer.parseInt(uid);
            if (uidInt > 0){
                return shoppingcarDao.findShoppingCarByUid(uidInt);
            }
        }

        return null;
    }
}