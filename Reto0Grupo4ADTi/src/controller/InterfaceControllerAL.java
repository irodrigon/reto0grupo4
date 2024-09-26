/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.ConvocatoriaExamen;

/**
 *
 * @author Alin
 */
public interface InterfaceControllerAL {
public ArrayList<ConvocatoriaExamen> consultarConvocatoriaEnunciado(Integer idE);
    public ConvocatoriaExamen nuevaConvocatoria();
    public void crearConvocatoria();
}
