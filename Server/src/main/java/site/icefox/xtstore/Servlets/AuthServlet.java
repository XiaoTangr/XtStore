package site.icefox.xtstore.Servlets;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Service.UserService;

import java.io.IOException;
import java.sql.SQLException;

//完成的接口
@WebServlet("/api/auth")
@MultipartConfig
public class AuthServlet extends HttpServlet {

    /**
     * 登录
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
            UserService.login(request, response);
    }

    /**
     * 登录验证 get
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
            UserService.isAuthed(request, response);
    }
}
