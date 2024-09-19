/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UnidadDidactica;
import utils.Util;



/**
 *
 * @author egure
 */
public class ControllerAr implements InterfaceControllerAr{
    private Connection conexion;
	private PreparedStatement sentencia;
	private ResultSet resultado;


    final String INSERTud = "INSERT INTO UnidadDidactica VALUES (?,?,?,?,?)";

    
    private void openConnection() {
		try {
			String url = "jdbc:mysql://localhost:3306/Sanrio?serverTimezone=Europe/Madrid&useSSL=false";
			conexion = DriverManager.getConnection(url, "root", "abcd*1234");
		} catch (SQLException error) {
			System.out.println("Error al intentar abrir la BD: " + error.getMessage());
			error.printStackTrace();
		}
	}

	/**
	 * Closes the connection to the database.
	 */
	private void closeConnection() {
		try {
			if (sentencia != null) {
				sentencia.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		} catch (SQLException error) {
			System.out.println("Error al intentar cerrar la conexión: " + error.getMessage());
			error.printStackTrace();
		}
	}


        
    @Override
    public UnidadDidactica newUD() {
       Integer idUd;
       String acronimo;
       String titulo;
       String evaluacion;
       String descripcion;
       
       System.out.println("Nueva unidad didáctica:");
       
       System.out.println("Introzuzca el ID del la unidad didáctica:");
       idUd=Util.leerInt();
       
       System.out.println("Introduzca el acrónimo correspondiente a esta unidad didáctica:");
       acronimo= Util.introducirCadena();
       
       System.out.println("Introduzca el título de esta unidad didáctica:");
       titulo= Util.introducirCadena();
       
       System.out.println("Introduzca la evaluación correspondiente a esta unidad didáctica:");
       evaluacion= Util.introducirCadena();
       
       System.out.println("Introduzca la descripción de esta unidad didáctica:");
       descripcion= Util.introducirCadena();
       
       UnidadDidactica unidadDidactica = new UnidadDidactica(idUd, acronimo, titulo, evaluacion, descripcion);
        return unidadDidactica;
    }

    @Override
    public void crearUD() {
        UnidadDidactica unidadDidactica= newUD();
        
        this.openConnection();

		try {
			sentencia = conexion.prepareStatement(INSERTud);
			resultado = sentencia.executeQuery();
				listaProducto.add(producto);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			this.closeConnection();
		}


    }
    
}
