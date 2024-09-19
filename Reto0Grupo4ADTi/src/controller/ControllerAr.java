package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.UnidadDidactica;
import utils.Util;

public class ControllerAr implements InterfaceControllerAr {
    private Connection conexion;
    private PreparedStatement sentencia;
    
    final String INSERTud = "INSERT INTO UnidadDidactica (idUd, acronimo, titulo, evaluacion, descripcion) VALUES (?, ?, ?, ?, ?)";

    private void openConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/Sanrio?serverTimezone=Europe/Madrid&useSSL=false";
            conexion = DriverManager.getConnection(url, "root", "abcd*1234");
        } catch (SQLException error) {
            System.out.println("Error al intentar abrir la BD: " + error.getMessage());
            error.printStackTrace();
        }
    }

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

        System.out.println("Introduzca el ID de la unidad didáctica:");
        idUd = Util.leerInt();

        System.out.println("Introduzca el acrónimo correspondiente a esta unidad didáctica:");
        acronimo = Util.introducirCadena();

        System.out.println("Introduzca el título de esta unidad didáctica:");
        titulo = Util.introducirCadena();

        System.out.println("Introduzca la evaluación correspondiente a esta unidad didáctica:");
        evaluacion = Util.introducirCadena();

        System.out.println("Introduzca la descripción de esta unidad didáctica:");
        descripcion = Util.introducirCadena();

        return new UnidadDidactica(idUd, acronimo, titulo, evaluacion, descripcion);
    }

    @Override
    public void crearUD() {
        UnidadDidactica unidadDidactica = newUD();

        this.openConnection();

        try {
            sentencia = conexion.prepareStatement(INSERTud);
            sentencia.setInt(1, unidadDidactica.getIdUd());
            sentencia.setString(2, unidadDidactica.getAcronimo());
            sentencia.setString(3, unidadDidactica.getTitulo());
            sentencia.setString(4, unidadDidactica.getEvaluacion());
            sentencia.setString(5, unidadDidactica.getDescripcion());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }
}
