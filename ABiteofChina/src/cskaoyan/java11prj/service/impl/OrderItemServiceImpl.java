package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.OrderDaoImpl;
import cskaoyan.java11prj.dao.impl.OrderItemDaoImpl;
import cskaoyan.java11prj.dao.impl.ProductDaoImpl;
import cskaoyan.java11prj.domain.Order;
import cskaoyan.java11prj.domain.OrderItem;
import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.service.OrderItemService;
import cskaoyan.java11prj.service.OrderService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:28
 * Detail requirement:
 * Method:
 */
public class OrderItemServiceImpl implements OrderItemService {
    OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();

    @Override
    public boolean addOrderItems(List<OrderItem> orderitems) throws SQLException {
        Boolean success = false;
        if (orderitems != null){
            for (OrderItem o:orderitems) {
                if (o == null || o.getOid() == null || o.getBuynum()<=0)
                    return false;
            }

            for (OrderItem o:orderitems) {
                success = orderItemDao.addOrderItems(o);
            }
        }
        return success;
    }

    @Override
    public List<OrderItem> findOrderitemsDetailByOid(String oid) throws NumberFormatException, SQLException {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        ProductDaoImpl productDao = new ProductDaoImpl();

        if (oid !=null && !"".equals(oid)){
            if (Integer.parseInt(oid) >0 ){
                List<OrderItem> orderItems = orderItemDao.findOrderitemsDetailByOid(oid);
                for (OrderItem o:orderItems) {
                    Order order = orderDao.findOrdersByOid(oid);
                    String pid = o.getPid();
                    Product productByPid = productDao.findProductByPid(pid);
                    o.setOrder(order);
                    o.setProduct(productByPid);
                }
                return orderItems;
            }
        }
        return null;
    }
}