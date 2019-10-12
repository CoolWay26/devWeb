package test;

import part1.JDBCCPool;

import java.sql.Connection;

public class TestJdbcPool {
    public static void main (String[] args) {
            for (int i = 0; i < 10; i++) {
                new TestThread().start();
            }
    }
}

//用多线程测试
class TestThread extends Thread {
    public void run() {
        //获取
        Connection conn = JDBCCPool.getConnection();
        //使用
        System.out.println(Thread.currentThread() + ":" + conn.toString());
        //释放
        JDBCCPool.releaseConnection(conn);
    }
}
//效果如下：
//Thread[Thread-2,5,main]:oracle.jdbc.driver.T4CConnection@17ad352e
//Thread[Thread-7,5,main]:oracle.jdbc.driver.T4CConnection@dda4f7b
//Thread[Thread-5,5,main]:oracle.jdbc.driver.T4CConnection@17ad352e
//Thread[Thread-6,5,main]:oracle.jdbc.driver.T4CConnection@3edcbc7b
//Thread[Thread-1,5,main]:oracle.jdbc.driver.T4CConnection@dda4f7b
//Thread[Thread-9,5,main]:oracle.jdbc.driver.T4CConnection@17ad352e
//Thread[Thread-4,5,main]:oracle.jdbc.driver.T4CConnection@dda4f7b
//Thread[Thread-0,5,main]:oracle.jdbc.driver.T4CConnection@3edcbc7b
//Thread[Thread-3,5,main]:oracle.jdbc.driver.T4CConnection@17ad352e
//Thread[Thread-8,5,main]:oracle.jdbc.driver.T4CConnection@dda4f7b

