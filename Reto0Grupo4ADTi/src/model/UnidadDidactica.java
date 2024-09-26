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
public class UnidadDidactica {
    
    private Integer idUd;
    private String acronimo;
    private String titulo;
    private String evaluacion;
    private String descripcion;
    
    public UnidadDidactica(){
    
    }

    public UnidadDidactica(Integer idUd, String acronimo, String titulo, String evaluacion, String descripcion) {
        this.idUd = idUd;
        this.acronimo = acronimo;
        this.titulo = titulo;
        this.evaluacion = evaluacion;
        this.descripcion = descripcion;
    }
<<<<<<< HEAD
    
=======
<<<<<<< HEAD
    
=======
    public UnidadDidactica(){
        
    }
>>>>>>> main
>>>>>>> main
    public Integer getIdUd() {
        return idUd;
    }

    public void setIdUd(Integer idUd) {
        this.idUd = idUd;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "UnidadDidactica{" + "idUd=" + idUd + ", acronimo=" + acronimo + ", titulo=" + titulo + ", evaluacion=" + evaluacion + ", descripcion=" + descripcion + '}';
    }
<<<<<<< HEAD
}
=======
    
    
    
<<<<<<< HEAD
}
=======
}
>>>>>>> main
>>>>>>> main
