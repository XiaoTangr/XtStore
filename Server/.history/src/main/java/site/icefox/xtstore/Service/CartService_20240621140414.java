package site.icefox.xtstore.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Dao.UserDao;
import site.icefox.xtstore.Entities.GoodsInCart;
import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Utils.JsonUtil;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CartService {
    /**
     * get cart of user
     *
     * @param request  req
     * @param response res
     * @throws IOException e
     */
    public static void getCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User reqUser = UserService.getUserInfoFromHeader(request);
        if (reqUser == null) {
            RespSendUtil.sendErrorResponse(response, "User not found!");
            return;
        }
        try {
            List<GoodsInCart> list = getUserCartByID(reqUser.getUserID());

            RespSendUtil.sendSuccessResponse(response, "获取购物车成功!", list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update cart of user
     *
     * @param request  req
     * @param response res
     * @throws IOException  e
     * @throws SQLException e
     */
    public static void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        User reqUser = UserService.getUserInfoFromHeader(request);

        String reqCartString = request.getParameter("UserCart");
        if (reqUser == null) {
            RespSendUtil.sendErrorResponse(response, "User not found!");
            return;
        }
        if (reqCartString == null) {
            RespSendUtil.sendErrorResponse(response, "缺少参数!");
            return;
        }
        if (UserService.updateUserCartByStr(reqUser.getUserID(), reqCartString)) {

            RespSendUtil.sendSuccessResponse(response, "更新购物车成功!", CartService.getUserCartByID(reqUser.getUserID()));
            return;
        }
        RespSendUtil.sendErrorResponse(response, "更新购物车失败!");

    }

    /**
     * get cart of user
     *
     * @param UserID userid
     * @return list of GoodsInCart
     * @throws SQLException e
     */
    private static List<GoodsInCart> getUserCartByID(long UserID) throws SQLException {
        return UserDao.queryUserCart(UserID);
    }

    /**
     * delete cart of user
     *
     * @param request  req
     * @param response res
     */
    public static void deleteCart(HttpServletRequest request, HttpServletResponse response) {
        User reqUser = UserService.getUserInfoFromHeader(request);

        try {
            if (reqUser == null) {
                RespSendUtil.sendErrorResponse(response, "User not found!");
                return;
            }
            if (UserService.updateUserCartByStr(reqUser.getUserID(), null)) {
                RespSendUtil.sendSuccessResponse(response, "删除购物车成功!");
                return;
            }
            RespSendUtil.sendErrorResponse(response, "删除购物车失败!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
