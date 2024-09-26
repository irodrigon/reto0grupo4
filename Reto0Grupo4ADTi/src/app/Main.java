/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

<<<<<<< HEAD
import controller.Controller;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import model.Enunciado;
=======

<<<<<<< HEAD
import controller.ControllerAr;
=======
import controller.ControllerAI;
import controller.InterfaceControllerAI;
>>>>>>> main
>>>>>>> main
import utils.Util;

/**
 *
 * @author Iñi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        int option = 0;

        Controller c = new Controller();

        do {
            option = menu();

            switch (option) {

                case 1:
                    int choice = 0;

                    do {
                        choice = menu2();

                        switch (choice) {

                            case 1:
                                crearUnidad(c);
                                break;

                            case 2:
                                crearConvocatoria(c);
                                break;

                            default:
                                System.out.println("Por favor elija una opcion valida.");
                                break;

                        }

                    } while (choice != 3);
                    break;

                case 2:
                    crearEnunciadoExamen(c);
                    break;

                case 3:
                    consultarEnunciadoPorUnidad(c);
                    break;

                case 4:
                    consultarConvocatoriaPorEnunciado(c);
                    break;

                case 5:
                    visualizarDocumentoTexto(c);
                    break;

                case 6:
                    asignarEnunciadoConvocatoria(c);
                    break;

                case 7:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Por favor elija una opcion disponible.");
                    break;
            }

        } while (option != 7);
    }

    private static int menu() {

        System.out.println("************************GESTION DE EXAMENES**************************");
        System.out.println("1. Crear una unidad didactica o una convocatoria de examen:");
        System.out.println("2. Crear un enunciado de examen:");
        System.out.println("3. Consultar los enunciados de examen por unidad didactica:");
        System.out.println("4. Consultar convocatorias por enunciado:");
        System.out.println("5. Visualizar el documento de texto asociado a un enunciado:");
        System.out.println("6. Asignar un enunciado a una convocatoria:");
        System.out.println("7. Salir");
        System.out.println("Introduce una opcion:");

        return Util.leerInt(1, 7);

    }

    private static int menu2() {

        System.out.println("************************SUBMENU DE ELECCION**************************");
        System.out.println("1. Crear unidad didactica:");
        System.out.println("2. Crear convocatoria de examen:");
        System.out.println("3. Volver al menu anterior:");

        return Util.leerInt(1, 3);

    }

<<<<<<< HEAD
    private static void crearUnidad(Controller c) {
        c.crearUD();
=======
    private static void crearUnidad() {
      ControllerAr c = new ControllerAr();
      c.crearUD();
>>>>>>> main
    }

    private static void crearConvocatoria(Controller c) {
        c.crearConvocatoria();
    }

    private static void crearEnunciadoExamen(Controller c) {
        Enunciado enunciado = c.newEnunciado();
        c.crearEnunciado(enunciado);
        c.agregarUnidadesDidacticas(enunciado);
        c.asociaEnunciado(enunciado);
    }

    private static void consultarEnunciadoPorUnidad(Controller c) {
        c.mostra_unidad_enunciado();
    }

    private static void consultarConvocatoriaPorEnunciado(Controller c) {
        System.out.println("Introduzca el ID del enunciado:");
        int idE = Util.leerInt();
        System.out.println("Convocatorias con el enunciado: " + idE);
        c.convocatoriaEnunciadoPorId(idE);
    }

    private static void visualizarDocumentoTexto(Controller c) {
        String ruta = "";
        Enunciado enunciado = null;
        System.out.println();
        System.out.println("Introduzca el id del enunciado:");
        ruta = c.rutaArchivoEnunciadoPorId(Util.leerInt()); 
        if (ruta != null && !ruta.isEmpty()) {
            try {
                File archivo = new File(System.getProperty("user.dir") + "/src/enunciados/" + ruta);
                if (archivo.exists() && Desktop.isDesktopSupported()) {
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
    }

    private static void asignarEnunciadoConvocatoria(Controller c) {
        c.asignarEnunciado();
    }

}
