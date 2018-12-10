package cskaoyan.java11prj.controller.web.user;

import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.domain.Shoppingcar;
import cskaoyan.java11prj.domain.Shoppingitem;
import cskaoyan.java11prj.domain.User;
import cskaoyan.java11prj.service.impl.ProductServiceImpl;
import cskaoyan.java11prj.service.impl.ShoppingcarServiceImpl;
import cskaoyan.java11prj.service.impl.ShoppingitemServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 10:09
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "CartServlet",urlPatterns = "/user/CartServlet")
public class CartServlet extends HttpServlet {
    ShoppingcarServiceImpl shoppingcarService = new ShoppingcarServiceImpl();
    ShoppingitemServiceImpl shoppingitemService = new ShoppingitemServiceImpl();
    ProductServiceImpl productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        switch (op){
            case "addCart":
                addCart(request,response);
                break;
            case "delItem":
                delItem(request,response);
                break;
            case "findCart":
                findCart(request,response);
                break;
            default:
                response.getWriter().println("操作出错了哦");
                response.setHeader("refresh","0;url=" + request.getContextPath() + "/index.jsp");
                break;
        }
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        try {
            Shoppingcar shoppingCart = shoppingcarService.findShoppingCarByUid("" + user.getUid());
            if (shoppingCart != null){
                int sid = shoppingCart.getSid();
                List<Shoppingitem> shoppingitems = shoppingitemService.findAllitemsBysid(sid);

                shoppingCart.setShoppingitems(shoppingitems);
                session.setAttribute("shoppingCart",shoppingCart);
            }
            request.getRequestDispatcher("/user/shoppingcart.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        String itemid = request.getParameter("itemid");
        //String snum = request.getParameter("snum");

        if (uid == null || itemid == null){
            try {
                request.getRequestDispatcher(request.getContextPath() + "/user/shoppingcart.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            Shoppingcar shoppingCart = shoppingcarService.findShoppingCarByUid(uid);
            int sid = shoppingCart.getSid();
            boolean success = shoppingitemService.deleteItemById(itemid);
            if (success){
                List<Shoppingitem> shoppingitems = shoppingitemService.findAllitemsBysid(sid);

                shoppingCart.setShoppingitems(shoppingitems);
                HttpSession session = request.getSession(true);
                session.setAttribute("shoppingCart",shoppingCart);
            }
            request.getRequestDispatcher(request.getContextPath() + "/user/shoppingcart.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        String snum = request.getParameter("snum");


        if (pid == null || uid == null){
            try {
                request.getRequestDispatcher(request.getContextPath() + "/user/shoppingcart.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return;
    }

        try {
            //确认是否该用户已有购物车，没有就创建一个
            boolean available= shoppingcarService.isShoppingCarAvailableByUid(uid);
            if (!available){
                boolean success = shoppingcarService.addShoppingCarByUid(uid);
            }
            //获取用户购物车的sid，新建一个购物车项
            Shoppingcar shoppingCart = shoppingcarService.findShoppingCarByUid(uid);
            int sid = shoppingCart.getSid();
            int snumInt;
            if (snum == null || "".equals(snum))
                snumInt = 1;
            else {
                snumInt = Integer.parseInt(snum);
            }
            if (snumInt <= 0){
                return;
            }

            Shoppingitem shoppingitem = new Shoppingitem();
            shoppingitem.setPid(pid);
            shoppingitem.setSid(sid);
            shoppingitem.setSnum(snumInt);

            //把购物车项加入购物车项列表
            boolean ok = shoppingitemService.addShoppingitem(shoppingitem);
            if (ok){
                List<Shoppingitem> shoppingItems= shoppingitemService.findAllitemsBysid(sid);
                //把新购物列表放进用户购物车，放进sesion域里
                shoppingCart.setShoppingitems(shoppingItems);

                HttpSession session = request.getSession(true);
                session.setAttribute("shoppingCart",shoppingCart);
                response.getWriter().println("添加成功！");
            } else
                response.getWriter().println("添加失败！");

            response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/shoppingcart.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
