//Http
//
//1.概念
//    1.http协议指的是超文本传输协议，一种网络协议，用于定义web浏览器和服务器之间交换数据的过程，基于TCP/IP
//    2.工作原理
//        浏览器（客户端）通过url向web服务器（服务端）发送请求
//        服务器接收请求后，做出响应，向客户端发送响应数据
//    3.特点
//        http默认端口为80，可以修改；请求和响应成对存在
//        无连接：限制每次连接只处理一个请求，接收到响应后断开连接
//        无状态：http协议对于事务处理没有记忆能力，如果想处理之前的数据，就要重新传输
//        客户端和服务端交互是用过通用网关接口（Common Gateway Interface／CGI）实现的，换句话说，CGI使得客户端和服务端有了交互功能
//2.消息结构
//    1.客户端请求消息
//        请求行
//            请求方式    资源路径    协议/版本
//                请求方式：共有8种，常用：get, post
//                    get：    参数通过url传输（这意味着没有请求体），不安全，且存在url超长的风险
//                                href和img标签都是通过get请求方式获取数据
//                    post：   参数通过请求体传输，较安全
//                资源路径：   指向目标资源的url
//                协议/版本：  HTTP/1.0请求一次可以获取1个资源        1.1版本请求一次可以获取多个资源
//        请求头
//            以键值对key:value的形式编写请求头，常见如：
//            Referer:当前请求来自何处
//            Cookie: 会话相关，存放浏览器缓存的cookie信息
//            Accept: 浏览器支持的文件类型（MIME）  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8   优先顺序从左到右，html,xhtml,xml
//                大类型/小类型[;参数]，如      text-标准文本    application-应用程序数据或二进制数据
//                text/html       html文件
//                text/css        css文件
//                text/javascript js文件
//                image/*         所有图片文件
//            Accept-Encoding:浏览器支持的压缩格式  gzip, deflate, br
//            Accept-Language:浏览器支持的语言  zh-CN,zh;q=0.9      zh-CN中文 zh简体中文  q是权重系数[0,1]，q值越大，请求越倾向于获得其“;”之前的类型表示的内容，
//                            若没有指定q值，则默认为1，若被赋值为0，则用于提醒服务器哪些是浏览器不接受的内容类型
//            Accept-Charset: 浏览器支持的字符编码  GB2312,utf-8;q=0.7,*;q=0.7  权重都是0.7更倾向明确的指定而不是*
//            Content-Length:请求体的字节长度，如果设置则必须和请求体长度保持一致，过短会截断，过长会超时
//            Host:域名
//            Connection:客户端与服务连接类型  Keep-Alive持久连接（HTTP 1.1默认进行持久连接），close不需要持久连接
//            User-Agent:用户代理，使得服务器能够识别客户端使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、
//                       浏览器语言、浏览器插件等  Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)
//                       Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3732.400     Mozilla 是浏览器名，版本是 5.0；
//        空行
//            请求头和请求体之间有一个空行
//        请求体
//            key=value&...
//            键值对，多个用&连接
//            post请求才有请求体
//    2.服务端响应数据
//        响应行
//            协议/版本 状态码 状态码描述     HTTP/1.1 200 OK
//            状态码
//                100-199 请求成功，但需要客户端进行下一次请求才能完成整个处理过程
//                200-299 请求成功，并已完成整个请求过程
//                300-399 未完成请求，客户端需细化请求
//                400-499 客户端的请求有错误
//                500-599 服务器出现内部错误
//            常见状态码
//                200 成功
//                302 请求重定向
//                303 重定向,把你重定向到其他页面
//                304 请求资源没有改变，访问本地缓存
//                400 Bad Request  客户端请求有语法错误，不能被服务器所理解
//                401 Unauthorized 请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用
//                403 Forbidden  服务器收到请求，但是拒绝提供服务
//                404 请求资源不存在（通常是资源路径编写错误，或是服务器资源发生了移动）
//                500 服务器内部错误
//                503 Server Unavailable  服务器当前不能处理客户端的请求，一段时间后可能恢复正常
//        响应头
//            Content-Disposition 通知客户端弹出一个文件下载框，并且可以指定下载文件名，使得浏览器以下载的方式解析响应体
//                                Content-Disposition: attachment; filename="fname.ext"
//            Content-Encoding 设置数据使用的压缩类型    gzip
//            Content-Length 响应体的字节长度
//            Content-Type 设置响应体的MIME类型   text/html; charset=utf-8
//            Location 在重定向中或者创建新资源时使用（此时状态码为302）
//            Refresh 定时刷新，可以重定向（通过url控制，url默认是本页，可以重定向为其他资源路径）
//                    秒数;url=...
//                    在页面的头部有个扩展可以实现相似的功能，并且大部分浏览器都支持，即
//                    <meta http-equiv="refresh" content="5; url=http://example.com/">，相当于设置了响应头
//                    Refresh:5;url=http://www.w3.org/pub/WWW/People.html
//            Server  服务器名称   Server: Apache/2.4.1 (Unix)
//            Set-Cookie 设置HTTP Cookie，会话相关，向浏览器中写入cookie    Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1
//        响应体
//            服务器返回给客户端的正文
//2.web开发
//    1.web通信
//        web通信采用B/S模式
//        浏览器（request）--HTTP协议--服务器（response）
//    2.C/S和B/S
//        C/S 需要安装客户端程序
//            优点：用户体验好，服务器负荷轻（部分计算可以在客户端完成），对信息安全的控制更好
//            缺点：安装程序受环境制约，占用硬盘，维护麻烦
//        B/S 通过浏览器与服务器交互
//            优点：使用方便，维护简单
//            缺点：用户体验受限于浏览器，服务器负荷大，信息安全控制差
//    3.web服务器
//        Tomcat:apache提供的小型免费服务器软件，支持servlet和jsp规范
//        WebLogic:bea提供的大型收费服务器软件，支持所有的J2EE规范
//        WebSphere:IBM提供的大型收费服务器软件，支持所有的J2EE规范
//        JBoss:开源应用服务器，用来管理EJB的容器，不支持servlet和jsp，一般与Tomcat或Jetty绑定使用
//    4.url
//        资源定位符，互联网上每个资源都有唯一的标志符
//        格式：
//            协议://用户名:密码@域名:端口号/资源位置?参数=值#标志
//                协议  http,https,ftp
//                用户名:密码  通常用于访问ftp
//                域名  域名或者IP都可以
//                端口号 程序有自己的端口号，指定端口号才能让其他计算机准确的访问，http程序默认端口是80
//                标志  锚点，用于指定页面的某一位置，锚点其实就是某个id的元素，因为id是唯一的，所以可以确定
//    5.Tomcat
//        1.概述
//            免费开源，中小型系统，并发访问不多的场合广泛使用
//            实际上，apache有两款服务器软件，apache web和tomcat web    apache是用来专门处理html的  tomcat用来处理servlet和jsp，也可以处理html，但不如apache
//            所以这两者经常被整合在一起使用
//        2.下载并解压    https://tomcat.apache.org/download-90.cgi
//        3.目录结构
//            bin     脚本
//                        startup.bat     shutdown.bat
//            conf    配置
//                        核心配置                server.xml  端口号
//                        所有web项目默认配置     web.xml
//                        用户权限配置            tomcat-users.xml
//            lib     jar包
//            logs    日志
//            temp    临时文件
//            webapps web应用发布目录
//                        web项目文件目录
//                            项目名
//                                static静态资源
//                                WEB-INF
//                                    web.xml 当前web项目的核心配置    servlet2.5必须要，3.0可以省略
//                                    lib     当前web项目所需jar包
//                                    classes java应用编译生成的class文件
//            work    处理jsp的工作目录
//        4.使用    参考  https://www.cnblogs.com/weixinyu98/p/9822048.html
package part3;

public class AHttp {
}
