/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.ExcepcionComprobarDificultad;
import java.sql.Connection;
import java.sql.DriverManager;
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

    private Connection connectionToDatabase;

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
    private final String OBTENERconvocatorias = "select * from convocatoriaexamen";
    private final String getEnunciados = "select * from enunciado";

    @Override
    public Enunciado newEnunciado(int idE) {
        Boolean dificultadValida = false;
        char eleccion = 0;
        char eleccion2 = 0;
        String descripcion = "";
        String nivel = "";
        boolean disponible = true;
        String ruta = "";
        Enunciado enunciado = new Enunciado();
        System.out.println("\n**************************CREANDO ENUNCIADOS**************************");
        System.out.println("\nIntroduce la descripcion del enunciado:");
        descripcion = Util.introducirCadena();
        while (!dificultadValida) {
            System.out.println("\nIntroduce el nivel de dificultad del enunciado(ALTA/MEDIA/BAJA):");
            nivel = Util.introducirCadena();
            if (nivel.equalsIgnoreCase("ALTA") || nivel.equalsIgnoreCase("MEDIA") || nivel.equalsIgnoreCase("BAJA")) {
                dificultadValida = true;
            } else {
                ExcepcionComprobarDificultad excepcionComprobarDificultad = new ExcepcionComprobarDificultad(nivel);
                excepcionComprobarDificultad.mostrarMensajeIncorrecto();
            }
        }
        System.out.println("\nIntroduce si esta disponible o no(S/N):");
        eleccion = Util.leerChar('S', 'N');
        if (eleccion == 'S') {
            disponible = true;
        } else {
            disponible = false;
        }
        System.out.println("\nIntroduce la ruta del enunciado:");
        ruta = Util.introducirCadena();

        return new Enunciado(idE,descripcion,nivel,disponible,ruta);
    }

    @Override
    public void crearEnunciado(Enunciado enunciado) {
        
        this.openConnection();

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
            this.closeConnection();

        }

        System.out.println("\nEnunciado creado correctamente.");

    }

    @Override
    public String rutaArchivoEnunciadoPorId(Integer idE) {
        
        this.openConnection();

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

            this.closeConnection();

        }

        return ruta;

    }

    @Override
    public UnidadDidactica newUD(int idUd){
        
        String acronimo;
        String titulo;
        String evaluacion;
        String descripcion;

        System.out.println("Nueva unidad didáctica:");

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
    public void crearUD(int idUd) {
        UnidadDidactica unidadDidactica = newUD(idUd);
        
        this.openConnection();

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
            this.closeConnection();

        }
        System.out.println("\nUnidad didáctica creada correctamente.\n");

    }

    @Override
    public ArrayList<ConvocatoriaExamen> convocatoriaEnunciadoPorId(Integer idE) {
        
        this.openConnection();

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
            if(convocatorias.isEmpty()){
                System.out.println("No existen convocatorias con ese enunciado.");
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

            this.closeConnection();

        }

        return convocatorias;
    }

    @Override
    public ConvocatoriaExamen newConvocatoria(String convocatoria) {
        String descripcion;
        LocalDate fecha;
        String curso;

        System.out.println("Nueva convocatoria de examen:");

        System.out.println("\nIntroduzca la descripcion a esta convocatoria de examen:");
        descripcion = Util.introducirCadena();

        System.out.println("\nIntroduzca la fecha de esta convocatoria de examen(dd/MM/yyyy):");
        fecha = Util.leerFechaDMA();

        System.out.println("\nIntroduzca el curso a esta convocatoria de examen:");
        curso = Util.introducirCadena();

        return new ConvocatoriaExamen(convocatoria, descripcion, fecha, curso);
    }

    @Override
    public void crearConvocatoria(String convocatoria) {
        ConvocatoriaExamen convocatoriaExamen = newConvocatoria(convocatoria);
        
        this.openConnection();

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
            this.closeConnection();
        }
        System.out.println("\nConvocatoria de examen creada correctamente.\n");
    }

    @Override
    public ArrayList<String> getEnunciados(int eleccion) {
        
        this.openConnection();

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
            
            this.closeConnection();
        }

        return enunciados;
    }

    @Override
    public ArrayList<UnidadDidactica> getUnidades() {
        
        this.openConnection();

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

            this.closeConnection();
        }

        return unidades;
    }

    @Override
    public void mostrar_unidad_enunciado() {

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
    public void actualizaTablaEnunciado_Unidad(int idE, int idUd) {
        
        this.openConnection();

        try {
            stmt = connectionToDatabase.prepareStatement(AGREGAR_UNIDADES_DIDACTICAS);
            stmt.setInt(1, idE);
            stmt.setInt(2, idUd);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
        System.out.println("\nUnidades didacticas agregadas correctamente.\n");
    }

    @Override
    public void agregarUnidadesDidacticas(Enunciado enunciado) {
        char eleccion = 0;
        System.out.println("\n**************************AGREGANDO UNIDADES DIDACTICAS**************************");
        ArrayList<UnidadDidactica> unidades = getUnidades();

        int max = 0;

        for (UnidadDidactica unidad : unidades) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-5s %-9s %-35s %-15s %-10s %n",
                    "IDUD:", "ACRONIMO:", "TITULO:", "EVALUACION:", "DESCRIPCION:");
            System.out.printf("%-5d %-9s %-35s %-15s %-10s %n",
                    unidad.getIdUd(), unidad.getAcronimo(), unidad.getTitulo(), unidad.getEvaluacion(), unidad.getDescripcion());
            System.out.println("--------------------------------------------------------------------------");
            max += 1;
        }

        int idUd = 0;

        do {
            System.out.println("Seleccione la unidad didactica que desea agregar al enunciado:");
            idUd = Util.leerInt(1, max);
            actualizaTablaEnunciado_Unidad(enunciado.getId(), idUd);
            System.out.println("¿Desea agregar mas unidades didacticas?");
            eleccion = Util.leerChar('S', 'N');
        } while (eleccion == 'S');
    }

    @Override
    public void asignarEnunciado() {
        
        this.openConnection();

        ResultSet rs = null;

        try {
            stmt = connectionToDatabase.prepareStatement(GETconvocatoria);
            rs = stmt.executeQuery();
            ArrayList<String> convocatorias = new ArrayList<>();
            
            while (rs.next()) {
                String convocatoria = rs.getString("convocatoria");
                convocatorias.add(convocatoria);
            }

            if (convocatorias.isEmpty()) {
                System.out.println("No hay convocatorias sin enunciado. Crea mas convocatorias.");
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

            this.closeConnection();
        }

    }

    @Override
    public void asociaEnunciado(Enunciado enunciado) {
        
        this.openConnection();

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
                System.out.println("No hay convocatorias sin enunciado. Crea mas convocatorias.");
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

            this.closeConnection();
        }

    }

    private void openConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/examendb";

            connectionToDatabase = DriverManager.getConnection(url, "root", "abcd*1234");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (connectionToDatabase != null) {
                connectionToDatabase.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void mostrar_unidades_didacticas(){
        ArrayList<UnidadDidactica> unidades = getUnidades();
        
        System.out.println("-------------------UNIDADES DIDACTICAS YA EXISTENTES----------------------");

        for (UnidadDidactica unidad : unidades) {

            System.out.println("----------------------------------------------------------------------");
            System.out.printf("%-5s %-9s %-35s %-15s %-10s %n",
                    "IDUD:", "ACRONIMO:", "TITULO:", "EVALUACION:", "DESCRIPCION:");
            System.out.printf("%-5d %-9s %-35s %-15s %-10s %n",
                    unidad.getIdUd(), unidad.getAcronimo(), unidad.getTitulo(), unidad.getEvaluacion(), unidad.getDescripcion());
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    
    @Override
    public boolean comprobarUnidadDidactica(int idUD){
        ArrayList<UnidadDidactica> unidades = getUnidades();
        for (UnidadDidactica unidad : unidades){
            if(unidad.getIdUd() == idUD){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArrayList<ConvocatoriaExamen> getConvocatorias(){
        this.openConnection();

        ArrayList<ConvocatoriaExamen> convocatorias = new ArrayList();
        ResultSet rs = null;

        try {

            stmt = connectionToDatabase.prepareStatement(OBTENERconvocatorias);

            rs = stmt.executeQuery();
            while (rs.next()) {
                ConvocatoriaExamen convocatoria = new ConvocatoriaExamen();

                convocatoria.setConvocatoria(rs.getString("convocatoria"));
                convocatoria.setDescripcion(rs.getString("descripcion"));
                convocatoria.setFecha(rs.getDate("fecha").toLocalDate());
                convocatoria.setCurso(rs.getString("curso"));
      
                convocatorias.add(convocatoria);
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

            this.closeConnection();
        }

        return convocatorias;
    }
    
    
    @Override
    public void mostrarConvocatoriasExamen(){
        ArrayList<ConvocatoriaExamen> convocatorias = getConvocatorias();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        System.out.println("-------------------CONVOCATORIAS YA EXISTENTES----------------------------");

        for (ConvocatoriaExamen convocatoria : convocatorias) {

            System.out.println("----------------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-15s %-15s %n",
                    "CONVOCATORIA:", "DESCRIPCION:", "FECHA:", "CURSO:");
            System.out.printf("%-15s %-20s %-15s %-15s %n",
                    convocatoria.getConvocatoria(), convocatoria.getDescripcion(), convocatoria.getFecha().format(formato), convocatoria.getCurso());
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    
    @Override
    public boolean comprobarConvocatoria(String convocatoria){
        ArrayList<ConvocatoriaExamen> convocatorias = getConvocatorias();
        for (ConvocatoriaExamen convocatoria1 : convocatorias){
            if(convocatoria1.getConvocatoria().equalsIgnoreCase(convocatoria)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArrayList<Enunciado> todosLosEnunciados(){
        this.openConnection();

        ArrayList<Enunciado> enunciados = new ArrayList();
        ResultSet rs = null;

        try {

            stmt = connectionToDatabase.prepareStatement(getEnunciados);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Enunciado enunciado = new Enunciado();

                enunciado.setId(rs.getInt("idE"));
                enunciado.setDescripcion(rs.getString("descripcion"));
                enunciado.setNivel(rs.getString("nivel"));
                enunciado.setDisponible(rs.getBoolean("disponible"));
                enunciado.setRuta(rs.getString("ruta"));
      
                enunciados.add(enunciado);
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

            this.closeConnection();
        }

        return enunciados;
    }
    
    @Override
    public void mostrarEnunciados(){
        ArrayList<Enunciado> enunciados = todosLosEnunciados();
        
        System.out.println("-------------------Enunciados YA EXISTENTES----------------------------");

        for (Enunciado enunciado : enunciados) {

            System.out.println("----------------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-15s %-15s %-20s %n",
                    "IDE:", "DESCRIPCION:", "DIFICULTAD:", "DISPONIBLE:","RUTA");
            System.out.printf("%-15s %-20s %-15s %-15s %-20s %n",
                    enunciado.getId(), enunciado.getDescripcion(), enunciado.getNivel(), enunciado.isDisponible()? "si": "no", enunciado.getRuta());
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    
    @Override
    public boolean comprobarEnunciado(int idE){
        ArrayList<Enunciado> enunciados = todosLosEnunciados();
        for (Enunciado enunciado : enunciados){
            if(enunciado.getId() == idE){
                return true;
            }
        }
        return false;
    }
}
