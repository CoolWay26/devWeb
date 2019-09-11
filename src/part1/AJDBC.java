package part1;
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
//                    public ParameterTest(int except, int input1, int input2) {
//                        this.except = except;
//                        this.input1 = input1;
//                        this.input2 = input2;
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
public class AJDBC {
}



