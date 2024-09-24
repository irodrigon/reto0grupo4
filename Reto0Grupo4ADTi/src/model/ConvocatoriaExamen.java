/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Iñi
 */
public class ConvocatoriaExamen {
    
    private String convocatoria;
    private String descripcion;
    private LocalDate fecha;
    private String curso;
    
    public ConvocatoriaExamen(){
    
    }
    
    public ConvocatoriaExamen(String convocatoria, String descripcion,LocalDate fecha,String curso){
        this.convocatoria=convocatoria;
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.curso=curso;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
