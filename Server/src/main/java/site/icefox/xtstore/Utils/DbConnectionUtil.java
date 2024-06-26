package site.icefox.xtstore.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnectionUtil {
    private Connection conn;

//    数据库信息
    String className = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/XtStore";
    String username = "root";
    String password = "root";

    // 构造函数
    public DbConnectionUtil() {
        conn = null;
    }

    // 获取数据库连接
    public Connection getConn() {
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
