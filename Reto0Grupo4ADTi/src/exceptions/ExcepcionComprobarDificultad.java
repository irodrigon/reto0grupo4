/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Iñi
 */
public class ExcepcionComprobarDificultad extends Exception{
    
        private String nivel;
    
        public ExcepcionComprobarDificultad(String nivel) {
		super();
		this.nivel = nivel;
	}
	
	public void mostrarMensajeIncorrecto() {
		System.out.println("La dificultad " + nivel +" no existe.");
	}
}
