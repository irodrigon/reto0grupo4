/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controller.Controller;
import exceptions.ExcepcionComprobarDificultad;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Enunciado;
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
                                crearUnidad();
                                break;

                            case 2:
                                crearConvocatoria();
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
                    consultarEnunciadoPorUnidad();
                    break;

                case 4:
                    consultarConvocatoriaPorEnunciado();
                    break;

                case 5:
                    visualizarDocumentoTexto(c);
                    break;

                case 6:
                    asignarEnunciadoConvocatoria();
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
        System.out.println("1. Crear una unidad didactica y una convocatoria de examen:");
        System.out.println("2. Crear un enunciado de examen:");
        System.out.println("3. Consultar los enunciados de examen por unidad didactica:");
        System.out.println("4. Consultar convocatorias por enunciado:");
        System.out.println("5. Visualizar el documento de texto asociado a un enunciado:");
        System.out.println("6. Asignar un enunciado a una convocatoria:");
        System.out.println("7. Salir");
        System.out.println("Introduce una opcion:");

        return Util.leerInt();

    }

    private static int menu2() {

        System.out.println("************************SUBMENU DE ELECCION**************************");
        System.out.println("1. Crear unidad didactica:");
        System.out.println("2. Crear convocatoria de examen:");
        System.out.println("3. Volver al menu anterior:");

        return Util.leerInt();

    }

    private static void crearUnidad() {

    }

    private static void crearConvocatoria() {

    }

    private static void crearEnunciadoExamen(Controller c) {
        Enunciado enunciado = rellenarDatosEnunciado();
        c.crearEnunciado(enunciado);
        File fichero = new File(enunciado.getRuta());
        try {
            if (!fichero.exists()) {
                FileOutputStream fileOutputStream = new FileOutputStream(fichero);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(enunciado);
                objectOutputStream.close();
                fileOutputStream.close();
            } else {
                System.out.println("El fichero asociado a este enunciado ya existe.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void consultarEnunciadoPorUnidad() {

    }

    private static void consultarConvocatoriaPorEnunciado() {

    }

    private static void visualizarDocumentoTexto(Controller c) {
        String ruta = "";
        Enunciado enunciado = null;
        System.out.println("Introduzca el id del enunciado:");
        ruta = c.rutaEnunciadoPorId(Util.leerInt());
        File ficheroRuta = new File(ruta);
        ObjectInputStream ois = null;
        try {
            if (ficheroRuta.exists()) {
                ois = new ObjectInputStream(new FileInputStream(ficheroRuta));

                enunciado = (Enunciado) ois.readObject();
                System.out.println("Id del enunciado:" + enunciado.getId());
                System.out.println("Descripcion del enunciado: "+ enunciado.getDescripcion());
                System.out.println("Nivel de dificultad: " + enunciado.getNivel());
                if(enunciado.isDisponible()){
                    System.out.println("Disponible: si");
                }else{
                    System.out.println("Disponible: no");        
                }
            } else {
                System.out.println("El fichero no existe.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void asignarEnunciadoConvocatoria() {

    }

    private static Enunciado rellenarDatosEnunciado() {
        Boolean dificultadValida = false;
        char eleccion = 0;
        String nivel = "";
        Enunciado enunciado = new Enunciado();
        System.out.println("**************************CREANDO ENUNCIADOS**************************");
        System.out.println("Introduce el ID del enunciado:");
        enunciado.setId(Util.leerInt());
        System.out.println("Introduce la descripcion del enunciado:");
        enunciado.setDescripcion(Util.introducirCadena());
        while (!dificultadValida) {
            System.out.println("Introduce el nivel de dificultad del enunciado(ALTA/MEDIA/BAJA):");
            nivel = Util.introducirCadena();
            if (nivel.equals("ALTA") || nivel.equals("MEDIA") || nivel.equals("BAJA")) {
                dificultadValida = true;
                enunciado.setNivel(nivel);
            } else {
                ExcepcionComprobarDificultad excepcionComprobarDificultad = new ExcepcionComprobarDificultad(nivel);
                excepcionComprobarDificultad.mostrarMensajeIncorrecto();
            }
        }
        System.out.println("Introduce si esta disponible o no(S/N):");
        eleccion = Util.leerChar('S', 'N');
        if (eleccion == 'S') {
            enunciado.setDisponible(true);
        } else {
            enunciado.setDisponible(false);
        }
        System.out.println("Introduce la ruta del enunciado:");
        enunciado.setRuta(Util.introducirCadena());

        return enunciado;
    }

}
