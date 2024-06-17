package site.icefox.xtstore.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/api/cart")
public class CartFilter implements Filter {

    private final Set<String> allowedMethods = new HashSet<>();
    @Override
    public void init(FilterConfig filterConfig) {
        allowedMethods.add("POST");
        allowedMethods.add("GET");
        allowedMethods.add("PUT");
        allowedMethods.add("DELETE");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!allowedMethods.contains(request.getMethod())) {
            RespSendUtil.sendErrorResponse(response, "Method Not Allowed");
            return;
        }
        String Authorization = request.getHeader("Authorization");
        if (Authorization == null) {
            RespSendUtil.sendErrorResponse(response, "Unauthorized");
            return;
        }
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
