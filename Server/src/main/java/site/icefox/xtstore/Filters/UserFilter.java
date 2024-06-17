package site.icefox.xtstore.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Service.UserService;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/api/user")
public class UserFilter implements Filter {
    private final Set<String> allowedMethods = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedMethods.add("GET");
        allowedMethods.add("POST");
        allowedMethods.add("PUT");
        allowedMethods.add("DELETE");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


//         检查请求方法是否在允许的方法列表中
        if (!allowedMethods.contains(request.getMethod())) {
            RespSendUtil.sendErrorResponse(response, "Method Not Allowed!");
            return;
        }
//        放行注册方法
        if ("POST".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        if ("GET".equals(request.getMethod()) || "PUT".equals(request.getMethod()) || "DELETE".equals(request.getMethod())) {
            User reqUser = UserService.getUserInfoFromHeader(request);
            if (reqUser == null) {
                RespSendUtil.sendErrorResponse(response, "Unauthorized!");
                return;
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
