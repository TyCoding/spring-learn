# Spring Boot整合Mybatis

之前讲到 [Spring Boot整合JPA](https://github.com/TyCoding/spring-boot-learn/tree/master/spring-boot-jpa) ，其实对我个人而言还是不够熟悉JPA、hibernate，所以觉得这两种框架使用起来好麻烦啊。一直用的Mybatis作为持久层框架，JPA(Hibernate)主张所有的SQL都用Java代码生成，而Mybatis则更主张用原生SQL。

我也觉得Mybatis这种用XML的方式学习成本更低一些吧，但是熟悉JPA能很大程度简化项目开发。

之前已经用Spring Boot整合Thymeleaf、Mybatis实现了基础的CRUD，并且前端使用Vue.js开发，这里先送上仓库地址：[传送门](https://github.com/TyCoding/spring-boot)

# 准备

## 引入依赖

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>
```

## 配置参数

修改`application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot_mybatis?characterEncoding=utf-8
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## 初始化数据库

```sql
-- create database springboot_mybatis charset utf8;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
```

# 注解版

Mybatis提供了一些注解实现快速CRUD，比如：`@Select`,`@Update`,`@Insert`,`@Delete`

相信你在看这篇文章之前已经用过Mybatis了（比如之前的SSM开发），所以呢，使用注解方式按照XML方式的SQL写法就好。

**写在前面**

之前在SSM开发时，会在`MapperScannerConfigurer`中配置：`<property name="basePackage" value="xxx.mapper"/>`用于使用Mybatis的接口代理开发模式（且接口和XML需要名称相同）。那么在SpringBoot整合Mybatis中也要有对应的配置：

* 方式一：在每个interface Mapper前添加`@Mapper`注解
* 方式二：在`Application.java`启动类前添加`@MapperScan("cn.tycoding.mapper")`注解

## CRUD

创建Entity `/entity/User.java`

```java
@Data
@ToString
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Date createTime;
}
```

创建interface `/mapper/UserMapperAno.java`

```java
public interface UserMapperAno {

    @Select("select * from user")
    @Results({
            @Result(property = "createTime", column = "create_time")
    })
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "createTime", column = "create_time")
    })
    User findById(Long id);

    @Insert("insert into user(username,password,create_time) values(#{username},#{password},#{createTime})")
    void save(User user);

    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    void update(User user);

    @Delete("delete from user where id=#{id}")
    void delete(Long id);
}
```

其中`@Result`注解用于修饰返回结果集，若Entity和数据表字段不一致可以用其修饰

## 测试

创建测试类 `/mapper/UserMapperAnoTest.java`

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperAnoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> list = userMapper.findAll();
        list.forEach(user -> {
            logger.info("user={}", user);
        });
    }

    @Test
    public void testFindById(){
        logger.info("user={}", userMapper.findById(1L));
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("测试");
        user.setPassword("123");
        user.setCreateTime(new Date());
        userMapper.save(user);
        testFindAll();
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(4L);
        user.setUsername("测试呀");
        userMapper.update(user);
        testFindAll();
    }

    @Test
    public void delete() {
        userMapper.delete(3L);
        testFindAll();
    }
}
```

## 结

以上是常用CRUD操作的Mybatis注解版实现，对于基本的操作，使用注解确实比传统的XML简单好多，虽然也是SQL写在注解中，但是感觉比JPA的方式要简便一些（个人理解）。

# XML版

使用Mybatis的XML开发方式应该是我们比较熟悉的，和注解版最大的不同就是Dao层，XML版会自动根据Dao层接口的方法名自动映射到XML中同名`id`对应的SQL。

## 修改application.yml

添加如下Mybatis配置属性

```yaml
#mybatis配置
mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: cn.tycoding.entity
  configuration:
    # 使用jdbc的getGeneratedKeys 可以获取数据库自增主键值
    use-generated-keys: true
    # 开启驼峰命名转换，如：Table(create_time) -> Entity(createTime)。不需要我们关心怎么进行字段匹配，mybatis会自动识别`大写字母与下划线`
    map-underscore-to-camel-case: true
```

## CRUD

创建interface `UserMapperXML.java`

```java
public interface UserMapperXML {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void update(User user);

    void delete(Long id);
}
```

在`resources/`下创建`/mapper/UserMapperXML.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="cn.tycoding.mapper.UserMapperXML">

    <select id="findAll" resultType="cn.tycoding.entity.User">
        select * from user
    </select>

    <select id="findById" resultType="cn.tycoding.entity.User">
        select * from user where id = #{id}
    </select>

    <insert id="save" parameterType="cn.tycoding.entity.User">
        insert into user(username,password,create_time) values(#{username},#{password},#{createTime}
    </insert>

    <update id="update" parameterType="cn.tycoding.entity.User">
        update user set username=#{username},password=#{password} where id=#{id}
    </update>

    <delete id="delete" parameterType="long">
        delete from user where id=#{id}
    </delete>

</mapper>
```

## 测试

创建测试类`UserMapperXMLTest.java`

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperXMLTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapperXML userMapperXML;

    @Test
    public void testFindAll() {
        List<User> list = userMapperXML.findAll();
        list.forEach(user -> {
            logger.info("user={}", user);
        });
    }

    @Test
    public void testFindById(){
        logger.info("user={}", userMapperXML.findById(1L));
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("测试");
        user.setPassword("123");
        user.setCreateTime(new Date());
        userMapperXML.save(user);
        testFindAll();
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(4L);
        user.setUsername("测试呀");
        userMapperXML.update(user);
        testFindAll();
    }

    @Test
    public void delete() {
        userMapperXML.delete(3L);
        testFindAll();
    }
}
```

# 结

练习了Mybatis注解版和XML版开发模式，更觉得两者配合使用最好，简单的CRUD操作使用注解完全可以实现；复杂的查询，比如Mybatis的动态SQL特性在注解中应该很难体现，而在XML中就很容易实现了。
