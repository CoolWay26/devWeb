package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Utils {
    private static DataSource dataSource;
    static {
        //指定配置文件的路径（如果放在src下不需要这一步），要给绝对路径
        System.setProperty("com.mchange.v2.c3p0.cfg.xml",System.getProperty("user.dir")+"/src/resources/c3p0/c3p0-config.xml");
        //创建连接池对象
        dataSource = new ComboPooledDataSource("oracleTest");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
