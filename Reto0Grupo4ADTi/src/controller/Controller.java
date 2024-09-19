/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ConvocatoriaExamen;
import model.Enunciado;

/**
 *
 * @author Iñi
 */
public class Controller implements InterfaceController{
    
    private SingletonConnection singletonConnection = SingletonConnection.getSingletonConnection();
    private Connection connectionToDatabase;

	// Sirve para gestionar las sentencias SQL.
    private PreparedStatement stmt;
    
    private final String CREAR_ENUNCIADO = "INSERT INTO ENUNCIADO VALUES(?,?,?,?,?)";
    private final String MUESTRA_RUTA_ENUNCIADO_POR_ID = "SELECT ruta FROM ENUNCIADO WHERE IDE = ?";
    private final String CREAR_CONVOCATORIA_EXAMEN = "INSERT INTO CONVOCATORIAEXAMEN VALUES(?,?,?,?,?)";

    @Override
    public Boolean crearEnunciado(Enunciado enunciado) {
                Boolean cambios = false;
		
		connectionToDatabase = singletonConnection.getConnection();
		
		try {
			stmt = connectionToDatabase.prepareStatement(CREAR_ENUNCIADO);
			stmt.setInt(1,enunciado.getId());
			stmt.setString(2,enunciado.getDescripcion());
			stmt.setString(3,enunciado.getNivel());
			stmt.setBoolean(4,enunciado.isDisponible());
			stmt.setString(5,enunciado.getRuta());
			
			if (stmt.executeUpdate()== 1) {
				cambios = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cambios;
    }

    @Override
    public String rutaEnunciadoPorId(Integer idE) {
                ResultSet rs = null;
		String ruta = "";

		connectionToDatabase = singletonConnection.getConnection();

		try {
			stmt = connectionToDatabase.prepareStatement(MUESTRA_RUTA_ENUNCIADO_POR_ID);

			// Cargamos los parámetros
			stmt.setInt(1, idE);

			rs = stmt.executeQuery();

			if (rs.next()) {
				
				ruta = rs.getString("ruta");
				
			}else{
                            System.out.println("No se encuentra la entrada.");
                        }
		} catch (SQLException e) {
			System.out.println("Error de SQL");
			e.printStackTrace();
		} finally {
			// Cerramos ResultSet
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println("Error en cierre del ResultSet");
				}
			}
		}
		return ruta;
    }

    @Override
    public Boolean crearConvocatoria(ConvocatoriaExamen convocatoriaExamen) {
        Boolean cambios = false;
		
		connectionToDatabase = singletonConnection.getConnection();
		
		try {
			stmt = connectionToDatabase.prepareStatement(CREAR_ENUNCIADO);
			stmt.setString(1, convocatoriaExamen.getConvocatoria());
			stmt.setString(2, convocatoriaExamen.getDescripcion());
			stmt.setDate(3, Date.valueOf(convocatoriaExamen.getFecha()));
			stmt.setString(4, convocatoriaExamen.getCurso());
                        stmt.setInt(5, 0);
			
			if (stmt.executeUpdate() == 1) {
				cambios = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cambios;
    }
}

    
    


