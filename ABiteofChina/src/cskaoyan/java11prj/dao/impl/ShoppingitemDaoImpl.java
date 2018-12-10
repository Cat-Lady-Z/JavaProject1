package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.ShoppingitemDao;
import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.domain.Shoppingcar;
import cskaoyan.java11prj.domain.Shoppingitem;
import cskaoyan.java11prj.util.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 11:04
 * Detail requirement:
 * Method:
 */
public class ShoppingitemDaoImpl implements ShoppingitemDao {
    @Override
    public List<Shoppingitem> findAllShoppingitemBySid(int sid) throws SQLException {
        if (sid <= 0 )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Shoppingitem> shoppingitems = queryRunner.query("select *from `shoppingitem` where `sid`=?;",
                new BeanListHandler<Shoppingitem>(Shoppingitem.class), sid);
        return shoppingitems;
    }

    @Override
    public Shoppingitem findShoppingitemByItemId(int itemid) throws SQLException {
        if (itemid <= 0 )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Shoppingitem shoppingitem = queryRunner.query("select *from `shoppingitem` where `itemid`=?;",
                new BeanHandler<Shoppingitem>(Shoppingitem.class), itemid);
        return shoppingitem;
    }

    @Override
    public List<Shoppingitem> findShoppingitemsByPid(String pid) throws SQLException {
        if (pid == null && "".equals(pid) )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Shoppingitem> shoppingitems = queryRunner.query("select *from `shoppingitem` where `pid`=?;",
                new BeanListHandler<Shoppingitem>(Shoppingitem.class), pid);
        return shoppingitems;
    }

    @Override
    public boolean addShoppingitem(Shoppingitem shoppingitem) throws SQLException {
        if (shoppingitem == null || shoppingitem.getSid() <=0 || shoppingitem.getSnum() <=0)
            return false;

        int sid = shoppingitem.getSid();
        String pid = shoppingitem.getPid();
        int snum = shoppingitem.getSnum();

        int isSuccess;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Shoppingitem> shoppingitems = findShoppingitemsByPid(pid);
        if ( shoppingitems == null || shoppingitems.size() == 0){
            isSuccess = queryRunner.update("insert into `shoppingitem` values (null,?,?,?);", sid, pid, snum);
        } else {
            int snumBefore = 0;
            for (Shoppingitem s:shoppingitems) {
                snumBefore = s.getSnum();
            }
            int totalSnum = snumBefore + snum;
            isSuccess = queryRunner.update("update `shoppingitem` set `snum`=? where `pid`=?;", totalSnum, pid);
        }
        if (isSuccess > 0)
            return true;
        return false;
    }

    @Override
    public boolean deleteShoppingitem(int itemid) throws SQLException {
        if (itemid <= 0)
            return false;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        //先判断cid在表里是否存在
        Shoppingitem query = queryRunner.query("select *from `shoppingitem` where `itemid`=?;",
                new BeanHandler<Shoppingitem>(Shoppingitem.class), itemid);
        if (query == null)
            return false;

        int isSuccess = queryRunner.update("delete from `shoppingitem` where itemid=?;",itemid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public boolean reduceShoppingitemSnum(int itemid, int snum) throws SQLException {
        if (itemid <= 0)
            return false;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        //先判断itemid在表里是否存在
        Shoppingitem query = queryRunner.query("select *from `shoppingitem` where `itemid`=?;",
                new BeanHandler<Shoppingitem>(Shoppingitem.class), itemid);
        if (query == null)
            return false;

        int isSuccess = queryRunner.update("update `shoppingitem` set `snum`=? where `itemid`=?;",snum,itemid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public List<Shoppingitem> findAllitemsByPids(String[] pids, int uid) throws SQLException {
        ShoppingcarDaoImpl shoppingcarDao = new ShoppingcarDaoImpl();
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Shoppingitem> shoppingitemsTotal = new ArrayList<>();

        if (uid>0){
            Shoppingcar car = shoppingcarDao.findShoppingCarByUid(uid);
            int sid = car.getSid();

            for (int i = 0; i < pids.length; i++) {
                Shoppingitem shoppingitem = null;
                if (pids[i] != null){
                    shoppingitem = queryRunner.query("select *from `shoppingitem` " +
                                    "where `pid`=? and `sid`=?;",
                            new BeanHandler<Shoppingitem>(Shoppingitem.class), pids[i],sid);
                } else
                    return null;
                shoppingitemsTotal.add(shoppingitem);
            }
        }
        return shoppingitemsTotal;
    }
}