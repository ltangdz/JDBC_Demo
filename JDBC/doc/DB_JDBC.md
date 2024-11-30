# Java JDBC

## * 题注

> 课程链接：【黑马JDBC视频全套视频教程，快速入门jdbc原理+jdbc实战，一套掌握】https://www.bilibili.com/video/BV1s3411K7jH?p=12&vd_source=33dddd4ef8f1605f35cb00074e1a60e5
>
> Github示例代码和笔记：

## 01 JDBC简介 快速入门

JDBC是用Java语言操作关系型数据库的一套API***（面向接口编程）***

![image-20241129220215345](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241129220215345.png)

![image-20241129220621969](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241129220621969.png)

```java
package com.wanf2004.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        String url = "jdbc:mysql://127.0.0.1:3306/hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.定义sql
        String sql = "UPDATE account SET money = 1000000 WHERE id = 1";


        // 4.获取执行sql的对象 Statement
        Statement statement = connection.createStatement();

        // 5.执行sql
        int count = statement.executeUpdate(sql); // 受影响的行数

        // 6.处理对象
        System.out.println(count);

        // 7.释放资源
        statement.close();
        connection.close();
    }
}
```







## 02 DriverManager

![image-20241129232441132](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241129232441132.png)

![image-20241129232655408](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241129232655408.png)

```java
package com.wanf2004.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        // jar5后可省略
        // Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        // 以下两种url（本地）相同
        // String url = "jdbc:mysql://127.0.0.1:3306/hpgc_jdbc";
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.定义sql
        String sql = "UPDATE account SET money = 1000000 WHERE id = 1";


        // 4.获取执行sql的对象 Statement
        Statement statement = connection.createStatement();

        // 5.执行sql
        int count = statement.executeUpdate(sql); // 受影响的行数

        // 6.处理对象
        System.out.println(count);

        // 7.释放资源
        statement.close();
        connection.close();
    }
}
```







## 03 Connection

![image-20241129233228959](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241129233228959.png)

![image-20241129233351254](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241129233351254.png)

### 示例

1. 初始化Account表如下

![image-20241130001102915](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130001102915.png)

2. 执行以下Java代码*（关闭自动提交）*

```java
package com.wanf2004.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        // jar5后可省略
        // Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        // 以下两种url（本地）相同
        // String url = "jdbc:mysql://127.0.0.1:3306/hpgc_jdbc";
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.定义sql
        String sql1 = "UPDATE account SET money = 1000000 WHERE id = 1";
        String sql2 = "UPDATE account SET money = 2000000 WHERE id = 2";

        // 4.获取执行sql的对象 Statement
        Statement statement = connection.createStatement();

        try {
            // 开始事务
            connection.setAutoCommit(false);

            // 5.执行sql
            int count1 = statement.executeUpdate(sql1); // 受影响的行数

            // 6.处理对象
            System.out.println(count1);

            int i = 3/0;
            // 5.执行sql
            int count2 = statement.executeUpdate(sql2); // 受影响的行数

            // 6.处理对象
            System.out.println(count2);

            // 提交事务
            connection.commit();
        } catch (Exception e) {
            // 回滚事务
            connection.rollback();

            throw new RuntimeException(e);
        }

        // 7.释放资源
        statement.close();
        connection.close();
    }
}
```

3. int i = 3/0 出现异常 try/catch捕捉到并**回滚事务**
   因此Account表项不变



4. 执行以下Java代码*（开启自动提交）*

```java
package com.wanf2004.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        // jar5后可省略
        // Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        // 以下两种url（本地）相同
        // String url = "jdbc:mysql://127.0.0.1:3306/hpgc_jdbc";
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.定义sql
        String sql1 = "UPDATE account SET money = 1000000 WHERE id = 1";
        String sql2 = "UPDATE account SET money = 2000000 WHERE id = 2";

        // 4.获取执行sql的对象 Statement
        Statement statement = connection.createStatement();

        try {
            // 开始事务
            //connection.setAutoCommit(false);

            // 5.执行sql
            int count1 = statement.executeUpdate(sql1); // 受影响的行数

            // 6.处理对象
            System.out.println(count1);

            int i = 3/0;
            // 5.执行sql
            int count2 = statement.executeUpdate(sql2); // 受影响的行数

            // 6.处理对象
            System.out.println(count2);

            // 提交事务
            //connection.commit();
        } catch (Exception e) {
            // 回滚事务
            connection.rollback();

            throw new RuntimeException(e);
        }

        // 7.释放资源
        statement.close();
        connection.close();
    }
}
```

由于开启了自动提交，在sql1执行完后提交了新表项，sql1和sql2之间出现异常，sql2不执行
因此执行完这段代码后Account表为

![image-20241130002148536](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130002148536.png)





## 04 Statement

![image-20241130002355621](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130002355621.png)

### 1. 测试DML语句：count返回影响的行数

```java
// 3.定义sql
    String sql = "UPDATE account SET money = 1000000 WHERE id = 1";		//count = 1
    // String sql = "UPDATE account SET money = 1000000 WHERE id = 5";	//count = 0

    // 4.获取执行sql的对象 Statement
    Statement statement = connection.createStatement();

    try {
        // 开始事务
        connection.setAutoCommit(false);

        // 5.执行sql
        int count = statement.executeUpdate(sql); // 执行DML: 受影响的行数

        // 6.处理对象
        // System.out.println(count);
        if (count > 0) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }

        // 提交事务
        connection.commit();
    } catch (Exception e) {
        // 回滚事务
        connection.rollback();

        throw new RuntimeException(e);
    }

    // 7.释放资源
    statement.close();
    connection.close();
}
```

### 2. 测试DDL语句：count可能返回0

#### count返回1：

```java
    // 3.定义sql
    String sql = "create database db1";

    // 4.获取执行sql的对象 Statement
    Statement statement = connection.createStatement();

    try {
        // 开始事务
        connection.setAutoCommit(false);

        // 5.执行sql
        int count = statement.executeUpdate(sql); // 执行DDL: 受影响的行数(可能返回0)

        // 6.处理对象
        System.out.println(count);

        // 提交事务
        connection.commit();
    } catch (Exception e) {
        // 回滚事务
        connection.rollback();

        throw new RuntimeException(e);
    }

    // 7.释放资源
    statement.close();
    connection.close();
}
```

数据库中创建了db1

![image-20241130005724000](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130005724000.png)

#### count返回0：

```java
// 3.定义sql
String sql = "drop database db1";
```

数据库db1被删除

![image-20241130005856609](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130005856609.png)







## 05 ResultSet

> Statement执行executeQuery(sql)后的返回值

![image-20241130010524539](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130010524539.png)

```java
    /**
     * 执行DQL语句
     * DQL（Data Query Language）语句：数据查询语言，主要是对数据进行查询操作。
     * 常用关键字有 SELECT、FROM、WHERE 等
     *
     * @throws Exception
     */
    @Test
    public void TestDQL() throws Exception {
        // 1.注册驱动
        // jar5后可省略
        // Class.forName("com.mysql.jdbc.Driver");

        // 2.获取连接
        // 以下两种url（本地）相同
        // String url = "jdbc:mysql://127.0.0.1:3306/hpgc_jdbc";
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.定义sql
        String sql = "select * from account";

        // 4.获取执行sql的对象 Statement
        Statement statement = connection.createStatement();

        // 5.执行sql
        ResultSet resultSet = statement.executeQuery(sql);

        // 6.处理结果: 遍历resultSet中所有数据
        // 光标向下移动一行 并且判断当前行是否有数据
        while (resultSet.next()) {
            // 获取数据 getXxx();
//            int id = resultSet.getInt(1);
//            String name = resultSet.getString(2);
//            //int money = resultSet.getInt(3);
//            double money = resultSet.getDouble(3);

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double money = resultSet.getDouble("money");

            System.out.println(id);
            System.out.println(name);
            System.out.println(money);

            System.out.println("---------------------------");
        }

        // 7.释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
```

测试结果如下：

![image-20241130013331730](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130013331730.png)

### Task

**需求：查询account账户表数据，封装为Account对象中，并且存储到ArrayList中**

![image-20241130013451969](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130013451969.png)

### Solution

```java
/**
 * 查询account账户表数据，封装为Account对象中，并且存储到ArrayList中
 * 1.定义实体类Account
 * 2.查询数据，封装到Account对象中
 * 3.将Account对象存入ArrayList集合中
 * @throws Exception
 */
@Test
public void ResultSetTask() throws Exception {
    // 1.注册驱动
    // jar5后可省略
    // Class.forName("com.mysql.jdbc.Driver");

    // 2.获取连接
    String url = "jdbc:mysql:///hpgc_jdbc";
    String username = "root";
    String password = "hpgc040307.";
    Connection connection = DriverManager.getConnection(url, username, password);

    // 3.定义sql
    String sql = "select * from account";

    // 4.获取执行sql的对象 Statement
    Statement statement = connection.createStatement();

    // 5.执行sql
    ResultSet resultSet = statement.executeQuery(sql);

    List<Account> list = new ArrayList<>();

    // 6.处理结果: 遍历resultSet中所有数据
    while (resultSet.next()) {
        Account account = new Account();

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double money = resultSet.getDouble("money");

        // 赋值
        account.setId(id);
        account.setName(name);
        account.setMoney(money);

        // 存入集合
        list.add(account);
    }

    System.out.println(list);

    // 7.释放资源
    resultSet.close();
    statement.close();
    connection.close();
}
```

Account实体类：

```java
package com.wanf2004.pojo;

public class Account {
    private int id;
    private String name;
    private double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
```

测试结果：

![image-20241130014559990](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130014559990.png)







## 06 PreparedStatement

![image-20241130015014887](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130015014887.png)

### （1）SQL注入演示

首先创建一张user table

![image-20241130021917315](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130021917315.png)

接着在测试方法里尝试登录

Java代码：

```java
@Test
public void TestLogin() throws Exception {
    // 1.注册驱动

    // 2.获取连接
    String url = "jdbc:mysql:///hpgc_jdbc";
    String username = "root";
    String password = "hpgc040307.";
    Connection connection = DriverManager.getConnection(url, username, password);

    // 接收用户输入 用户名 密码
    String name = "君莫笑";
    String pwd = "123";

    // 3.定义sql
    String sql = "select * from tb_user where username = '" + name + "' and password = '" + pwd + "'";

    // 4.获取执行sql的对象 Statement
    Statement statement = connection.createStatement();

    // 5.执行sql
    ResultSet resultSet = statement.executeQuery(sql);

    // 6.处理结果
    if (resultSet.next()) {
        System.out.println("登录成功");
    } else {
        System.out.println("登录失败");
    }

    // 7.释放资源
    statement.close();
    connection.close();
}
```

测试结果：登录成功

```java
// 接收用户输入 用户名 密码
String name = "君莫笑";
String pwd = "123123";
```

测试结果：登录失败



如果把密码改为以下：

```java
        // 接收用户输入 用户名 密码
        String name = "君莫笑";
//        String pwd = "123123";
        // sql注入:
        String pwd = "'or'1'='1";
```

数据库里并没有对应的密码 但是测试结果总是成功
原因：把sql打印出来 会发现

```java
// 6.处理结果
if (resultSet.next()) {
    System.out.println("登录成功");
} else {
    System.out.println("登录失败");
}
System.out.println(sql);
```

![image-20241130023538711](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130023538711.png)

我们发现 sql语句中 where部分恒为true
**因此只要数据库中tb_user不为空 resultSet就不可能为空！！！**





### （2）解决方法：PreparedStatement

![image-20241130024007452](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130024007452.png)



```java
/**
 * 测试用户输入（防止sql注入）
 * 将Statement换成PreparedStatement
 *
 * @throws Exception
 */
@Test
public void TestLogin_PreventInject() throws Exception {
    // 1.注册驱动

    // 2.获取连接
    String url = "jdbc:mysql:///hpgc_jdbc";
    String username = "root";
    String password = "hpgc040307.";
    Connection connection = DriverManager.getConnection(url, username, password);

    // 接收用户输入 用户名 密码
    String name = "君莫笑";
    // String pwd = "123";          // 登录成功
    // String pwd = "123123";       // 登录失败
    // sql注入:
    String pwd = "'or'1'='1";       // 登录失败

    // 3.定义sql
    String sql = "select * from tb_user where username = ? and password = ?";

    // 4.获取执行sql的对象 pstmt
    // Statement statement = connection.createStatement();
    PreparedStatement pstmt = connection.prepareStatement(sql);

    // 设置?的值
    pstmt.setString(1, name);
    pstmt.setString(2, pwd);

    // 5.执行sql
    // ResultSet resultSet = statement.executeQuery(sql);
    ResultSet resultSet = pstmt.executeQuery();     // pstmt的executeQuery()或executeUpdate()无参数!!!

    // 6.处理结果
    if (resultSet.next()) {
        System.out.println("登录成功");
    } else {
        System.out.println("登录失败");
    }
    System.out.println(sql);

    // 7.释放资源
    pstmt.close();
    connection.close();
}
```





### （3）PreparedStatement原理

![image-20241130030831992](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130030831992.png)

省流：设置url时加入useServerPrepStmts=true开启预编译功能

```java
// 2.获取连接
String url = "jdbc:mysql:///hpgc_jdbc?useServerPrepStmts=true";
```

* 什么是**预编译**？

  Java把sql语句发送到mysql服务器时，mysql服务器会先**检查sql语法 编译sql**，而这两步比较耗时

  预编译可以在获取PreparedStatement对象，把sql语句发送到mysql服务器时进行检查编译

  **执行sql**时就免去了这些步骤







## 07 数据库连接池

### 我的作业不需要 所以这一块我没有实现 感兴趣可以自己动手敲

![image-20241130033125070](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130033125070.png)



![image-20241130033638398](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130033638398.png)

![image-20241130033700305](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130033700305.png)











## 08 JDBC练习

### Task

![image-20241130035652850](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130035652850.png)



### （1）环境准备

![image-20241130035828610](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130035828610.png)

#### 数据库表tb_brand

CREATE TABLE tb_brand(
	-- id
	id int PRIMARY KEY auto_increment,
	-- 品牌名称
	brand_name varchar(20),
	-- 企业名称
	company_name varchar(20),
	-- 排序字段
	ordered int,
	-- 描述信息
	description varchar(100),
	-- 状态 0：禁用 1：启用
	status int
);

INSERT INTO tb_brand VALUES
(1,'企鹅','腾讯科技有限公司',5,'充Q币吗',0),
(2,'网异','网易科技有限公司',100,'修修你那破网',1),
(3,'华为','华为技术有限公司',50,'Made In China',1);

SELECT * FROM tb_brand;

![image-20241130041512282](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130041512282.png)

#### 实体类Brand

```java
package com.wanf2004.pojo;

public class Brand {
    // id
    private Integer id;

    // 品牌名称
    private String brandName;

    // 企业名称
    private String companyName;

    // 排序字段
    private Integer ordered;

    // 描述信息
    private String description;

    // 状态 0：禁用 1：启用
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
```

#### 测试用例

![image-20241130042654097](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130042654097.png)

项目结构：

![image-20241130042631575](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130042631575.png)



### （2）查询所有

```java
package com.wanf2004.example;

import com.wanf2004.pojo.Brand;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 品牌数据的增删改查
 */
public class BrandTest {
    /**
     * 查询所有
     * 1.sql select * from tb_brand
     * 2.参数 无
     * 3.结果 List<Brand>
     * @throws Exception
     */
    @Test
    public void QueryAll() throws Exception {
        // 1.Connect
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 2.Def sql
        String sql = "select * from tb_brand";

        // 3.Get stmt / pstmt
        PreparedStatement pstmt = connection.prepareStatement(sql);

        // 4.Set param(?)

        // 5.Execute sql
        ResultSet resultSet = pstmt.executeQuery();

        // 6.Handle result
        List<Brand> list = new ArrayList<>();
        Brand brand = new Brand();

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String brandName = resultSet.getString("brand_name");
            String companyName = resultSet.getString("company_name");
            Integer ordered = resultSet.getInt("ordered");
            String description = resultSet.getString("description");
            Integer status = resultSet.getInt("status");

            brand.setId(id);
            brand.setBrandName(brandName);
            brand.setCompanyName(companyName);
            brand.setOrdered(ordered);
            brand.setDescription(description);
            brand.setStatus(status);

            list.add(brand);
        }
        System.out.println(list);

        // 7.Release resources
        resultSet.close();
        pstmt.close();
        connection.close();
    }
}
```

测试结果：

![image-20241130045958149](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130045958149.png)

### （3）添加 修改 删除

#### 添加品牌

#### 根据id修改

#### 根据id删除

```java
package com.wanf2004.example;

import com.wanf2004.pojo.Brand;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 品牌数据的增删改查
 */
public class BrandTest {
    /**
     * 查询所有
     * 1.sql select * from tb_brand
     * 2.参数 无
     * 3.结果 List<Brand>
     *
     * @throws Exception
     */
    @Test
    public void QueryAll() throws Exception {
        // 1.Connect
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 2.Def sql
        String sql = "select * from tb_brand";

        // 3.Get stmt / pstmt
        PreparedStatement pstmt = connection.prepareStatement(sql);

        // 4.Set param(?)

        // 5.Execute sql
        ResultSet resultSet = pstmt.executeQuery();

        // 6.Handle result
        List<Brand> list = new ArrayList<>();
        Brand brand = new Brand();

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String brandName = resultSet.getString("brand_name");
            String companyName = resultSet.getString("company_name");
            Integer ordered = resultSet.getInt("ordered");
            String description = resultSet.getString("description");
            Integer status = resultSet.getInt("status");

            brand.setId(id);
            brand.setBrandName(brandName);
            brand.setCompanyName(companyName);
            brand.setOrdered(ordered);
            brand.setDescription(description);
            brand.setStatus(status);

            list.add(brand);
        }
        System.out.println(list);

        // 7.Release resources
        resultSet.close();
        pstmt.close();
        connection.close();
    }

    /**
     * 增加品牌
     * 1.sql insert into tb_brand(brand_name, company_name, ordered, description, status) values(?,?,?,?,?);
     * 2.参数 brandName companyName ordered description status
     * 3.结果 无
     *
     * @throws Exception
     */
    @Test
    public void AddBrand() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 1.Connect
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 2.Def sql
        String sql = "insert into tb_brand(brand_name, company_name, ordered, description, status) values(?,?,?,?,?)";

        // 3.Get stmt / pstmt
        PreparedStatement pstmt = connection.prepareStatement(sql);

        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // 4.Set param(?)
            System.out.println("请输入品牌名: ");
            pstmt.setString(1, scanner.nextLine());

            System.out.println("请输入企业名: ");
            pstmt.setString(2, scanner.nextLine());

            System.out.println("请输入排序: ");
            pstmt.setInt(3, scanner.nextInt());
            scanner.nextLine();

            System.out.println("请输入描述: ");
            pstmt.setString(4, scanner.nextLine());

            System.out.println("请输入状态（0：禁用 1：启用）: ");
            pstmt.setInt(5, scanner.nextInt());
            scanner.nextLine();

            // 5.Execute sql
            int count = pstmt.executeUpdate();

            // 6.Handle result
            if (count > 0) {
                System.out.println("增加成功");
            } else {
                System.out.println("增加失败");
                throw new Exception();
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction
            connection.rollback();

            throw new RuntimeException(e);
        } catch (Exception e) {
            // Rollback transaction
            connection.rollback();

            System.out.println("非法输入！！！");

            throw new Exception();
        }

        // 7.Release resources
        pstmt.close();
        connection.close();

        scanner.close();
    }

    /**
     * 通过id修改
     * 1.sql update tb_brand set brand_name = ?, company_name = ?, ordered = ?, description = ?, status = ? where id = ?;
     * 2.参数 all params
     * 3.结果 无
     *
     * @throws Exception
     */
    @Test
    public void ReviseById() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 1.Connect
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 2.Def sql
        String sql = "update tb_brand set brand_name = ?, company_name = ?, ordered = ?, description = ?, status = ? where id = ?";

        // 3.Get stmt / pstmt
        PreparedStatement pstmt = connection.prepareStatement(sql);

        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // 4.Set param(?)
            System.out.println("请输入要修改表项的id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            pstmt.setInt(6, id);

            System.out.println("请输入品牌名: ");
            pstmt.setString(1, scanner.nextLine());

            System.out.println("请输入企业名: ");
            pstmt.setString(2, scanner.nextLine());

            System.out.println("请输入排序: ");
            pstmt.setInt(3, scanner.nextInt());
            scanner.nextLine();

            System.out.println("请输入描述: ");
            pstmt.setString(4, scanner.nextLine());

            System.out.println("请输入状态（0：禁用 1：启用）: ");
            pstmt.setInt(5, scanner.nextInt());
            scanner.nextLine();

            // 5.Execute sql
            int flag = pstmt.executeUpdate();

            // 6.Handle result
            if (flag == 0) {
                System.out.printf("修改失败！不存在id=%d的表项！\n", id);
                throw new Exception();
            } else {
                System.out.println("修改成功！");
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction
            connection.rollback();

            throw new RuntimeException(e);
        }catch (Exception e) {
            // Rollback transaction
            connection.rollback();

            System.out.println("非法输入！！！");
            throw new Exception(e);
        }

        // 7.Release resources
        pstmt.close();
        connection.close();

        scanner.close();
    }

    /**
     * 通过id删除
     * 1.sql delete from tb_brand where id = ?;
     * 2.参数 id
     * 3.结果 无
     *
     * @throws Exception
     */
    @Test
    public void DeleteById() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 1.Connect
        String url = "jdbc:mysql:///hpgc_jdbc";
        String username = "root";
        String password = "hpgc040307.";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 2.Def sql
        String sql = "delete from tb_brand where id = ?";

        // 3.Get stmt / pstmt
        PreparedStatement pstmt = connection.prepareStatement(sql);

        // 4.Set param(?)
        System.out.println("请输入要删除表项的id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        pstmt.setInt(1, id);

        // 5.Execute sql
        int flag = pstmt.executeUpdate();

        // 6.Handle result
        if (flag == 0) {
            System.out.printf("删除失败！不存在id=%d的表项！\n", id);
        } else {
            System.out.println("删除成功！");
        }

        // 7.Release resources
        pstmt.close();
        connection.close();

        scanner.close();
    }
}
```





## Appendix：一个小bug

我用@Test注解表明的方法类中包含Scanner对象 但是无法在控制台输入

![image-20241130052713722](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130052713722.png)



解决方案：参考[IDEA中@Test测试Scanner无法在控制台输入的问题-CSDN博客](https://blog.csdn.net/qq_33406883/article/details/116305990)

1. 帮助中打开选项：编辑自定义虚拟机选项
   ![image-20241130052842893](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130052842893.png)

2. 添加一句话：-Deditable.java.test.console=true
   ![image-20241130053031584](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130053031584.png)
3. 重启后 成功输入！
   ![image-20241130054033443](https://github.com/ltangdz/JDBC_Demo/blob/main/JDBC/doc/images/image-20241130054033443.png)
