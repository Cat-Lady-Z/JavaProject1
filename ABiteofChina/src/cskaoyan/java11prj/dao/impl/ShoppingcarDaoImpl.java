package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.ShoppingcarDao;
import cskaoyan.java11prj.domain.Shoppingcar;
import cskaoyan.java11prj.util.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 10:49
 * Detail requirement:
 * Method:
 */
public class ShoppingcarDaoImpl implements ShoppingcarDao {
    @Override
    public Shoppingcar findShoppingCarByUid(int uid) throws SQLException {
        if (uid <= 0 )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Shoppingcar shoppingcar = queryRunner.query("select *from `shoppingcar` where `uid`=?;", new BeanHandler<Shoppingcar>(Shoppingcar.class), uid);
        return shoppingcar;
    }

    @Override
    public boolean addShoppingCar(int uid) throws SQLException {
        if (uid <=0)
            return false;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("insert into `shoppingcar` values (null,?);", uid);
        if (update>0)
            return true;
        return false;
    }
}