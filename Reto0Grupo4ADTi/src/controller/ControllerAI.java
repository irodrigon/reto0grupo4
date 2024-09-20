/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.UnidadDidactica;
import utils.Util;

/**
 *
 * @author Iñi
 */
public class ControllerAI implements InterfaceControllerAI{
    
    private Connection connection;
    private PreparedStatement statement;
    final String OBTENERunidades = "select * from unidaddidactica;";
    final String OBTENERenunciados = "SELECT e.descripcion FROM enunciado e JOIN enunciado_unidaddidactica eu ON e.idE = eu.idE WHERE eu.idUD = ?;";
    
    
    @Override
    public ArrayList<String> getEnunciados(int eleccion) {
        ArrayList<String> enunciados = new ArrayList();
        ResultSet rs = null;
        
         this.openConnection();
         
         try {
            statement = connection.prepareStatement(OBTENERenunciados);
            statement.setString(1, Integer.toString(eleccion));
            
            rs = statement.executeQuery();
            while(rs.next()) {
                String descripcion = rs.getString("descripcion");
                               
                enunciados.add(descripcion);
            }

        }catch (SQLException e) {
                e.printStackTrace();
        } finally {

            this.closeConnection();
        }
        return enunciados;
    }
    
    @Override
    public ArrayList <UnidadDidactica> getUnidades() {
        ArrayList<UnidadDidactica> unidades = new ArrayList();
        ResultSet rs = null;
       
        
       this.openConnection();
        
       try {
            statement = connection.prepareStatement(OBTENERunidades);

            rs = statement.executeQuery();
            while(rs.next()) {
                UnidadDidactica unidad = new UnidadDidactica();
                
                unidad.setIdUd(rs.getInt("idUD"));
                unidad.setAcronimo(rs.getString("acronimo"));
                unidad.setDescripcion(rs.getString("descripcion"));
                unidad.setTitulo(rs.getString("descripcion"));
                unidad.setEvaluacion(rs.getString("evaluacion"));
                
                unidades.add(unidad);
            }

        }catch (SQLException e) {
                e.printStackTrace();
        } finally {

            this.closeConnection();
        }
        return unidades;
    }

        private void openConnection() {
            try {
             String url = "jdbc:mysql://localhost:3306/examendb?serverTimezone=Europe/Madrid&useSSL=false";
                    //String url = "jdbc:mysql:sql101.infinityfree.com:3306/if0_36170837_usurios";
                    connection = DriverManager.getConnection(url, "root", "1234");
                    //System.out.println("Open Connection");
            } catch (SQLException e) {
                    e.printStackTrace();
            }
	}

	private void closeConnection() {
            try {
                    if (statement != null)
                            statement.close();
                    if (connection != null)
                            connection.close();
                    //System.out.println("Close Connection");
            } catch (SQLException e) {
                    e.printStackTrace();
            }
	}

    @Override
    public void mostra_unidad_enunciado() {
        ArrayList<UnidadDidactica> unidades = getUnidades();
        
        int eleccion, max = 0;
        
        for(UnidadDidactica unidad : unidades){
            
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-5s %-9s %-35s %-15s %-10s %n",
                    "IDUD","ACRONIMO","TITULO","EVALUACION","DESCRIPCION");
            System.out.printf("%-5d %-9s %-35s %-15s %-10s %n", 
                    unidad.getIdUd(),unidad.getAcronimo(),unidad.getTitulo(),unidad.getEvaluacion(),unidad.getDescripcion());
            System.out.println("--------------------------------------------------------------------------");
            max += 1;
        }
        System.out.println("\nIntroduce el ID de la unidad didactica deseada");
        eleccion = Util.leerInt(1,max);
        
        ArrayList<String> enunciados = getEnunciados(eleccion);
        
        System.out.println("\nLos enunciados relacioanados son:");
        
        for(String enunciado : enunciados){
            System.out.println(enunciado+"\n");
            
        }
    }

    
}
