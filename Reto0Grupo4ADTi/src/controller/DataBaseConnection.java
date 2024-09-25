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
public class DataBaseConnection {
    private static Connection con = null;

	static {
		String url = "jdbc:mysql://localhost:3306/examendb";
		String user = "root";
		String pass = "abcd*1234";
		try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}
        
        public static void closeConnection() {
            try{
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
}
