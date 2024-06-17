package site.icefox.xtstore.Servlets;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Service.CartService;

@WebServlet("/api/cart")
@MultipartConfig
public class CartServlet extends HttpServlet {
    /**
     * 获取购物车
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            CartService.getCart(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新购物车
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            CartService.updateCart(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){

            CartService.deleteCart(request, response);

    }
}