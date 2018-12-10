package cskaoyan.java11prj.controller.web.user;

import cskaoyan.java11prj.domain.User;
import cskaoyan.java11prj.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/14
 * Time: 下午 10:55
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "ActiveUserServlet",urlPatterns = "/ActiveUser")
public class ActiveUserServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activecode = request.getParameter("activecode");

        try {
            User user = userService.isActivecodeExist(activecode);
            if (user != null){
                if (user.getStatus() !=1){
                    user.setStatus(1);
                    if (userService.updateUser(user)){
                        response.getWriter().println("激活成功，请登录！");
                        response.setHeader("refresh","1;url=" + request.getContextPath() + "/user/login.jsp");
                    } else {
                        response.getWriter().println("激活失败，请重新注册！");
                        response.setHeader("refresh","1;url=" + request.getContextPath() + "/user/register.jsp");
                    }
                } else {
                    response.getWriter().println("已经激活成功过了哟，请登录！");
                    response.setHeader("refresh","1;url=" + request.getContextPath() + "/user/login.jsp");
                }
            } else {
                response.getWriter().println("激活失败，请重新注册！");
                response.setHeader("refresh","1;url=" + request.getContextPath() + "/user/register.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


    }
}
