/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


import controller.ControllerAI;
import controller.InterfaceControllerAI;
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
        
        
        
        do{
            option = menu();
            
            switch(option){
                
                case 1:
                    int choice = 0;
                    
                    do {
                        choice = menu2();
                        
                        switch(choice){
                            
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
                        
                    }while(choice != 3);
                    break;
                
                case 2:
                    crearEnunciadoExamen();
                    break;
                
                case 3:
                    consultarEnunciadoPorUnidad();
                    break;
                
                case 4:
                    consultarConvocatoriaPorEnunciado();
                    break;
                
                case 5:
                    visualizarDocumentoTexto();
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

    private static void crearEnunciadoExamen() {
        
    }

    private static void consultarEnunciadoPorUnidad() {
        InterfaceControllerAI c = new ControllerAI();
        c.mostra_unidad_enunciado();
    }

    private static void consultarConvocatoriaPorEnunciado() {
        
    }

    private static void visualizarDocumentoTexto() {
        
    }

    private static void asignarEnunciadoConvocatoria() {
        
    }
    
}
