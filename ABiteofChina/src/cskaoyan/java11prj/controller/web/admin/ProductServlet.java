package cskaoyan.java11prj.controller.web.admin;

import cskaoyan.java11prj.domain.Category;
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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 下午 4:23
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "ProductServlet",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {
    ProductServiceImpl productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        Boolean flag = false;  //判断操作是否成功

        switch (op) {
            case "findAllProduct":
                if (!findAllProduct(request, response)) {
                    System.out.println("获取商品列表失败！");
                    response.getWriter().println("获取商品列表失败！");
                }
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/product/productList.jsp");
                break;
            case "findProductByUpdate":
                flag = findProductByUpdate(request,response);
                if (!flag)
                    response.getWriter().println("获取失败.....");
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/product/updateProduct.jsp");
                break;
            case "deleteOne":
                flag = deleteOneProduct(request,response);
                if (flag)
                    response.getWriter().println("删除成功.....");
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/product/productList.jsp");
                break;
            case "deleteMulti":
                flag = deleteMulti(request,response);
                if (flag)
                    response.getWriter().println("删除成功.....");
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/product/productList.jsp");
                break;                
            case "multiConditionSearchProduct":
                flag = multiConditionSearchProduct(request,response);
                if (!flag)
                    response.getWriter().println("查询失败.....");
                response.setHeader("refresh", "0;url=" + request.getContextPath() + "/admin/product/productList.jsp");
                break;
            default:
                System.out.println("操作失败！");
                break;
        }
    }

    private Boolean multiConditionSearchProduct(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String pname = request.getParameter("pname");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");

        Page<Product> searchPage = null;
        try {
            searchPage = productService.multiConditionSearchProduct(pid,cid,pname,minprice,maxprice);

            HttpSession session = request.getSession(true);
            session.setAttribute("page",searchPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    private Boolean deleteMulti(HttpServletRequest request, HttpServletResponse response) {
        String[] pids = request.getParameterValues("pid");
        HttpSession session = request.getSession(false);
        Page<Product> page = (Page<Product>)session.getAttribute("page");

        if (pids.length <= 0)
            return false;

        Boolean isSuccess = false;
        try {
            for (int i = 0; i < pids.length; i++) {
                isSuccess = productService.deleteProductByPid(pids[i]);
            }
            if (isSuccess){
                Page<Product> page1 = productService.findPageProduct(page.getCurrentPageNum() + "");
                session.setAttribute("page",page1);
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    private Boolean findProductByUpdate(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        HttpSession session = request.getSession(false);
        session.setAttribute("pid",pid);

        CategoryServiceImpl categoryService = new CategoryServiceImpl();
        List<Category> categories = null;
        try {
            categories = categoryService.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        session.setAttribute("categories",categories);

        Product product = null;
        Page<Product> page = (Page<Product>)session.getAttribute("page");
        List<Product> records = page.getRecords();
        for (Product p:records) {
            if (pid.equals(p.getPid()+""))
                product = p;
        }
        session.setAttribute("product",product);

        return true;
    }

    private Boolean deleteOneProduct(HttpServletRequest request, HttpServletResponse response){
        String pidString = request.getParameter("pid");
        try {
            Boolean isSuccess= productService.deleteProductByPid("pid");

            Page<Product> page = productService.findPageProduct("1");
            HttpSession session = request.getSession(false);
            session.setAttribute("page",page);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private Boolean findAllProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String num = request.getParameter("num");

        if (num == null || num.isEmpty())
            return false;

        Page<Product> page = null;
        try {
            page = productService.findPageProduct(num);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error!");
            return false;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("page",page);
        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
