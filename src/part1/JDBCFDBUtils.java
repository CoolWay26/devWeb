//1.JDBC
//DBUtils
//1.概述
//    1.apache commons组件成员
//    2.简化JDBC操作过程
//2.JavaBean  实体类
//    1.封装数据的类就是JavaBean
//    2.特性
//        1.实现接口java.io.Serializable
//        2.空参构造
//        3.私有字段 private
//        4.提供getter/setter方法
//3.DBUtils完成CRUD
//    1.核心
//        1.QueryRunner   操作sql
//            QuertRunner(DataSource ds)  构造方法读取数据源，提供连接池
//            update(String sql, Object params)
//            query(String sql, ResultSetHandler rsh, Object params)
//        2.ResultSetHandler  处理结果集，是个接口，要使用其实现类对象，如下  参考https://blog.csdn.net/damogu_arthur/article/details/40585999
//            BeanHandler 将结果第一条记录封装到一个指定的JaveBean中
//            BeanListHandler 结果所有记录封装到JaveBean中，再封装成List<Bean>
//            MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值
//            MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List
//            ScalarHandler:将结果集第一行的某一列放到某个对象中，单行单列处理器，把结果集转换成Object，例如select count(*) from tab_student
//                对聚合函数的查询结果，有的驱动返回的是Long，有的返回的是BigInteger，可以把它转换成Number，Number是Long和BigInteger的父类，然后再调用Number的intValue()或longValue()方法就OK了，int cnt = number.intValue();
//
//            ArrayHandler：把结果集中的第一行数据转成对象数组
//            ArrayListHandler：把结果集中的每一行数据都转成一个对象数组，再存放到List中
//            ColumnListHandler：将结果集中某一列的数据存放到List中，多行单列处理器，把结果集转换成List<Object>，使用ColumnListHandler时需要指定某一列的名称或编号，例如：new ColumListHandler(“name”)表示把name列的数据放到List中
//            KeyedHandler(name)：将结果集中的每一行数据都封装到一个Map里(List<Map>)，再把这些map再存到一个map里，其key为指定的列
//        3.DbUtils   处理资源和事务
//            closeQuietly(Connection conn)
//            commitAndCloseQuietly(Connection conn)
//            rollbackAndCloseQuietly(Connection conn)
package part1;

import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;
import utils.C3P0Utils;

import java.sql.SQLException;

public class JDBCFDBUtils {
    @Test
    public void testAddUser() {
        //获取核心类QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        //编写sql
        String sql = "insert into test_user(name,age,tel) values(?,?,?)";
        //组织参数
        Object[] params = {"王王", 25, "18888888888"};
        //执行sql
        try {
            //update操作
            int rows = queryRunner.update(sql, params);
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryUser() {
        //获取核心类QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        //编写sql
        String sql = "select * from test_user where instr(tel, ?) > 0";
        //组织参数
        Object[] params = {"18888888888"};
        //执行sql
        try {
            //query操作
            User user = queryRunner.query(sql, new BeanHandler<User>(User.class), params);
            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
