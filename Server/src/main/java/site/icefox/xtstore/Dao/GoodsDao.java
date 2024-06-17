package site.icefox.xtstore.Dao;

import site.icefox.xtstore.Entities.Goods;
import site.icefox.xtstore.Utils.DbConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDao {
    /**
     * 插入商品
     *
     * @param goods 商品对象
     * @return 是否插入成功
     * @throws SQLException e
     */
    public static boolean insertGoods(Goods goods) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "INSERT INTO goods(GoodsID, goodsName, goodsDesc, goodsImg, goodsPerPrice, goodsPerUnit,GoodsInven,GoodsCate) VALUES (?, ?, ?, ?,?,?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goods.getGoodsID());
            pstmt.setString(2, goods.getGoodsName());
            pstmt.setString(3, goods.getGoodsDesc());
            pstmt.setString(4, goods.getGoodsImg());
            pstmt.setDouble(5, goods.getGoodsPerPrice());
            pstmt.setString(6, goods.getGoodsPerUnit());
            pstmt.setInt(7, goods.getGoodsInven());
            pstmt.setString(8, goods.getGoodsCate());

            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 删除商品
     *
     * @param goodsId 商品ID
     * @return 是否删除成功
     * @throws SQLException e
     */
    public static boolean deleteGoodsById(int goodsId) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "DELETE FROM goods WHERE goodsId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goodsId);

            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 查询商品
     *
     * @param goodsId 商品ID
     * @return 商品对象
     * @throws SQLException e
     */
    public static Goods queryOneGoodsById(int goodsId) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Goods goods = null;
        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM goods WHERE goodsId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goodsId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                goods = new Goods();
                goods.setGoodsID(rs.getInt("GoodsID"));
                goods.setGoodsName(rs.getString("goodsName"));
                goods.setGoodsDesc(rs.getString("goodsDesc"));
                goods.setGoodsImg(rs.getString("goodsImg"));
                goods.setGoodsPerPrice(rs.getDouble("goodsPerPrice"));
                goods.setGoodsPerUnit(rs.getString("goodsPerUnit"));
                goods.setGoodsInven(rs.getInt("GoodsInven"));
                goods.setGoodsCate(rs.getString("GoodsCate"));

            }
            return goods;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 查询所有商品
     *
     * @return 商品对象列表
     * @throws SQLException e
     */
    public static List<Goods> queryAllGoods() throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Goods> goodsList = new ArrayList<>();
        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM goods";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setGoodsID(rs.getInt("GoodsID"));
                goods.setGoodsName(rs.getString("goodsName"));
                goods.setGoodsDesc(rs.getString("goodsDesc"));
                goods.setGoodsImg(rs.getString("goodsImg"));
                goods.setGoodsPerPrice(rs.getDouble("goodsPerPrice"));
                goods.setGoodsPerUnit(rs.getString("goodsPerUnit"));
                goods.setGoodsInven(rs.getInt("GoodsInven"));
                goods.setGoodsCate(rs.getString("GoodsCate"));
                goodsList.add(goods);

            }
            return goodsList;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 更新商品信息
     *
     * @param goods 商品对象
     * @return 是否成功
     * @throws SQLException e
     */
    public static boolean updateGoods(Goods goods) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "UPDATE goods SET goodsName = ?, goodsDesc = ?, goodsImg = ?, goodsPerPrice = ?, goodsPerUnit = ?,GoodsInven =?,GoodsCate= ? WHERE goodsId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, goods.getGoodsName());
            pstmt.setString(2, goods.getGoodsDesc());
            pstmt.setString(3, goods.getGoodsImg());
            pstmt.setDouble(4, goods.getGoodsPerPrice());
            pstmt.setString(5, goods.getGoodsPerUnit());
            pstmt.setInt(6, goods.getGoodsInven());
            pstmt.setString(7, goods.getGoodsCate());
            pstmt.setInt(8, goods.getGoodsID());

            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 更新商品图片
     *
     * @param goodsId     商品ID
     * @param imageBase64 图片的Base64编码字符串
     * @return 是否成功
     * @throws SQLException e
     */
    public static boolean updateGoodsImage(int goodsId, String imageBase64) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "UPDATE goods SET goodsImg = ? WHERE GoodsID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, imageBase64);
            pstmt.setInt(2, goodsId);

            return pstmt.executeUpdate() <= 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 删除商品图片
     *
     * @param goodsId 商品ID
     * @return 是否删除成功
     * @throws SQLException e
     */
    public static boolean deleteGoodsImage(int goodsId) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "UPDATE goods SET goodsImg = NULL WHERE goodsId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goodsId);

            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 查询商品图片信息
     *
     * @param goodsId 商品ID
     * @return 商品图片信息（Base64 编码的字符串）
     */
    public static String queryGoodsImgById(int goodsId) throws SQLException {
        String goodsImg = null;
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = mydb.getConn();
            String sql = "SELECT GoodsImg FROM goods WHERE GoodsId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goodsId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                goodsImg = rs.getString("GoodsImg");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();

        }

        return goodsImg;
    }

    public static List<Goods> queryGoodsByCateName(String CateName) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Goods> goodsList = new ArrayList<>();
        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM goods WHERE GoodsCate = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CateName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setGoodsID(rs.getInt("GoodsID"));
                goods.setGoodsName(rs.getString("goodsName"));
                goods.setGoodsDesc(rs.getString("goodsDesc"));
                goods.setGoodsImg(rs.getString("goodsImg"));
                goods.setGoodsPerPrice(rs.getDouble("goodsPerPrice"));
                goods.setGoodsPerUnit(rs.getString("goodsPerUnit"));
                goods.setGoodsInven(rs.getInt("GoodsInven"));
                goods.setGoodsCate(rs.getString("GoodsCate"));
                goodsList.add(goods);
            }
            return goodsList;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
}
