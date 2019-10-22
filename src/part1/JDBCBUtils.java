//1.JDBC
//  抽取工具类
//1.直接抽取全部代码形成工具类     死板
//    1.关闭资源的时候，如果出现异常，不能抛出，要使用try catch处理掉，否则会影响后续资源的关闭
//        可以用多个try catch 或者使用try catch嵌套（代码看着不清晰）
//2.在工具类中使用配置文件
//    1.配置文件
//        1.通常，驱动，url，用户名，密码都存在配置文件中，方便维护，要改数据源只需要改配置文件
//        2.扩展名为  .properties
//        3.文件内容  一行一条数据，格式是  key = value
//            key:多个单词用.隔开
//            通常不是用中文，否则需要转码
//    2.如何加载配置文件
//        1.使用ResourceBundle加载properties
//            ResourceBundle bundle = ResourceBundle.getBundle("resources.config.jdbc");    //路径从src开始写，不带后缀，分隔符为.或者/
//            driver = bundle.getString("jdbc.driver");
//            通常配置只需要加载一次，因为重复加载也是一样的内容
//            定义成static，跟随类在static块中加载
//        2.使用ClassLoader + Properties对象加载配置文件        参考  https://blog.csdn.net/Coding__man/article/details/81118275
//            ClassLoader classLoader = JDBCBUtils.class.getClassLoader();
//            InputStream is = classLoader.getResourceAsStream("resources/jdbc.properties");    //从src下开始写路径，带后缀，分隔符为/
//            Properties properties = new Properties();
//            properties.load(is);
package part1;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class JDBCBUtils {
    //查询功能工具类--方式一
    public static String queryDemo(){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String res = "";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");   //ClassNotFoundException   驱动的jar包有问题
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            con = DriverManager.getConnection(url, "stu", "stu");
            String sql = "select * from test_user where tel != ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"sada' and 'a' != 'a");
            rs = pstmt.executeQuery();
            rs.next();
            res = rs.getString(1);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(rs, stmt, con);
        }
        return res;
    }

    public static void release(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    //配置文件只需要被加载一次
    static {
        //工具类--方法二  ResourceBundle
//        ResourceBundle bundle = ResourceBundle.getBundle("resources/config/jdbc");
//        driver = bundle.getString("driver");
//        url = bundle.getString("url");
//        username = bundle.getString("username");
//        password = bundle.getString("password");

        //工具类--方法三
        try {
            //通过当前类获取类加载器
            ClassLoader classLoader = JDBCBUtils.class.getClassLoader();
            //通过类加载器获得一个输入流，写绝对路径（带后缀）
            InputStream is = classLoader.getResourceAsStream("resources/config/jdbc.properties");
            //创建一个Properties对象
            Properties properties = new Properties();
            //加载输入流到Properties对象
            properties.load(is);
            //通过properties对象读取配置文件内容
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int updateDemo() {
        int resNum = 0;
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            String sql = "update test_user set tel = '110' where name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "王王");
            resNum = pstmt.executeUpdate();
            System.out.println(resNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resNum;
    }

    @Test
    public void test() {
//        queryDemo();
        updateDemo();
    }
}
