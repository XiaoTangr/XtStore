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

@WebFilter("/api/category")
public class CategoryFilter implements Filter {


    private final Set<String> allowedMethods = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedMethods.add("POST");
        allowedMethods.add("GET");
        allowedMethods.add("PUT");
        allowedMethods.add("DELETE");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
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
            if (reqUser.getUserType() != 2) {
                RespSendUtil.sendErrorResponse(response, "Permission Denied!");
                return;
            }
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
