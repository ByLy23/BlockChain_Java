/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import EDD.ArbolB;

/**
 *
 * @author byron
 */
public class Categoria implements Comparable<Categoria>{

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public ArbolB getArbolLibro() {
        return arbolLibro;
    }

    public void setArbolLibro(ArbolB arbolLibro) {
        this.arbolLibro = arbolLibro;
    }

    public Categoria(String nombreCategoria, ArbolB arbolLibro, int carnet) {
        this.nombreCategoria = nombreCategoria;
        this.arbolLibro = arbolLibro;
        this.carnet= carnet;
    }

    public Categoria() {
        nombreCategoria="";
        arbolLibro= new ArbolB(2);
        carnet=0;
    }
    
    String nombreCategoria;
    ArbolB arbolLibro;
    int carnet;

    public int getCarnet() {
        return carnet;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    @Override
    public int compareTo(Categoria o) {
        int resultado=0;
        if(this.nombreCategoria.compareTo(o.nombreCategoria)<0){resultado=-1;}
        else if(this.nombreCategoria.compareTo(o.nombreCategoria)>0){resultado=1;}
        else{resultado=0;}
        return resultado;
    }
    
    /* int resultado=0;
        if(this.ISBN<o.getISBN()){resultado=-1;}
        else if(this.ISBN>o.getISBN()){resultado=1;}
        else{resultado=0;}
        return resultado;*/
}
