package part2.impl;

import part2.BmyServlet;

public class BmyServletImpl implements BmyServlet {

    @Override
    public void init() {
        System.out.println("初始化");
    }

    @Override
    public void service() {
        System.out.println("执行");
    }

    @Override
    public void destory() {
        System.out.println("销毁");
    }
}
