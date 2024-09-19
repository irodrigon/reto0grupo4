/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Iñi
 */
public class SingletonConnection {
    public String name;
    private static SingletonConnection singletonConnection;
    private Connection connection;

    private SingletonConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/examendb", "root", "abcd*1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static SingletonConnection getSingletonConnection() {
        if (singletonConnection == null) {
            singletonConnection = new SingletonConnection();
        }
        return singletonConnection;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
