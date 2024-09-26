package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.UnidadDidactica;
import utils.Util;

public class ControllerAr implements InterfaceControllerAr {

    private Connection conexion;
    private ResultSet resultado;
    private PreparedStatement sentencia;

    final String INSERTud = "INSERT INTO UnidadDidactica (idUd, acronimo, titulo, evaluacion, descripcion) VALUES (?, ?, ?, ?, ?)";
    final String GETconvocatoria = "SELECT convocatoria FROM convocatoriaexamen WHERE idE IS NULL";
    final String GETenunciado = "SELECT idE, descripcion FROM enunciado";
    final String getConvocatoria = "SELECT convocatoria FROM convocatoriaexamen WHERE idE IS NULL";
    final String getEnunciado = "SELECT idE, descripcion FROM enunciado";
    final String UPDATEConvocatoria = "UPDATE convocatoriaexamen SET idE = ? WHERE convocatoria = ?";
    final String GETenunciadoRuta = "SELECT ruta FROM enunciado WHERE idE = ?";

    private void openConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/examendb?serverTimezone=Europe/Madrid&useSSL=false";
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

        System.out.println("\nIntroduzca el ID de la unidad didáctica:");
        idUd = Util.leerInt();

        System.out.println("\nIntroduzca el acrónimo correspondiente a esta unidad didáctica:");
        acronimo = Util.introducirCadena();

        System.out.println("\nIntroduzca el título de esta unidad didáctica:");
        titulo = Util.introducirCadena();

        System.out.println("\nIntroduzca la evaluación correspondiente a esta unidad didáctica:");
        evaluacion = Util.introducirCadena();

        System.out.println("\nIntroduzca la descripción de esta unidad didáctica:");
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
        System.out.println("\nUnidad didáctica creada correctamente.\n");
    }

    /**
     *
     */
    @Override
    public void asignarEnunciado() {
        this.openConnection();
        try {
            sentencia = conexion.prepareStatement(getConvocatoria);
            resultado = sentencia.executeQuery();
            ArrayList<String> convocatorias = new ArrayList<>();

            System.out.println("Convocatorias sin enunciado asignado:");
            while (resultado.next()) {
                String convocatoria = resultado.getString("convocatoria");
                convocatorias.add(convocatoria);
                System.out.println(convocatoria);
            }

            if (convocatorias.isEmpty()) {
                System.out.println("No hay convocatorias sin enunciado.");
                return;
            }

            System.out.println("Introduzca la convocatoria a la que quiere asignar un enunciado (nombre): ");
            String nombreConvocatoria = Util.introducirCadena();
            if (!convocatorias.contains(nombreConvocatoria)) {
                System.out.println("La convocatoria introducida no existe o ya tiene un enunciado asignado.");
                return;
            }

            sentencia = conexion.prepareStatement(getEnunciado);
            resultado = sentencia.executeQuery();
            ArrayList<Integer> enunciados = new ArrayList<>();

            System.out.println("Enunciados disponibles:");
            while (resultado.next()) {
                int idE = resultado.getInt("idE");
                String descripcion = resultado.getString("descripcion");
                enunciados.add(idE);
                System.out.println("ID: " + idE + " - Descripción: " + descripcion);
            }

            if (enunciados.isEmpty()) {
                System.out.println("No hay enunciados disponibles para asignar.");
                return;
            }

            System.out.println("Introduzca el ID del enunciado que desea asignar: ");
            int idEnunciado = Util.leerInt();
            if (!enunciados.contains(idEnunciado)) {
                System.out.println("El ID de enunciado introducido no es válido.");
                return;
            }

            sentencia = conexion.prepareStatement(UPDATEConvocatoria);
            sentencia.setInt(1, idEnunciado);
            sentencia.setString(2, nombreConvocatoria);

            int convocatoriaActualizada = sentencia.executeUpdate();
            if (convocatoriaActualizada > 0) {
                System.out.println("Enunciado asignado correctamente a la convocatoria " + nombreConvocatoria);
            } else {
                System.out.println("No se pudo asignar el enunciado a la convocatoria.");
            }

        } catch (SQLException e) {
            System.out.println("Error al asignar enunciado a la convocatoria.");
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void visualizarEnunciado() {
        this.openConnection();
        try {

            sentencia = conexion.prepareStatement(GETenunciado);
            resultado = sentencia.executeQuery();

            ArrayList<Integer> enunciados = new ArrayList<>();
            System.out.println("Enunciados disponibles:");

            while (resultado.next()) {
                int idEnunciado = resultado.getInt("idE");
                String descripcion = resultado.getString("descripcion");
                enunciados.add(idEnunciado);
                System.out.println("ID: " + idEnunciado + " - Descripción: " + descripcion);
            }

            if (enunciados.isEmpty()) {
                System.out.println("No hay enunciados disponibles.");
                return;
            }

            System.out.println("Introduzca el ID del enunciado que desea ver: ");
            int idEnunciadoSeleccionado = Util.leerInt();

            if (!enunciados.contains(idEnunciadoSeleccionado)) {
                System.out.println("El ID de enunciado introducido no es válido.");
                return;
            }

            sentencia = conexion.prepareStatement(GETenunciadoRuta);
            sentencia.setInt(1, idEnunciadoSeleccionado);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                String ruta = rs.getString("ruta");
                if (ruta != null && !ruta.isEmpty()) {
                    try {
                        File archivo = new File(ruta);
                        if (archivo.exists()) {
                            Desktop.getDesktop().open(archivo);
                        } else {
                            System.out.println("El archivo no existe en la ruta especificada.");
                        }
                    } catch (IOException e) {
                        System.out.println("Error al abrir el archivo en la ruta local: " + ruta);
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No hay ruta asociada a este enunciado.");
                }
            } else {
                System.out.println("No se encontró el enunciado con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el enunciado o la ruta en la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

}
