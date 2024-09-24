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
    public UnidadDidactica newUD();
    public void crearUD();
    public void crearConvocatoria();
    public ConvocatoriaExamen newConvocatoria();
    public Enunciado newEnunciado();
    public void crearEnunciado(Enunciado enunciado);
    public void agregarUnidadesDidacticas(Enunciado enunciado);
    public void actualizaTablaEnunciado_Unidad(int idE, int idUd);
    public void asociaEnunciado(Enunciado enunciado);
    public ArrayList<UnidadDidactica> getUnidades();
    public ArrayList<String> getEnunciados(int eleccion);
    public void mostra_unidad_enunciado();
    public ArrayList<ConvocatoriaExamen> convocatoriaEnunciadoPorId(Integer idE);
    public String rutaArchivoEnunciadoPorId(Integer idE);
    public void asignarEnunciado();
}
