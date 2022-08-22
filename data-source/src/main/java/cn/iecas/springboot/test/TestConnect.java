package cn.iecas.springboot.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnect {
    // 定义数据库驱动名称
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    // 定义数据库url
    private static String url = "jdbc:mysql://localhost:3306";
    //定义数据库连接用户名
    private static String username = "root";
    //定义数据库连接密码
    private static String password = "123";

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driverName);
            System.out.println("loaded driver");
            System.out.println("jdbc url [" + url + "]");
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("get success connection");
//            connection.setAutoCommit(false);
//            PreparedStatement stmt = conn.prepareStatement("select sysdate from dual");
//            rs = stmt.executeQuery();
//            conn.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
                System.out.println("connection closed");
            }
        }
    }
}
