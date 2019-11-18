//2.反射
//
//1.反射概述  参考  https://www.cnblogs.com/ysocean/p/6516248.html
//    反射机制是指在运行过程中，仍然能获取和调用任意一个类的属性和方法
//        应用程序一旦运行，就无法再进行new对象的创建，这时再想调用某个类的属性或方法就需要用到反射机制
//2.相关对象
//    1.Class 字节码对象
//        1.如何获取
//            1.obj.getClass()   即Oject的getClass()
//                需要用到该类的对象，不用知道具体是什么类
//            2.类名.class      每个类都具有class静态属性
//                需要用到该类
//            3.Class.forName(..) 要使用全类名，这种方法用的最多
//                名字可以作为参数比较方便，但可能抛出ClassNotFoundException异常
//        注意：一个类在 JVM 中只会有一个 Class 实例，对于一个类而言，获取多个Class进行比较会发现，其实获取的是一个字节码对象
//        2.通过 Class 类获取成员变量、成员方法、接口、超类、构造方法等
//            1.创建对象
//                Object object = clazz.newInstance();//调用空参构造
//                Constructor c = clazz.getConstructor(String.class,int.class);	//获取有参构造
//                Person p = (Person) c.newInstance("张三",23);
//            2.获取全类名
//                String className = c2.getName();
//            3.获得类的public类型的属性
//                Field[] fields = c2.getFields();
//            4.获得类的所有属性，包括私有的
//                Field [] allFields = c2.getDeclaredFields();
//            5.获得指定的属性
//                Field f1 = c2.getField("age");
//            6.获得指定的私有属性
//                Field f2 = c2.getDeclaredField("name");
//            7.获得类的public类型的方法，这里包括 Object 类的一些方法
//                Method [] methods = c2.getMethods();
//            8.获得类的所有方法
//                Method [] allMethods = c2.getDeclaredMethods();
//            9.获取构造方法
//                Constructor [] constructors = c2.getConstructors();
//            10.为object对象的field字段设置值
//                field.set(object, "value");
//    2.Constructor对象
//        1.获取公共构造方法
//            Constructor [] constructors = c2.getConstructors();//可跟参数.class获取有参构造
//        2.获取所有的构造方法
//            Constructor [] constructors = c2.getDeclaredConstructors(String.class, int.class);//可跟参数.class获取有参构造
//        3.创建对象
//            newInstance(Object objs)//可跟参数（实际值，不是class值）
//    3.Method对象
//        Method method = c2.getMethod()//可跟参数class
//        Method methdo = c2.getDeclaredMethod()//可跟参数class
//        method.invoke(Obj obj);//指定对象执行方法
//    4.Field对象
//        3.获得类的public类型的属性
//            Field[] fields = c2.getFields();
//        4.获得类的所有属性，包括私有的
//            Field [] allFields = c2.getDeclaredFields();
//        5.获得指定的属性
//            Field f1 = c2.getField("age");
//        6.获得指定的私有属性
//            Field f2 = c2.getDeclaredField("name");
//3.使用反射模拟servlet执行
//    1.可以直接通过硬编码实现
//    2.解析xml实现
//    3.如果针对不同的值做不同的处理，那么可以定义一个Map成员变量，在每次初始化的时候就去先把所有情况从XML中解析出来存入map中即可满足后续操作需要

package part2;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import part2.BmyServlet;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Breflect {
    //硬编码
//    @Test
//    public void reflectTest() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        //获取Class，这里要用全类名，全类名指的是排除src，从下一级的包开始写
//        Class aClass = Class.forName("part2.impl.BmyServletImpl");
//        //创建对象
//        BmyServlet bmyServlet = (BmyServlet) aClass.newInstance();
//        //调用方法
//        bmyServlet.init();
//        bmyServlet.service();
//        bmyServlet.destory();
//    }

    @Test
    public void reflectXmlTest() throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //解析XML
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("src/part2/schemaTest.xml"));
        //获取根元素
        Element rootElement = document.getRootElement();
        //通过根元素获取其他元素
        Element servletElemet = rootElement.element("servlet");
        //获取最底层元素的值
        String className = servletElemet.elementText("servlet-class");

        //反射
        Class bClass = Class.forName(className);
        BmyServlet bmyServlet = (BmyServlet) bClass.newInstance();
        //获取某个方法，并用invoke()调用
        bClass.getMethod("init").invoke(bmyServlet);
        //或者用对象直接调用
        bmyServlet.init();
        bmyServlet.service();
        bmyServlet.destory();
    }

}
