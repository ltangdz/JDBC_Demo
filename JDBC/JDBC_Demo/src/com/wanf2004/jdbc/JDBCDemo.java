package com.wanf2004.jdbc;

import com.wanf2004.pojo.Account;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDemo {
    /**
     * 执行DML语句
     * DML（Data Manipulation Language）语句: 数据操纵语言，主要是对数据进行增加、删除、修改操作。
     * 常用的语句关键字有 INSERT、UPDATE、DELETE 等
     *
     * @throws Exception
     */
    @Test
    public void TestDML() throws Exception {
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
        String sql = "UPDATE account SET money = 1000000 WHERE id = 5";

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

    /**
     * 执行DDL语句
     * DDL（Data Definition Language）语句： 数据定义语言，主要是进行定义/改变表的结构、数据类型、表之间的链接等操作。
     * 常用的语句关键字有 CREATE、DROP、ALTER 等
     *
     * @throws Exception
     */
    @Test
    public void TestDDL() throws Exception {
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
        String sql = "drop database db1";

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

    /**
     * 查询account账户表数据，封装为Account对象中，并且存储到ArrayList中
     * 1.定义实体类Account
     * 2.查询数据，封装到Account对象中
     * 3.将Account对象存入ArrayList集合中
     *
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
//        String pwd = "123123";
        // sql注入:
        String pwd = "'or'1'='1";

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
        System.out.println(sql);

        // 7.释放资源
        statement.close();
        connection.close();
    }

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

}
