/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Enunciado;



/**
 *
 * @author Iñi
 */
public interface InterfaceController {
    public Boolean crearEnunciado(Enunciado enunciado);
    public String rutaEnunciadoPorId(Integer idE);
}
