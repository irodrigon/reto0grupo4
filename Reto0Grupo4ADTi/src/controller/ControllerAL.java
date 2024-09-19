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
import java.util.ArrayList;


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

    public ArrayList rutaEnunciadoPorId(Integer idE) {
                ResultSet rs = null;
		String convocatoria = "";
                ArrayList <String> convocatorias = new ArrayList <String>();
		con = SingletonConnection.getConnection();

		try {
			stmt = con.prepareStatement(MUESTRA_CONVOCATORIA_ENUNCIADO_POR_ID);

			// Cargamos los parámetros
			stmt.setInt(1, idE);

			rs = stmt.executeQuery();

			while (rs.next()) {
				
				convocatoria = rs.getString("convocatoria");
				convocatorias.add(convocatoria);
			}
                        /*else{
                            System.out.println("No se encuentra la entrada.");
                        */
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
		return convocatorias;
    }

    
    
}
