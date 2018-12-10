package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.ShoppingitemDaoImpl;
import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.domain.Shoppingitem;
import cskaoyan.java11prj.service.ShoppingitemService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 12:01
 * Detail requirement:
 * Method:
 */
public class ShoppingitemServiceImpl implements ShoppingitemService {
    ShoppingitemDaoImpl shoppingitemDao = new ShoppingitemDaoImpl();
    ProductServiceImpl productService = new ProductServiceImpl();

    @Override
    public boolean addShoppingitem(Shoppingitem shoppingitem) throws SQLException {
        if (shoppingitem != null && shoppingitem.getSnum() > 0){
            return shoppingitemDao.addShoppingitem(shoppingitem);
        }
        return false;
    }

    @Override
    public List<Shoppingitem> findAllitemsBysid(int sid) throws SQLException {
        if (sid >0){
            List<Shoppingitem> shoppingitems = shoppingitemDao.findAllShoppingitemBySid(sid);
            for (Shoppingitem s:shoppingitems) {
                String pid = s.getPid();
                Product product = productService.findProductByPid(pid);
                s.setProduct(product);
            }
            return shoppingitems;
        }

        return null;
    }

    @Override
    public boolean deleteItemById(String itemid) throws SQLException,NumberFormatException {
        if (itemid != null && !"".equals(itemid)){
            int itemidInt = Integer.parseInt(itemid);
            if (itemidInt > 0 ) {
                return shoppingitemDao.deleteShoppingitem(itemidInt);
            }
        }
        return false;
    }

    @Override
    public List<Shoppingitem> findAllitemsByPids(String[] pids, int uid) throws SQLException {
        if (pids != null && uid > 0){
            List<Shoppingitem> shoppingitems = shoppingitemDao.findAllitemsByPids(pids,uid);
            for (Shoppingitem s:shoppingitems) {
                String pid = s.getPid();
                Product product = productService.findProductByPid(pid);
                s.setProduct(product);
            }
            return shoppingitems;
        }

        return null;
    }

    @Override
    public boolean deleteItems(List<Shoppingitem> shoppingitems) throws SQLException {
        boolean success = false;
        if (shoppingitems != null){
            for (Shoppingitem s:shoppingitems) {
                int itemid = s.getItemid();
                success = deleteItemById(itemid + "");
                if (!success)
                    return false;
            }
        }
        return success;
    }
}

