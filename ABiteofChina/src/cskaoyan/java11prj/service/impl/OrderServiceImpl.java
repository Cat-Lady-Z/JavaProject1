package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.OrderDaoImpl;
import cskaoyan.java11prj.dao.impl.ProductDaoImpl;
import cskaoyan.java11prj.domain.Order;
import cskaoyan.java11prj.domain.OrderItem;
import cskaoyan.java11prj.service.OrderService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:26
 * Detail requirement:
 * Method:
 */
public class OrderServiceImpl implements OrderService {
    OrderDaoImpl orderDao = new OrderDaoImpl();
    OrderItemServiceImpl orderItemService = new OrderItemServiceImpl();
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    public List<Order> findOrdersByUid(int uid) throws SQLException {
        if (uid >= 0){
            return orderDao.findOrdersByUid(uid);
        }
        return null;
    }

    @Override
    public boolean addOrder(Order order) throws SQLException {
        if (order != null){
            return orderDao.addOrder(order);
        }
        return false;
    }

    @Override
    public boolean changeOrderStateByOid(String oid, String state) throws SQLException {
        boolean result = false;
        if (oid != null && !"".equals(oid) && state != null && !"".equals(state)){
            int stateInt = Integer.parseInt(state);
            if (stateInt>=0)
                result =  orderDao.changeOrderStateByOid(oid, stateInt);
        }
        return result;
    }

    @Override
    public boolean cancelOrder(String oid, String state) throws SQLException {
        boolean result = false;
        if (oid != null && !"".equals(oid) && state != null && !"".equals(state)){
            if (changeOrderStateByOid(oid, state)){
                //商品库存加回去
                List<OrderItem> orders = orderItemService.findOrderitemsDetailByOid(oid);
                for (OrderItem o:orders) {
                    String pid = o.getPid();
                    int buynum = o.getBuynum();
                    if (pid !=null && !"".equals(buynum)){
                        if (!productDao.updateProductPnumByPid(pid, buynum)){
                            result = false;
                            break;
                        }
                    }
                    result = true;
                }
            }
        }
        return result;
    }
}