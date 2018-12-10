package cskaoyan.java11prj.controller.web.user;

import com.sun.org.apache.bcel.internal.generic.FADD;
import cskaoyan.java11prj.domain.*;
import cskaoyan.java11prj.service.OrderService;
import cskaoyan.java11prj.service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 下午 5:25
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "OrderServlet",urlPatterns = "/user/OrderServlet")
public class OrderServlet extends HttpServlet {
    OrderItemServiceImpl orderItemService = new OrderItemServiceImpl();
    OrderServiceImpl orderService = new OrderServiceImpl();
    ProductServiceImpl productService = new ProductServiceImpl();
    ShoppingitemServiceImpl shoppingitemService = new ShoppingitemServiceImpl();
    ShoppingcarServiceImpl shoppingcarService = new ShoppingcarServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        switch (op){
            case "myoid":
                myoid(request,response);
                break;
            case "placeOrder":
                placeOrder(request,response);
                break;
            case "cancelOrder":
                cancelOrder(request,response);
                break;
            case "findOrderDetail":
                findOrderDetail(request,response);
                break;
            default:
                response.getWriter().println("系统出错了哦，请重新试一下~");
                response.setHeader("refresh","0;url=" + request.getContextPath() + "/index.jsp");
                break;
        }
    }

    private void findOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String oid = request.getParameter("oid");
        //orderitems
        List<OrderItem> orderitems = null;
        try {
            orderitems = orderItemService.findOrderitemsDetailByOid(oid);
            session.setAttribute("orderitems",orderitems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setHeader("refresh", "0;url=" + request.getContextPath() + "/user/orderdetail.jsp");
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
        //op=cancelOrder&oid=${order.oid}&state=0
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        int uid = user.getUid();

        String state = request.getParameter("state");
        String oid = request.getParameter("oid");
        try {
            if (!orderService.cancelOrder(oid, state)) {
                response.getWriter().println("订单取消失败");
            } else {
                response.getWriter().println("订单取消成功");
                List<Order> orders = orderService.findOrdersByUid(uid);
                session.setAttribute("orders",orders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHeader("refresh", "0;url=" + request.getContextPath() + "/user/myOrders.jsp");
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) {
        /*商品表：对应商品库存-n
        订单表：增加一条记录（关联用户）
        订单项表：增加多条记录（关联商品）
        购物车项表：删除相应的记录（清空购物车*/
        String[] pids = request.getParameterValues("pid");

        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        int uid = user.getUid();

        try {
            //根据pid和uid去购物项里面取数据items
            List<Shoppingitem> shoppingitems = shoppingitemService.findAllitemsByPids(pids,uid);
            if (shoppingitems != null){

                //改变商品表里面的库存信息，pid、snum(购物车项的商品数)
                if (productService.reducePnumByPid(shoppingitems)){
                    //新增一条订单表信息,oid,money,recipients,tel,address,state,ordertime,uid
                    String recipients = request.getParameter("recipients");
                    String tel = request.getParameter("tel");
                    String address = request.getParameter("address");
                    String money = request.getParameter("money");

                    if (recipients != null && !"".equals(recipients) && tel != null && !"".equals(tel) &&
                     address != null && !"".equals(address) && money != null && !"".equals(money)){
                        String tPaten = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|4|5|6|7|8|9])\\d{8}$";
                        if (tel.matches(tPaten)){
                            Order order = new Order();
                            StringBuilder str=new StringBuilder();
                            //定义变长字符串
                            Random random=new Random();
                            //随机生成数字，并添加到字符串
                            for(int i=0;i<8;i++){
                                str.append(random.nextInt(10));
                            }
                            String oid = str.toString();
                            order.setOid(oid);
                            order.setRecipients(recipients);
                            order.setTel(tel);
                            order.setAddress(address);
                            order.setMoney(Double.parseDouble(money));
                            String ordertime = "" + (new Timestamp(System.currentTimeMillis()));
                            order.setOrdertime(ordertime);
                            order.setState(1);
                            order.setUid(uid);

                            if (orderService.addOrder(order)){
                                //在订单项表里增加多条记录，oid、pid ，buynum
                                List<Order> orders = orderService.findOrdersByUid(uid);

                                List<OrderItem> orderitems = new ArrayList<>();
                                for (Shoppingitem s:shoppingitems) {
                                    String pid = s.getPid();
                                    int snum = s.getSnum();

                                    OrderItem orderItem = new OrderItem();
                                    orderItem.setOid(oid);
                                    orderItem.setPid(pid);
                                    orderItem.setBuynum(snum);
                                    orderitems.add(orderItem);
                                }
                                if (orderItemService.addOrderItems(orderitems))
                                    //购物车项：删除相应的记录
                                    if (shoppingitemService.deleteItems(shoppingitems)) {
                                        session.setAttribute("orders",orders);

                                        response.getWriter().println("已下订单，请尽快支付哦~");
                                        response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/myOrders.jsp");
                                        return;
                                    }
                            }
                        }
                    }
                }
            }
            response.getWriter().println("下单失败，请重新下单！");
            response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/myOrders.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void myoid(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        int uid = user.getUid();

        List<Order> orders = null;
        try {
            orders = orderService.findOrdersByUid(uid);
            if (orders != null){
                session.setAttribute("orders",orders);
            } else
                response.getWriter().println("亲，目前没有订单哦，去购物吧~");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/myOrders.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
