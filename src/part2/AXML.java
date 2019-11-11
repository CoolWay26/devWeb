//1.XML
//Extensible Markup Language简称XML
//
//1.概述
//    1.XML是什么
//        1.可扩展标记语言（EXtensible Markup Language），类似 HTML
//        2.设计的初衷是为了传输数据（现在多用json），如今XML更多的用于配置文件
//        3.没有预定义标签，需要自行定义（根据一定的规则），采用树结构
//    3.XML是不作为的，只是用来结构化、存储、传输数据（载体）
//2.语法
//    1.xml声明
//        <?xml version="1.0" encoding="utf-8"?>
//        <?开始    ?>结束
//        必须从文档的0行0列开始
//        文档声明有3个属性
//            version必须有，一般是1.0，因为高版本可能会产生兼容问题
//            encoding可以没有（默认是utf-8）
//3.元素
//    1.普通元素  标签，元素体，结束标签组成
//        元素体可以是元素或者文本<a><b>元素体</b></a>
//        标签
//            规则
//                没有保留字
//                可以包含字母、数字以及其他的字符，不能包含空格
//                不能以数字或者标点符号开始
//                不能以字母 xml（或者 XML、Xml 等等）开始
//            约定
//                避免. :   -   éòá等
//                具有描述性，但要简短，单词用_隔开
//    2.空元素
//        <a/>没有元素体，自己闭合
//4.属性
//    属性是元素的一部分，只能出现在开始标签中
//    一个元素可以有0-N个属性
//    =""
//    <a version="1.0">属性</a>
//5.转义字符
//    部分字符会被解析成xml文档结构，实际上，只有字符 "<" 和 "&" 确实是非法的，但有些字符习惯上也被转义替换
//    其后跟;区分转义字符
//        <       &lt
//        >       &gt
//        "      &quot
//        '      &apos
//        &       &amp
//6.预定义区  CDATA区
//    避免大量的转义字符难以阅读
//    CDATA区不会被解析
//    <! [ CDATA[] ] >
//        但要注意，CDATA区不可以出现]>，因为这是CDATA区的结束符号
//7.约束  定义 XML 文档的合法构建模块
//    1.DTD约束 文档类型定义Document Type Definiton
//        用来约束XML中元素的名称，顺序，属性
//        通常不会自己编写DTD，使用框架提供的DTD
//        1.了解DTD约束文档的内容
//            1.元素                    <!ELEMENT web-app (servlet*,servlet-mapping* , welcome-file-list?) >
//                <!ELEMENT 元素名 元素描述>
//                元素名：自定义
//                常见元素描述：
//                    ?   0次或1次
//                    *   任意次
//                    +   1次或多次
//                    ()  分组
//                    |   任选
//                    ,   多个元素分割，且引入dtd后一定要按该顺序编写xml
//            2.属性    <!ATTLIST web-app version CDATA #IMPLIED>
//                <!ATTLIST 元素名 属性名 属性类型 属性约束>
//                元素名：已定义的元素
//                属性名：自定义
//                属性类型：ID,CDATA,枚举...
//                    ID：ID类型的属性用来标志元素的唯一性
//                    CDATA：文本类型
//                    枚举：(A|B|C)多选一
//                约束：
//                    #IMPLIED：属性是可选的     implied
//                    #REQUIRED：属性是必须的    required
//        2.引入DTD约束文档的方式
//            1.内部声明
//                在xml文档声明之后，跟上DTD文档约束的内容（相当于在XML中写DTD约束）
//                <!DOCTYPE 根元素 [元素声明]>
//            2.外部声明
//                1.本地
//                    <!DOCTYPE 根元素 SYSTEM "DTD文件的绝对路径">
//                2.网络
//                    <!DOCTYPE 根元素 PUBLIC "文件名">
//    2.Schema约束      参考  https://www.runoob.com/schema/schema-why.html
//        Schema也是xml约束，但比DTD强大的多，是DTD的替代者
//        Schema约束文件本身是xml文件，但拓展名是xsd，根节点是Schema
//            这意味着可以以处理xml的方式处理Schema文件（编辑，解析-XML解析器，处理-DOM，转换-XSLT）
//        Schema支持名称空间，支持数据类型
//            名称空间
//                每个Schema约束模式文档都被赋以一个唯一的名称空间
//                名称空间用一个唯一的URI（Uniform Resource Identifier）表示
//                在XML实例文件中书写标签时，通过名称空间声明（xmlns）来声明当前编写的标签来自哪个Schema约束文档，在根标签处声明
//                而在xsd文件中，显然要在根节点<Schema>处声明，这里还可以给引入的名称空间起别名，如果不起别名在使用约束文件的标签时也不需要使用别名
//
//            支持数据类型，意味着在数据约束上更为方便
//                更容易定义数据约束（data facets）
//                可更容易地定义数据模型（或称数据格式）
//                更容易检查文档内容是否符合数据约束
//                可更容易地在不同的数据类型间转换数据
//                    综上，更容易保护数据通信
//                        因为通过明确的数据约束，可以确保接受的数据符合预期
//        1.了解Schema文档的内容
//            <xsd:schema xmlns="http://www.w3.org/2001/XMLSchema"
//                    targetNamespace="http://www.example.org/web-app_2_5"
//                    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
//                    xmlns:tns="http://www.example.org/web-app_2_5"
//                    elementFormDefault="qualified">
//            <xsd:element name="web-app">
//                <xsd:complexType>
//                    <xsd:choice minOccurs="0" maxOccurs="unbounded">
//                        <xsd:element name="servlet">
//                            <xsd:complexType>
//                                <xsd:sequence>
//                                    <xsd:element name="servlet-name"></xsd:element>
//            解释上述内容：
//                xmlns=""    声明名称空间，对于Schema文件，是固定的，跟的是一个w3c提供的严格限制xml的约束文件，可以取别名，如xmlns:xsd=""
//                            取了别名在使用该命名空间中的标签时，就要加上别名声明，别名可以区分不同命名空间的重名元素（防止冲突）
//                targetNamespace     外部的xml使用该约束文件是通过该命名空间，习惯上写一个url
//                elementFormDefault  取值为qualified 或者 unqualified     参考https://blog.csdn.net/xiaozhao_19/article/details/2172801   https://blog.csdn.net/lmj623565791/article/details/12655781
//                                    全局元素必须加上所属名称空间的前缀（别名），如果是没有别名则不用加
//                                    非全局的元素当设置为qualified时，必须添加命名空间的前缀；设置为unqualified时，不必也不能添加前缀
//                                    本质上是规定元素属于哪个名称空间
//                element标签       声明一个元素，用name属性进行命名
//                complexType标签   定义为复杂类型元素，复杂类型的元素是指包含其他元素和/或属性的 XML 元素  参考  https://www.w3school.com.cn/schema/schema_complex.asp
//                                  对于复杂类型元素的定义，还有另一种方式
//                                    //使用 element的type属性，引用复合类型定义的名称
//                                    <xs:element name="employee" type="personinfo"/>
//                                    //定义complexType并命名
//                                    <xs:complexType name="personinfo">
//                                        <xs:sequence>
//                                            //这是个简易元素，不包含其他元素或者属性标签，type指定了数据类型
////                                                最常用的类型是：
////                                                xs:string xs:integer  xs:time s:date  xs:decimal  xs:boolean
//                                            <xs:element name="firstname" type="xs:string"/>
//                                            <xs:element name="lastname" type="xs:string"/>
//                                        </xs:sequence>
//                                    </xs:complexType>
//                指示器
//                    all标签   规定子元素可以按照任意顺序出现，且每个子元素必须只出现一次
//                    choice标签        规定出现被包含的某个子元素
//                    sequence标签      表示包含的元素以指定的顺序出现在包含元素中。每个子元素可出现 0 次到任意次数
//                minOccurs   规定某个元素能够出现的最小次数
//                maxOccurs   规定某个元素能够出现的最大次数
//                属性
//                    <xs:attribute name="xxx" type="yyy" default="def" use="required"/>
//                    定义了属性xxx，规定内容要为yyy类型
//                    属性还可以声明default,fixed,use属性，表示默认值，固定值，是否必须（required）
//                    要注意，简易类型元素没有属性
//        2.Schema实例  参考https://blog.csdn.net/yangyuge1987/article/details/59536964
//            1.xmlns 为当前xml的标签所属的名称空间指定一个名称（默认或者别名），并非是这一步引入了xsd文档
//            2.xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"     表示准备引入符合W3C标准的xsd实例，固定写法
//              xsi:schemaLocation="http://www.example.org/web-app_2_5
//              http://www.example.org/web-app_2_5.xsd"                   绑定目标名称空间和xsd文档，名称和文档成对出现
//        3.xml解析
//            1.解析方式
//                DOM SAX PULL
//                解析器及开发包（JAXP JDOM jsoup dom4j）
//        2.dom4j解析
//            1.DOM解析的原理
//                将xml文档加载到内存生成一个DOM树，获得一个Document对象，通过该对象操作xml
//            2.API使用 导入jar包
//                1.SaxReader
//                    read(..)加载xml文档
//                2.Document
//                    getRootElement()获得根元素
//                3.Element  通常用rootElement调用如下方法
//                    elements(..)    获取某名称的所有子元素，可以不指定
//                    element(..)     获取某名称的第一个子元素
//                    getName()       获取当前元素的元素名
//                    attributeValue(..)获取某属性的属性值
//                    elementText(..) 获取某子元素的文本值
//                    getText()       获取当前元素的文本内容
package part2;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class AXML {

    @Test
    public void testSchema() throws DocumentException {
        //加载xml
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("src/part2/xmlTest.xml"));

        //获取根元素
        Element rootElement = document.getRootElement();
        //获取属性值
        String version = rootElement.attributeValue("version");
        System.out.println(version);
        //获取所有子元素
        List<Element> elementList = rootElement.elements();
        if (elementList.size() > 0) {
            for (Element element : elementList) {
                System.out.println(element.getName());
                System.out.println(element.getText());
            }
        }
    }
}
