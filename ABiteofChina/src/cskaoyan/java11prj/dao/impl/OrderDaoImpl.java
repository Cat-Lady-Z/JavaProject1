package cskaoyan.java11prj.dao.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import cskaoyan.java11prj.dao.OrderDao;
import cskaoyan.java11prj.domain.Order;
import cskaoyan.java11prj.util.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:21
 * Detail requirement:
 * Method:
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> findOrdersByUid(int uid) throws SQLException {
        if (uid > 0 ){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            List<Order> orders = queryRunner.query("select *from `order` where `uid`=?;", new BeanListHandler<Order>(Order.class), uid);
            return orders;
        }
        return null;
    }

    @Override
    public boolean addOrder(Order order) throws SQLException {
        if (order != null){
            String oid = order.getOid();
            double money = order.getMoney();
            String recipients = order.getRecipients();
            String tel = order.getTel();
            String address = order.getAddress();
            int state = order.getState();
            String ordertime = order.getOrdertime();
            int uid = order.getUid();

            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("insert into `order` values (?,?,?,?,?,?,?,?);",
                    oid, money, recipients, tel, address, state, ordertime, uid);
            if (update > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean changeOrderStateByOid(String oid, int state) throws SQLException {
        boolean result = false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("update `order` set `state`=? where `oid`=?;",state,oid);
        if (update > 0){
            result = true;
        }
        return result;
    }

    @Override
    public Order findOrdersByOid(String oid) throws SQLException {
        if (oid != null && !"".equals(oid) ){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            Order order = queryRunner.query("select *from `order` where `oid`=?;", new BeanHandler<Order>(Order.class), oid);
            return order;
        }
        return null;
    }
}