package site.icefox.xtstore.Dao;

import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Utils.DbConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    /**
     * 新建用户
     *
     * @param user User对象
     * @return 结果
     * @throws SQLException e
     */
    public static boolean insertUser(User user) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "INSERT INTO User (UserID,UserName, Password, UserType,UserCart,UserPhone,UserAddr) VALUES (?,?,?,?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getUserType());
            pstmt.setString(5, user.getUserCart());
            pstmt.setString(6, user.getUserPhone());
            pstmt.setString(7, user.getUserAddr());
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 删除用户
     *
     * @param UserID UserID
     * @return 结果
     * @throws SQLException e
     */
    public static boolean deleteUser(int UserID) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "DELETE FROM User WHERE UserID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, UserID);
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param UserID 用户ID
     * @return User对象
     * @throws SQLException e
     */
    public static User queryOneUserByID(int UserID) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = new User();

        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM User WHERE UserID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, UserID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user.setUserID(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setUserType(rs.getInt("UserType"));
                user.setUserCart(rs.getString("UserCart"));
                user.setUserPhone(rs.getString("UserPhone"));
                user.setUserAddr(rs.getString("UserAddr"));
            }
            return user;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

    }

    /**
     * 查询所有用户信息
     *
     * @return User对象列表
     * @throws SQLException e
     */
    public static List<User> queryAllUser() throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = mydb.getConn();
            String sql = "SELECT * FROM User";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            // 使用通用的ResultSet转换方法
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setUserType(rs.getInt("UserType"));
                user.setUserCart(rs.getString("UserCart"));
                user.setUserPhone(rs.getString("UserPhone"));
                user.setUserAddr(rs.getString("UserAddr"));
                list.add(user);
            }
            return list;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    /**
     * 更新用户信息
     *
     * @param user user对象
     * @return true or false
     * @throws SQLException e
     */
    public static boolean updateUser(User user) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "UPDATE User SET UserName = ?, Password = ?, UserType = ?, UserCart = ?,UserPhone =? ,UserAddr= ? WHERE UserID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getUserType());
            pstmt.setString(4, user.getUserCart());
            pstmt.setString(5, user.getUserPhone());
            pstmt.setString(6, user.getUserAddr());
            pstmt.setInt(7, user.getUserID());
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 判断用户是否存在
     *
     * @param UserID 用户ID
     * @return true or false
     */
    public static boolean isUserExist(int UserID) throws SQLException {

        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "select * from user WHERE UserID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, UserID);
            return pstmt.executeQuery().next();
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 更新用户购物车信息
     *
     * @param USerID  UserID
     * @param CartStr 购物车json
     * @return boolean
     * @throws SQLException e
     */
    public static boolean updateUserCart(int USerID, String CartStr) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mydb.getConn();
            String sql = "UPDATE User SET UserCart =? WHERE UserID =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, CartStr);
            pstmt.setInt(2, USerID);
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    /**
     * 查询用户购物车信息
     *
     * @param UserID 用户ID
     * @return 购物车json
     */
    public static String queryUserCart(int UserID) throws SQLException {
        DbConnectionUtil mydb = new DbConnectionUtil();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = mydb.getConn();
            String sql = "SELECT UserCart FROM User WHERE UserID =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, UserID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("UserCart");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return null;
    }

}
