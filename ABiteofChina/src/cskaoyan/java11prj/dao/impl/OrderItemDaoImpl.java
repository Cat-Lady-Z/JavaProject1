package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.OrderItemDao;
import cskaoyan.java11prj.domain.OrderItem;
import cskaoyan.java11prj.util.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:23
 * Detail requirement:
 * Method:
 */
public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public Boolean addOrderItems(OrderItem o) throws SQLException {
        if (o != null){
            String oid = o.getOid();
            String pid = o.getPid();
            int buynum = o.getBuynum();
            if ( oid != null && pid != null && buynum >= 0){
                QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
                int update = queryRunner.update("insert into `orderitem` values (null,?,?,?);",
                        oid, pid, buynum);
                if (update > 0)
                    return true;
            }
        }
        return false;
    }

    @Override
    public List<OrderItem> findOrderitemsDetailByOid(String oid) throws SQLException {
        if (oid !=null && !"".equals(oid) ){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            List<OrderItem> orders = queryRunner.query("select *from `orderitem` where `oid`=?;", new BeanListHandler<OrderItem>(OrderItem.class), oid);
            return orders;
        }
        return null;
    }
}