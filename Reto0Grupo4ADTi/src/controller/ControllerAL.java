/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ConvocatoriaExamen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import utils.Util;


/**
 *
 * @author Alin
 */
public class ControllerAL implements InterfaceControllerAL{

    @Override
    public ArrayList<ConvocatoriaExamen> consultarConvocatoriaEnunciado(Integer idE) {
        ArrayList<ConvocatoriaExamen> convocatorias = new ArrayList<>();
        ResultSet rs = null;

        this.openConnection();

        try {
            // Preparar la consulta SQL
            String query = "SELECT * FROM CONVOCATORIAEXAMEN c JOIN enunciado_convocatoria ec ON c.idConvocatoria = ec.idConvocatoria WHERE ec.idE = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, idE);  // Establecer el idE (id del enunciado)

            rs = stmt.executeQuery();

            // Procesar el resultado
            while (rs.next()) {
                String convocatoria = rs.getString("convocatoria");
                String descripcion = rs.getString("descripcion");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String curso = rs.getString("curso");

                // Crear el objeto ConvocatoriaExamen y añadirlo al ArrayList
                ConvocatoriaExamen ce = new ConvocatoriaExamen(convocatoria, descripcion, fecha, curso);
                convocatorias.add(ce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }

        return convocatorias;
    }

    private Connection con;
    private PreparedStatement state;

	// Sirve para gestionar las sentencias SQL.
    private PreparedStatement stmt;

    private final String MUESTRA_CONVOCATORIA_ENUNCIADO_POR_ID = "SELECT convocatoria FROM CONVOCATORIAEXAMEN WHERE IDE = ?";
    final String INSERT_CONVOCATORIA = "INSERT INTO UnidadDidactica (convocatoria,decripcion,fecha,curso) VALUES (?, ?, ?, ?)";
    
    private void openConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/examendb?serverTimezone=Europe/Madrid&useSSL=false";
            con = DriverManager.getConnection(url, "root", "abcd*1234");
        } catch (SQLException error) {
            System.out.println("Error al intentar abrir la BD: " + error.getMessage());
            error.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (state != null) {
                state.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException error) {
            System.out.println("Error al intentar cerrar la conexión: " + error.getMessage());
            error.printStackTrace();
        }
    }
            
    public ArrayList<String> rutaEnunciadoPorId(Integer idE) {
        ResultSet rs = null;
        String convocatoria = "";
        ArrayList<String> convocatorias = new ArrayList<>();
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

    public ConvocatoriaExamen newConvocatoria() {
        String convocatoria;
        String descripcion;
        LocalDate fecha;
        String curso;

        System.out.println("Nueva convocatoria de examen:");

        System.out.println("\nIntroduzca la convocatoria del examen:");
        convocatoria = Util.introducirCadena();

        System.out.println("\nIntroduzca la descripcion a esta convocatoria de examen:");
        descripcion = Util.introducirCadena();

        System.out.println("\nIntroduzca la fecha de esta convocatoria de examen:");
        fecha = Util.leerFechaDMA();

        System.out.println("\nIntroduzca el curso a esta convocatoria de examen:");
        curso = Util.introducirCadena();

        return new ConvocatoriaExamen(convocatoria, descripcion, fecha, curso);
    }

    @Override
    public void crearConvocatoria() {
        ConvocatoriaExamen convocatoriaExamen = newConvocatoria();

        this.openConnection();

        try {
            state = con.prepareStatement(INSERT_CONVOCATORIA);
            state.setString(1, convocatoriaExamen.getConvocatoria());
            state.setString(2, convocatoriaExamen.getDescripcion());
            state.setDate(3, java.sql.Date.valueOf(convocatoriaExamen.getFecha())); 
            state.setString(4, convocatoriaExamen.getCurso());
            state.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
        System.out.println("\nConvocatoria de examen creada correctamente.\n");
    }

    @Override
    public ConvocatoriaExamen nuevaConvocatoria() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
