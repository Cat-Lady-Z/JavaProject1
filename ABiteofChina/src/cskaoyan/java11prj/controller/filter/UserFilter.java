package cskaoyan.java11prj.controller.filter;

import cskaoyan.java11prj.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/12
 * Time: 下午 5:22
 * Detail requirement:
 * Method:
 */
@WebFilter(filterName = "UserFilter",urlPatterns = "/user/*")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();

        if (uri.endsWith("/UserServlet") || uri.endsWith("/login.jsp") || uri.endsWith("/register.jsp")
                || uri.endsWith("/style.css")){
            chain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession(false);
            if (session != null ){
                User user = (User) session.getAttribute("user");
                if (user == null){
                    String path = request.getContextPath() + "/user/login.jsp";
                    response.getWriter().println("<script>alert('请先登录!');window.location.href='"+ path +"'</script>");
                } else {
                    chain.doFilter(req, resp);
                }
            } else {
                String path = request.getContextPath() + "/user/login.jsp";
                response.getWriter().println("<script>alert('请先登录!');window.location.href='"+ path +"'</script>");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
