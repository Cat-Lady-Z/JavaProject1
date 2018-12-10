package cskaoyan.java11prj.controller.filter;

import cskaoyan.java11prj.domain.Admin;

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
 * Date: 2018/11/07
 * Time: 下午 10:10
 * Detail requirement:
 * Method:
 */
@WebFilter(filterName = "Filter",urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();

        if (uri.endsWith("/") || (uri.endsWith("/index.jsp")) || uri.endsWith("/AdminServlet") || uri.endsWith("/AjaxServlet")
                || uri.endsWith("/ProductServlet")|| uri.endsWith(".gif")
                || uri.endsWith(".js") || uri.endsWith(".css")){
            chain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession(false);
            if (session != null ){
                Admin admin = (Admin) session.getAttribute("admin");
                if (admin == null){
                    String path = request.getContextPath() + "/admin/index.jsp";
                    response.getWriter().println("访问出错了哟，3 秒后返回<a href='"+ path + "'>登录页面</a>！");

                    response.setHeader("refresh","3;url=" + path);
                } else {
                    chain.doFilter(req, resp);
                }
            } else { 
                String path = request.getContextPath() + "/admin/index.jsp";
                response.getWriter().println("访问出错了哟，3 秒后返回<a href='"+ path + "'>登录页面</a>！");

                response.setHeader("refresh","3;url=" + path);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
