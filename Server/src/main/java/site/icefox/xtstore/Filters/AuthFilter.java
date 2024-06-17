package site.icefox.xtstore.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/api/auth")
public class AuthFilter implements Filter {
    private final Set<String> allowedMethods = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        登录
        allowedMethods.add("POST");
//        验证
        allowedMethods.add("GET");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!allowedMethods.contains(request.getMethod())) {
            RespSendUtil.sendErrorResponse(response, "Method Not Allowed");
            return;
        }

        if ("GET".equals(request.getMethod())) {
//            对GET请求进行拦截

//            严格模式,必须成功通过jwt获取到用户才算鉴权成功
//            if (UserService.getUserInfoFromHeader(request) == null) {
//                RespSendUtil.sendErrorResponse(response, "Unauthorized");
//                return;
//            }

//            非严格模式，只检查jwt是否成功解析
            String Authorization = request.getHeader("Authorization");
            if (Authorization == null) {
                RespSendUtil.sendErrorResponse(response, "Unauthorized");
                return;
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
