/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.ConvocatoriaExamen;
import model.Enunciado;
import model.UnidadDidactica;



/**
 *
 * @author Iñi
 */
public interface InterfaceController {
    public UnidadDidactica newUD(int idUd);
    public void crearUD(int idUd);
    public void crearConvocatoria(String convocatoria);
    public ConvocatoriaExamen newConvocatoria(String convocatoria);
    public Enunciado newEnunciado(int idE);
    public void crearEnunciado(Enunciado enunciado);
    public void agregarUnidadesDidacticas(Enunciado enunciado);
    public void actualizaTablaEnunciado_Unidad(int idE, int idUd);
    public void asociaEnunciado(Enunciado enunciado);
    public ArrayList<UnidadDidactica> getUnidades();
    public ArrayList<String> getEnunciados(int eleccion);
    public void mostrar_unidad_enunciado();
    public ArrayList<ConvocatoriaExamen> convocatoriaEnunciadoPorId(Integer idE);
    public String rutaArchivoEnunciadoPorId(Integer idE);
    public void asignarEnunciado();
    public void mostrar_unidades_didacticas();
    public boolean comprobarUnidadDidactica(int idUD);
    public ArrayList<ConvocatoriaExamen> getConvocatorias();
    public void mostrarConvocatoriasExamen();
    public boolean comprobarConvocatoria(String convocatoria);
    public ArrayList<Enunciado> todosLosEnunciados();
    public void mostrarEnunciados();
    public boolean comprobarEnunciado(int idE);
}
