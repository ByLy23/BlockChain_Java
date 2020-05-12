/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import EDD.ArbolB;
import EDD.arbolAVL;
import EDD.tablaHash;

/**
 *
 * @author byron
 */
public class Clases_Estaticas {
    public  arbolAVL arbolito;
    public static tablaHash tablita;
    public  ArbolB bTree;//comentar esta, porque solo v a ir AVL y HAsh
    public Clases_Estaticas(){
        arbolito= new arbolAVL();
        bTree= new ArbolB(2);
        tablita= new tablaHash();
    }
}
