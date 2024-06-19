package site.icefox.xtstore.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import site.icefox.xtstore.Dao.GoodsDao;
import site.icefox.xtstore.Utils.RespSendUtil;
import site.icefox.xtstore.Utils.ImageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class ImageService {
    /**
     * insert img of goods
     *
     * @param request  req
     * @param response res
     */
    public static void insertImage(HttpServletRequest request, HttpServletResponse response) {
        // 获取商品ID参数
        String goodsIdParam = request.getParameter("GoodsID");
        try {


            if (goodsIdParam == null || goodsIdParam.isEmpty()) {
                RespSendUtil.sendErrorResponse(response, "缺少商品ID参数");
                return;
            }

            int goodsId = Integer.parseInt(goodsIdParam);
            if (!GoodsService.isGoodsExist(goodsId)) {
                RespSendUtil.sendErrorResponse(response, "商品不存在");
                return;
            }
            // 判断请求是否为 multipart/form-data 类型
            if (!request.getContentType().startsWith("multipart/form-data")) {
                RespSendUtil.sendErrorResponse(response, "内容类型无效，期望 multipart/form-data");
                return;
            }
            // 处理文件部分
            for (Part part : request.getParts()) {
                // 获取上传文件的文件名
                String fileName = part.getSubmittedFileName();
                if (fileName == null || fileName.isEmpty()) {
                    continue; // 如果文件名为空，跳过该文件部分
                }
                // 获取文件类型
                String contentType = part.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    RespSendUtil.sendErrorResponse(response, "无效的文件类型，期望 image/*");
                    return;
                }

                // 获取图像格式 (如 "png", "jpeg")
                String imageFormat = contentType.substring(contentType.lastIndexOf('/') + 1);

                // 读取文件内容并进行 Base64 编码
                try (InputStream inputStream = part.getInputStream()) {
                    String imageBase64 = ImageUtil.encodeImageToBase64(inputStream,imageFormat);

                    // 更新编码后的图片到数据库
                    if (GoodsDao.updateGoodsImage(goodsId, imageBase64)) {
                        RespSendUtil.sendErrorResponse(response, "保存图片到数据库失败");
                        return;
                    }
                }
                String ImgDataUrl = GoodsDao.queryGoodsImgById(goodsId);
                // 返回成功响应
                RespSendUtil.sendSuccessResponse(response, "商品图片插入成功!", ImgDataUrl);
            }
        } catch (ServletException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update img of goods
     *
     * @param request  req
     * @param response res
     */
    public static void updateImage(HttpServletRequest request, HttpServletResponse response) {
        // 获取商品ID参数
        String goodsIdParam = request.getParameter("GoodsID");
        try {


            if (goodsIdParam == null || goodsIdParam.isEmpty()) {
                RespSendUtil.sendErrorResponse(response, "缺少商品ID参数");
                return;
            }

            int goodsId = Integer.parseInt(goodsIdParam);
            if (!GoodsService.isGoodsExist(goodsId)) {
                RespSendUtil.sendErrorResponse(response, "商品不存在");
                return;
            }
            // 判断请求是否为 multipart/form-data 类型
            if (!request.getContentType().startsWith("multipart/form-data")) {
                RespSendUtil.sendErrorResponse(response, "内容类型无效，期望 multipart/form-data");
                return;
            }
            // 处理文件部分
            for (Part part : request.getParts()) {
                // 获取上传文件的文件名
                String fileName = part.getSubmittedFileName();
                if (fileName == null || fileName.isEmpty()) {
                    continue; // 如果文件名为空，跳过该文件部分
                }
                // 获取文件类型
                String contentType = part.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    RespSendUtil.sendErrorResponse(response, "无效的文件类型，期望 image/*");
                    return;
                }

                // 获取图像格式 (如 "png", "jpeg")
                String imageFormat = contentType.substring(contentType.lastIndexOf('/') + 1);

                // 读取文件内容并进行 Base64 编码
                try (InputStream inputStream = part.getInputStream()) {
                    String imageBase64 = ImageUtil.encodeImageToBase64(inputStream, imageFormat);

                    // 更新编码后的图片到数据库
                    if (!GoodsDao.updateGoodsImage(goodsId, imageBase64)) {
                        RespSendUtil.sendErrorResponse(response, "保存图片到数据库失败");
                        return;
                    }
                }
                String ImgDataUrl = GoodsDao.queryGoodsImgById(goodsId);
                // 返回成功响应
                RespSendUtil.sendSuccessResponse(response, "商品图片更新成功!", ImgDataUrl);
            }
        } catch (ServletException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * del img of goods
     *
     * @param request  req
     * @param response res
     */
    public static void deleteImage(HttpServletRequest request, HttpServletResponse response) {
        String goodsIdParam = request.getParameter("GoodsID");
        try {
            if (goodsIdParam == null || goodsIdParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少商品ID参数");
                return;
            }

            int goodsId = Integer.parseInt(goodsIdParam);

            if (!GoodsService.isGoodsExist(goodsId)) {
                // 返回成功响应
                RespSendUtil.sendErrorResponse(response, "商品不存在");
                return;
            }
            // 删除数据库中的商品图片信息
            if (!GoodsDao.deleteGoodsImage(goodsId)) {
                RespSendUtil.sendErrorResponse(response, "删除商品图片失败");
                return;
            }
            // 返回成功响应
            RespSendUtil.sendSuccessResponse(response, "商品图片删除成功");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询商品图片
     *
     * @param request  request
     * @param response response
     */
    public static void getImage(HttpServletRequest request, HttpServletResponse response) {
        String goodsIdParam = request.getParameter("GoodsID");
        try {


            if (goodsIdParam == null || goodsIdParam.isEmpty()) {
                RespSendUtil.sendErrorResponse(response, "缺少商品ID参数");
                return;
            }

            int goodsId = Integer.parseInt(goodsIdParam);

            if (!GoodsService.isGoodsExist(goodsId)) {
                // 返回成功响应
                RespSendUtil.sendErrorResponse(response, "商品不存在");
                return;
            }
            String ImgDataUrl = GoodsDao.queryGoodsImgById(goodsId);
            // 返回成功响应
            RespSendUtil.sendSuccessResponse(response, "商品图片获取成功!", ImgDataUrl);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
