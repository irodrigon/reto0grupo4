<<<<<<< HEAD
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
    
    public void closeConnection() {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }

    }
}
=======
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

	private static Connection con = null;

	static {

		//La conexión a la base de datos gracias a las librerías importadas por el conector mysql.
		String url = "jdbc:mysql://localhost:3306/examendb?serverTimezone=Europe/Madrid&allowPublicKeyRetrieval=true&useSSL=false";
		//El usuario a usar, en este caso administrador.
		String user = "root";
		//La contraseña del usuario en cuestión.
		String pass = "abcd*1234";
		try {
			//Sentencias propias del método: Driver de conexión.

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}
}
    
>>>>>>> main
