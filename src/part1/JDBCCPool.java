//1.JDBC
//  连接池
//1.概念
//    1.什么是连接池     管理Connection的工具
//    2.为什么用连接池   因为获取连接，释放资源非常消耗系统资源，使用连接池来共享Connection
//    3.基本原理  从池中获取Connection，使用完后调用close()不是销毁Connection，而是归还到池中
//                java提供了javax.sql.DataSource公共接口，厂商自行实现接口让程序能使用不同厂商的连接池
//    4.常见连接池 DBCP,C3P0
//2.手写简单的自定义连接池
//    1.思想    本来应该实现DataSource接口，但是这里只使用getConnection()，为了省去重写方法的麻烦，不实现DataSource
//        1.创建一个容器，存放Connection
package part1;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCCPool{
    //创建容器，为方便增删，使用LinkedList，限制权限为private static
    private static LinkedList<Connection> pool = new LinkedList<>();

    static {
        try {
            //注册驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //获取连接
            String url = "jdbc:oracle:thin:@192.168.0.19:1521:orcl";
            for (int i = 0; i<3; i++) {
                Connection connection = DriverManager.getConnection(url, "bdcdj_huaian", "gtis");
                //添加Connection到连接池
                pool.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接的方法
    public static Connection getConnection() {
        Connection conn = null;
        if (!pool.isEmpty()) {
            conn = pool.removeFirst();
            return conn;
        } else {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getConnection();
        }
    }

    //归还连接的方法
    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            pool.add(conn);
        }
    }
}

