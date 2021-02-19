package com.issac.jvm.commonspool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author: ywy
 * @date: 2021-02-04
 * @desc:
 */
public class JDBCTest {
    public static void main(String[] args) throws Exception {
        Class<?> jdbcClass = Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/foodie-shop-dev?useUnicode=true&&characterEncoding=utf8",
                "root",
                "rootroot"
        );

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from  `foodie-shop-dev`.orders"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id"));
            System.out.println(resultSet.getString("user_id"));
        }
        resultSet.close();
        preparedStatement.close();

        PreparedStatement preparedStatement2 = connection.prepareStatement(
                "select * from  `foodie-shop-dev`.orders"
        );
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
            System.out.println(resultSet2.getString(1));
            System.out.println(resultSet2.getString(2));
        }
        resultSet2.close();
        preparedStatement2.close();



        connection.close();
    }
}
