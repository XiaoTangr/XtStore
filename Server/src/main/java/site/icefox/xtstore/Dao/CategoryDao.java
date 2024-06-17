package site.icefox.xtstore.Dao;

import site.icefox.xtstore.Entities.Category;
import site.icefox.xtstore.Utils.DbConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public static boolean insertCate(Category category) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "INSERT INTO category( CateName) VALUES (?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category.getCateName());

            return pstmt.executeUpdate() > 0;
        } finally {

            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 通过名称删除分类
     *
     * @param CateName CateName
     * @return boolean
     * @throws SQLException e
     */
    public static boolean deleteCateByName(String CateName) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "DELETE FROM category WHERE CateName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CateName);

            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 通过名称查询分类
     *
     * @param CateName CateName
     * @return Category对象
     * @throws SQLException e
     */
    public static Category queryOneCateByName(String CateName) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Category category = new Category();
        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM category WHERE CateName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CateName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                category.setCateName("CateName");
            }
            return category;

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 查询所有分类
     *
     * @return List<Category> 所有分类
     * @throws SQLException e
     */
    public static List<Category> queryAllCate() throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Category> categories = new ArrayList<>();
        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM category";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCateName(rs.getString("CateName"));
                categories.add(category);
            }
            return categories;
        } finally {

            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 更新分类名称
     *
     * @param oldCateNAme oldCateNAme
     * @param newCateNAme newCateNAme
     * @return 是否成功
     * @throws SQLException e
     */
    public static boolean updateCate(String oldCateNAme, String newCateNAme) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "UPDATE category SET CateName = ? WHERE CateName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCateNAme);
            pstmt.setString(2, oldCateNAme);
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
}

