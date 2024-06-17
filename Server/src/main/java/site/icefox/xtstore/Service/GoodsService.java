package site.icefox.xtstore.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Dao.GoodsDao;
import site.icefox.xtstore.Entities.Goods;
import site.icefox.xtstore.Utils.ParamCheckUtil;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GoodsService {

    /**
     * 商品是否存在
     *
     * @param GoodsID 商品id
     * @return 是否存在
     * @throws SQLException e
     */
    public static boolean isGoodsExist(int GoodsID) throws SQLException {
        return GoodsDao.queryOneGoodsById(GoodsID) != null;
    }

    /**
     * 插入商品
     *
     * @param request  req
     * @param response res
     * @throws IOException  e
     * @throws SQLException e
     */
    public static void insertGoods(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        String rGoodsID = request.getParameter("GoodsID");
        String rGoodsName = request.getParameter("GoodsName");
        String rGoodsDesc = request.getParameter("GoodsDesc");
        String rGoodsImg = request.getParameter("GoodsImg");
        String rGoodsPerPrice = request.getParameter("GoodsPerPrice");
        String rGoodsPerUnit = request.getParameter("GoodsPerUnit");
        String rGoodsInven = request.getParameter("GoodsInven");
        String rGoodsCate = request.getParameter("GoodsCate");

        if (!ParamCheckUtil.hasAllParams(request, Goods.class)) {
            RespSendUtil.sendErrorResponse(response, "缺少参数");
            return;
        }
        int GoodsID = Integer.parseInt(rGoodsID);
        if (isGoodsExist(GoodsID)) {
            RespSendUtil.sendErrorResponse(response, "商品已存在");
            return;
        }
        Goods newGoods = new Goods();
        newGoods.setGoodsID(GoodsID);
        newGoods.setGoodsName(rGoodsName);
        newGoods.setGoodsDesc(rGoodsDesc);
        newGoods.setGoodsImg(rGoodsImg);
        newGoods.setGoodsPerPrice(Double.parseDouble(rGoodsPerPrice));
        newGoods.setGoodsPerUnit(rGoodsPerUnit);
        newGoods.setGoodsInven(Integer.parseInt(rGoodsInven));
        newGoods.setGoodsCate(rGoodsCate);
        if (!CategoryService.isCategoryExist(newGoods.getGoodsCate())) {
            RespSendUtil.sendErrorResponse(response, "分类不存在");
            return;
        }
        if (!GoodsDao.insertGoods(newGoods)) {
            RespSendUtil.sendErrorResponse(response, "插入失败");
            return;
        }
        RespSendUtil.sendSuccessResponse(response, "插入成功", newGoods);
    }

    /**
     * del goods
     *
     * @param request  req
     * @param response res
     * @throws IOException  e
     * @throws SQLException e
     */
    public static void deleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        String rGoodsID = request.getParameter("GoodsID");

        if (!ParamCheckUtil.hasAllParams(request, Goods.class)) {
            RespSendUtil.sendErrorResponse(response, "缺少参数");
            return;
        }
        int GoodsID = Integer.parseInt(rGoodsID);
        if (!isGoodsExist(GoodsID)) {
            RespSendUtil.sendErrorResponse(response, "商品不存在");
            return;
        }
        if (!GoodsDao.deleteGoodsById(GoodsID)) {
            try {
                RespSendUtil.sendErrorResponse(response, "删除失败");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        RespSendUtil.sendSuccessResponse(response);

    }

    /**
     * get all goods
     *
     * @param response res
     */
    public static void getAllGoods(HttpServletResponse response) {
        List<Goods> list;
        try {

            list = GoodsDao.queryAllGoods();
            RespSendUtil.sendSuccessResponse(response, list);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get all goods by goods category name
     *
     * @param response     res
     * @param reqGoodsCate 分类名称
     */
    public static void getAllGoodsByCate(HttpServletResponse response, String reqGoodsCate) {
        try {
            List<Goods> list = GoodsDao.queryGoodsByCateName(reqGoodsCate);
            if (!CategoryService.isCategoryExist(reqGoodsCate)) {
                RespSendUtil.sendErrorResponse(response, "分类不存在");
                return;
            }
            if (list == null) {
                RespSendUtil.sendErrorResponse(response, "分类下不存在商品");
                return;
            }
            RespSendUtil.sendSuccessResponse(response, list);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * get one goods by id
     *
     * @param response res
     * @param goodsID  goodsid
     */
    public static void getOneGoods(HttpServletResponse response, int goodsID) {
        Goods goods;
        try {
            goods = GoodsDao.queryOneGoodsById(goodsID);
            RespSendUtil.sendSuccessResponse(response, goods);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update goods
     *
     * @param request  req
     * @param response res
     */
    public static void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String paramGoodsID = request.getParameter("GoodsID");
        Goods reqGoods;
        int GoodsID;
        if (paramGoodsID == null) {
            RespSendUtil.sendErrorResponse(response, "缺少参数");
            return;
        }
        GoodsID = Integer.parseInt(paramGoodsID);
        reqGoods = GoodsDao.queryOneGoodsById(GoodsID);

        if (reqGoods == null) {
            RespSendUtil.sendErrorResponse(response, "商品不存在");
            return;
        }
        if (request.getParameter("GoodsName") != null)
            reqGoods.setGoodsName(request.getParameter("GoodsName"));
        if (request.getParameter("GoodsDesc") != null)
            reqGoods.setGoodsDesc(request.getParameter("GoodsDesc"));
        if (request.getParameter("GoodsImg") != null)
            reqGoods.setGoodsImg(request.getParameter("GoodsImg"));
        if (request.getParameter("GoodsPerPrice") != null)
            reqGoods.setGoodsPerPrice(Double.parseDouble(request.getParameter("GoodsPerPrice")));
        if (request.getParameter("GoodsPerUnit") != null)
            reqGoods.setGoodsPerUnit(request.getParameter("GoodsPerUnit"));
        if (request.getParameter("GoodsInven") != null)
            reqGoods.setGoodsInven(Integer.parseInt(request.getParameter("GoodsInven")));
        if (request.getParameter("GoodsCate") != null)
            reqGoods.setGoodsCate(request.getParameter("GoodsCate"));
        if (!GoodsDao.updateGoods(reqGoods)) {
            RespSendUtil.sendErrorResponse(response, "更新失败");
        } else {
            RespSendUtil.sendSuccessResponse(response, "更新成功", reqGoods);
        }

    }


}
