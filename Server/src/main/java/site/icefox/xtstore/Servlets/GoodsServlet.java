package site.icefox.xtstore.Servlets;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Service.GoodsService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/goods")
@MultipartConfig

public class GoodsServlet extends HttpServlet {

    //    添加商品
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            GoodsService.insertGoods(request, response);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    删除商品
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            GoodsService.deleteGoods(request, response);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    查询商品
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String reqGoodsID = request.getParameter("GoodsID");
        String reqGoodsCate = request.getParameter("GoodsCate");
        int GoodsID;
        if (reqGoodsID != null) {
            GoodsID = Integer.parseInt(reqGoodsID);
            GoodsService.getOneGoods(response, GoodsID);
            return;
        }
        if (reqGoodsCate != null) {
            GoodsService.getAllGoodsByCate(response, reqGoodsCate);
            return;
        }
        GoodsService.getAllGoods(response);
    }

    //    修改商品
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            GoodsService.updateGoods(request, response);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
