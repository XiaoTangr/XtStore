package site.icefox.xtstore.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Dao.CategoryDao;
import site.icefox.xtstore.Entities.Category;
import site.icefox.xtstore.Utils.ParamCheckUtil;
import site.icefox.xtstore.Utils.RespSendUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryService {

    /**
     * 判断分类是否存在
     *
     * @param CateName 分类名称
     * @return 是否存在
     */
    public static boolean isCategoryExist(String CateName) throws SQLException {
        return CategoryDao.queryOneCateByName(CateName) != null;
    }

    /**
     * 添加分类
     *
     * @param request  请求
     * @param response 响应
     */
    public static void insertCategory(HttpServletRequest request, HttpServletResponse response) {
        try {


            if (!ParamCheckUtil.hasAllParams(request, Category.class)) {
                RespSendUtil.sendErrorResponse(response, "缺少必要参数!");
                return;
            }
            Category cate = new Category();
            cate.setCateName(request.getParameter("CateName"));

            if (isCategoryExist(cate.getCateName())) {
                RespSendUtil.sendErrorResponse(response, "分类已存在");
                return;
            }
            if (CategoryDao.insertCate(cate)) {
                Category newcate = CategoryDao.queryOneCateByName(cate.getCateName());
                RespSendUtil.sendSuccessResponse(response, "添加成功", newcate);
            } else {
                RespSendUtil.sendErrorResponse(response, "添加失败");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有分类
     *
     * @param request  请求
     * @param response 响应
     */
    public static void getAllCategory(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = null;
        try {
            categories = CategoryDao.queryAllCate();
            RespSendUtil.sendSuccessResponse(response, categories);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新分类
     *
     * @param request  请求
     * @param response 响应
     */
    public static void updateCategory(HttpServletRequest request, HttpServletResponse response) {
        String oldCategory = request.getParameter("oldCategory");
        String newCategory = request.getParameter("newCategory");
        try {
            if (oldCategory == null || newCategory == null) {
                RespSendUtil.sendErrorResponse(response, "缺少必要参数!");
                return;
            }
            if (isCategoryExist(newCategory)) {
                RespSendUtil.sendErrorResponse(response, "分类已存在");
                return;
            }
            if (CategoryDao.updateCate(oldCategory, newCategory)) {
                RespSendUtil.sendSuccessResponse(response, "修改成功");
            } else {
                RespSendUtil.sendErrorResponse(response, "修改失败");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除分类
     *
     * @param request  请求
     * @param response 响应
     */
    public static void deleteCategory(HttpServletRequest request, HttpServletResponse response) {
        String category = request.getParameter("category");
        try {
            if (category == null) {
                RespSendUtil.sendErrorResponse(response, "缺少必要参数!");
                return;
            }
            if (!isCategoryExist(category)) {
                RespSendUtil.sendErrorResponse(response, "分类不存在");
                return;
            }
            if (CategoryDao.deleteCateByName(category)) {
                RespSendUtil.sendSuccessResponse(response, "删除成功");
            } else {
                RespSendUtil.sendErrorResponse(response, "删除失败");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
