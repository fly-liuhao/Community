## 《基于微服务架构的信息分享社区的设计与实现》

#### 作者
- 刘浩

#### 相似网站
- [码匠社区](http://www.mawen.co/)
- [elastic中文社区](https://elasticsearch.cn/)
- [V2EX论坛](https://www.v2ex.com/t/624147#reply26)
- [知乎](https://www.zhihu.com/)

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
- [FlyWay 使用教程](https://flywaydb.org/getstarted/firststeps/maven#migrating-the-database)：用于多人共同使用数据库，持续的更新集成我们的脚本，使得数据库的维护成本降低

#### 工具
- [Git](https://git-scm.com/)
- [Visual Paradigm UML绘图工具](https://www.visual-paradigm.com)

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



