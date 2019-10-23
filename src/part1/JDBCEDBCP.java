//1.JDBC
//     DBCP连接池
//1.配置文件    参考  https://www.cnblogs.com/qlqwjy/p/8018741.html
//    1.名称    *.properties  不支持xml配置
//    2.位置    任意
//    3.内容    不支持中文
//2.jar
//    commons-dbcp-1.4

package part1;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCEDBCP {
    private static DataSource dataSource;
    static {
        //手动配置，不管怎么配置，加载配置都是用BasicDataSource
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
//        dataSource.setUsername("stu");
//        dataSource.setPassword("stu");


        //加载配置文件
        ClassLoader classLoader = JDBCEDBCP.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("resources/dbcp/jdbc.properties");
        //处理配置文件
        Properties properties = new Properties();
        try {
            properties.load(is);
            //创建连接池
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnetion() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //测试上面的DBCP工具类
    @Test
    public void testDbcp() {
        Connection conn = JDBCEDBCP.getConnetion();
        String sql = "select * from bdc_xm";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
