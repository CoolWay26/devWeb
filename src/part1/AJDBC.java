package part1;

import org.junit.Test;

import java.sql.*;

//1.1JDBC
//
//1.概述
//    JDBC（Java DataBase Connectivity）即Java数据库连接，也就是使用java语言操作数据库（使用java向数据库发送SQL语句）
//2.JDBC原理
//    1.JDBC是一种用于执行SQL语句的Java API（接口），是SUN公司推出的Java访问数据库的标准规范，由一组Java工具类和接口组成
//    2.开发者原本的初衷是编写可以连接各种数据库的API，因为各种数据库服务器差异太大，无法实现完全的契合所有，于是SUN公司和数据库厂商达成一致
//        由SUN提供一套访问数据库的规范和协议（JDBC--接口），不同的厂商遵循规范和协议，提供访问自己的一套API（驱动--接口的实现）
//        程序--JDBC--驱动--数据库（这也是为什么一定要导入驱动）
//3.Junit单元测试     方便的进行方法的测试，不再需要写main入口
//        参考  https://zhuanlan.zhihu.com/p/42453559   https://www.cnblogs.com/qiyexue/p/6822791.html
//        maven依赖 https://mvnrepository.com/artifact/junit/junit/4.12
//        参考  http://blog.sina.com.cn/s/blog_1650b23bc0102wuk5.html   测试无返回值方法
//    1.步骤
//        1.导入依赖，非maven直接导入jar包也可以
//        2.写好等待测试的类和方法
//        3.创建测试类和测试方法（点一下目标类名，再点附件的小光标，选择"Create Test"，即可为该类创建测试方法！可以设置测试类名，包，需要测试的方法）
//        4.在测试类的测试方法中写断言函数assertArrayEquals();
//            第一个参数表示预期的结果
//            第二个参数表示程序的执行结果（new一个匿名对象执行目标函数）
//            当预期结果与执行结果是一致的时候，则表示单元测试成功
//        5.运行测试方法
//            可以右击需要运行的方法选择单独的run，如果有问题就会显示报错信息，没问题则方法正常结束
//    2.测试类测试方法的注解
//        @Test   表示待测试的方法，必须以public void修饰，并且不包含参数
//                @Test(excepted=xx.class): xx.class表示异常类，表示测试的方法抛出此异常时，认为是正常的测试通过的
//                @Test(timeout=毫秒数) :测试方法执行时间是否符合预期
//        @BeforeClass    这个注解表示这个方法会在所有测试方法执行之前执行，static void修饰，不包含参数，通常用于加载配置等
//        @AfterClass     这个注解表示这个方法会在所有方法执行完毕之后执行，通常用来释放资源，static void
//        @Before         这个注解表示这个方法会在每个测试方法执行之前执行一次，有多少个@Test就执行多少次
//        @After          这个注解表示这个方法会在每个测试方法执行之后执行一次，有多少个@Test就执行多少次
//        @Ignore         所修饰的测试方法会被测试运行器忽略
//        @RunWith        可以更改测试运行器org.junit.runner.Runner
//        @Parameters     参数化注解
//    3.测试套件类
//        如果要同时测试多个类，可以新增一个测试套件，将多个所有测试类包含进去，每次执行测试套件类的时候，就会把包含的测试类全都执行一遍
//                测试套件是用来组织多个测试类一起运行的，使用 @RunWith注解
//                更改测试运行器为Suite.class，将要测试的类作为数组传入到Suite.SuiteClasses({})中
//                测试套件类不能包含其他测试方法
//                        @RunWith(Suite.class)
//                        @Suite.SuiteClasses({ TaskTest1.class, TaskTest2.class, TaskTest3.class })
//                        public class SuiteTest {}
//                测试套件也可以包含其他的测试套件，具体用法和包含多个测试类是一样的
//                        @RunWith(Suite.class)
//                        @Suite.SuiteClasses({SuiteTest.class,TaskTest4.class})
//                        public class SuiteTest1 {}
//    4.参数化测试
//        对于一个方法需要进行多种场景进行测试时，可以通过参数化测试减少测试的工作量
//                更改测试运行器为RunWith(Parameterized.class)
//                声明变量用来存放预期值与结果值
//                声明一个返回值为Collection的公共静态方法，并使用@Parameters进行修饰
//                为测试类声明一个带有参数的公共构造方法，并在其中为声明变量赋值
//                断言函数传入第二步定义好的预期值和结果值变量
//                @RunWith(Parameterized.class)
//                public class ParameterTest {
//                        int except;  //用来存储预期结果
//                        int input1;  //用来存储第一个输入参数
//                        int input2;  //用来存储第二个输入参数
//                    @Parameters
//                    public static Collection<Object[]> initTestData() {
//                        return Arrays.asList(
//                            new Object[][]{
//                                {3, 1, 2},
//                                {10, 5, 5},
//                                {6, 4, 2},
//                                {7, 3, 4}}
//                        );
//                    }
                    //对于参数是数组的，可以采用如下定义的方式
//                    @Parameterized.Parameters
//                    public static Collection initData() {
//                        int[] input1 = {3,2,1,4};
//                        int[] input2 = {2,1,4,3};
//                        int[] input3 = {4,3,2,1};
//
//                        int[] output = {1,2,3,4};
//
//                        return Arrays.asList(new Object[][] {
//                        {output,input1},
//                        {output,input2},
//                        {output,input3},
//                        });
//                    }
//                    public ParameterTest(int except, int input1) {
//                        this.except = except;
//                        this.input1 = input1;
//                    }
//                    @Test
//                    public void testAdd() {
//                        assertEquals(except, new Claculate().add(input1, input2));
//                    }
//                }
//    5.注意事项
//        1.一般使用单元测试会新建一个test目录存放测试代码，在生产部署的时候只需要将test目录下代码删除即可
//        2.测试代码的包应该和被测试代码包结构保持一致
//        3.测试单元中的每个方法必须可以独立测试，方法间不能有任何依赖
//        4.测试类一般使用Test作为类名的后缀，测试方法使一般用test作为方法名的前缀
//4.JDBC开发步骤  sql包
//    1.注册驱动  让JDBC知道要使用的是哪个驱动
//        Class.forName(“com.mysql.jdbc.Driver”)      Class.forName()是进行类的加载
//        Class.forName("oracle.jdbc.driver.OracleDriver")    各个厂商驱动的写法是不一样的
//        为什么加载类就是注册驱动？
//        其实是因为驱动类的static块
//            实际上，注册要使用DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//            但这样属于硬编码，这样写只能满足一种，要满足多种就要进行多次注册
//            实际上，JDBC中规定，驱动类在被加载时，需要自己“主动”把自己注册到DriverManger中
//            com.mysql.jdbc.Driver类中的static块会创建本类对象，并注册到DriverManager中
//            这说明只要去加载com.mysql.jdbc.Driver类，那么就会执行这个static块
//            从而也就会把com.mysql.jdbc.Driver注册到DriverManager中
//    2.获取连接  Connection对象表示连接，与数据库的通讯都是通过这个对象展开的
//        DriverManager.getConnection(url,username,password)
//        url：jdbc:mysql://localhost:3306/mydb
//            oracle有三种写法     参考  https://blog.csdn.net/xlxxcc/article/details/51148188
//                1.普通SID方式
//                    jdbc:oracle:thin:username/password@x.x.x.1:1521:SID
//                2.普通ServerName方式
//                    jdbc:oracle:thin:username/password@//x.x.x.1:1522/ABCD
//                3.RAC方式
//                    jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=x.x.x.1)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=x.x.x.2)(PORT=1521)))(LOAD_BALANCE=yes)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=xxrac)))
//            分为三部分   第一部分jdbc是固定的；第二部分是数据库名（mysql,oracle）；第三部分是数据库厂商规定的（IP+端口+database名）
//            此外，还可以跟两个参数jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF8
//            useUnicode：指定这个连接数据库的过程中，使用的字节集是Unicode字节集
//            characterEncoding：声明传输数据使用的字节集编码为UTF-8编码（mysql要写成UTF8而不是UTF-8）
//                取数据：按数据库编码解码成字节码，按UTF-8编码传给程序
//                存数据：按UTF-8解码成字节码，按数据库编码编码存入数据库中
//            还有另一个不常用参数allowMultiQueries=true
//                可以使String sql = "select 'hello';select 'world'"这样的sql不报错
//    3.获取Statement   Statement是用来向数据库发送SQL语句的，这样数据库就会执行发送过来的SQL语句，Connection最为重要的一个方法就是用来获取Statement对象
//        Connection con = DriverManager.getConnection(“jdbc:mysql://localhost:3306/web08”,”root”,”root”);
//        Statement stmt = con.createStatement();
//    4.发送sql语句
//        stmt.executeQuery() 执行查询操作，返回的查询结果就是ResultSet
//        stmt.executeUpdate()执行更新操作（insert、update、delete等）
//    5.读取ResultSet
//        String sql = “select * from user”;
//        ResultSet rs = stmt.executeQuery(sql);
//        ResultSet对象表示查询结果集，只有在执行查询操作后才会有结果集的产生，结果集是一个二维的表格，有行有列
//        通过移动ResultSet内部的“行光标”，以及获取当前行上的每一列上的数据        行列都是从1开始计数
//            光标初始位置是第一行的上方
//            next()使行光标向下移动一行（到了第一行）
//            getXXX(int col)获取当前行某一列的数据，XXX指的是数据类型，如果无法确定数据类型，最好使用getObject(int col)
//    6.关闭
//        与IO流一样，使用后的东西都需要关闭！关闭的顺序是先得到的后关闭，后得到的先关闭
//        无论是否出现异常，都要执行关闭操作（规范化代码）
//            rs.close();
//            stmt.close();
//            con.close();
//    7.注册驱动的前提是导入驱动的包到项目中
//        参考  https://blog.csdn.net/zwj1030711290/article/details/56678353/
//            IDEA下通过Modules的Dependencies添加：(推荐)
//                1.打开 File -> Project Structure （Ctrl + Shift + Alt + S）
//                2.单击 Modules -> Dependencies -> "+" -> "Jars or directories"
//                3.选择硬盘上的jar包
//                4.Apply -> OK
public class AJDBC {

    public void jdbcDemo () {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.0.19:1521:orcl";
            con = DriverManager.getConnection(url, "bdcdj_huaian", "gtis");
            stmt = con.createStatement();
            String sql = "select * from bdc_xm";
            rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
    }

    @Test
    public void jdbcDemoTest () {
        jdbcDemo();
    }
}



