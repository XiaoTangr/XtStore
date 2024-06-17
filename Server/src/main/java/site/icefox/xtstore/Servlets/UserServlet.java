package site.icefox.xtstore.Servlets;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Dao.UserDao;
import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Service.UserService;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//完成的接口
@WebServlet("/api/user")
@MultipartConfig
public class UserServlet extends HttpServlet {
    /**
     * 处理POST方法的请求, 用于创建新用户(注册和超级管理员添加)
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {


            UserService.insertUser(request, response);

    }

    /**
     * 删除用户
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        UserService.deleteUser(request, response);
    }

    /**
     * 获取用户信息
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        UserService.getUser(request, response);
    }

    /**
     * 更新用户信息
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

        UserService.updateUser(request, response);

    }
}
