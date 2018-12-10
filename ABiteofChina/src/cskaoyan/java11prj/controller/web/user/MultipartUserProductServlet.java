package cskaoyan.java11prj.controller.web.user;

import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.service.impl.CategoryServiceImpl;
import cskaoyan.java11prj.service.impl.ProductServiceImpl;
import cskaoyan.java11prj.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/12
 * Time: 下午 8:30
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "MultipartUserProductServlet",urlPatterns = "/MultipartUserProductServlet")
public class MultipartUserProductServlet extends HttpServlet {
    ProductServiceImpl productService = new ProductServiceImpl();
    CategoryServiceImpl categoryService = new CategoryServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //findProductsByName
        String op = request.getParameter("op");
        Boolean flag = false;  //判断操作是否成功

        switch (op) {
            case "findProductsByName":
                flag = findProductsByName(request, response);
                //response.setHeader("refresh", "0;url=" + request.getContextPath() + "/searchProducts.jsp");
                break;
            case "findProductByCid":
                if (!findProductByCid(request,response)){
                    response.getWriter().println("获取商品列表失败！");
                }
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/products.jsp");
                break;
            case "findProductByPid":
                if (!findProductByPid(request, response)) {
                    response.getWriter().println("获取商品详情失败！");
                }
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/productdetail.jsp");
                break;
            default:
                System.out.println("操作失败！");
                break;
        }
    }

    private boolean findProductByPid(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        try {
            Product productByPid = productService.findProductByPid(pid);
            HttpSession session = request.getSession(true);
            session.setAttribute("product",productByPid);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean findProductByCid(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String num = request.getParameter("num");
        if (num == null || "".equals(num))
            num = "1";

        Page<Product> page = null;
        try {
            page = productService.findProductByCid(cid,num);
            HttpSession session = request.getSession(true);
            session.setAttribute("page",page);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Boolean findProductsByName(HttpServletRequest request, HttpServletResponse response) {
        String pname = request.getParameter("pname");
        request.setAttribute("pname",pname);
        String num = request.getParameter("num");
        if (num == null || "".equals(num))
            num = "1";
        Page<Product> page = null;
        try {
            page = productService.findProductsByName(pname,num);
            HttpSession session = request.getSession(false);
            session.setAttribute("page",page);

            request.getRequestDispatcher("/searchProducts.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
