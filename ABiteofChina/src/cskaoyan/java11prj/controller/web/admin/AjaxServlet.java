package cskaoyan.java11prj.controller.web.admin;

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
 * Date: 2018/11/13
 * Time: 下午 8:37
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "AjaxServlet",urlPatterns = "/admin/AjaxServlet")
public class AjaxServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    private boolean isUserUsernameAvailable(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");

        try {
            boolean available = userService.isUserUsernameAvailable(username);
            if (available){
                response.getWriter().print("true");
                return true;
            }
            else
                response.getWriter().print("false");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajax = request.getParameter("ajax");

        switch (ajax){
            case "isUserUsernameAvailable":
                isUserUsernameAvailable(request,response);
                break;
            case "isUserEmailAvailable":
                boolean available = isUserEmailAvailable(request,response);
                break;
            default:
                response.getWriter().println("");
                break;
        }
    }

    private boolean isUserEmailAvailable(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");

        try {
            boolean available = userService.isUserEmailAvailable(email);
            if (available){
                response.getWriter().print("true");
                return true;
            }
            else
                response.getWriter().print("false");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
