package cskaoyan.java11prj.controller.web.user;

import cskaoyan.java11prj.domain.User;
import cskaoyan.java11prj.util.MD5Util;
import cskaoyan.java11prj.util.SendEmailUtil;
import org.apache.commons.beanutils.BeanUtils;
import cskaoyan.java11prj.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/13
 * Time: 下午 8:17
 * Detail requirement:
 * Method:
 */
@WebServlet(name = "UserServlet", urlPatterns = "/user/UserServlet")
public class UserServlet extends HttpServlet {
    UserServiceImpl userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        switch (op){
            case "login":
                login(request,response);
                break;
            case "register":
                register(request,response);
                break;
            case "logout":
                logout(request,response);
                break;
            case "update":
                update(request,response);
                break;
            default:
                response.getWriter().println("操作出错了哦");
                response.setHeader("refresh","0;url=" + request.getContextPath() + "/index.jsp");
                break;
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = false;
        User user = new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());

            Boolean success = userService.updateUser(user);
            if (success){
                response.getWriter().println("更新成功，请重新登录。即将跳转登录页面！");
                HttpSession session = request.getSession(false);
                if (session != null){
                    session.setAttribute("user",null);
                }
                flag =true;
                response.setHeader("refresh","0;url=" + request.getContextPath() + "/index.jsp");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!flag){
            try {
                response.getWriter().println("更新失败！");
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/personal.jsp");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session != null){
            session.setAttribute("user",null);
        }

        response.setHeader("refresh","0;url=" + request.getContextPath() + "/index.jsp");
    }

    private void register(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
            String updatetime = "" + (new Timestamp(System.currentTimeMillis()));
            user.setUpdatetime(updatetime);

            String username = user.getUsername();
            String password = user.getPassword();
            String nickname = user.getNickname();
            String birthday = user.getBirthday();
            String email = user.getEmail();

            if (isUsernameAvailable(username) && isNicknameAvailable(nickname) && isPasswordAvailable(password)
                    && isBirthdayAvailable(birthday) && isEmailAvailable(email)){

                UUID uuid = UUID.randomUUID();
                String activecode = uuid.toString();
                String passwordParam = password + username + activecode;
                password = MD5Util.encrypt(passwordParam);

                user.setPassword(password);
                user.setActivecode(activecode);
                Boolean isSuccess = userService.regist(user);
                if (isSuccess){
                    response.getWriter().println("注册成功，请记得收邮件激活哦！");
                    Cookie usernameCookie = new Cookie("username", username);
                    response.addCookie(usernameCookie);

                    //发送激活邮件
                    String content= "请复制访问以下网页激活账号: http://192.168.4.54:80/ActiveUser?activecode=" + activecode;
                    SendEmailUtil.sendEmail(email,content);

                    response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/login.jsp");
                } else {
                    response.getWriter().println("注册失败，请重新输入试一下吧~");
                    response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/register.jsp");
                }
            } else {
                response.getWriter().println("注册失败，请重新输入试一下吧~");
                response.setHeader("refresh","0;url=" + request.getContextPath() + "/user/register.jsp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return;
        }
    }

    private boolean isEmailAvailable(String email) {
        boolean result = false;
        if (email != null && !"".equals(email)){
            String ePattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
            result = email.matches(ePattern);
        }
        return result;
    }

    private boolean isBirthdayAvailable(String birthday) {
        boolean result = false;
        if (birthday != null && !"".equals(birthday)){
            String bPattern = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
            result = birthday.matches(bPattern);
        }
        return result;
    }

    private boolean isPasswordAvailable(String password) {
        boolean result = false;
        if (password != null && !"".equals(password)){
            String pPattern = "^[a-zA-Z0-9_-]{4,16}$";
            result = password.matches(pPattern);
        }
        return result;
    }

    private boolean isNicknameAvailable(String nickname) {
        boolean result = false;
        if (nickname != null && !"".equals(nickname)){
            String nPattern = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,16}$";
            result = nickname.matches(nPattern);
        }
        return result;
    }

    private boolean isUsernameAvailable(String username) {
        boolean result = false;
        if (username != null && !"".equals(username)){
            String uPattern = "^\\w{3,8}$";
            result = username.matches(uPattern);
        }
        return result;
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("verifyCode");
        HttpSession session = request.getSession(false);
        String checkcode_session = (String)session.getAttribute("checkcode_session");

        try {
            if (!checkcode_session.equals(verifyCode)){
                response.getWriter().println("验证码输入错误，请重新输入试一下吧~");
                response.setHeader("refresh","1;url=" + request.getContextPath() + "/index.jsp");
                return;
            }

            User user = userService.login(username, password);
            if (user == null)
                response.getWriter().println("用户名或密码输入错误，请重新输入试一下吧~");
            else{
                response.getWriter().println("登录成功！");
                session.setAttribute("user",user);
            }

            response.setHeader("refresh","1;url=" + request.getContextPath() + "/index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
