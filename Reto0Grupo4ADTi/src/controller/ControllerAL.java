/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ConvocatoriaExamen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Enunciado;


/**
 *
 * @author Alin
 */
public class ControllerAL implements InterfaceControllerAL{

    @Override
    public ConvocatoriaExamen consultarConvocatoriaEnunciado(Integer idE) {
        return null;
    }
    
    private Connection con;

	// Sirve para gestionar las sentencias SQL.
    private PreparedStatement stmt;

    
    private final String MUESTRA_CONVOCATORIA_ENUNCIADO_POR_ID = "SELECT convocatoria FROM CONVOCATORIAEXAMEN WHERE IDE = ?";

    @Override
    public String rutaEnunciadoPorId(Integer idE) {
                ResultSet rs = null;
		String convocatoria = "";

		con = SingletonConnection.getConnection();

		try {
			stmt = con.prepareStatement(MUESTRA_CONVOCATORIA_ENUNCIADO_POR_ID);

			// Cargamos los parámetros
			stmt.setInt(1, idE);

			rs = stmt.executeQuery();

			if (rs.next()) {
				
				convocatoria = rs.getString("convocatoria");
				
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
		return convocatoria;
    }

    
    
}
