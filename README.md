## 《基于微服务架构的信息分享社区的设计与实现》

#### 作者
- 刘浩

#### 相似网站
- [码匠社区](http://www.mawen.co/)
- [elastic中文社区](https://elasticsearch.cn/)
- [V2EX论坛](https://www.v2ex.com/t/624147#reply26)
- [知乎](https://www.zhihu.com/)
- [思否](https://segmentfault.com/blogs)

#### 资料
- [小匠社区github源码](https://github.com/codedrinker/community)：作者的github仓库地址
- [Spring Web文档](https://spring.io/guides/gs/serving-web-content/)：用于构建SpringBoot的web的实例demo
- [github SSH配置](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)：用于本地仓库链接远程仓库
- [BootStrap文档](https://v3.bootcss.com/getting-started/)：用于快速搭建网站前端页面
- [github OAuth 授权机制](https://developer.github.com/apps/building-oauth-apps/)：用于授权登录，实现登录功能
- [OkHttp](https://square.github.io/okhttp/)：用于后端模拟post请求
- [Maven Repository](https://mvnrepository.com/)：用于使用Maven工程的jar包引入
- [Spring Boot & MyBatis](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)：Spring Boot项目使用MyBatis框架
- [Spring Boot 参考指南](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)：查找内嵌的数据源
- [FlyWay 使用教程](https://flywaydb.org/getstarted/firststeps/maven#migrating-the-database)：Flyway管理并跟踪数据库变更的数据库版本管理工具，它可以像Git管理不同人的代码那样，管理不同人的sql脚本，从而做到数据库同步。
- [Lombok](https://projectlombok.org/features/all)：使用@Data注解，自动生成geter、seter、toString、equals等方法，但是IDEA需要引入lombok插件
- [Thymeleaf 模板引擎官方文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)：类似于JSP页面的EL表达式以及JSTL表达式
- [Spring Developer Tools 工具——IDEA热部署插件](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)：引入依赖-->IDEA配置自动Bulid-->修改IDEA注册表-->Chrome 安装 LiveReload 插件-->启动项目
- [Spring Web MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)：官方文档，eg:拦截器
- [MyBatis Generator](http://mybatis.org/generator/)：反向创建数据库表对应model、mapper接口以及映射文件
- [JQuery API 官方文档](https://api.jquery.com/)：使用Ajax请求
- [Window localStorage 属性](https://www.runoob.com/jsref/prop-win-localstorage.html)：用于浏览器端存储数据（HTML5）,类似于Cookie但更加强大
- [Moment.js](http://momentjs.cn/)：JavaScript 日期处理类库
- [Markdown编辑器插件](https://pandao.github.io/editor.md/index.html)：用于使用Markdown文本框发布、评论问题
- [UCloud官网-UFile对象存储-说明文档](https://docs.ucloud.cn/storage_cdn/ufile/introduction/concept)：类似于阿里云、腾讯云、华为云的云计算提供商
- [UCloud-UFile对象存储-github-SDK](https://github.com/ucloud/ufile-sdk-java)：用于文件的上传下载



#### 工具
- [Git](https://git-scm.com/)
- [Visual Paradigm UML绘图工具](https://www.visual-paradigm.com)
- [Postman](https://chrome.google.com/webstore/detail/tabbed-postman-rest-clien/coohjcphdfgbiolnekdpbcijmhambjff):用于模拟浏览器端的JSON请求（在此使用浏览器插件方式）
- [JSON Editor Online](https://jsoneditoronline.org/)：用于Json格式的解析查看
- [github Chrome插件：LiveReload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)：配合IDEA的自动热部署使用，检测到服务更新后自动的渲染到页面（将页面元素刷新）
- [github Chrome插件：Octotree](https://chrome.google.com/webstore/detail/octotree/bkhaagjahfmjljalopjnoealnfndnagc?hl=zh-CN)：类似于IDE中的侧边导航栏
- [github Chrome插件：Git History Browser Extension](https://chrome.google.com/webstore/detail/git-history-browser-exten/laghnmifffncfonaoffcndocllegejnf?hl=zh-CN)：用于查看代码历史提交的变化
- [github Chrome插件：GayHub](https://chrome.google.com/webstore/detail/gayhub/mdcffelghikdiafnfodjlgllenhlnejl?hl=zh-CN)：出导航栏功能，还有罗列大纲的功能
- [github Chrome插件：Table of contents sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)：可以根据 H* 标签快速生成文档目录的插件工具。

#### 脚本
- SQL
    ```sql
    -- auto-generated definition
    create table USER
    (
        ID         INT auto_increment,
        ACCOUNT_ID VARCHAR(100) not null,
        NAME       VARCHAR(20)  not null,
        TOKEN      CHAR(36)     not null,
        GMT_CREATE BIGINT       not null,
        GMT_MODIFY BIGINT       not null,
        constraint USER_PK
            primary key (ID)
    );
    ```
    ```bash
    // 执行flyway的脚本
    mvn flyway:migrate
  
    // 执行MyBatis genergtor 自动生成xml文件
    mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
    ```



