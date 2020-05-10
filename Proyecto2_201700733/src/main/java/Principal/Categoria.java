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
public class Categoria {

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

    public Categoria(String nombreCategoria, ArbolB arbolLibro) {
        this.nombreCategoria = nombreCategoria;
        this.arbolLibro = arbolLibro;
    }
    String nombreCategoria;
    ArbolB arbolLibro;
}
