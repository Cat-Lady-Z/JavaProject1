package cskaoyan.java11prj.controller.web.admin;

import cskaoyan.java11prj.domain.Admin;
import cskaoyan.java11prj.service.impl.AdminServiceImpl;
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
 * Time: 上午 11:55
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "AdminServlet",urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
    AdminServiceImpl adminService = new AdminServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        Boolean flag = false;  //判断操作是否成功

        switch (op){
            case "login":
                if (login(request,response)){
                    response.getWriter().println("登录成功.....");
                    response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/main.jsp");
                }
                else{
                    response.getWriter().println("登录失败.....");
                    response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/index.jsp");
                }
                break;
            case "lgout":
                flag = lgout(request,response);
                if (!flag)
                    response.getWriter().println("退出失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/index.jsp");
                break;
            case "addAdmin":
                if (checkPassword(request,response))
                    flag = addAdmin(request,response);
                else
                    response.getWriter().println("两次密码输入不一致.....</br>");
                if (flag)
                    response.getWriter().println("添加成功.....");
                else
                    response.getWriter().println("添加失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/admin/addAdmin.jsp");
                break;
            case "findAllAdmin":
                flag = findAllAdmin(request,response);
                if (!flag)
                    response.getWriter().println("查看失败.....");
                response.setHeader("refresh","0;url="+ request.getContextPath() + "/admin/admin/adminList.jsp");
                break;
            case "deleteOne":
                flag = deleteOne(request,response);
                if (flag)
                    response.getWriter().println("删除成功.....");
                else
                    response.getWriter().println("删除失败.....");
                response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/admin/adminList.jsp");
                break;
            case "updateAdmin":
                if (checkPassword(request,response))
                    flag = updateAdmin(request,response);
                else
                    response.getWriter().println("两次密码输入不一致.....</br>");
                if (flag){
                    response.getWriter().println("修改成功，请重新登录。即将跳转登录页面！");
                    HttpSession session = request.getSession(false);
                    if (session != null){
                        session.setAttribute("user",null);
                    }
                    response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/index.jsp");
                }
                else {
                    response.getWriter().println("修改失败.....");
                    response.setHeader("refresh","1;url="+ request.getContextPath() + "/admin/admin/adminList.jsp");
                }
                break;
                
            default:
                System.out.println("操作失败！");
                break;
        }

    }

    private Boolean lgout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("admin");
        return true;
    }


    private boolean login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Admin admin = adminService.login(username, password);
            if (admin !=null){
                session.setAttribute("admin",admin);
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean updateAdmin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String aidString = request.getParameter("aid");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setUsername(username);

        try {
            int aid = Integer.parseInt(aidString);
            if (adminService.deleteAdmin(aid))
                if (adminService.addAdmin(admin)){
                    Page<Admin> page = adminService.findPageAdmin("1");
                    session.setAttribute("page",page);
                    return true;
                } else
                    return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        return false;
    }

    private Boolean deleteOne(HttpServletRequest request, HttpServletResponse response) {
        String aidString = request.getParameter("aid");

        try {
            int aid = Integer.parseInt(aidString);
            Boolean isSuccess = adminService.deleteAdmin(aid);

            if (isSuccess){
                Page<Admin> page = adminService.findPageAdmin("1");
                HttpSession session = request.getSession(false);
                session.setAttribute("page",page);
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
    }

    private Boolean findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String num = request.getParameter("num");
        if (num == null || num.isEmpty())
            return false;

        Page<Admin> page = null;
        try {
            page = adminService.findPageAdmin(num);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error!");
            return false;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("page",page);
        return true;
    }

    private boolean checkPassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        String checkpassword = request.getParameter("checkpassword");

        if ("".equals(password) || password==null || "".equals(checkpassword) || checkpassword==null)
            return false;
        else if (password.equals(checkpassword))
            return true;
        else
            return false;
    }

    private Boolean addAdmin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        try {
            return adminService.addAdmin(admin);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
