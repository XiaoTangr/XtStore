package site.icefox.xtstore.Servlets;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Entities.Category;
import site.icefox.xtstore.Service.CategoryService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/category")
@MultipartConfig
public class CategoryServlet extends HttpServlet {

    // 添加分类
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        CategoryService.insertCategory(request, response);
    }

    // 获取分类
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        CategoryService.getAllCategory(request, response);
    }

    // 删除分类
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        CategoryService.deleteCategory(request, response);
    }

    // 修改分类
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        CategoryService.updateCategory(request, response);
    }
}
