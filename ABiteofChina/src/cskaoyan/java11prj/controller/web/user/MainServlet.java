package cskaoyan.java11prj.controller.web.user;

import cskaoyan.java11prj.domain.Category;
import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.service.impl.CategoryServiceImpl;
import cskaoyan.java11prj.service.impl.ProductServiceImpl;

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
 * Date: 2018/11/12
 * Time: 下午 5:11
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //把topProducts,categories,hotProducts
       // request.getParameter("op");
        HttpSession session = request.getSession(true);

        CategoryServiceImpl categoryService = new CategoryServiceImpl();
        ProductServiceImpl productService = new ProductServiceImpl();
        try {
            List<Category> categories = categoryService.findAllCategory();
            session.setAttribute("categories",categories);

            List<Product> topProducts = productService.findTopProducts();
            session.setAttribute("topProducts",topProducts);

            List<Product> hotProducts = productService.findHotProducts();
            session.setAttribute("hotProducts",hotProducts);

            response.setHeader("refresh", "0;url=" + request.getContextPath() + "/index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
