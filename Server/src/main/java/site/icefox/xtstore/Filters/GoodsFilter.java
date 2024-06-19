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

@WebFilter("/api/goods")

public class GoodsFilter implements Filter {

    private final Set<String> allowedMethods = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedMethods.add("POST");
        allowedMethods.add("GET");
        allowedMethods.add("PUT");
        allowedMethods.add("DELETE");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //设置跨域访问
        //允许跨域的主机地址
        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许跨域的请求方法GET, POST, HEAD 等
        response.setHeader("Access-Control-Allow-Methods", "*");
        //允许跨域的请求头
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Expose-Headers", "*");

        // 对于 OPTIONS 请求，直接返回成功
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }


        if (!allowedMethods.contains(request.getMethod())) {
            RespSendUtil.sendErrorResponse(response, "Method Not Allowed");
            return;
        }

        if (!"GET".equals(request.getMethod())) {
            User reqUser = UserService.getUserInfoFromHeader(request);
            if (reqUser == null) {
                RespSendUtil.sendErrorResponse(response, "Unauthorized");
                return;
            }
            if (reqUser.getUserType() < 1) {
                RespSendUtil.sendErrorResponse(response, "Permission Denied!");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
