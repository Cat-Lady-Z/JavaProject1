package cskaoyan.java11prj.controller.web.admin;

import cskaoyan.java11prj.domain.Category;
import cskaoyan.java11prj.service.impl.CategoryServiceImpl;
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
 * Date: 2018/11/07
 * Time: 下午 9:53
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "CategoryServlet",urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    CategoryServiceImpl categoryService = new CategoryServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        Boolean flag = false;  //判断操作是否成功

        switch (op){
            case "addCategory":
                flag = addCategory(request,response);
                if (flag)
                    response.getWriter().println("添加成功.....");
                else
                    response.getWriter().println("添加失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/category/addCategory.jsp");
                break;
            case "updateCategory":
                flag = updateCategory(request,response);
                if (flag)
                    response.getWriter().println("修改成功.....");
                else
                    response.getWriter().println("修改失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/category/categoryList.jsp");
                break;
            case "findAllCategory":
                //为product准备的接口方法
                flag = findAllCategory(request, response);
                if (flag)
                    response.setHeader("refresh","0;url="+ request.getContextPath() + "/admin/product/addProduct.jsp");
                break;
            case "findAllCategoryByPage":
                flag = findAllCategoryByPage(request, response);
                if (flag)
                    response.setHeader("refresh","0;url="+ request.getContextPath() + "/admin/category/categoryList.jsp");
                break;
            case "deleteCategory":
                flag = deleteCategory(request,response);
                if (flag)
                    response.getWriter().println("删除成功.....");
                else
                    response.getWriter().println("删除失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/category/categoryList.jsp");
                break;
            case "deleteMulti":
                flag = deleteMultiCategory(request,response);
                if (flag)
                    response.getWriter().println("删除成功.....");
                else
                    response.getWriter().println("删除失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/category/categoryList.jsp");
                break;
            case "isCategoryNameAvailable":
                flag = isCategoryNameAvailable(request,response);
                break;
            default:
                System.out.println("操作失败！");
                break;
        }

    }

    private Boolean findAllCategoryByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String num = request.getParameter("num");

        if (num == null || num.isEmpty())
            return false;

        Page<Category> page = null;
        try {
            page = categoryService.findPageCategory(num);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error!");
            return false;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("page",page);
        return true;
    }

    private Boolean isCategoryNameAvailable(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    private Boolean deleteMultiCategory(HttpServletRequest request, HttpServletResponse response) {
        String[] cids = request.getParameterValues("cid");
        if (cids.length <= 0)
            return false;

        Boolean isSuccess = false;
        for (int i = 0; i < cids.length; i++) {
            int cid = Integer.parseInt(cids[i]);
            if (cid <= 0)
                return false;

            System.out.print(cids[i]+" ");

            try {
                isSuccess = categoryService.deleteCategory(cid);

                if (isSuccess){
                    List<Category> categoryList = categoryService.findAllCategory();
                    HttpSession session = request.getSession(false);
                    session.setAttribute("categoryList",categoryList);
                    isSuccess = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    private Boolean deleteCategory(HttpServletRequest request, HttpServletResponse response) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        if (cid<=0)
            return false;

        Boolean isSuccess = false;
        Boolean deleteCategory = false;
        try {
            deleteCategory = categoryService.deleteCategory(cid);
            //更新session域的列表值
            if (deleteCategory){
                List<Category> categoryList = categoryService.findAllCategory();
                HttpSession session = request.getSession(false);
                session.setAttribute("categoryList",categoryList);
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return isSuccess; 
    }

    private Boolean findAllCategory(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = null;
        try {
            categories = categoryService.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //待优化
        HttpSession session = request.getSession(true);
        session.setAttribute("categories",categories);

        return true;
    }

    private Boolean updateCategory(HttpServletRequest request, HttpServletResponse response) {
        if ("".equals(request.getParameter("cname")) || "null".equals(request.getParameter("cname")+""))
            return false;

        int cid = Integer.parseInt(request.getParameter("cid"));
        String cname = request.getParameter("cname");
        Category category = new Category(cid,cname);

        Boolean isSuccess = false;
        try {
            Boolean isSuccessUpdate = categoryService.updateCategory(category);

            if (isSuccessUpdate){
                List<Category> categoryList = categoryService.findAllCategory();
                HttpSession session = request.getSession(false);
                session.setAttribute("categoryList",categoryList);
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    private Boolean addCategory(HttpServletRequest request, HttpServletResponse response) {
        if ("".equals(request.getParameter("cname")) || "null".equals(request.getParameter("cname")+""))
            return false;

        String cname = request.getParameter("cname");
        Category category = new Category();
        category.setCname(cname);

        Boolean isSuccess = false;
        try {
            Boolean isSuccessAdd = categoryService.addCategory(category);

            if (isSuccessAdd){
                List<Category> categoryList = categoryService.findAllCategory();
                HttpSession session = request.getSession(false);
                session.setAttribute("categoryList",categoryList);
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return isSuccess;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
