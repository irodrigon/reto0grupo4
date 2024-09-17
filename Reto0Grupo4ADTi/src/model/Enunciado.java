/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Iñi
 */
public class Enunciado {
    
    private Integer idE;
    private String descripcion;
    private Dificultad nivel;
    private boolean disponible;
    private String ruta;
    
    public Enunciado(){
    
    }

    public Enunciado(Integer id, String descripcion, Dificultad nivel, boolean disponible, String ruta) {
        this.idE = id;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.disponible = disponible;
        this.ruta = ruta;
    }

    public Integer getId() {
        return idE;
    }

    public void setId(Integer id) {
        this.idE = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getNivel() {
        return nivel;
    }

    public void setNivel(Dificultad nivel) {
        this.nivel = nivel;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    
}
