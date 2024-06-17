package site.icefox.xtstore.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnectionUtil {
    private Connection conn;

    // 构造函数
    public DbConnectionUtil() {
        conn = null;
    }

    // 获取数据库连接
    public Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//mysql5之后，这⾏代码可以不写
            String url = "jdbc:mysql://localhost:3306/XtStore"; //ctr+D 快速复制⼀⾏代码
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
