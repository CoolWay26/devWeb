//1.JDBC
//  连接池
//1.概念
//    1.什么是连接池     管理Connection的工具
//    2.为什么用连接池   因为获取连接，释放资源非常消耗系统资源，使用连接池来共享Connection
//    3.基本原理  从池中获取Connection，使用完后调用close()不是销毁Connection，而是归还到池中
//                java提供了javax.sql.DataSource公共接口，厂商自行实现接口让程序能使用不同厂商的连接池
//    4.常见连接池 dbcp,C3P0
//2.手写简单的自定义连接池
//    1.思想    本来应该实现DataSource接口，但是这里只使用getConnection()，为了省去重写方法的麻烦，不实现DataSource
//        1.创建一个容器，存放Connection
//        2.getConnection()和release()
//3.增强方法
//    1.方法增强常见的几种方式
//        1.继承
//            继承父类方法，重写需要增强的方法
//        2.装饰者设计模式
//            实现接口，重写接口所有方法
//        3.动态代理
//            接口+反射
//        4.字节码增强
//    2.装饰者设计模式
//        创建类B实现接口A，将A的某个实现类C对象传给B构造方法，在B中创建一个成员变量接收C，B中实现A所有方法，
//        不需要增强的在方法中用C.方法名调用一次即可（见下方例子）

package part1;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class JDBCCPool{
    //创建容器，为方便增删，使用LinkedList，限制权限为private static
    private static LinkedList<Connection> pool = new LinkedList<>();

    static {
        try {
            //注册驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //获取连接
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            for (int i = 0; i<3; i++) {
                Connection connection = DriverManager.getConnection(url, "stu", "stu");
//                //生产连接，添加Connection到连接池
//                pool.add(connection);
                //装饰者增强原本的Connection
                MyConnection myConn = new MyConnection(connection, pool);
                pool.add(myConn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接的方法
    public static Connection getConnection() {
        Connection conn = null;
        //池子中有连接就取出，没有就等待若干时间重复判断有无可用连接
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

    //归还连接的方法，这里连接用完以后不需要关闭连接，放入连接池中即可
    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            pool.add(conn);
        }
    }
}

class MyConnection implements Connection {
    private Connection conn = null;
    private List<Connection> pool = null;
    MyConnection(Connection connection, List<Connection> pool) {
        //将要增强的对象传入装饰者的构造方法
        this.conn = connection;
        this.pool = pool;
    }

    //需要增强的方法
    @Override
    public void close() throws SQLException {
        //这里一定add的this，因为连接池中要用的是装饰后的connection对象,this指的就是当前的MyConnetion对象
        this.pool.add(this);
    }
    //不需增强的方法，这也是装饰者的缺点，明明不需要增强，却还要重写
    @Override
    public Statement createStatement() throws SQLException {
        //不需要增强就直接调用传入对象的原方法
        return this.conn.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.conn.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return this.conn.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}

