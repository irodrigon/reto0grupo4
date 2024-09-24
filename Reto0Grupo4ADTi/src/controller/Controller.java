/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.ExcepcionComprobarDificultad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.ConvocatoriaExamen;
import model.Enunciado;
import model.UnidadDidactica;
import utils.Util;

/**
 *
 * @author Iñi
 */
public class Controller implements InterfaceController {

    private final SingletonConnection singletonConnection = SingletonConnection.getSingletonConnection();
    private final Connection connectionToDatabase = singletonConnection.getConnection();

    // Sirve para gestionar las sentencias SQL.
    private PreparedStatement stmt = null;

    private final String CREAR_ENUNCIADO = "INSERT INTO ENUNCIADO VALUES(?,?,?,?,?)";
    private final String MUESTRA_RUTA_ENUNCIADO_POR_ID = "SELECT ruta FROM ENUNCIADO WHERE IDE = ?";
    private final String INSERTud = "INSERT INTO UnidadDidactica (idUd, acronimo, titulo, evaluacion, descripcion) VALUES (?, ?, ?, ?, ?)";
    private final String MUESTRA_CONVOCATORIA_ENUNCIADO_POR_ID = "SELECT * FROM CONVOCATORIAEXAMEN WHERE idE = ?";
    private final String INSERT_CONVOCATORIA = "INSERT INTO CONVOCATORIAEXAMEN (convocatoria,descripcion,fecha,curso) VALUES (?, ?, ?, ?)";
    private final String OBTENERunidades = "select * from unidaddidactica;";
    private final String OBTENERenunciados = "SELECT e.descripcion FROM enunciado e JOIN enunciado_unidaddidactica eu ON e.idE = eu.idE WHERE eu.idUD = ?;";
    private final String AGREGAR_UNIDADES_DIDACTICAS = "INSERT INTO ENUNCIADO_UNIDADDIDACTICA (idE,idUD) VALUES (?,?)";
    private final String GETconvocatoria = "SELECT convocatoria FROM convocatoriaexamen WHERE idE IS NULL";
    private final String GETenunciado = "SELECT idE, descripcion FROM enunciado";
    private final String UPDATEConvocatoria = "UPDATE convocatoriaexamen SET idE = ? WHERE convocatoria = ?";

    @Override
    public Enunciado newEnunciado() {
        Boolean dificultadValida = false;
        char eleccion = 0;
        char eleccion2 = 0;
        String nivel = "";
        Enunciado enunciado = new Enunciado();
        System.out.println("\n**************************CREANDO ENUNCIADOS**************************");
        System.out.println("\nIntroduce el ID del enunciado:");
        enunciado.setId(Util.leerInt());
        System.out.println("\nIntroduce la descripcion del enunciado:");
        enunciado.setDescripcion(Util.introducirCadena());
        while (!dificultadValida) {
            System.out.println("\nIntroduce el nivel de dificultad del enunciado(ALTA/MEDIA/BAJA):");
            nivel = Util.introducirCadena();
            if (nivel.equals("ALTA") || nivel.equals("MEDIA") || nivel.equals("BAJA")) {
                dificultadValida = true;
                enunciado.setNivel(nivel);
            } else {
                ExcepcionComprobarDificultad excepcionComprobarDificultad = new ExcepcionComprobarDificultad(nivel);
                excepcionComprobarDificultad.mostrarMensajeIncorrecto();
            }
        }
        System.out.println("\nIntroduce si esta disponible o no(S/N):");
        eleccion = Util.leerChar('S', 'N');
        if (eleccion == 'S') {
            enunciado.setDisponible(true);
        } else {
            enunciado.setDisponible(false);
        }
        System.out.println("\nIntroduce la ruta del enunciado:");
        enunciado.setRuta(Util.introducirCadena());
        
        return enunciado;
    }

    @Override
    public void crearEnunciado(Enunciado enunciado) {

        try {
            stmt = connectionToDatabase.prepareStatement(CREAR_ENUNCIADO);
            stmt.setInt(1, enunciado.getId());
            stmt.setString(2, enunciado.getDescripcion());
            stmt.setString(3, enunciado.getNivel());
            stmt.setBoolean(4, enunciado.isDisponible());
            stmt.setString(5, enunciado.getRuta());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nEnunciado creado correctamente.");
        
    }

    @Override
    public String rutaArchivoEnunciadoPorId(Integer idE) {
        ResultSet rs = null;
        String ruta = "";

        try {
            stmt = connectionToDatabase.prepareStatement(MUESTRA_RUTA_ENUNCIADO_POR_ID);

            // Cargamos los parámetros
            stmt.setInt(1, idE);

            rs = stmt.executeQuery();

            if (rs.next()) {

                ruta = rs.getString("ruta");

            } else {
                System.out.println("No se encuentra la entrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        } finally {
            // Cerramos ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en cierre del ResultSet");
            }
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ruta;

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
        
        try {
            
            stmt = connectionToDatabase.prepareStatement(INSERTud);
            stmt.setInt(1, unidadDidactica.getIdUd());
            stmt.setString(2, unidadDidactica.getAcronimo());
            stmt.setString(3, unidadDidactica.getTitulo());
            stmt.setString(4, unidadDidactica.getEvaluacion());
            stmt.setString(5, unidadDidactica.getDescripcion());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nUnidad didáctica creada correctamente.\n");
        
    }

    @Override
    public ArrayList<ConvocatoriaExamen> convocatoriaEnunciadoPorId(Integer idE) {
        ResultSet rs = null;
        
        ArrayList<ConvocatoriaExamen> convocatorias = new ArrayList<ConvocatoriaExamen>();
        
        
        try {
            stmt = connectionToDatabase.prepareStatement(MUESTRA_CONVOCATORIA_ENUNCIADO_POR_ID);

            // Cargamos los parámetros
            stmt.setInt(1, idE);

            rs = stmt.executeQuery();
            
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            while (rs.next()) {

                String convocatoria = rs.getString("convocatoria");
                String descripcion = rs.getString("descripcion");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String curso = rs.getString("curso");
                
                System.out.println();
                System.out.println("Convocatoria: " + convocatoria);
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Fecha: " + fecha.format(formato));
                System.out.println("Curso: " + curso);

                // Crear el objeto ConvocatoriaExamen y añadirlo al ArrayList
                ConvocatoriaExamen ce = new ConvocatoriaExamen(convocatoria, descripcion, fecha, curso);
                convocatorias.add(ce);
            }
            /*else{
                            System.out.println("No se encuentra la entrada.");
             */
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            e.printStackTrace();
        } finally {
            // Cerramos ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en cierre del ResultSet");
            }
        }

        return convocatorias;
    }

    @Override
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

        try {
            stmt = connectionToDatabase.prepareStatement(INSERT_CONVOCATORIA);
            stmt.setString(1, convocatoriaExamen.getConvocatoria());
            stmt.setString(2, convocatoriaExamen.getDescripcion());
            stmt.setDate(3, java.sql.Date.valueOf(convocatoriaExamen.getFecha()));
            stmt.setString(4, convocatoriaExamen.getCurso());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nConvocatoria de examen creada correctamente.\n");
    }

    @Override
    public ArrayList<String> getEnunciados(int eleccion) {
        ArrayList<String> enunciados = new ArrayList();
        ResultSet rs = null;

        try {
            stmt = connectionToDatabase.prepareStatement(OBTENERenunciados);
            stmt.setString(1, Integer.toString(eleccion));

            rs = stmt.executeQuery();
            while (rs.next()) {
                String descripcion = rs.getString("descripcion");

                enunciados.add(descripcion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerramos ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en cierre del ResultSet");
            }
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    

        return enunciados;
    }

    @Override
    public ArrayList<UnidadDidactica> getUnidades() {
        ArrayList<UnidadDidactica> unidades = new ArrayList();
        ResultSet rs = null;

        try {
            stmt = connectionToDatabase.prepareStatement(OBTENERunidades);

            rs = stmt.executeQuery();
            while (rs.next()) {
                UnidadDidactica unidad = new UnidadDidactica();

                unidad.setIdUd(rs.getInt("idUD"));
                unidad.setAcronimo(rs.getString("acronimo"));
                unidad.setDescripcion(rs.getString("descripcion"));
                unidad.setTitulo(rs.getString("descripcion"));
                unidad.setEvaluacion(rs.getString("evaluacion"));

                unidades.add(unidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerramos ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en cierre del ResultSet");
            }
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unidades;
    }

    @Override
    public void mostra_unidad_enunciado() {
        ArrayList<UnidadDidactica> unidades = getUnidades();

        int eleccion, max = 0;

        for (UnidadDidactica unidad : unidades) {

            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-5s %-9s %-35s %-15s %-10s %n",
                    "IDUD:", "ACRONIMO:", "TITULO:", "EVALUACION:", "DESCRIPCION:");
            System.out.printf("%-5d %-9s %-35s %-15s %-10s %n",
                    unidad.getIdUd(), unidad.getAcronimo(), unidad.getTitulo(), unidad.getEvaluacion(), unidad.getDescripcion());
            System.out.println("--------------------------------------------------------------------------");
            max += 1;
        }
        System.out.println("\nIntroduce el ID de la unidad didactica deseada:");
        eleccion = Util.leerInt(1, max);

        ArrayList<String> enunciados = getEnunciados(eleccion);

        System.out.println("\nLos enunciados relacionados son:");

        for (String enunciado : enunciados) {
            System.out.println(enunciado + "\n");

        }
    }
    
    @Override
    public void actualizaTablaEnunciado_Unidad(int idE, int idUd){
        try {
            stmt = connectionToDatabase.prepareStatement(AGREGAR_UNIDADES_DIDACTICAS);
            stmt.setInt(1, idE);
            stmt.setInt(2,idUd);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nUnidades didacticas agregadas correctamente.\n");
    }
    
    @Override
    public void agregarUnidadesDidacticas(Enunciado enunciado){
        char eleccion = 0;
        System.out.println("\n**************************AGREGANDO UNIDADES DIDACTICAS**************************");
        ArrayList<UnidadDidactica> unidades = getUnidades();
        
        int max = 0;
        
        for(UnidadDidactica unidad : unidades){
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-5s %-9s %-35s %-15s %-10s %n",
                    "IDUD:", "ACRONIMO:", "TITULO:", "EVALUACION:", "DESCRIPCION:");
            System.out.printf("%-5d %-9s %-35s %-15s %-10s %n",
                    unidad.getIdUd(), unidad.getAcronimo(), unidad.getTitulo(), unidad.getEvaluacion(), unidad.getDescripcion());
            System.out.println("--------------------------------------------------------------------------");
            max +=1;
        }
        
        int idUd = 0;
        
        do{
            System.out.println("Seleccione la unidad didactica que desea agregar al enunciado:");
            idUd = Util.leerInt(1,max);
            for(UnidadDidactica unidad : unidades){
                if(idUd == unidad.getIdUd()){
                    enunciado.getUnidadesDidacticas().add(unidad);
                }
            }
            actualizaTablaEnunciado_Unidad(enunciado.getId(), idUd);
            System.out.println("¿Desea agregar mas unidades didacticas?");
            eleccion = Util.leerChar('S','N');
        }while(eleccion == 'S');
    }
    
     @Override
    public void asignarEnunciado() {
        
        
        ResultSet rs = null;
        
        try {
            stmt = connectionToDatabase.prepareStatement(GETconvocatoria);
            rs = stmt.executeQuery();
            ArrayList<String> convocatorias = new ArrayList<>();

            System.out.println("Convocatorias sin enunciado asignado:");
            while (rs.next()) {
                String convocatoria = rs.getString("convocatoria");
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
            stmt = connectionToDatabase.prepareStatement(GETenunciado);
            rs = stmt.executeQuery();
            ArrayList<Integer> enunciados = new ArrayList<>();

            System.out.println("Enunciados disponibles:");
            while (rs.next()) {
                int idE = rs.getInt("idE");
                String descripcion = rs.getString("descripcion");
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

            stmt = connectionToDatabase.prepareStatement(UPDATEConvocatoria);
            stmt.setInt(1, idEnunciado);
            stmt.setString(2, nombreConvocatoria);

            int convocatoriaActualizada = stmt.executeUpdate();
            if (convocatoriaActualizada > 0) {
                System.out.println("Enunciado asignado correctamente a la convocatoria " + nombreConvocatoria);
            } else {
                System.out.println("No se pudo asignar el enunciado a la convocatoria.");
            }

        } catch (SQLException e) {
            System.out.println("Error al asignar enunciado a la convocatoria.");
            e.printStackTrace();
        } finally {
              // Cerramos ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en cierre del ResultSet");
            }
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void asociaEnunciado(Enunciado enunciado){
        
        System.out.println("\n**************************ASOCIANDO ENUNCIADOS A CONVOCATORIAS**************************");
        
        ResultSet rs = null;
        
        try {
            stmt = connectionToDatabase.prepareStatement(GETconvocatoria);
            rs = stmt.executeQuery();
            ArrayList<String> convocatorias = new ArrayList<>();

            System.out.println("Convocatorias sin enunciado asignado:");
            while (rs.next()) {
                String convocatoria = rs.getString("convocatoria");
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
            
            stmt = connectionToDatabase.prepareStatement(UPDATEConvocatoria);
            stmt.setInt(1, enunciado.getId());
            stmt.setString(2, nombreConvocatoria);

            int convocatoriaActualizada = stmt.executeUpdate();
            if (convocatoriaActualizada > 0) {
                System.out.println("Enunciado asignado correctamente a la convocatoria " + nombreConvocatoria);
            } else {
                System.out.println("No se pudo asignar el enunciado a la convocatoria.");
            }

        } catch (SQLException e) {
            System.out.println("Error al asignar enunciado a la convocatoria.");
            e.printStackTrace();
        } finally {
              // Cerramos ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en cierre del ResultSet");
            }
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
