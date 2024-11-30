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
