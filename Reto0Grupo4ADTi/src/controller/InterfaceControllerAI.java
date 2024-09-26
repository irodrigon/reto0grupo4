/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.UnidadDidactica;

/**
 *
 * @author Iñi
 */
public interface InterfaceControllerAI {
    public ArrayList<UnidadDidactica> getUnidades();
    public ArrayList<String> getEnunciados(int eleccion);
    public void mostra_unidad_enunciado();
}
