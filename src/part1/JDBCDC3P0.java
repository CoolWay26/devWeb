//1.JDBC
//  C3P0连接池 重要的开源免费连接池
//1.概述
//    1.jar包
//        c3p0-版本.jar
//2.配置文件  可以用xml或properties配置     参考https://blog.csdn.net/caychen/article/details/79625411  https://baike.baidu.com/item/c3p0/3719378?fr=aladdin
//    1.文件名固定 c3p0-config.xml
//    2.假如不放在根目录下，那么在使用前要指定配置文件的目录
//        System.setProperty("com.mchange.v2.c3p0.cfg.xml",System.getProperty("user.dir")+"/src/resources/c3p0/c3p0-config.xml");
//3.使用c3p0连接池
//    1.c3p0核心工具类 ComboPooledDataSource
//        getConnection() 获取连接

package part1;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDC3P0 {

    @Test
    public void testCtpz() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //指定配置文件的路径（如果放在src下不需要这一步），要给绝对路径
        System.setProperty("com.mchange.v2.c3p0.cfg.xml",System.getProperty("user.dir")+"/src/resources/c3p0/c3p0-config.xml");
        //创建连接池对象
        ComboPooledDataSource dataSource = new ComboPooledDataSource("oracleTest");
        try {
            conn = dataSource.getConnection();
            String sql = "select * from bdc_xm";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCBUtils.release(rs, pstmt, conn);
        }
    }



}
